package com.main.catchy.utils;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomListResp {
private String hashids;
private Date date;
private String senderName;
private String message;
private String senderImg;
private String statut;
private long receivedID;
private String receiverImg;
private String receiverName;





}
