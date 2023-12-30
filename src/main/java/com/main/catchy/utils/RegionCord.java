package com.main.catchy.utils;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegionCord {
	private long regionID;
	private Double lat;
	private Double lng;
	private  String name;
	
	
	
}
