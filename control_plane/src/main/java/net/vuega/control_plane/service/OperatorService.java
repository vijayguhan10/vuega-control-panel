package net.vuega.control_plane.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.vuega.control_plane.model.Operator;
import net.vuega.control_plane.repository.OperatorRepository;

@Service
public class OperatorService {

    @Autowired
    private OperatorRepository operatorRepository;

    public List<Operator> getAllOperators() {
        return operatorRepository.findAll();

    }

    public Optional<Operator> getOperatorById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Invalid operator ID");
        }
        return operatorRepository.findById(id);

    }

    public Operator createOperator(Operator operator) {
        validateOperator(operator);
        if (operatorRepository.existsByOperatorNameAndCompanyName(
                operator.getOperatorName(),
                operator.getCompanyName())) {
            throw new IllegalArgumentException("Operator already exists for the given name and company");
        }
        return operatorRepository.save(operator);

    }

    public Operator updateOperator(Long id, Operator operator) {
        if (id == null) {
            throw new IllegalArgumentException("Invalid operator ID");
        }
        validateOperator(operator);

        Operator existingOperator = operatorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Operator not found with ID: " + id));

        existingOperator.setOperatorName(operator.getOperatorName());
        existingOperator.setCompanyName(operator.getCompanyName());
        existingOperator.setStatus(operator.getStatus());

        return operatorRepository.save(existingOperator);

    }

    public void deleteOperator(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Invalid operator ID");
        }
        if (!operatorRepository.existsById(id)) {
            throw new IllegalArgumentException("Operator not found with ID: " + id);
        }
        operatorRepository.deleteById(id);
    }

    private void validateOperator(Operator operator) {

        if (operator == null) {
            throw new IllegalArgumentException("Operator cannot be null");
        } else if (operator.getOperatorName() == null || operator.getOperatorName().isEmpty()) {
            throw new IllegalArgumentException("Operator name cannot be null or empty");
        } else if (operator.getCompanyName() == null || operator.getCompanyName().isEmpty()) {
            throw new IllegalArgumentException("Company name cannot be null or empty");
        } else if (operator.getStatus() == null) {
            throw new IllegalArgumentException("Operator status cannot be null");
        }

    }

}
