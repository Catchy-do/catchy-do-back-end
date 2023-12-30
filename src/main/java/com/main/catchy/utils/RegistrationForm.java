package com.main.catchy.utils;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationForm {

	private String description;
	private String type;
	private Long userID;
	private MultipartFile[] files;



}
