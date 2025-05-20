package com.sale.hot.domain.post.service.dto.request;

import com.sale.hot.entity.category.Category;
import com.sale.hot.entity.common.constant.BooleanYn;
import com.sale.hot.entity.post.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.thymeleaf.util.StringUtils;

public record PostUpdateRequest(
        @Schema(description = "제목", example = "게시글 제목입니다.")
        @NotBlank(message = "제목은 필수입니다.")
        String title,

        @Schema(description = "내용", example = "게시글 내용입니다.")
        @NotBlank(message = "내용은 필수입니다.")
        String content,

        @Schema(description = "링크", example = "https://naver.com")
        String link,

        @Schema(description = "판매처명", example = "쿠팡")
        String shopName,

        @Schema(description = "상품명", example = "햇반")
        String itemName,

        @Schema(description = "가격", example = "10000")
        Integer price,

        @Schema(description = "카테고리 고유번호", example = "1")
        Long categoryId,

        @Schema(description = "광고 진행 여부", example = "false")
        boolean promotion
) {

    public Post toEntity(Category category, String thumbnail){
        return Post.builder()
                .title(this.title())
                .content(this.content)
                .thumbnail(StringUtils.isEmpty(thumbnail) ? null : thumbnail)
                .link(this.link)
                .shopName(this.shopName)
                .itemName(this.itemName)
                .price(this.price)
                .category(category)
                .promotion(promotion ? BooleanYn.Y : BooleanYn.N)
                .build();
    }
}
