package com.backoffice.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
// 본인의 root-context.xml 경로에 맞게 지정해 주세요
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class ActivityLogServiceTests {

    @Autowired
    private ActivityLogService service;

    @Test
    public void testRegisterLog() {
        // 사원 테이블에 1번 사원이 존재한다고 가정하거나, 없다면 null로 변경하여 테스트 가능합니다.
        Long testEmployeeId = 1L; 
        String testAction = "단위 테스트: 서비스 단독 로그 적재 확인";
        
        service.registerLog(testEmployeeId, testAction);
        System.out.println("====== 로그 데이터 삽입 테스트 완료 ======");
    }
}
