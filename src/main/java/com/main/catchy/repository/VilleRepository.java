package com.main.catchy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.main.catchy.model.Ville;


public interface VilleRepository extends JpaRepository<Ville, Long>{
	@Query("select v from Ville v where v.villeRegion.name like :name")
	List<Ville> findAllVilleByregionName(@Param("name")String name);
	@Query("select v from Ville v where v.villeID like :villeID")
	Ville findVilleById(@Param("villeID")long villeID);

}
