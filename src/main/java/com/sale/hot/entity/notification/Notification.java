package com.sale.hot.entity.notification;

import com.sale.hot.entity.BaseEntity;
import com.sale.hot.entity.common.constant.BooleanYn;
import com.sale.hot.entity.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
@Table(name = "Notification")
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "message")
    private String content;

    @Column(name = "is_read")
    private BooleanYn isRead;

    public void setUser(User user) {
        this.user = user;
    }
}
