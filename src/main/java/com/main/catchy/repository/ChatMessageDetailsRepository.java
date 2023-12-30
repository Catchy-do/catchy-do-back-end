package com.main.catchy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.main.catchy.model.ChatMessageDetails;

@Repository
public interface ChatMessageDetailsRepository extends JpaRepository<ChatMessageDetails, Long>{
	@Query("select m from ChatMessageDetails m INNER JOIN ChatSingleRoom r"
			+ " on r.roomId=m.messagDetails.roomId where r.hashids like :hashids ORDER BY m.date ASC")
	List<ChatMessageDetails> findMEssageByhashID(@Param("hashids") String hashids);
	@Modifying
	@Transactional()
	@Query("DELETE FROM ChatMessageDetails m   where m.messageId like :messageID ")
	void deleteMEssageByID(@Param("messageID")long messageID);

}
