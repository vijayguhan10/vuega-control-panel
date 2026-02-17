package net.vuega.control_plane.controller.Licenses;

import java.util.List;

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

import net.vuega.control_plane.dto.Licenses.LicensesDto;
import net.vuega.control_plane.service.Licenses.LicenseService;

@RestController
@RequestMapping("/api/licenses")
public class LicenseController {

    private final LicenseService licenseService;

    public LicenseController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @GetMapping("/")
    public ResponseEntity<List<LicensesDto>> getAllLicenses() {
        return ResponseEntity.ok(licenseService.getAllLicenses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LicensesDto> getLicenseById(@PathVariable Long id) {
        LicensesDto dto = licenseService.getLicenseById(id)
                .orElseThrow(() -> new RuntimeException("License not found"));
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/")
    public ResponseEntity<LicensesDto> createLicense(@RequestBody LicensesDto dto) {
        LicensesDto created = licenseService.createLicense(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LicensesDto> updateLicense(@PathVariable Long id, @RequestBody LicensesDto dto) {
        LicensesDto updated = licenseService.updateLicense(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLicense(@PathVariable Long id) {
        licenseService.deleteLicense(id);
        return ResponseEntity.noContent().build();
    }

}
