package com.dt.ducthuygreen.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dt.ducthuygreen.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    Page<User> findUserByDeletedFalse(Pageable pageable);

    Page<User> findUserByDeletedIsFalseAndUsernameContainsOrFullNameContainsAndDeletedIsFalseOrEmailContainsAndDeletedIsFalse(Pageable pageable, String username, String fullName, String email);

    User findUserByEmailAndDeletedFalse(String email);

    User findUserByIdAndDeletedFalse(Long id);

    @Query("SELECT c FROM User c WHERE c.email = ?1")
    public User findByEmail(String email);

    public User findByResetPasswordToken(String token);

}
