package com.main.catchy.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.main.catchy.utils.UserProfile;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Appointment")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@appointmentId")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment implements Serializable {
    private static final long serialVersionUID = -6123703288245602356L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointmentId")
    private long appointmentId;
    //@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private Contact mentor;
    //@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "mentee_id")
    private Contact mentee;
    @Column(name = "status")
    private String status;
    @Column(name = "creationDate")
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private LocalDateTime creationDate;
    @Column(name = "scheduledtime")
    private String scheduledtime;
    @Column(name = "scheduledDate")
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private LocalDate scheduledDate;


}
