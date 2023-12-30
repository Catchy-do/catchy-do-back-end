package com.main.catchy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.main.catchy.model.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long>{
	@Query("select c from Contact c where c.usr.id like :pkcontactID ")
	Contact finContactBYID(@Param("pkcontactID")long pkcontactID);
	@Modifying
	@Transactional()
	@Query("DELETE  FROM Contact c  where c.contactId like :pkcontactID ")
	void deleteContact(@Param("pkcontactID")long pkcontactID);
	@Query("select c from Contact c where c.phoneNumber like :phoneNumber ")
	Contact checkIfPhoenExist(@Param("phoneNumber")String phoneNumber);

	@Query("select c from Contact c where c.usr.id like :userId ")
	Contact finContactBYUserID(@Param("userId")Long userId);

	@Query("select c from Contact c where c.isMentor =:isMentor ")
	List<Contact> findContactByType(@Param("isMentor")boolean isMentor);

	@Query("select c from Contact c, MentorCompetences m where c.isMentor = TRUE and c.sexe =:sexe " +
			"and c.contactId = m.contactId and m.competenceId in :cID ")
	List<Contact> findMentors(@Param("sexe")String sexe, @Param("cID")List<Long> cID);




}
