package com.jobipro.taskservice.repositories;

import com.jobipro.taskservice.enteties.Task;
import com.jobipro.taskservice.enums.TaskStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TaskRepo extends JpaRepository<Task, Long> {

    Optional<Task> findByPublicId(String publicId);

    Optional<Task> findByPublicIdAndStatus(String publicId, TaskStatus status);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
                SELECT t
                FROM Task t
                WHERE t.publicId = :publicId
            """)
    Optional<Task> findByPublicIdForUpdate(
            @Param("publicId") String publicId
    );


}
