package net.vuega.control_plane.controller.expansionrequest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.vuega.control_plane.dto.expansionrequest.ExpansionRequestDto;
import net.vuega.control_plane.service.expansionrequest.ExpansionRequestService;

@RestController
@RequestMapping("/api/expansion-requests")
@RequiredArgsConstructor
public class ExpansionRequestController {

    private final ExpansionRequestService service;

    // ================= CREATE =================
    @PostMapping("/")
    public ResponseEntity<ExpansionRequestDto> createRequest(
            @RequestBody ExpansionRequestDto dto) {

        ExpansionRequestDto response = service.createRequest(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ================= GET ALL =================
    @GetMapping("/")
    public ResponseEntity<List<ExpansionRequestDto>> getAllRequests() {

        return ResponseEntity.ok(service.getAllRequests());
    }

    // ================= APPROVE =================
    @PutMapping("/{id}/approve")
    public ResponseEntity<ExpansionRequestDto> approveRequest(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.approveRequest(id));
    }

    // ================= REJECT =================
    @PutMapping("/{id}/reject")
    public ResponseEntity<ExpansionRequestDto> rejectRequest(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.rejectRequest(id));
    }
}