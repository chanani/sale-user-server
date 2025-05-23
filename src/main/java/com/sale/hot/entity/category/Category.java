package com.sale.hot.entity.category;

import com.sale.hot.entity.BaseEntity;
import com.sale.hot.entity.common.constant.BooleanYn;
import com.sale.hot.entity.post.Post;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Builder
@Table(name = "category")
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "sort_order")
    private Integer order;

    @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST)
    @Builder.Default
    private List<Post> posts = new ArrayList<>();

    public void setPost(Post post) {
        this.posts.add(post);
    }

    /**
     * 카테고리 수정
     */
    public void update(Category category){
        if(category.getName() != null){
            this.name = category.getName();
        }
    }

    /**
     * 순서 변경
     */
    public void updateOrder(int order){
        this.order = order;
    }
}
