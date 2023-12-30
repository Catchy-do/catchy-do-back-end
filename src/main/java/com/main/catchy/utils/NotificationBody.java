package com.main.catchy.utils;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationBody {
    private Long receiverId;
    private String receiverNtf;
    private String message;
    private Long senderId;
    private String senderNtf;
    private String senderName;
    private String senderEmail;
    private Long appointmentId;
}
