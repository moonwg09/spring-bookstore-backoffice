package com.backoffice.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.backoffice.model.EmployeeVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class EmployeeServiceTests {

    // 방금 에러를 잡고 완성한 EmployeeService 공장을 주입받습니다.
    @Autowired
    private EmployeeService employeeService;

    @Test
    public void testLoginFailure() {
        System.out.println("====== 로그인 실패 시나리오 테스트 ======");
        
        // 일부러 틀린 비밀번호나 없는 ID를 입력해 봅니다.
        String wrongId = "admin";
        String wrongPw = "wrong1234";
        
        EmployeeVO vo = employeeService.login(wrongId, wrongPw);
        
        // 결과 검증: 실패했다면 서비스가 null을 반환해야 합니다.
        assertNull(vo);
        System.out.println("서비스 검증 완료: 틀린 비밀번호 입력 시 null 반환 확인!");
    }

   
}