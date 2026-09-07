package com.jobipro.userservice.repositories;

import com.jobipro.userservice.enteties.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
