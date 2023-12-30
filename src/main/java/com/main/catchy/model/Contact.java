package com.main.catchy.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "contact")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@contactId")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contact implements Serializable {
	private static final long serialVersionUID = -6123703288245602356L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "contactId")
	private long contactId;
	@Column(name = "firstName")
	private String firstName;
	@Column(name = "lastName")
	private String lastName;
	@Column(name = "cin")
	private String cin;
	@Column(name = "email")
	private String email;
	@Column(name = "sexe")
	private String sexe;
	@Column(name = "niveau")
	private String niveau;
	@Column(name = "adresse", columnDefinition = "TEXT")
	private String adresse;
	@Column(name = "age")
	private String age;
	@Column(name = "zipCode")
	private String zipCode;
	@Column(name = "biographie", columnDefinition = "TEXT")
	private String biographie;
	@Column(name = "phoneNumber")
	private String phoneNumber;
	@Column(name = "lat")
	private Double lat;
	@Column(name = "lng")
	private Double lng;
	@Column(name = "notifId")
	private String notifId;
	@Column(name = "fullName")
	private String fullName;
	@Column(name = "DateOfBirth")
	private String DateOfBirth;
	@Column(name = "isMentor")
	private Boolean isMentor;
	@Column(name = "isConfirmed")
	private Boolean isConfirmed;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "villeId")
	private Ville villeContact;

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "usrId" )
	private User usr;

	@OneToMany(mappedBy = "contact" ,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<SchedTime> schedTime;
	@JsonIgnore
	@OneToMany(mappedBy = "mentor" ,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Appointment> mentorAppointment;
	@JsonIgnore
	@OneToMany(mappedBy = "mentee",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Appointment> manteeAppointment;
}
