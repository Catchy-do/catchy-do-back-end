package com.main.catchy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.main.catchy.model.Images;


public interface ImagesRepository extends JpaRepository<Images, Long> {
	
	@Transactional
	@Modifying
	@Query("DELETE  FROM Images img  where img.user.id like :id")
	void deleteImages(@Param("id")long id);
	@Query("select img  FROM Images img  where img.user.id like :id")
	List<Images> findAllUserFile(long id);
	@Query("select img  FROM Images img  where img.user.id like :id and img.type like :type")
	List<Images> findAllUserImage(long id,String type);

	@Query("select img.imgURL  FROM Images img  where img.user.id like :id ")
	String findUserImage(long id);
}
