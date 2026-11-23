package com.jobipro.taskservice.repositories;

import com.jobipro.taskservice.enteties.Task;
import com.jobipro.taskservice.enteties.TaskAssignment;
import com.jobipro.taskservice.enums.TaskAssignmentStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface AssignmentRepo extends JpaRepository<TaskAssignment, Long> {
    boolean existsByTaskAndWorkerIdAndStatus(Task task, String workerId, TaskAssignmentStatus status);

    boolean existsByTaskAndStatusIn(Task task, Collection<TaskAssignmentStatus> status);

    Optional<TaskAssignment> findByPublicId(String publicId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT t FROM Task t WHERE t.id = :id")
    Optional<Task> findByIdForUpdate(@Param("id") long id);
}
