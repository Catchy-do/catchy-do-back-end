package com.main.catchy.repository;

import com.main.catchy.model.Days;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DaysRepository extends JpaRepository<Days, Long> {
      Days findByName(String name);
}
