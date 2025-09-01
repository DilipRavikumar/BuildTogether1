package com.buildtogether.repository;

import com.buildtogether.entity.Hackathon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HackathonRepository extends JpaRepository<Hackathon, Long> {
    List<Hackathon> findByStartDateAfter(LocalDate date);
    List<Hackathon> findByEndDateBefore(LocalDate date);
    List<Hackathon> findByTitleContainingIgnoreCase(String title);
    
    @Query("SELECT h FROM Hackathon h WHERE h.startDate <= CURRENT_DATE AND h.endDate >= CURRENT_DATE")
    List<Hackathon> findByActiveStatus();
}
