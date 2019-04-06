package com.tourism.psk.repository;

import com.tourism.psk.model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {
    @Query(value = "select ul.user.id from UserLogin ul where username = ?1 and password = ?2")
    Long findUserIdByUsernameAndPassword(String username, String password);
}
