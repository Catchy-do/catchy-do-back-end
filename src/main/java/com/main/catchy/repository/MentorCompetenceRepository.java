package com.main.catchy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.main.catchy.model.MentorCompetences;


@Repository
public interface MentorCompetenceRepository extends JpaRepository<MentorCompetences, Long> {
	@Query("select c from MentorCompetences c where c.contactId like :contactId ")
	List<MentorCompetences> findCompetenceByContactID(long contactId);



}
