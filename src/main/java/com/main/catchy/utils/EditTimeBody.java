package com.main.catchy.utils;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditTimeBody {
private long appointmentID;
private String date;
private String type;
private String status;

}
