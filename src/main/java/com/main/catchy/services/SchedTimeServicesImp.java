package com.main.catchy.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.main.catchy.repository.SchedTimeRepository;
import com.main.catchy.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Component
public class SchedTimeServicesImp {
	@Autowired
    SchedTimeRepository schdTDao;

	@Autowired
	UserRepository userRepo;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

}
