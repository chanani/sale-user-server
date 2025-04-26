package com.sale.hot.entity.report;

import com.sale.hot.domain.report.repository.ReportRepository;
import com.sale.hot.domain.user.repository.UserRepository;
import com.sale.hot.entity.common.constant.ReportType;
import com.sale.hot.entity.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReportTest {

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void sNames() throws Exception {

        List<User> users = userRepository.findAll();
        Report report = Report.builder()
                .type(ReportType.USER)
                .reason("욕설로 인한 신고 드립니다.")
                .targetId(3L)
                .user(users.get(0))
                .build();

        Report save = reportRepository.save(report);
        System.out.println("save = " + save.getReason());


    }

}