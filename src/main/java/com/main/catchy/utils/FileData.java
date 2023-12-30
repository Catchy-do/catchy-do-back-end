package com.main.catchy.utils;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileData {	
	private String documentType;
	private String userID;
	private MultipartFile[] files;
	
}
