package com.sale.hot.domain.common.service;

import com.sale.hot.global.util.FileUtil;
import com.sale.hot.global.util.dto.FileName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class DefaultCommonService implements CommonService {

    private final FileUtil fileUtil;

    @Override
    public String editorImageUpload(MultipartFile inputFile) {
        // 파일 확장자 확인
        fileUtil.existsImageFileExtension(inputFile);
        // 파일 업로드
        FileName uploadFileName = fileUtil.fileUpload(inputFile, "editor");
        return uploadFileName.getModifiedFileName();
    }
}
