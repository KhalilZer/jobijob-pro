package com.jobipro.companyservice.repositories;

import com.jobipro.companyservice.entities.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SiteRepo extends JpaRepository<Site, Long> {
    Optional<Site> findByPublicId(String publicId);

    @Query("""
                SELECT COUNT(s) > 0
                FROM Site s
                WHERE LOWER(TRIM(s.name)) = LOWER(TRIM(:name))
                 AND s.company.publicId = :companyPublicId
                AND LOWER(TRIM(s.address)) = LOWER(TRIM(:address))
                  AND LOWER(TRIM(s.city)) = LOWER(TRIM(:city))
                  AND LOWER(TRIM(s.countryCode)) = LOWER(TRIM(:countryCode))
                  AND TRIM(s.postalCode) = TRIM(:postalCode)
                  AND s.latitude = :latitude
                  AND s.longitude = :longitude
            """)
    boolean existsNormalized(
            @Param("name") String name,
            @Param("companyPublicId") String companyPublicId,
            @Param("address") String address,
            @Param("city") String city,
            @Param("countryCode") String countryCode,
            @Param("postalCode") String postalCode,
            @Param("latitude") Double latitude,
            @Param("longitude") Double longitude
    );
}
