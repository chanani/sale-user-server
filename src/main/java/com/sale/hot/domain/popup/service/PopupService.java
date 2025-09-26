package com.sale.hot.domain.popup.service;

import com.sale.hot.domain.popup.service.dto.response.PopupsResponse;

import java.util.List;

public interface PopupService {

    /**
     * 활성화된 팝업 목록 조회
     * @return 활성화된 팝업 리스트
     */
    List<PopupsResponse> getPopups();
}
