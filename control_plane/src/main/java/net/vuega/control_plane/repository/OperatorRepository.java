package net.vuega.control_plane.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.vuega.control_plane.model.Operator;
import net.vuega.control_plane.util.OperatorStatus;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Long> {
    List<Operator> findByStatus(OperatorStatus status);

    boolean existsByOperatorNameAndCompanyName(String operatorName, String companyName);
    // List<Operator> findByIdList(Long operatorId);

}
