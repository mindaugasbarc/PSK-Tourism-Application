package com.tourism.psk.repository;

import com.tourism.psk.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select u from User u where u.username = ?1")
    User findByUsername(String username);
    @Query(value = "select count(u) > 0 from User u where username = ?1 or email = ?2")
    boolean exists(String username, String email);
}
