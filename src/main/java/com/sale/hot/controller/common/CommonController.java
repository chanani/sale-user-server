package com.sale.hot.controller.common;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "Common API Controller", description = "공통으로 사용하는 Thymeleaf 를 제공합니다.")
@Controller
@RequiredArgsConstructor
public class CommonController {

    @GetMapping(value = "/login")
    public String login(){
        return "login";
    }

}
