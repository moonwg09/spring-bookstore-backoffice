package com.backoffice.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * 1. 시스템 메인 대시보드 페이지 (GET)
     * 로그인 성공 후 리다이렉트되어 들어오는 주소입니다.
     */
    @GetMapping({"/", "/main"})
    public String mainPage(HttpSession session, HttpServletResponse response) {
        // 보안 검증: 세션에 로그인한 사원 정보(loginUser)가 없다면 로그인 페이지로 강제 추방!
        if (session.getAttribute("loginUser") == null) {
            return "redirect:/shop/main";
        }
        
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        
        response.setHeader("Pragma", "no-cache");
        
        response.setDateHeader("Expires", 0);
        
        // 세션이 있다면 정상적으로 /WEB-INF/views/main.jsp 화면을 보여줍니다.
        return "main";
    }

}