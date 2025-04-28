package com.sale.hot.entity.company;

import com.sale.hot.domain.company.repository.CompanyRepository;
import com.sale.hot.entity.common.constant.Gender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CompanyTest {

    @Autowired
    CompanyRepository companyRepository;

    @Test
    public void create() throws Exception {

        Company company = Company.builder()
                .companyId("testCompany")
                .email("testCompany@gmail.com")
                .password("asd123!@#")
                .name("김사원")
                .phone("01000000001")
                .gender(Gender.M)
                .birth(LocalDate.now())
                .businessNumber("302-111-3333")
                .businessType("소매업")
                .companyName("쿠팡")
                .address("서울시")
                .zipcode("33111")
                .build();

        Company findCompany = companyRepository.save(company);
        System.out.println("findCompany = " + findCompany.getCompanyName());

    }

}