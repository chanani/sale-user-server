package com.sale.hot.entity.common.constant;

/**
 * 신고 종류
 */
public enum ReportType {
    POST("게시글", "POST"),
    COMMENT("댓글", "COMMENT"),
    USER("회원", "USER");

    private final String name;
    private final String value;

    ReportType(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public static ReportType forValue(String input) {
        for (ReportType type : ReportType.values()) {
            if (type.value.equals(input.toUpperCase())) {
                return type;
            }
        }
        throw new IllegalArgumentException("No matching PointChargeMethod for name : " + input);
    }

}
