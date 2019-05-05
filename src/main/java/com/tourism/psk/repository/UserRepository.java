package com.tourism.psk.repository;

import com.tourism.psk.constants.UserRole;
import com.tourism.psk.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select count(u) > 0 from User u where u.userLogin.username = ?1 or email = ?2")
    boolean exists(String username, String email);
    @Query(value = "select u from User u where u.id = ?1")
    User findById(long id);
    @Transactional
    @Modifying
    @Query(value = "update User u set u.fullname = :fullname, u.email = :email, u.role = :role where u.id = :id")
    void updateById(@Param("id") long id,
                    @Param("fullname") String fullname,
                    @Param("email") String email,
                    @Param("role")UserRole role);
}
