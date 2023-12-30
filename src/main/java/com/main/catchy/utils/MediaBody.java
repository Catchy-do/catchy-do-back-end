package com.main.catchy.utils;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MediaBody {
private Long id;
private String type;
private String url;
private String intitule;
private String description;
}
