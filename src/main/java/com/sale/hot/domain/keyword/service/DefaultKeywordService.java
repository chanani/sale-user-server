package com.sale.hot.domain.keyword.service;

import com.sale.hot.domain.keyword.repository.KeywordRepository;
import com.sale.hot.domain.keyword.service.dto.request.KeywordCreateRequest;
import com.sale.hot.domain.keyword.service.dto.response.KeywordResponse;
import com.sale.hot.entity.common.constant.StatusType;
import com.sale.hot.entity.keyword.Keyword;
import com.sale.hot.entity.user.User;
import com.sale.hot.global.exception.OperationErrorException;
import com.sale.hot.global.exception.dto.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DefaultKeywordService implements KeywordService {

    private final KeywordRepository keywordRepository;
    private final Integer ADD_LIMIT = 30;

    @Override
    @Transactional
    public void addKeyword(KeywordCreateRequest request, User user) {
        // 이미 등록되어 있는 키워드인지 확인
        if (keywordRepository.existsByUserIdAndName(user.getId(), request.keyword())) {
            throw new OperationErrorException(ErrorCode.EXISTS_KEYWORD_NAME);
        }
        // 등록된 키워드가 30개 최과했는지 확인
        if (keywordRepository.countByUserIdAndStatus(user.getId(), StatusType.ACTIVE) >= ADD_LIMIT) {
            throw new OperationErrorException(ErrorCode.EXCESS_KEYWORD);
        }
        // 등록
        Keyword newEntity = request.toEntity(user);
        keywordRepository.save(newEntity);
    }

    @Override
    @Transactional
    public void deleteKeyword(Long keywordId, User user) {
        // Keywrod 조회
        Keyword findKeyword = keywordRepository.findByIdAndUserIdAndStatus(keywordId, user.getId(), StatusType.ACTIVE)
                .orElseThrow(() -> new OperationErrorException(ErrorCode.NOT_FOUND_KEYWORD));
        // 삭제(Hard)
        keywordRepository.delete(findKeyword);
    }

    @Override
    public List<KeywordResponse> getKeywords(User user) {
        // 키워드 목록 조회
        return keywordRepository.findByUserIdAndStatusOrderByCreatedAtDesc(user.getId(), StatusType.ACTIVE)
                .stream()
                .map(KeywordResponse::new)
                .toList();
    }
}
