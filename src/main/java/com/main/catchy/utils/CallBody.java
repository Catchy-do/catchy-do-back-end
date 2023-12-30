package com.main.catchy.utils;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CallBody {
    private String message;
    private String notifid;
    private long appointmentID;
    private long monitorID;
    private long menteeID;


}
