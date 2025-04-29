package com.sale.hot.entity.companyNotification;

import com.sale.hot.domain.company.repository.CompanyRepository;
import com.sale.hot.domain.companyNotification.repository.CompanyNotificationRepository;
import com.sale.hot.entity.company.Company;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CompanyNotificationTest {

    @Autowired
    CompanyNotificationRepository companyNotificationRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Test
    public void sNames() throws Exception {

        List<Company> companies = companyRepository.findAll();

        CompanyNotification notification = CompanyNotification.builder()
                .company(companies.get(0))
                .title("기업 알림 제목입니다.")
                .message("기업 알림 내용입니다.")
                .build();

        CompanyNotification save = companyNotificationRepository.save(notification);
        System.out.println("save = " + save.getCompany());

    }


}