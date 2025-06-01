package com.sale.hot.domain.user.service;

import com.sale.hot.domain.attend.repository.AttendRepository;
import com.sale.hot.domain.attend.service.AttendService;
import com.sale.hot.domain.grade.repository.GradeRepository;
import com.sale.hot.domain.grade.service.GradeService;
import com.sale.hot.domain.grade.service.dto.response.GradeUpdateResponse;
import com.sale.hot.domain.user.repository.UserRepository;
import com.sale.hot.domain.user.service.dto.request.JoinRequest;
import com.sale.hot.domain.user.service.dto.request.LoginRequest;
import com.sale.hot.domain.user.service.dto.request.UserUpdatePasswordRequest;
import com.sale.hot.domain.user.service.dto.request.UserUpdateRequest;
import com.sale.hot.domain.user.service.dto.response.LoginResponse;
import com.sale.hot.domain.user.service.dto.response.UserInfoResponse;
import com.sale.hot.entity.attend.Attend;
import com.sale.hot.entity.common.constant.StatusType;
import com.sale.hot.entity.common.constant.UserType;
import com.sale.hot.entity.grade.Grade;
import com.sale.hot.entity.user.User;
import com.sale.hot.global.exception.OperationErrorException;
import com.sale.hot.global.exception.dto.ErrorCode;
import com.sale.hot.global.jwt.JWTProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final GradeRepository gradeRepository;
    private final AttendRepository attendRepository;
    private final AttendService attendService;
    private final PasswordEncoder passwordEncoder;
    private final JWTProvider jwtProvider;
    private final GradeService gradeService;

    @Override
    @Transactional
    public void join(JoinRequest request) {
        // 아이디 중복 검사
        checkUserId(request.userId());
        // 비밀번호 일치 여부 확인
        equalPassword(request.password(), request.passwordCheck());
        // 연락처 중복 검사
        checkUserPhone(request.phone());
        // 이메일 중복 검사
        checkUserEmail(request.email());
        // 닉네임 중복 검사
        if (StringUtils.hasText(request.nickname())) {
            checkUserNickname(request.nickname());
        }

        // Entity로 변환
        User newUser = request.toEntity();
        // 최저 등급 조회
        Grade grade = gradeRepository.findFirstByStatusOrderByRankingAsc(StatusType.ACTIVE).orElseThrow();
        newUser.addGrade(grade);

        // 회원가입
        User user = userRepository.save(newUser);
        log.info("join success ! user id = {}", user.getUserId());
    }

    @Override
    @Transactional
    public LoginResponse login(LoginRequest request) throws Exception {
        // 아이디로 정보 조회 및 비밀번호 일치 여부 조회
        User user = userRepository.findByUserIdAndStatus(request.userId(), StatusType.ACTIVE)
                .filter(u -> passwordEncoder.matches(request.password(), u.getPassword()))
                .orElseThrow(() -> new OperationErrorException(ErrorCode.NOT_EQUAL_ID_PASSWORD));

        // accessToken 발급
        String accessToken = jwtProvider.createAccessToken(user.getId(), UserType.USER);
        // refreshToken 발급
        String refreshToken = jwtProvider.createRefreshToken(user.getId(), UserType.USER);

        // 최근 접속일 정보 업데이트
        user.updateLastVisit();

        // 출석 등록 : 오늘 날짜 조회 후 오늘 로그인하지 않았다면 출석 체크 진행
        LocalDate now = LocalDate.now();
        String nextGrade = null;
        if (!attendRepository.existsByUserIdAndAttendDate(user.getId(), now)) {
            // 출석 체크
            attendService.save(user, now);
            // 회원 정보에 출석 + 1
            user.plusAttendCount();
            // 등업 대상자인지 확인
            nextGrade = gradeService.upgradeGrade(user);
        }
        // 등업 여부 확인(nextGrade가 null일 경우 false)
        GradeUpdateResponse gradeUpdated = new GradeUpdateResponse(StringUtils.hasText(nextGrade), nextGrade);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .gradeUpdate(gradeUpdated)
                .build();
    }

    @Override
    @Transactional
    public void updateUser(UserUpdateRequest request, User user) {
        // 연락처 중복 검사
        checkUserPhoneNotId(request.phone(), user.getId());
        // 이메일 중복 검사
        checkUserEmailNotId(request.email(), user.getId());
        // 닉네임 중복 검사
        checkUserNicknameNotId(request.nickname(), user.getId());

        // 회원 Entity 조회
        User findUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new OperationErrorException(ErrorCode.NOT_FOUND_USER));
        User newUser = request.toEntity();
        // 수정
        findUser.update(newUser);
    }

    @Override
    @Transactional
    public void updateUserPassword(UserUpdatePasswordRequest request, User user) {
        // 비밀번호 일치 여부 확인
        equalPassword(request.password(), request.passwordCheck());
        // 회원 정보 조회
        User findUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new OperationErrorException(ErrorCode.NOT_FOUND_USER));
        // 비밀번호 변경
        findUser.updatePassword(request.password());
    }

    @Override
    public UserInfoResponse getInfo(User user) {
        User findUser = userRepository.findByIdAndStatus(user.getId(), StatusType.ACTIVE)
                .orElseThrow(() -> new OperationErrorException(ErrorCode.NOT_FOUND_USER));
        return new UserInfoResponse(findUser);
    }


    /**
     * 회원 아이디 중복 체크(탈퇴한 아이디로 가입 불가)
     * 중복일 경우 바로 예외 발생
     *
     * @param userId 회원 아이디
     */
    private void checkUserId(String userId) {
        if (userRepository.existsByUserId(userId)) {
            throw new OperationErrorException(ErrorCode.EXISTS_USER_ID);
        }
    }

    /**
     * 비밀번호 동일 여부 체크
     * 동일하지 않을 경우 예외 발생
     *
     * @param password      비밀번호
     * @param passwordCheck 확인 비밀번호
     */
    private void equalPassword(String password, String passwordCheck) {
        if (!password.equals(passwordCheck)) {
            throw new OperationErrorException(ErrorCode.NOT_EQUAL_PASSWORD);
        }
    }

    /**
     * 회원 연락처 중복 체크
     * 중복일 경우 바로 예외 발생
     *
     * @param phone 연락처
     */
    private void checkUserPhone(String phone) {
        if (userRepository.existsByPhoneAndStatus(phone, StatusType.ACTIVE)) {
            throw new OperationErrorException(ErrorCode.EXISTS_USER_PHONE);
        }
    }


    private void checkUserPhoneNotId(String phone, Long userId) {
        if (userRepository.existsByPhoneAndStatusAndIdNot(phone, StatusType.ACTIVE, userId)) {
            throw new OperationErrorException(ErrorCode.EXISTS_USER_PHONE);
        }
    }

    /**
     * 회원 이메일 중복 체크
     * 중복일 경우 바로 예외 발생
     *
     * @param email 이메일
     */
    private void checkUserEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new OperationErrorException(ErrorCode.EXISTS_USER_EMAIL);
        }
    }

    /**
     * 회원 이메일 중복 체크(본인 이메일 제외)
     * 중복일 경우 바로 예외 발생
     *
     * @param email 이메일
     */
    private void checkUserEmailNotId(String email, Long userId) {
        if (userRepository.existsByEmailAndIdNot(email, userId)) {
            throw new OperationErrorException(ErrorCode.EXISTS_USER_EMAIL);
        }
    }

    /**
     * 회원 닉네임 중복 체크
     * 중복일 경우 바로 예외 발생
     *
     * @param nickname 닉네임
     */
    private void checkUserNickname(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new OperationErrorException(ErrorCode.EXISTS_USER_NICKNAME);
        }
    }

    /**
     * 회원 닉네임 중복 체크(본인 닉네임 제외)
     * 중복일 경우 바로 예외 발생
     *
     * @param nickname 닉네임
     */
    private void checkUserNicknameNotId(String nickname, Long userId) {
        if (userRepository.existsByNicknameAndIdNot(nickname, userId)) {
            throw new OperationErrorException(ErrorCode.EXISTS_USER_NICKNAME);
        }
    }

}
