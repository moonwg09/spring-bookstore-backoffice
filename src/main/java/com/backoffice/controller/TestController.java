package com.backoffice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.backoffice.aop.LogActivity;

@Controller
@RequestMapping("/test")
public class TestController {

    @LogActivity("AOP 컨트롤러 가로채기 테스트")
    @GetMapping("/log")
    @ResponseBody
    public String testLogAop() {
        System.out.println("--- 1. 컨트롤러의 실제 비즈니스 로직이 실행되었습니다. ---");
        
        //  이 컨트롤러가 AOP 포장지(CGLIB)에 잘 감싸져 있는지 확인하는 결정적 단서!
        System.out.println("현재 클래스의 진짜 정체: " + this.getClass().getName());
        
        return "AOP Test Complete!";
    }
}
