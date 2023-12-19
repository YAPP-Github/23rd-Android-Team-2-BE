package com.moneymong.domain.test.controller;

import com.moneymong.domain.test.TestEntity;
import com.moneymong.domain.test.service.TestService;
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
    public TestEntity create(
            @RequestParam("name") String name
    ) {
        return testService.create(name);
    }

    @Operation(summary = "Test Entity 조회")
    @GetMapping("/test/{id}")
    public TestEntity find(
            @PathVariable("id") Long id
    ) {
        return testService.find(id);
    }
}
