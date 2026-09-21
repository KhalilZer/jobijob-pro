package com.jobipro.userservice.repositories;

import com.jobipro.userservice.enteties.User;
import com.jobipro.userservice.enteties.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkerRepo extends JpaRepository<Worker, Long> {

    Optional<Worker> findByUser(User user);
}
