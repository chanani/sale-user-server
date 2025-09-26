package com.sale.hot.domain.popup.repository;

import com.sale.hot.entity.common.constant.BooleanYn;
import com.sale.hot.entity.common.constant.StatusType;
import com.sale.hot.entity.common.constant.TargetType;
import com.sale.hot.entity.popup.Popup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PopupRepository extends JpaRepository<Popup, Long> {
    List<Popup> findByActiveAndTargetTypeAndStatus(BooleanYn booleanYn, TargetType targetType, StatusType statusType);
}
