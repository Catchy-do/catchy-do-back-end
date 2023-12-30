package com.main.catchy.repository;

import com.main.catchy.model.SchedTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface SchedTimeRepository extends JpaRepository<SchedTime, Long> {
    @Query(" select s from SchedTime s where s.contact.contactId like :contactId")
    List<SchedTime> findSchedTimeByContactId(Long contactId);
}
