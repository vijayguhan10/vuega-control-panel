package net.vuega.control_plane.repository.LicenseLimits;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.vuega.control_plane.model.LicenseLimits.LicenseLimits;

@Repository
public interface LicenseLimitsRepository extends JpaRepository<LicenseLimits, Long> {
    LicenseLimits findByLicenseId(long licenseId);
    
}
