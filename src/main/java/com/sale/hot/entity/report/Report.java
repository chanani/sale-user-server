package com.sale.hot.entity.report;

import com.sale.hot.entity.BaseEntity;
import com.sale.hot.entity.common.constant.ReportType;
import com.sale.hot.entity.user.User;
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
@Table(name = "report")
public class Report extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ReportType type;

    @Column(name = "target_id")
    private Long targetId;

    @Column(name = "reason")
    public String reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_id")
    private User user;

    /**
     * 신고자 등록
     */
    public void setUser(User user){
        this.user = user;
    }

}
