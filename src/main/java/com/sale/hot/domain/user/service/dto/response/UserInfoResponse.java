package com.sale.hot.domain.user.service.dto.response;

import com.sale.hot.entity.common.constant.Gender;
import com.sale.hot.entity.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UserInfoResponse {
    @Schema(description = "아이디")
    private String userId;

    @Schema(description = "이메일")
    private String email;

    @Schema(description = "이름")
    private String name;

    @Schema(description = "닉네임")
    private String nickname;

    @Schema(description = "연락처")
    private String phone;

    @Schema(description = "성별")
    private String gender;

    @Schema(description = "생년월일")
    private String birth;

    @Schema(description = "등록한 게시글 수")
    private Integer postCount;

    @Schema(description = "등록한 댓글 수")
    private Integer commentCount;

    @Schema(description = "출석일 수")
    private Integer attendCount;

    public UserInfoResponse(User user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.phone = user.getPhone();
        this.gender = user.getGender() == Gender.M ? "남자" : "여자";
        this.birth = String.valueOf(user.getBirth());
        this.postCount = user.getPostCount();
        this.commentCount = user.getCommentCount();
        this.attendCount = user.getAttendCount();
    }
}
