package com.sale.hot.global.eventListener.keyword.dto;

import com.sale.hot.entity.post.Post;
import com.sale.hot.entity.user.User;

public record KeywordEvent(
        Post post
) {
}
