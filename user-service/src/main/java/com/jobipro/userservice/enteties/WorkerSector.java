package com.jobipro.userservice.enteties;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "worker_sectors",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_worker_sector",
                        columnNames = {"worker_id", "sector_id"}
                )
        })
@Getter
@Setter
public class WorkerSector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "worker_id")
    private Worker worker;

    @ManyToOne
    @JoinColumn(name = "sector_id")
    private Sector sector;

    @Column(name = "level_experience")

    private Integer levelExperience;
}