package com.sale.hot.domain.common.service;

import org.springframework.web.multipart.MultipartFile;

public interface CommonService {

    /**
     * 에디터 툴 이미지 업로드
     * @param inputFile 업로드 파일 객체
     * @return 업로드 된 파일 경로
     */
    String EditorImageUpload(MultipartFile inputFile);
}
