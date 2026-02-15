package net.vuega.control_plane.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.vuega.control_plane.model.Licenses;
import net.vuega.control_plane.repository.LicenseRepository;
import net.vuega.control_plane.repository.OperatorRepository;

@Service
public class LicenseService {

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    public List<Licenses> getAllLicenses() {
        return licenseRepository.findAll();
    }

    public Licenses updateLicense(Long id, Licenses license) {
        if (id == null) {
            throw new IllegalArgumentException("Invalid license ID");
        }
        validateLicense(license);

        Licenses existingLicense = licenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("License not found with ID: " + id));

        existingLicense.setLicenseKey(license.getLicenseKey());
        existingLicense.setStartDate(license.getStartDate());
        existingLicense.setEndDate(license.getEndDate());
        existingLicense.setStatus(license.getStatus());

        return licenseRepository.save(existingLicense);
        
    }

    public Optional<Licenses> getLicenseById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Invalid operator ID");
        }
        return licenseRepository.findById(id);
    }

    public Licenses createLicense(Licenses license) {
        validateLicense(license);
        if (license.getOperatorId() <= 0) {
            throw new IllegalArgumentException("Operator ID is required");
        }
        if (!operatorRepository.existsById(license.getOperatorId())) {
            throw new IllegalArgumentException("Operator not found with ID: " + license.getOperatorId());
        }
        if (licenseRepository.existsByLicenseId(
                license.getLicenseId(),
                license.getLicenseKey())) {
            throw new IllegalArgumentException("License already exists for the given name and company");
        }
        return licenseRepository.save(license);
    }


    public void deleteLicense(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Invalid license ID");
        }
        if (!licenseRepository.existsById(id)) {
            throw new IllegalArgumentException("License not found with ID: " + id);
        }
        licenseRepository.deleteById(id); 
    }

    private void validateLicense(Licenses license) {
        if (license.getLicenseKey() == null || license.getLicenseKey().isEmpty()) {
            throw new IllegalArgumentException("L   icense key is required");
        }
        if (license.getOperatorId() <= 0) {
            throw new IllegalArgumentException("Operator ID is required");
        }
        if (license.getEndDate() == null) {
            throw new IllegalArgumentException("End date is required");
        }
        if (license.getStartDate() == null) {
            throw new IllegalArgumentException("Start date is required");
        }
        if (license.getStatus() == null) {
            throw new IllegalArgumentException("License status is required");
        }
    }
    
}
