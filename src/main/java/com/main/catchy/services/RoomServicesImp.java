package com.main.catchy.services;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.main.catchy.model.Room;
import com.main.catchy.repository.RoomRepository;
import com.main.catchy.utils.RoomBody;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Component
@AllArgsConstructor
public class RoomServicesImp {
	@Autowired
    RoomRepository rmDao;

	public static String uuidGenerator() {

		UUID uuid = UUID.randomUUID();
		String randomUUIDString = uuid.toString();

		return randomUUIDString;
	}

	public static String getRandomNumberString() {

		Random rnd = new Random();
		int number = rnd.nextInt(999999);
		// this will convert any number sequence into 6 character.
		return String.format("%06d", number);
	}

	public Room getRoomByName(String rmCode) {
		return rmDao.findById(Long.valueOf(1)).get();
	}

	public List<Room> getLastRoomeCreated(String ptPhone, String smsCode) {
		List<Room> room = rmDao.findAll();
		return room;
	}

	public List<Room> getAllRoom() {
		return rmDao.findAll();
	}

	public Object createRoom(RoomBody rmBody) {
		Room room = new Room();
		String rmCode = uuidGenerator();
		String smsCode = getRandomNumberString();
		room.setRmCode(rmCode);
		rmDao.save(room);
		// envoi du sms to patient

		return room;
	}

}
