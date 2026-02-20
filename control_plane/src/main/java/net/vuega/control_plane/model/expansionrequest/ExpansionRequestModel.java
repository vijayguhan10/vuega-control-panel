package net.vuega.control_plane.model.expansionrequest;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import net.vuega.control_plane.util.ExpansionRequestFor;
import net.vuega.control_plane.util.ExpansionRequestStatus;

@Entity
@Table(name = "expansion_requests")
public class ExpansionRequestModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "request_id")
    private Long requestId;

    @Column(name = "operator_id", nullable = false)
    private Long operatorId;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_for", nullable = false)
    private ExpansionRequestFor requestFor;

    @Column(nullable = false)
    private Integer count;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExpansionRequestStatus status;

    // Getters
    public Long getRequestId() {
        return requestId;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public ExpansionRequestFor getRequestFor() {
        return requestFor;
    }

    public Integer getCount() {
        return count;
    }

    public ExpansionRequestStatus getStatus() {
        return status;
    }

    // Setters
    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public void setRequestFor(ExpansionRequestFor requestFor) {
        this.requestFor = requestFor;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setStatus(ExpansionRequestStatus status) {
        this.status = status;
    }
}
