package com.sale.hot.domain.attend.service;

import com.sale.hot.domain.attend.repository.AttendRepository;
import com.sale.hot.entity.attend.Attend;
import com.sale.hot.entity.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DefaultAttendService implements AttendService{

    private final AttendRepository attendRepository;

    @Override
    public void save(User user, LocalDate now) {
        Attend attend = Attend.builder()
                .user(user)
                .attendDate(now)
                .build();
        attendRepository.save(attend);
    }
}
