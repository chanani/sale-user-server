package com.sale.hot.global.eventListener.keyword;

import com.sale.hot.domain.keyword.repository.KeywordRepository;
import com.sale.hot.domain.notification.service.NotificationService;
import com.sale.hot.domain.user.repository.UserRepository;
import com.sale.hot.entity.common.constant.BooleanYn;
import com.sale.hot.entity.keyword.Keyword;
import com.sale.hot.entity.notification.Notification;
import com.sale.hot.entity.post.Post;
import com.sale.hot.entity.user.User;
import com.sale.hot.global.eventListener.keyword.dto.KeywordEvent;
import lombok.RequiredArgsConstructor;
import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.*;

@Component
@RequiredArgsConstructor
public class KeywordEventListener {

    private final NotificationService notificationService;
    private final KeywordRepository keywordRepository;
    private final UserRepository userRepository;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void saveKeyword(KeywordEvent keywordEvent) {
        String postTitle = keywordEvent.post().getTitle();

        // 모든 키워드 조회 후 키워드별 사용자 ID 맵핑
        List<Keyword> allKeywords = keywordRepository.findAll();
        Map<String, List<Long>> keywordToUsersMap = new HashMap<>();

        for (Keyword keyword : allKeywords) {
            keywordToUsersMap.computeIfAbsent(keyword.getName(), k -> new ArrayList<>())
                    .add(keyword.getUser().getId());
        }

        // Aho-Corasick 트라이 구성
        Trie trie = Trie.builder()
                .addKeywords(keywordToUsersMap.keySet())
                .build();

        // 게시글 제목에서 키워드 찾기
        Collection<Emit> emits = trie.parseText(postTitle);

        // 발견된 키워드 별로 알림 생성을 위한 사용자 목록 구성
        Map<Long, Set<String>> userToMatchedKeywords = new HashMap<>();

        for (org.ahocorasick.trie.Emit emit : emits) {
            String keyword = emit.getKeyword();
            List<Long> userIds = keywordToUsersMap.get(keyword);

            for (Long userId : userIds) {
                userToMatchedKeywords
                        .computeIfAbsent(userId, k -> new HashSet<>())
                        .add(keyword);
            }
        }

        // 각 사용자에게 알림 생성
        for (Map.Entry<Long, Set<String>> entry : userToMatchedKeywords.entrySet()) {
            User user = userRepository.findById(entry.getKey()).orElse(null);
            if (user != null) {
                Notification notification = Notification.builder()
                        .user(user)
                        .title("키워드 알람이 도착하였습니다.")
                        .content(entry.getValue() + " : " + postTitle)
                        .isRead(BooleanYn.N)
                        .build();
                notificationService.save(notification);
            }
        }
    }
}
