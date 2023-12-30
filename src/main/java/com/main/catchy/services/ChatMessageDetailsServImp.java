package com.main.catchy.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.main.catchy.utils.Responce.UARC;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Transient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.main.catchy.model.ChatMessageDetails;
import com.main.catchy.model.ChatSingleRoom;
import com.main.catchy.model.User;
import com.main.catchy.repository.ChatMessageDetailsRepository;
import com.main.catchy.repository.ChatSingleRoomRepository;
import com.main.catchy.repository.UserRepository;
import com.main.catchy.utils.MessageBody;
import com.main.catchy.utils.Responce.MessageResp;
import com.main.catchy.utils.Responce.Response;


@Service
public class ChatMessageDetailsServImp  {
	@Autowired
	ChatMessageDetailsRepository messageDao;
	@Autowired
	ChatSingleRoomRepository chtRoomDao;
	
	
	UserRepository userDao;
	@Transient
	@Value("${url.notification}")
	private String URL_NOTIF;
	@Value("${message.notification}")
	private String notifMsg;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
 
	
	public Object sendMessage(MessageBody data) {
		User sender = userDao.findUserByID(data.getMsgFrom());
		User receiver = userDao.findUserByID(data.getMsgTo());
		ChatSingleRoom room = chtRoomDao.findById(Long.valueOf(1)).get();
		var msgDets=	 ChatMessageDetails.builder()
		.messagDetails(room)
		.msgFrom(sender.getId())
		.msgTo(receiver.getId())
		.date(new Date(System.currentTimeMillis()))
		.message(data.getMessage()).build();
		messageDao.save(msgDets);
		/// sent notification

		sendNotification("notifID", "message", "name");

		return data;
	}

	public void sendNotification(String notifID, String message, String name) {
		logger.info("sending notification to :" + notifID + "  --Starting");
		message = message.replaceAll("-N-", name);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject personJsonObject = new JSONObject();
		personJsonObject.put("message", message);
		personJsonObject.put("notifid", notifID);
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> notifications = new HttpEntity<String>(personJsonObject.toString(), headers);
		String response = restTemplate.postForObject(URL_NOTIF, notifications, String.class);
		notifMsg = notifMsg.replaceAll(name, "-N-");
		logger.info("Notification has ben send seccssefully");
	}

	
	public Object getRoomMessage(String hashids) {
		List<ChatMessageDetails> msgs = messageDao.findMEssageByhashID(hashids);
		MessageResp result = new MessageResp();
		List<MessageBody> details = new ArrayList<MessageBody>();
		result.setHashids(hashids);
		if (msgs != null && !msgs.isEmpty()) {
			for (int i = 0; i < msgs.size(); i++) {
				MessageBody m = new MessageBody();
				m.setDate(msgs.get(i).getDate());
				m.setMessage(msgs.get(i).getMessage());
				m.setMsgFrom(msgs.get(i).getMsgFrom());
				m.setMsgTo(msgs.get(i).getMsgTo());
				details.add(m);
			}
			result.setDetails(details);
		}

		Response<MessageResp> respense = new Response<MessageResp>();
		respense.setData(result);
		respense.setStatus("200");
		respense.setResp(new UARC(200), HttpStatus.OK);
		logger.info("ChatMessageDetailsServImp::::::methode=  getAllMessage() =====>execute succssefully!");
		return respense;
	}

	
	public Object deletemessage(long messageID) {
		messageDao.deleteMEssageByID(messageID);
		Response<MessageResp> respense = new Response<MessageResp>();
		respense.setStatus("200");
		respense.setResp(new UARC(200), HttpStatus.OK);
		logger.info("ChatMessageDetailsServImp::::::methode=  deletemessage() =====>execute succssefully!");
		return respense;
	}
}
