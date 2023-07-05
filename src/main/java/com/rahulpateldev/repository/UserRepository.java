package com.rahulpateldev.repository;

import com.rahulpateldev.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<UserDetails> findUserByUsername(String username);
    Optional<UserDetails> findUserByUsernameOrEmail(String username, String email);
}
