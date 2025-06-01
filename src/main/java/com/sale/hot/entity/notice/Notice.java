package com.sale.hot.entity.notice;

import com.sale.hot.entity.BaseEntity;
import com.sale.hot.entity.common.constant.BooleanYn;
import com.sale.hot.entity.common.constant.NoticeType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name = "notice")
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private NoticeType type;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "active")
    @Enumerated(EnumType.STRING)
    private BooleanYn active;

    @Column(name = "view_count")
    @Builder.Default
    private Integer viewCount = 0;

    /**
     * 조회수 증가
     */
    public void addViewCount(){
        this.viewCount++;
    }

    /**
     * 공지사항 수정
     */
    public void update(Notice notice){
        if(notice.getTitle() != null){
            this.title = notice.getTitle();
        }
        if(notice.getContent() != null){
            this.content = notice.getContent();
        }
        if(notice.getActive() != null){
            this.active = notice.getActive();
        }
    }
}
