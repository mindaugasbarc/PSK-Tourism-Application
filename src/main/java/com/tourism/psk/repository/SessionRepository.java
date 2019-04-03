package com.tourism.psk.repository;

import com.tourism.psk.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SessionRepository extends JpaRepository<Session, Long> {
    @Query(value = "select s from Session s where s.token = ?1")
    Session findByToken(String token);
}
