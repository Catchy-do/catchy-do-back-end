package com.main.catchy.utils;

import com.main.catchy.model.Images;
import jakarta.annotation.Nullable;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentBody {
    private long appointmentId;
    private Long mentorId;
    private Long menteeId;
    private String Status;
    private LocalDateTime creationDate;
    private String scheduledtime;
    private LocalDate scheduledDate;
}
