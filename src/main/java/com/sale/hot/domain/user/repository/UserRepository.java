package com.sale.hot.domain.user.repository;

import com.sale.hot.entity.common.constant.StatusType;
import com.sale.hot.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUserId(String userId);
    boolean existsByPhoneAndStatus(String phone, StatusType statusType);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

    Optional<User> findByUserIdAndStatus(String userId, StatusType statusType);

    Optional<User> findByIdAndStatus(Long userNo, StatusType statusType);
}
