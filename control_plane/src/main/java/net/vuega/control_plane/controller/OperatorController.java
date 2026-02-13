package net.vuega.control_plane.controller;

import java.util.HashMap;
import java.util.List;
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

import net.vuega.control_plane.model.Operator;
import net.vuega.control_plane.service.OperatorService;

@RestController
@RequestMapping("/api/operators")
public class OperatorController {

    @Autowired
    private OperatorService operatorService;

    @GetMapping("/")
    public ResponseEntity<?> getAllOperators() {
        List<Operator> operators = operatorService.getAllOperators();
        return buildSuccessResponse(operators, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOperatorById(@PathVariable Long id) {
        try {
            return operatorService.getOperatorById(id)
                    .map(operator -> buildSuccessResponse(operator, HttpStatus.OK))
                    .orElse(buildFailureResponse("Operator not found", HttpStatus.NOT_FOUND));
        } catch (IllegalArgumentException e) {
            return buildFailureResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createOperator(@RequestBody Operator operator) {
        try {
            Operator createdOperator = operatorService.createOperator(operator);
            return buildSuccessResponse(createdOperator, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return buildFailureResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOperator(@PathVariable Long id, @RequestBody Operator operator) {
        try {
            Operator updatedOperator = operatorService.updateOperator(id, operator);
            return buildSuccessResponse(updatedOperator, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return buildFailureResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return buildFailureResponse("Operator not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOperator(@PathVariable Long id) {
        try {
            operatorService.deleteOperator(id);
            return buildSuccessResponse(null, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return buildFailureResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return buildFailureResponse("Operator not found", HttpStatus.NOT_FOUND);
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
