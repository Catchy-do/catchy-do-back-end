package com.main.catchy.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "Pays")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@pkPaysID")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pays implements Serializable {
	private static final long serialVersionUID = -6123703288245602356L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pkPaysID")
	private long pkPaysID;

	@Column(name = "cca2")
	private String cca2;
	@Column(name = "cca3")
	private String cca3;

	@Column(name = "capital")
	private String capital;

	@Column(name = "subregion")
	private String subregion;

	@Column(name = "NomEng")
	private String NomEng;

	@Column(name = "NomFr")
	private String NomFr;

	@Column(name = "lattitude")
	private float lattitude;

	@Column(name = "longitude")
	private float longitude;
	@Column(name = "phoneCode")
	private int phoneCode;

	@OneToMany(mappedBy = "paysRegion")
	private List<Region> region;

}
