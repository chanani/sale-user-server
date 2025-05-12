package com.sale.hot.global.eventListener.comment.dto;

import com.sale.hot.entity.comment.Comment;
import com.sale.hot.entity.post.Post;
import com.sale.hot.entity.user.User;

public record CommentEvent(
        User user,
        Post post,
        Comment comment
) {
}
