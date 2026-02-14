package net.vuega.control_plane.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.vuega.control_plane.model.Licenses;
import net.vuega.control_plane.util.LicenseStatus;

@Repository
public interface LicenseRepository extends JpaRepository<Licenses, Long> {
    List<Licenses> findByStatus(LicenseStatus status);

    boolean existsByLicenseId(long licenseId, String licenseKey);
}
