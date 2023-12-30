package com.main.catchy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.main.catchy.model.Pays;


public interface PaysRepository extends JpaRepository<Pays, Long> {
	@Query("select p from Pays p where p.cca3 like :cca3")
	public Pays findByCode(@Param("cca3") String code);
	@Query("select p from Pays p where p.cca2 like :cca2")
	public Pays findByCodeCA2(@Param("cca2") String code);

	@Query("select p from Pays p where p.NomFr like :NomFr")
	public Pays findByNomFr(@Param("NomFr") String code);

	@Query("select p from Pays p where p.capital like :capital")
	public Pays findBycapital(@Param("capital") String capital);

}
