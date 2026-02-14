package net.vuega.control_plane.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import net.vuega.control_plane.model.Licenses;
import net.vuega.control_plane.service.LicenseService;

@RestController
@RequestMapping("/api/licenses")
public class LicenseController {

    @Autowired
    private LicenseService licenseService;

    @GetMapping("/")
    public ResponseEntity<?> getAllLicenses() {
        List<Licenses> licenses = licenseService.getAllLicenses();
        return buildSuccessResponse(licenses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLicenseById(@PathVariable Long id) {
        try {
            return licenseService.getLicenseById(id)
                    .map(license -> buildSuccessResponse(license, HttpStatus.OK))
                    .orElse(buildFailureResponse("License not found", HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return buildFailureResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createLicense(@RequestBody Licenses license) {
        try {
            Licenses createdLicense = licenseService.createLicense(license);
            return buildSuccessResponse(createdLicense, HttpStatus.CREATED);
        } catch (Exception e) {
            return buildFailureResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLicense(@PathVariable Long id, @RequestBody Licenses license) {
        try {
            Licenses updatedLicense = licenseService.updateLicense(id, license);
            return buildSuccessResponse(updatedLicense, HttpStatus.OK);
        } catch (Exception e) {
            return buildFailureResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLicense(@PathVariable Long id) {
        try {
            licenseService.deleteLicense(id);
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
