package com.main.catchy.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Days")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@dayId")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Days implements Serializable {
	private static final long serialVersionUID = -6123703288245602356L;
	@Id
	@Column(name = "dayId")
	private Long dayId;

	@Column(name = "name")
	private String name;

	@OneToMany(mappedBy = "day")
	List<SchedTime> schedTime;

}
