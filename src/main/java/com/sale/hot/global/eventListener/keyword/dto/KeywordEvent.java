package com.sale.hot.global.eventListener.keyword.dto;

import com.sale.hot.entity.post.Post;

public record KeywordEvent(
        Post post
) {
}
