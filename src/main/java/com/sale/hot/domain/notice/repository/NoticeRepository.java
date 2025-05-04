package com.sale.hot.domain.notice.repository;

import com.sale.hot.entity.common.constant.StatusType;
import com.sale.hot.entity.notice.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long>, NoticeRepositoryCustom {
    Optional<Notice> findByIdAndStatus(Long id, StatusType statusType);
}
