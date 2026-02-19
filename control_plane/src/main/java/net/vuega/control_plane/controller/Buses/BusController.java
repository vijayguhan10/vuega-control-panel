package net.vuega.control_plane.controller.buses;

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

import net.vuega.control_plane.dto.buses.BusesDto;
import net.vuega.control_plane.service.buses.BusesService;

@RestController
@RequestMapping("/api/buses")
public class BusController {

    private final BusesService busService;

    public BusController(BusesService busService) {
        this.busService = busService;
    }

    @PostMapping("/")
    public ResponseEntity<BusesDto> createBus(@RequestBody BusesDto dto) {
        BusesDto created = busService.createBus(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/")
    public ResponseEntity<List<BusesDto>> getAllBuses() {
        return ResponseEntity.ok(busService.getAllBuses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusesDto> getBus(@PathVariable Long id) {
        return ResponseEntity.ok(busService.getBusById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BusesDto> updateBus(
            @PathVariable Long id,
            @RequestBody BusesDto dto) {
        return ResponseEntity.ok(busService.updateBus(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateBus(@PathVariable Long id) {
        busService.deactivateBus(id);
        return ResponseEntity.noContent().build();
    }
}
