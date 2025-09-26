package com.sale.hot.domain.popup.service;

import com.sale.hot.domain.popup.repository.PopupRepository;
import com.sale.hot.domain.popup.service.dto.response.PopupsResponse;
import com.sale.hot.entity.common.constant.BooleanYn;
import com.sale.hot.entity.common.constant.StatusType;
import com.sale.hot.entity.common.constant.TargetType;
import com.sale.hot.entity.popup.Popup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DefaultPopupService implements PopupService {

    private final PopupRepository popupRepository;

    @Override
    public List<PopupsResponse> getPopups() {
        return popupRepository.findByActiveAndTargetTypeAndStatus(BooleanYn.Y, TargetType.USER, StatusType.ACTIVE).stream()
                .map(PopupsResponse::new)
                .toList();
    }
}
