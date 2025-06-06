package com.sale.hot.domain.grade.service;

import com.sale.hot.domain.grade.repository.GradeRepository;
import com.sale.hot.domain.grade.service.dto.response.GradeResponse;
import com.sale.hot.domain.user.repository.UserRepository;
import com.sale.hot.entity.common.constant.StatusType;
import com.sale.hot.entity.grade.Grade;
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
public class DefaultGradeService implements GradeService {

    private final GradeRepository gradeRepository;
    private final UserRepository userRepository;

    @Override
    public List<GradeResponse> getGrades() {
        return gradeRepository.findByStatusOrderByRankingAsc(StatusType.ACTIVE).stream()
                .map(GradeResponse::new)
                .toList();
    }

    @Override
    @Transactional
    public String upgradeGrade(User user) {
        User findUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new OperationErrorException(ErrorCode.NOT_FOUND_USER));

        // 회원의 다음 등급 조회 (현재 등급보다 ranking이 높은 가장 낮은 등급)
        Grade nextGrade = gradeRepository.findFirstByRankingGreaterThanAndStatusOrderByRankingAsc(
                findUser.getGrade().getRanking(), StatusType.ACTIVE)
                .orElse(null);
        // 다음 등급이 없으면 이미 최고 등급
        if (nextGrade == null) {
            return null;
        }
        
        // 등급 업그레이드 조건 확인 후 조건 만족 시 등급 업그레이드
        if (checkUpgradeConditions(findUser, nextGrade)) {
            findUser.addGrade(nextGrade);
            return nextGrade.getName();
        }
        
        return null;
    }
    
    /**
     * 등급 업그레이드 조건 확인
     * @param user 사용자
     * @param nextGrade 다음 등급
     * @return 업그레이드 가능 여부
     */
    private boolean checkUpgradeConditions(User user, Grade nextGrade) {
        // 각 조건 충족 여부 확인
        boolean attendanceCondition = user.getAttendCount() >= nextGrade.getAttendance();
        boolean postCondition = user.getPostCount() >= nextGrade.getPost();
        boolean commentCondition = user.getCommentCount() >= nextGrade.getComment();
        // 모든 조건을 만족해야 등급 업그레이드 가능
        return attendanceCondition && postCondition && commentCondition;
    }

    /**
     * 등급명 중복 체크
     * 중복일 경우 바로 예외 발생
     *
     * @param name 등급명
     */
    private void checkGradeName(String name) {
        if (gradeRepository.existsByNameAndStatus(name, StatusType.ACTIVE)) {
            throw new OperationErrorException(ErrorCode.EXISTS_GRADE_NAME);
        }
    }

    /**
     * 등급명 중복 체크(해당 레코드 제외)
     * 중복일 경우 바로 예외 발생
     *
     * @param name 등급명
     */
    private void checkGradeNameNotId(Long gradeId, String name) {
        if (gradeRepository.existsByNameAndStatusAndIdNot(name, StatusType.ACTIVE, gradeId)) {
            throw new OperationErrorException(ErrorCode.EXISTS_GRADE_NAME);
        }
    }
}
