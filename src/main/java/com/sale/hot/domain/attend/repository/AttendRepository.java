package com.sale.hot.domain.attend.repository;

import com.sale.hot.entity.attend.Attend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface AttendRepository extends JpaRepository<Attend, Long> {
    boolean existsByUserIdAndAttendDate(Long id, LocalDate now);
}
