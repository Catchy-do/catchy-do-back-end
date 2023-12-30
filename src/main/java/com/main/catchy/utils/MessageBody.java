package com.main.catchy.utils;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageBody {
private String hashids;
private long msgFrom;
private long msgTo; 
private String message;
private Date date;
private long messageID;





}
