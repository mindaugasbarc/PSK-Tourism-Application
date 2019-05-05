package com.tourism.psk.repository;

import com.tourism.psk.model.UserOccupation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface UserOccupationRepository extends JpaRepository<UserOccupation, Long> {
    @Query(value = "select uo from UserOccupation uo where uo.user.id = :userId and uo.from <= :finish and uo.to >= :start")
    List<UserOccupation> getOccupationsInPeriod(@Param("userId") long userId,
                                                @Param("start") Date start,
                                                @Param("finish") Date end);
}
