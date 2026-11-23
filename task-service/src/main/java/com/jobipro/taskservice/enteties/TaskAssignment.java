package com.jobipro.taskservice.enteties;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jobipro.taskservice.enums.AssignmentType;
import com.jobipro.taskservice.enums.TaskAssignmentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "task_assignments",
        indexes = {
                @Index(name = "idx_assignment_task_status", columnList = "task_id,status"),
                @Index(name = "idx_assignment_worker_status", columnList = "worker_public_id,status")
        }
)
@Getter
@Setter
public class TaskAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "public_id", unique = true, nullable = false)
    private String publicId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @Column(name = "worker_id", nullable = false)
    private String workerId;

    @Column(name = "initiated_by_id", nullable = false)
    private String initiatedById;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskAssignmentStatus status = TaskAssignmentStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "assignment_type")
    private AssignmentType assignmentType;

    @Column(name = "accepted_at")
    private Instant acceptedAt;

    @Column(name = "rejected_at")
    private Instant rejectedAt;

    @Column(name = "cancelled_at")
    private Instant cancelledAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    public void onCreate() {
        publicId = "ASN@V1@" + UUID.randomUUID();
        createdAt = Instant.now();
        updatedAt = Instant.now();
        this.status = TaskAssignmentStatus.PENDING;
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = Instant.now();
    }
}