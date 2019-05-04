package com.tourism.psk.repository;

import com.tourism.psk.model.UserOccupation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface UserOccupationRepository extends JpaRepository<UserOccupation, Long> {
    @Query(value = "select count(o) > 0 from UserOccupation o where o.user.id = ?1 and o.start <= ?3 and o.end >= ?2")
    boolean hasOccupations(long userId, Date start, Date end);
}
