package net.vuega.control_plane.controller.Operators;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.vuega.control_plane.dto.Operators.OperatorsDto;
import net.vuega.control_plane.service.Operators.OperatorService;

@RestController
@RequestMapping("/api/operators")
public class OperatorController {

    private final OperatorService operatorService;

    public OperatorController(OperatorService operatorService) {
        this.operatorService = operatorService;
    }

    @GetMapping("/")
    public ResponseEntity<List<OperatorsDto>> getAllOperators() {
        return ResponseEntity.ok(operatorService.getAllOperators());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperatorsDto> getOperatorById(@PathVariable Long id) {

        OperatorsDto dto = operatorService.getOperatorById(id)
                .orElseThrow(() -> new RuntimeException("Operator not found"));

        return ResponseEntity.ok(dto);
    }

    @PostMapping("/")
    public ResponseEntity<OperatorsDto> createOperator(@RequestBody OperatorsDto dto) {

        OperatorsDto created = operatorService.createOperator(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OperatorsDto> updateOperator(
            @PathVariable Long id,
            @RequestBody OperatorsDto dto) {

        OperatorsDto updated = operatorService.updateOperator(id, dto);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOperator(@PathVariable Long id) {

        operatorService.deleteOperator(id);

        return ResponseEntity.noContent().build();
    }
}
