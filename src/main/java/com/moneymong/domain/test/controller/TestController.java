package com.moneymong.domain.test.controller;

import com.moneymong.global.exception.problem.ProblemParameters;
import com.moneymong.domain.test.TestEntity;
import com.moneymong.domain.test.exception.TestNotFoundProblem;
import com.moneymong.domain.test.service.TestService;
import com.moneymong.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TestController {
    private final TestService testService;

    @Operation(summary = "Test Entity 생성")
    @PostMapping("/test/create")
    public ApiResponse<TestEntity> create(
            @RequestParam("name") String name
    ) {
        TestEntity testEntity = testService.create(name);
        return ApiResponse.success("[Success] create testEntity", testEntity);
    }

    @Operation(summary = "Test Entity 조회")
    @GetMapping("/test/{id}")
    public ApiResponse<TestEntity> find(
            @PathVariable("id") Long id
    ) {
        TestEntity testEntity = testService.find(id);
        return ApiResponse.success("[Success] find testEntity", testEntity);
    }

    @Operation(summary = "Custom 예외 발생")
    @GetMapping("/ex")
    public Void exception() {
        Long testId = 1L;
        throw new TestNotFoundProblem(ProblemParameters.of("testId", testId));
    }

    @Operation(summary = "Exception 발생")
    @GetMapping("/ex2")
    public Void exception2() throws Exception {
        throw new Exception("error occurs");
    }

}
