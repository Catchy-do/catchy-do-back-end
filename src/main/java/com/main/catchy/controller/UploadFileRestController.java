package com.main.catchy.controller;

import com.main.catchy.services.ExamenResultServicesImp;
import com.main.catchy.services.FileSystemStorageService;
import com.main.catchy.services.StorageFileNotFoundException;
import com.main.catchy.utils.FileData;
import com.main.catchy.utils.RegistrationForm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Arrays;

//@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MENTOR') or hasAuthority('MENTEE')")
@CrossOrigin(originPatterns = "*", allowedHeaders = "*")
@RequestMapping("api/")
@RestController
public class UploadFileRestController {
    private final FileSystemStorageService storageService;
    @Autowired
    ExamenResultServicesImp exmResultsrvices;

    @Autowired
    public UploadFileRestController(FileSystemStorageService storageService) {
        this.storageService = storageService;
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }





    @PostMapping("uploadFiles")
    public Object uploadFiles(@ModelAttribute RegistrationForm form, RedirectAttributes redirectAttributes) {
        MultipartFile[] files = form.getFiles();
        Arrays.asList(files).stream().forEach(file -> {
            try {
                Object exres = storageService.storeFile(file, form);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded all files!");
        return null;
    }





    @PostMapping("uploadImage")
    public Object uploadImage(@ModelAttribute FileData form, RedirectAttributes redirectAttributes) {
        MultipartFile[] files = form.getFiles();
        Arrays.asList(files).stream().forEach(file -> {
            try {
                if (files != null) {
                    Object exres = storageService.storeImage(file, Long.parseLong(form.getUserID()));
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        });
        return form.getUserID();
    }




    @GetMapping("view/attachement/{filename}")
    public ResponseEntity<Resource> Fileimage(@PathVariable(name = "filename") String filename,
                                              HttpServletRequest request) {

        Resource resource = exmResultsrvices.loadAsResource(filename);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.err.println("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);

    }

}
