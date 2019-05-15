package com.tourism.psk.repository;

import com.tourism.psk.model.OfficeRoom;
import com.tourism.psk.model.OfficeRoomOccupation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface OfficeRoomOccupationRepository extends JpaRepository<OfficeRoomOccupation, Long> {
    @Query(value = "select o from OfficeRoomOccupation o " +
            "where o.officeRoom.id = :roomId and o.start <= :finish and o.end >= :start " +
            "order by o.end asc")
    List<OfficeRoomOccupation> getOfficeRoomOccupationsInPeriod(@Param("roomId") long roomId,
                                                                @Param("start") Date start,
                                                                @Param("finish") Date end);
    @Query(value = "select distinct o.officeRoom.id from OfficeRoomOccupation o " +
            "where o.officeRoom.id in :ids and o.start <= :finish and o.end >= :start")
    List<Long> getOccupiedRooms(@Param("ids") List<Long> ids,
                                             @Param("start") Date start,
                                             @Param("finish") Date end);
}
