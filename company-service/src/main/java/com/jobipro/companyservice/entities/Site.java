package com.jobipro.companyservice.entities;


import com.jobipro.companyservice.enums.SiteStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "sites")
@Getter
@Setter
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "public_id", nullable = false, unique = true)
    private String publicId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "manager_user_id")

    private String managerUserId;

    @Column(nullable = false)
    private String name;

    private String description;

    private String address;

    private String city;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "country_code", length = 2)
    private String countryCode;

    private Double latitude;

    private Double longitude;

    @Enumerated(EnumType.STRING)
    private SiteStatus status = SiteStatus.ACTIVE;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;


    @PrePersist
    public void onCreate() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
        publicId = "STE@V1@" + UUID.randomUUID();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = Instant.now();
    }
}