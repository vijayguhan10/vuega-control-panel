package net.vuega.control_plane.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.vuega.control_plane.model.LicenseLimits;
import net.vuega.control_plane.service.LicenseLimitsService;




@RestController
@RequestMapping("/api/license-limits")
public class LicenseLimitsController {
    
    @Autowired
    private LicenseLimitsService licenseLimitsService;

    @PostMapping("/")
    public ResponseEntity<?> createLicenseLimits(@RequestBody LicenseLimits licenseLimits) {
        try {
            LicenseLimits createdLicenseLimits = licenseLimitsService.createLicenseLimits(licenseLimits);
            return buildSuccessResponse(createdLicenseLimits, HttpStatus.CREATED);
        } catch (Exception e) {
            return buildFailureResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/{licenseId}")
    public ResponseEntity<?> getLicenseLimitsByLicenseId(@PathVariable Long licenseId) {
        try {
            LicenseLimits licenseLimits = licenseLimitsService.getLicenseLimitsByLicenseId(licenseId);
            if (licenseLimits != null) {
                return buildSuccessResponse(licenseLimits, HttpStatus.OK);
            } else {
                return buildFailureResponse("License limits not found for license ID: " + licenseId, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return buildFailureResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{licenseId}")
    public ResponseEntity<?> updateLicenseLimitsByLicenseId(@PathVariable Long licenseId, @RequestBody LicenseLimits licenseLimits) {
        try{
            LicenseLimits updatedLicenseLimits = licenseLimitsService.updateLicenseLimitsByLicenseId(licenseId, licenseLimits);
            return buildSuccessResponse(updatedLicenseLimits, HttpStatus.OK);
        }
        catch (Exception e) {
            return buildFailureResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{licenseId}")
    public ResponseEntity<?> deleteLicenseLimitsByLicenseId(@PathVariable Long licenseId) {
        try {
            licenseLimitsService.deleteLicenseLimitsByLicenseId(licenseId);
            return buildSuccessResponse(null, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return buildFailureResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private ResponseEntity<Map<String, Object>> buildSuccessResponse(Object data, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("success", true);
        body.put("message", "success");
        if (data != null) {
            body.put("data", data);
        }
        return ResponseEntity.status(status).body(body);
    }

    private ResponseEntity<Map<String, Object>> buildFailureResponse(String message, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("success", false);
        body.put("message", message == null || message.isBlank() ? "failed" : message);
        return ResponseEntity.status(status).body(body);
    }
    
}
