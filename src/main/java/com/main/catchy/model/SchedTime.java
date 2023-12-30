package com.main.catchy.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "SchedTime")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@schedTimeId")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchedTime implements Serializable {

	private static final long serialVersionUID = -6123703288245602356L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "schedTimeId")
	private Long schedTimeId;
	@Column(name = "eTime")
	private String eTime;
	@Column(name = "sTime")
	private String sTime;
	@ManyToOne
	@JoinColumn(name = "dayId")
	Days day;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "contactId")
	private Contact contact;
}
