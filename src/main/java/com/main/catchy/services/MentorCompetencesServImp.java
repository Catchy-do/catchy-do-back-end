package com.main.catchy.services;

import org.springframework.stereotype.Service;

import com.main.catchy.repository.MentorCompetenceRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MentorCompetencesServImp {
	private final MentorCompetenceRepository manetorCmpDao;

}
