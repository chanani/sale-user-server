package com.sale.hot.domain.operator.repository;

import com.sale.hot.entity.common.constant.StatusType;
import com.sale.hot.entity.operator.Operator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OperatorRepository extends JpaRepository<Operator, Long> {
    Optional<Operator> findByOperatorIdAndStatus(String operatorId, StatusType statusType);
    Optional<Operator> findByIdAndStatus(Long operatorId, StatusType statusType);
    boolean existsByOperatorId(String operatorId);
}
