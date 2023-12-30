package com.main.catchy.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import jakarta.persistence.PostLoad;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.main.catchy.model.Images;
import com.main.catchy.repository.ImagesRepository;
import com.main.catchy.repository.UserRepository;
import com.main.catchy.utils.RegistrationForm;

import jakarta.persistence.Transient;


@Service

public class FileSystemStorageService {

	@Transient
	@Value("${storage.location1}")
	private String filePath;

	@Transient
	@Value("${server.url}")
	private String serverUrl;

	@Autowired
    ImagesRepository imgDao;

	@Autowired
	UserRepository userDao;

	@Transient
	@Value("${url.notification}")
	private String URL_NOTIF;

	@Transient
	@Value("${message.notification}")
	private String notMessage;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final Path rootLocation;

	@Autowired
	public FileSystemStorageService(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}

	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1).filter(path -> !path.equals(this.rootLocation))
					.map(this.rootLocation::relativize);
		} catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}
	}


	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new StorageFileNotFoundException("Could not read file: " + filename);

			}
		} catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	public void init() {
		try {
			Files.createDirectories(rootLocation);
		} catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}

	public void sendNotification(String notifID, String ptName, String message) {
		logger.info("sending notification to :" + ptName + "  --Starting");

		message = message.replaceAll("-N-", ptName);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject personJsonObject = new JSONObject();
		personJsonObject.put("message", message);
		personJsonObject.put("notifid", notifID);
		personJsonObject.put("patientName", ptName);

		RestTemplate restTemplate = new RestTemplate();

		HttpEntity<String> notifications = new HttpEntity<String>(personJsonObject.toString(), headers);
		String response = restTemplate.postForObject(URL_NOTIF, notifications, String.class);
		notMessage = notMessage.replaceAll(ptName, "-N-");

		logger.info("Notification has ben send seccssefully");
	}
	@PostLoad
	public Object storeImage(MultipartFile file, long userID) throws Exception {
		var user = userDao.findUserByID(userID);
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		Path targetLocation = this.rootLocation.resolve(fileName);
		Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		String storagePath = this.rootLocation.toString() + File.separator + fileName;
		String url = (String.format("%s", serverUrl) + "/view/" + "attachement/" + fileName);
		//delete all user image
		var usrImage=imgDao.findAllUserImage(userID,"PIC");
		if(usrImage!=null)
			imgDao.deleteAllInBatch(usrImage);
		var image = Images.builder().imgName(fileName).imgURL(url).user(user).type("PIC").build();
		imgDao.save(image);
		return image;
	}

	public Object storeFile(MultipartFile file, RegistrationForm form) throws Exception {
		var user = userDao.findUserByID(form.getUserID());
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		Path targetLocation = this.rootLocation.resolve(fileName);
		Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		String storagePath = this.rootLocation.toString() + File.separator + fileName;
		String url = (String.format("%s", serverUrl) + "/view/" + "attachement/" + fileName);
		Images files= new Images();	;
		files.setImgURL(url);
		files.setUser(user);
		files.setType(form.getType());
		files.setDescription(form.getDescription());
		imgDao.save(files);
		return "OK";
	}

}
