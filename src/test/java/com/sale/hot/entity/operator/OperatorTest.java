package com.sale.hot.entity.operator;

import com.sale.hot.domain.operator.repository.OperatorRepository;
import com.sale.hot.entity.common.constant.Gender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OperatorTest {

    @Autowired
    OperatorRepository operatorRepository;

    @Test
    public void sNames() throws Exception {
        Operator operator = Operator.builder()
                .operatorId("admin")
                .password("zxc123!@#")
                .name("관리자")
                .gender(Gender.M)
                .birth(LocalDate.now())
                .build();

        Operator save = operatorRepository.save(operator);
        System.out.println("save = " + save);

    }

}