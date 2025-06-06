package com.sale.hot.controller.grade;

import com.sale.hot.domain.grade.service.GradeService;
import com.sale.hot.domain.grade.service.dto.response.GradeResponse;
import com.sale.hot.global.annotation.NoneAuth;
import com.sale.hot.global.response.DataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Grade API Controller", description = "등급 관련 API를 제공합니다.")
@RestController
@RequiredArgsConstructor
public class GradeApiController {

    private final GradeService gradeService;

    @Operation(summary = "등급 목록 API",description = "등급 목록을 조회합니다.")
    @NoneAuth
    @GetMapping("/api/v1/none/grades")
    public ResponseEntity<DataResponse<List<GradeResponse>>> getGrades() {
        List<GradeResponse> grades = gradeService.getGrades();
        return ResponseEntity.ok(DataResponse.send(grades));
    }

}
