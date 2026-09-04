package com.backoffice.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.backoffice.model.EmployeeVO;
import com.backoffice.model.MemberVO;
import com.backoffice.service.EmployeeService;
import com.backoffice.service.MemberService;

@Controller
@RequestMapping("/shop")
public class MemberController {

    @Autowired
    private MemberService memberService;
    
    @Autowired
    private EmployeeService employeeService;

    // 1. 로그인 페이지 이동
    @GetMapping("/login")
    public String loginPage() {
        return "shop/login";
    }

 // 서비스 주입이 안 되어 있다면 상단에 추가되어 있는지 확인해주세요.
    // @Autowired
    // private EmployeeService employeeService;

    // 2. 통합 로그인 처리 (직원 우선 검사 후 일반 회원 검사)
    @PostMapping("/login")
    public String loginProcess(@RequestParam("loginId") String loginId,
                               @RequestParam("password") String password,
                               HttpSession session) {
        
        // 1. 먼저 Employee (백오피스 관리자/직원) 테이블 로그인 시도
        EmployeeVO employee = employeeService.login(loginId, password);
        if (employee != null) {
            // 직원 세션 저장
            session.setAttribute("loginEmployee", employee);
            // 백오피스 메인 페이지로 이동 (프로젝트의 백오피스 시작 경로)
            return "redirect:/admin/hr/list"; 
        }
        
        // 2. 직원이 아니라면 기존대로 Member (쇼핑몰 일반 회원) 테이블 로그인 시도
        MemberVO member = memberService.login(loginId, password);
        if (member != null) {
            // 일반 회원 세션 저장
            session.setAttribute("loginUser", member);
            // 쇼핑몰 메인 페이지로 이동
            return "redirect:/shop/main";
        }
        
        // 3. 둘 다 일치하는 계정이 없는 경우 로그인 페이지로 리다이렉트
        return "redirect:/shop/login?error=true";
    }

    // 3. 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/shop/main";
    }

    // 4. 회원가입 페이지 이동
    @GetMapping("/join")
    public String joinPage() {
        return "shop/join";
    }

    // 5. 회원가입 처리
    @PostMapping("/join")
    public String joinProcess(MemberVO vo) {
        memberService.register(vo);
        return "redirect:/shop/login";
    }

    // 6. 카카오 로그인 간이 리다이렉트 (실무 OAuth 연동 진입점)
    @GetMapping("/kakao/callback")
    public String kakaoCallback(@RequestParam("code") String code, HttpSession session) {
        // 실무에서는 여기서 Kakao API 서버로 Access Token을 요청하고 유저 정보를 가져옵니다.
        // 테스트 편의를 위해 가상의 카카오 유저 정보로 자동 로그인/가입 처리 시뮬레이션
        String mockKakaoId = "999888777";
        String mockNickname = "카카오회원";
        String mockEmail = "kakao@test.com";

        MemberVO member = memberService.kakaoLoginOrRegister(mockKakaoId, mockNickname, mockEmail);
        session.setAttribute("loginUser", member);
        
        return "redirect:/shop/main";
    }
}
