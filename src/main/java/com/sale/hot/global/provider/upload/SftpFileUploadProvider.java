package com.sale.hot.global.provider.upload;

import org.springframework.web.multipart.MultipartFile;

public interface SftpFileUploadProvider {
    String upload(MultipartFile file);
}
