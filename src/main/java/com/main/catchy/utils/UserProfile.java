package com.main.catchy.utils;

import lombok.*;
import org.apache.commons.codec.binary.Base32OutputStream;

import java.util.List;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
		private long userId;
		private String firstName;
		private String lastName;
		private String email;
		private String phoneNumber;
		private String username;
		private String password;
		private String countryName;
		private String cin;
		private String age;
		private String sexe;
		private String niveau;
		private Boolean isMentor;
		private String adresse;
		private String DateOfBirth;
		private String notifID;
		private String ville;
		private String region;
		private String status;
		private String active;
		private Long regionID;
		private Long villeID;
		private Long paysID;
		private List<Long> competencesId;
		private String startHour;
		private String endHour;
		private String biographie;
		private String zipCode;
		private String imgUrl;
		private Double lat;
		private Double lng;
		private Boolean isConfirmed;
	//	private String role;
		private List<String>  role;
		private List<CompetenceBody> competences;
		private List<MediaBody> media;	
}
