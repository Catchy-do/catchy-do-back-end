package com.main.catchy.services;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.main.catchy.repository.ChatSingleRoomRepository;
import com.main.catchy.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Component
@AllArgsConstructor
public class ChatSingleRoomServImp {

	private final ChatSingleRoomRepository chtRoomDao;

	private final UserRepository userDao;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public String uuidGenerator() {

		UUID uuid = UUID.randomUUID();
		String randomUUIDString = uuid.toString();
		String[] s = randomUUIDString.split("-");
		return s[0];
	}
}
