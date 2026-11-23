package com.jobipro.companyservice.entities;

import com.jobipro.common.enums.CompanyStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "companies",
        indexes = {
                @Index(name = "idx_company_owner_public_id", columnList = "owner_public_id"),
                @Index(name = "idx_company_city", columnList = "city"),
                @Index(name = "idx_company_country_code", columnList = "countryCode"),
        })
@Getter
@Setter

public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "public_id", nullable = false, unique = true)
    private String publicId;

    @Column(name = "owner_public_id", nullable = false)
    private String ownerPublicId;

    @Column(nullable = false)
    private String name;

    @Column(length = 2000)
    private String description;

    @Column(name = "sector_codes", columnDefinition = "text[]")
    private List<String> sectorCodes;

    // Object key MinIO
    private String logo;

    @Column(name = "vat_number", unique = true, nullable = false)
    private String vatNumber;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String phone;

    private String website;

    // Location
    private String address;

    private String city;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "country_code", length = 2)
    private String countryCode;

    private Double latitude;

    private Double longitude;

    @Enumerated(EnumType.STRING)
    private CompanyStatus status = CompanyStatus.ACTIVE;

    @OneToMany(
            mappedBy = "company",
            orphanRemoval = true
    )
    private List<Site> sites = new ArrayList<>();

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
        publicId = "CPM@V1@" + UUID.randomUUID();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = Instant.now();
    }

}
