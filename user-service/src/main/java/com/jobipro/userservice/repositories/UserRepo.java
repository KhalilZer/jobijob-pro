package com.jobipro.userservice.repositories;

import com.jobipro.userservice.enteties.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByKeycloakId(String keycloakId);

    Optional<User> findByPublicId(String publicId);

    boolean existsByKeycloakId(String keycloakId);
}
