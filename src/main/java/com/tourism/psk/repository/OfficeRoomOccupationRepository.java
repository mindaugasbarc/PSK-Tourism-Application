package com.tourism.psk.repository;

import com.tourism.psk.model.OfficeRoomOccupation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface OfficeRoomOccupationRepository extends JpaRepository<OfficeRoomOccupation, Long> {
    @Query(value = "select o from OfficeRoomOccupation o " +
            "where roomId = :roomId and o.from <= :finish and o.to >= :start " +
            "order by o.from asc")
    List<OfficeRoomOccupation> getOfficeRoomOccupationsInPeriod(@Param("roomId") long roomId,
                                                                @Param("start") Date start,
                                                                @Param("finish") Date end);
}
