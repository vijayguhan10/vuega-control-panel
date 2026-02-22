package net.vuega.control_plane.controller.heartbeats;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import net.vuega.control_plane.dto.heartbeats.HeartBeatDto;
import net.vuega.control_plane.service.heartbeats.HeartBeatService;

@RestController
@RequestMapping("/api/heartbeats")
@RequiredArgsConstructor
public class HeartBeatController {

    private final HeartBeatService service;

    // ================= POST =================
    @PostMapping("/")
    public ResponseEntity<HeartBeatDto> receiveHeartbeat(
            @RequestBody HeartBeatDto dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.receiveHeartbeat(dto));
    }

    // ================= GET =================
    @GetMapping("/{operatorId}")
    public ResponseEntity<List<HeartBeatDto>> getByOperator(
            @PathVariable Long operatorId) {

        return ResponseEntity.ok(service.getByOperator(operatorId));
    }
}