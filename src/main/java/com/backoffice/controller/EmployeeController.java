package com.backoffice.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.backoffice.model.EmployeeVO;
import com.backoffice.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {
	
	private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;
    
    @GetMapping("/login")
    public void loginPage() {
    	log.info("--- [컨트롤러] 로그인 페이지 이동 --- ");
    }
    
    @PostMapping("/login")
    public String loginDo(@RequestParam("loginId") String loginId,
    					  @RequestParam("password") String password,
    					  HttpSession session,
    					  RedirectAttributes rttr) {
    	log.info("--- [컨트롤러] 로그인 처리 요청 ID: {}---", loginId);
    	
    	EmployeeVO vo = employeeService.login(loginId, password);
    	
    	if(vo != null) {
    		session.setAttribute("loginUser", vo);
    		log.info("[컨트롤러] 로그인 성공! 메인페이지로 리다이렉트");
    		return "redirect:/main";
    	} else {
    		rttr.addFlashAttribute("errorMsg", "아이디 또는 비밀번호가 일치하지 않습니다.");
    		log.warn("[컨트롤러] 로그인 실패! 다시 로그인 페이지로 이동");
    		return "redirect:/employee/login";
    	}
    }
    
    @GetMapping("/logout")
    public String logoutDo(HttpSession session) {
    	log.info("--- [EmployeeController] 로그아웃 요청 감지 ---");
    	
    	session.invalidate();
    	
    	log.info("세션 파기 완료: 로그인 페이지로 이동");
    	return "redirect:/employee/login";
    }

}

