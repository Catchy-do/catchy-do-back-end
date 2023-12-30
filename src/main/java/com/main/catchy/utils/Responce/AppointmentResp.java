package com.main.catchy.utils.Responce;

import com.main.catchy.utils.UserProfile;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResp {


    private long appointmentId;
    private UserProfile mentor;
    private UserProfile mentee;
    private String Status;
    private LocalDateTime creationDate;
    private String scheduledtime;
    private LocalDate scheduledDate;

}
