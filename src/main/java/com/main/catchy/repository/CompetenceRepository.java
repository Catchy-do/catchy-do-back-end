package com.main.catchy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.catchy.model.Competence;

@Repository
public interface CompetenceRepository extends JpaRepository<Competence, Long> {

}
