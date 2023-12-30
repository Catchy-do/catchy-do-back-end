package com.main.catchy.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.main.catchy.model.Competence;
import com.main.catchy.repository.CompetenceRepository;
import com.main.catchy.utils.CompetenceBody;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CompetenceSrvImp {
	private final CompetenceRepository cmpDao;

	public Competence getCompetenceByID(Long ID) {
		return cmpDao.findById(ID).get();
	}

	public void addCompetence(CompetenceBody data) {
		var competence = Competence.builder().intitule(data.getIntitule()).build();
		cmpDao.save(competence);
	}

	public List<Competence> getCompetencesList() {
		var cps = cmpDao.findAll();
		return cps;
	}

	public Object addSkill(CompetenceBody data) {
		var skill = Competence.builder().intitule(data.getIntitule()).build();
		cmpDao.save(skill);
		return skill;
	}

}
