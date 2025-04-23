package com.sale.hot.domain.user.repository;

import com.sale.hot.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
