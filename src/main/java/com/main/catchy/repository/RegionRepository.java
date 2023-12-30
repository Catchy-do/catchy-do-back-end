package com.main.catchy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.main.catchy.model.Region;

public interface RegionRepository extends JpaRepository<Region, Long>{
	@Query("select reg from Region reg where reg.paysRegion.capital like :capital")
	List<Region> findALlRegionByCapital(@Param("capital") String capital);

	@Query("select reg from Region reg where reg.regionId like :regionID")
	Region findRegionByID(@Param("regionID") long regionID);

	@Query("select r from Region r where r.name like  :name% ")
	List<Region> findRegionByName(@Param("name") String name);
	@Query("select reg from Region reg where reg.paysRegion.pkPaysID like :pkPaysID")
	List<Region> findALlRegionByPaysID(@Param("pkPaysID")long pkPaysID);
	@Query("select reg from Region reg where reg.paysRegion.NomFr like :name")
	List<Region> findALlRegionByCountryName(String name);

}
