package net.vuega.control_plane.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.vuega.control_plane.model.LicenseLimits;
import net.vuega.control_plane.repository.LicenseLimitsRepository;
import net.vuega.control_plane.repository.LicenseRepository;


@Service
public class LicenseLimitsService {

    @Autowired
    private LicenseLimitsRepository licenseLimitsRepository;

    @Autowired
    private LicenseRepository licenseRepository;

    public LicenseLimits createLicenseLimits(LicenseLimits licenseLimits) {
        validateLicenseLimits(licenseLimits);
        if(!licenseRepository.existsById(licenseLimits.getLicenseId())) {
            throw new IllegalArgumentException("License not found with ID: " + licenseLimits.getLicenseId());
        }
        return licenseLimitsRepository.save(licenseLimits);
    }

    
    public LicenseLimits getLicenseLimitsByLicenseId(Long licenseId) {
        return licenseLimitsRepository.findByLicenseId(licenseId);
    }
    
    public LicenseLimits updateLicenseLimitsByLicenseId(Long licenseId, LicenseLimits updatedLicenseLimits) {
        validateLicenseLimits(updatedLicenseLimits);
        
        LicenseLimits existingLicenseLimits = licenseLimitsRepository.findByLicenseId(licenseId);
        if(existingLicenseLimits != null) {
            existingLicenseLimits.setBusLimit(updatedLicenseLimits.getBusLimit());
            existingLicenseLimits.setRouteLimit(updatedLicenseLimits.getRouteLimit());
            existingLicenseLimits.setPatnerLimit(updatedLicenseLimits.getPatnerLimit());
            existingLicenseLimits.setTripLimit(updatedLicenseLimits.getTripLimit());
            return licenseLimitsRepository.save(existingLicenseLimits);
        } else {
            throw new IllegalArgumentException("License limits not found for license ID: " + licenseId);
        }
    }
    
    public LicenseLimits updateLicenseLimits(Long id, LicenseLimits updatedLicenseLimits) {
        validateLicenseLimits(updatedLicenseLimits);
        Optional<LicenseLimits> existingLicenseLimits = licenseLimitsRepository.findById(id);
        if(existingLicenseLimits.isPresent()) {
            LicenseLimits licenseLimits = existingLicenseLimits.get();
            licenseLimits.setBusLimit(updatedLicenseLimits.getBusLimit());
            licenseLimits.setRouteLimit(updatedLicenseLimits.getRouteLimit());
            licenseLimits.setPatnerLimit(updatedLicenseLimits.getPatnerLimit());
            licenseLimits.setTripLimit(updatedLicenseLimits.getTripLimit());
            return licenseLimitsRepository.save(licenseLimits);
        } else {
            throw new IllegalArgumentException("License limits not found with ID: " + id);
        }
    }
    
    public void deleteLicenseLimitsByLicenseId(Long licenseId) {
        LicenseLimits existingLicenseLimits = licenseLimitsRepository.findByLicenseId(licenseId);
        if(existingLicenseLimits == null) {
            throw new IllegalArgumentException("License limits not found for license ID: " + licenseId);
        }
        licenseLimitsRepository.delete(existingLicenseLimits);
    }
    
    public void deleteLicenseLimits(Long id) {
        if(!licenseLimitsRepository.existsById(id)) {
            throw new IllegalArgumentException("License limits not found with ID: " + id);
        }
        licenseLimitsRepository.deleteById(id);
    }
    
    private void validateLicenseLimits(LicenseLimits licenseLimits) {
        if(licenseLimits.getBusLimit() < 0) {
            throw new IllegalArgumentException("Bus limit cannot be negative");
        }
        if(licenseLimits.getPatnerLimit() < 0) {
            throw new IllegalArgumentException("Device limit cannot be negative");
        }
        if(licenseLimits.getRouteLimit() < 0){
            throw new IllegalArgumentException("Route limit cannot be negative");
        }
        if(licenseLimits.getTripLimit() < 0){
            throw new IllegalArgumentException("Trip limit cannot be negative");
        }
    }
}
