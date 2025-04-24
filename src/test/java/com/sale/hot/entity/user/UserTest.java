package com.sale.hot.entity.user;

import com.sale.hot.domain.grade.repository.GradeRepository;
import com.sale.hot.domain.user.repository.UserRepository;
import com.sale.hot.entity.common.constant.Gender;
import com.sale.hot.entity.common.constant.SocialType;
import com.sale.hot.entity.grade.Grade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootTest
@Transactional
class UserTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    GradeRepository gradeRepository;

    @Test
    public void createTest(){
        Grade grade = Grade.builder()
                .attendance(0)
                .name("Bronze")
                .post(0)
                .comment(0)
                .build();

        Grade gradeResult = gradeRepository.save(grade);

        User user = User.builder()
                .userId("chanhan12")
                .password("asdf1234")
                .birth(LocalDate.now())
                .email("aa@aa.aa")
                .grade(gradeResult)
                .gender(Gender.M)
                .phone("010-9914-7710")
                .profile("profile")
                .name("이찬한")
                .socialType(SocialType.LOCAL)
                .build();

        User save = userRepository.save(user);
        System.out.println("save.getUserId() = " + save.getUserId());
    }
}