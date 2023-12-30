package com.main.catchy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.main.catchy.repository.VilleRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Component
@AllArgsConstructor
public class VilleServicesImp  {
	@Autowired
    VilleRepository villDao;

}
