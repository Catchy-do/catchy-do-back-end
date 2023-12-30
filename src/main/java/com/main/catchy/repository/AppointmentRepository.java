package com.main.catchy.repository;

import com.main.catchy.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {


    @Query("select a from Appointment a where a.mentor.contactId like :mentorId ")
    List<Appointment> findAppointmentByMentorId(@Param("mentorId")Long mentorId);

    @Query("select a from Appointment a where a.mentee.contactId like :menteeId ")
    List<Appointment> findBookingsByMenteeId(@Param("menteeId")Long menteeId);
}
