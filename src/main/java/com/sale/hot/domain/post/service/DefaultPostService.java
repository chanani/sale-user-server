package com.sale.hot.domain.post.service;

import com.sale.hot.domain.category.repository.CategoryRepository;
import com.sale.hot.domain.post.repository.PostRepository;
import com.sale.hot.domain.post.service.dto.request.PostCreateRequest;
import com.sale.hot.domain.post.service.dto.request.PostUpdateRequest;
import com.sale.hot.entity.category.Category;
import com.sale.hot.entity.post.Post;
import com.sale.hot.entity.user.User;
import com.sale.hot.global.exception.OperationErrorException;
import com.sale.hot.global.exception.dto.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DefaultPostService implements PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public void addPost(PostCreateRequest request, User user) {
        // 카테고리 엔티티 조회
        Category findCategory = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new OperationErrorException(ErrorCode.NOT_FOUND_CATEGORY));
        // 게시글 객체로 변환
        Post newPost = request.toEntity(findCategory, user);
        // 게시글 등록
        Post savePost = postRepository.save(newPost);
    }

    @Override
    @Transactional
    public void updatePost(Long postId, PostUpdateRequest request) {
        // 게시글 조회
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new OperationErrorException(ErrorCode.NOT_FOUND_POST));
        // 카테고리 엔티티 조회
        Category findCategory = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new OperationErrorException(ErrorCode.NOT_FOUND_CATEGORY));
        // 게시글 수정
        Post newEntity = request.toEntity(findCategory);
        findPost.update(newEntity);
    }

    @Override
    public void deletePost(Long postId) {
        // 게시글 조회
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new OperationErrorException(ErrorCode.NOT_FOUND_POST));
        // 게시글 삭제
        findPost.remove();
    }
}
