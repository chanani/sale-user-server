package com.sale.hot.entity.keyword;

import com.sale.hot.entity.BaseEntity;
import com.sale.hot.entity.user.User;
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
@Table(name = "keyword")
@Builder
public class Keyword extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "name")
    private String name;

    /**
     * 사용자 등록
     */
    public void setUser(User user) {
        this.user = user;
    }
}
