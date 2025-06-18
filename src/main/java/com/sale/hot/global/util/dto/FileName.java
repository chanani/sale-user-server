package com.sale.hot.global.util.dto;

import lombok.Getter;

@Getter
public class FileName {
    private final String modifiedFileName;
    private final String originalFileName;

    public FileName(String modifiedFileName, String originalFileName) {
        this.modifiedFileName = modifiedFileName;
        this.originalFileName = originalFileName;
    }
}
