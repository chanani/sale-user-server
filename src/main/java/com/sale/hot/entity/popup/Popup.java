package com.sale.hot.entity.popup;

import com.sale.hot.entity.BaseEntity;
import com.sale.hot.entity.common.constant.BooleanYn;
import com.sale.hot.entity.common.constant.TargetType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "popup")
@Builder
public class Popup extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "link")
    private String link;

    @Column(name = "target_type")
    @Enumerated(EnumType.STRING)
    private TargetType targetType;

    @Column(name = "active")
    @Enumerated(EnumType.STRING)
    private BooleanYn active;

}
