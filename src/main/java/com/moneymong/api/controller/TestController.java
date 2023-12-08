package com.moneymong.api.controller;

import com.moneymong.api.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TestController {
    private final TestService testService;

    @GetMapping("/test/create")
    public void create(
            @RequestParam("name") String name
    ) {
        testService.create(name);
    }
}