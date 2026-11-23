package com.jobipro.taskservice.enteties;

import com.jobipro.taskservice.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SoftDelete(columnName = "deleted")
@Entity
@Table(
        name = "tasks",
        indexes = {
                @Index(name = "idx_task_company_status", columnList = "company_public_id,status"),
                @Index(name = "idx_task_site_status", columnList = "site_public_id,status"),
                @Index(name = "idx_task_created_by", columnList = "created_by_public_id")
        }
)
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "public_id", nullable = false, unique = true)
    private String publicId;

    @Column(name = "company_id", nullable = false)
    private String companyId;

    @Column(name = "site_id")
    private String siteId;

    @Column(name = "created_by_id", nullable = false)
    private String createdById;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "price_for_period", precision = 10, scale = 2)
    private BigDecimal priceForPeriod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status = TaskStatus.OPEN;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "photos", columnDefinition = "text[]")
    private List<String> photos = new ArrayList<>();

    @OneToMany(
            mappedBy = "task",
            fetch = FetchType.LAZY
    )
    private List<TaskAssignment> assignments = new ArrayList<>();


    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;


    @PrePersist
    public void onCreate() {
        this.publicId = "TSK@V1@" + UUID.randomUUID();
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.status = TaskStatus.OPEN;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Instant.now();
    }
}