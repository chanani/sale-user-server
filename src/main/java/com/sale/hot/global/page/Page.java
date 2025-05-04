package com.sale.hot.global.page;

public record Page<T>(
        Pageable pageable,
        T data
) {
}
