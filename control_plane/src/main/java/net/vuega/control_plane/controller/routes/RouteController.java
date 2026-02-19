package net.vuega.control_plane.controller.routes;

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

import net.vuega.control_plane.dto.routes.RouteDto;
import net.vuega.control_plane.service.routes.RouteService;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping("/")
    public ResponseEntity<RouteDto> createRoute(@RequestBody RouteDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(routeService.createRoute(dto));
    }

    @GetMapping("/")
    public ResponseEntity<List<RouteDto>> getAllRoutes() {
        return ResponseEntity.ok(routeService.getAllRoutes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteDto> getRoute(@PathVariable Long id) {
        return ResponseEntity.ok(routeService.getRouteById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RouteDto> updateRoute(
            @PathVariable Long id,
            @RequestBody RouteDto dto) {
        return ResponseEntity.ok(routeService.updateRoute(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateRoute(@PathVariable Long id) {
        routeService.deactivateRoute(id);
        return ResponseEntity.noContent().build();
    }
}
