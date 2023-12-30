package com.main.catchy.services;

import java.net.MalformedURLException;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import jakarta.persistence.Transient;

@Service
public class ExamenResultServicesImp {


@Transient
@Value("${storage.location}")
private Path filePath;

public Path load(String filename) {
	return filePath.resolve(filename);
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
}
