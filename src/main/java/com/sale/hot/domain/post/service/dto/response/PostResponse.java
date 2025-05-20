package com.sale.hot.domain.post.service.dto.response;

import com.sale.hot.entity.common.constant.AuthorType;
import com.sale.hot.entity.common.constant.BooleanYn;
import com.sale.hot.global.util.DateUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.apache.commons.math3.geometry.spherical.twod.S2Point;

import java.time.LocalDateTime;

@Getter
public class PostResponse {
    @Schema(description = "게시글 고유번호")
    private Long id;

    @Schema(description = "작성자 타입")
    private AuthorType authorType;

    @Schema(description = "회원 정보")
    private PostUserResponse user;

    @Schema(description = "기업 정보")
    private PostCompanyResponse company;

    @Schema(description = "카테고리 정보")
    private PostCategoryResponse category;

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

    @Schema(description = "상품명")
    private String itemName;

    @Schema(description = "링크")
    private String link;

    @Schema(description = "가격")
    private Integer price;

    @Schema(description = "배송비")
    private Integer deliveryPrice;

    @Schema(description = "좋아요 수")
    private Integer likeCount;

    @Schema(description = "썸네일")
    private String thumbnail;

    @Schema(description = "싫어요 수")
    private Integer dislikeCount;

    @Schema(description = "등록일")
    private LocalDateTime createdAt;


    public PostResponse(Long id, PostUserResponse user, PostCompanyResponse company, PostCategoryResponse category,
                        AuthorType authorType, Long commentCount, BooleanYn promotion, String title,
                        String content, String shopName, String itemName, String link,
                        Integer price, Integer deliveryPrice, Integer likeCount,
                        Integer dislikeCount, String thumbnail, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.company = company;
        this.category = category;
        this.authorType = authorType;
        this.commentCount = commentCount;
        this.promotion = promotion.equals(BooleanYn.Y);
        this.title = title;
        this.content = content;
        this.shopName = shopName;
        this.itemName = itemName;
        this.link = link;
        this.price = price;
        this.deliveryPrice = deliveryPrice;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.thumbnail = thumbnail;
        this.createdAt = createdAt;
    }
}



