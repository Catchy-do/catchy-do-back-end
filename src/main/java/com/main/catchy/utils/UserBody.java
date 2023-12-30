package com.main.catchy.utils;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBody {
	private long userID;
	private String newPassword;
	private String phoneNumber;
	private String code;
	
}
