package com.main.catchy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.main.catchy.model.ChatSingleRoom;

public interface ChatSingleRoomRepository extends JpaRepository<ChatSingleRoom, Long> {

	ChatSingleRoom findByhashids(String hashids);
	
}
