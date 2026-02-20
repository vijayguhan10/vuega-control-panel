package net.vuega.control_plane.service.expansionrequest;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.vuega.control_plane.dto.expansionrequest.ExpansionRequestDto;
import net.vuega.control_plane.model.expansionrequest.ExpansionRequestModel;
import net.vuega.control_plane.repository.expansionrequest.ExpansionRequestRepository;
import net.vuega.control_plane.repository.operators.OperatorRepository;
import net.vuega.control_plane.util.ExpansionRequestStatus;

@Service
@Transactional
public class ExpansionRequestService {

    private final ExpansionRequestRepository repository;
    private final OperatorRepository operatorRepository;

    public ExpansionRequestService(ExpansionRequestRepository repository, OperatorRepository operatorRepository) {
        this.repository = repository;
        this.operatorRepository = operatorRepository;
    }

    // ================= CREATE =================
    public ExpansionRequestDto createRequest(ExpansionRequestDto dto) {

        if (dto == null)
            throw new IllegalArgumentException("Request body cannot be null");

        if (dto.getOperatorId() == null)
            throw new IllegalArgumentException("Operator ID is required");

        if (!operatorRepository.existsById(dto.getOperatorId()))
            throw new IllegalArgumentException("Operator not found");

        if (dto.getRequestFor() == null)
            throw new IllegalArgumentException("Request type is required");

        if (dto.getCount() == null || dto.getCount() <= 0)
            throw new IllegalArgumentException("Count must be greater than 0");

        ExpansionRequestModel request = new ExpansionRequestModel();
        request.setOperatorId(dto.getOperatorId());
        request.setRequestFor(dto.getRequestFor());
        request.setCount(dto.getCount());
        request.setStatus(ExpansionRequestStatus.PENDING);

        ExpansionRequestModel saved = repository.save(request);

        return convertToDto(saved);
    }

    // ================= GET ALL =================
    @Transactional(readOnly = true)
    public List<ExpansionRequestDto> getAllRequests() {

        return repository.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    // ================= APPROVE =================
    public ExpansionRequestDto approveRequest(Long id) {

        ExpansionRequestModel request = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expansion request not found"));

        if (request.getStatus() != ExpansionRequestStatus.PENDING)
            throw new IllegalStateException("Request already processed");

        request.setStatus(ExpansionRequestStatus.APPROVED);

        ExpansionRequestModel updated = repository.save(request);

        return convertToDto(updated);
    }

    // ================= REJECT =================
    public ExpansionRequestDto rejectRequest(Long id) {

        ExpansionRequestModel request = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expansion request not found"));

        if (request.getStatus() != ExpansionRequestStatus.PENDING)
            throw new IllegalStateException("Request already processed");

        request.setStatus(ExpansionRequestStatus.REJECTED);

        ExpansionRequestModel updated = repository.save(request);

        return convertToDto(updated);
    }

    // ================= MAPPER =================
    private ExpansionRequestDto convertToDto(ExpansionRequestModel request) {

        return new ExpansionRequestDto(
                request.getRequestId(),
                request.getOperatorId(),
                request.getRequestFor(),
                request.getCount(),
                request.getStatus());
    }
}