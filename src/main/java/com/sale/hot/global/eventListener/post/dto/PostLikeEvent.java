package com.sale.hot.global.eventListener.post.dto;

import com.sale.hot.entity.post.Post;

public record PostLikeEvent(
        Post post
) {
}
