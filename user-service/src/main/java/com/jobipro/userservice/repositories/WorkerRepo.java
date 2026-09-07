package com.jobipro.userservice.repositories;

import com.jobipro.userservice.enteties.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepo extends JpaRepository<Worker, Long> {
}
