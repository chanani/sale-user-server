package com.sale.hot.domain.post.service.dto.response;

import com.sale.hot.entity.common.constant.AuthorType;
import com.sale.hot.entity.common.constant.BooleanYn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Getter
public class PostsResponse {
    @Schema(description = "게시글 고유번호")
    private Long id;

    @Schema(description = "회원 정보")
    private String userNickname;

    @Schema(description = "작성자 타입")
    private AuthorType authorType;

    @Schema(description = "카테고리명")
    private String categoryName;

    @Schema(description = "댓글수")
    private Long commentCount;

    @Schema(description = "광고 여부")
    private Boolean promotion;

    @Schema(description = "제목")
    private String title;

    @Schema(description = "내용")
    private String content;

    @Schema(description = "매장명")
    private String shopName;

    @Schema(description = "가격")
    private Integer price;

    @Schema(description = "배송비")
    private Integer deliveryPrice;

    @Schema(description = "좋아요 수")
    private Integer likeCount;

    @Schema(description = "싫어요 수")
    private Integer dislikeCount;

    @Schema(description = "썸네일")
    private String thumbnail;

    @Schema(description = "등록일")
    private LocalDateTime createdAt;

    public PostsResponse(Long id, AuthorType authorType, String userNickname, String companyName, String categoryName,
                         Long commentCount, BooleanYn promotion,
                         String title, String content, String shopName,
                         Integer price, Integer deliveryPrice, Integer likeCount,
                         Integer dislikeCount, String thumbnail, LocalDateTime createdAt) {
        this.id = id;
        this.authorType = authorType;
        this.userNickname = StringUtils.hasText(userNickname) ? userNickname : companyName;
        this.categoryName = categoryName;
        this.commentCount = commentCount;
        this.promotion = promotion.equals(BooleanYn.Y);
        this.title = title;
        this.content = content;
        this.shopName = shopName;
        this.price = price;
        this.deliveryPrice = deliveryPrice;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.thumbnail = thumbnail;
        this.createdAt = createdAt;
    }
}
