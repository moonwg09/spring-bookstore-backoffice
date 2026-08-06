package com.backoffice.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.backoffice.model.ActivityLogVO;

@RunWith(SpringJUnit4ClassRunner.class)
// root-context.xml 경로가 본인 프로젝트와 일치하는지 확인해 주세요.
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class ActivityLogMapperTests {

    @Autowired
    private ActivityLogMapper mapper;

    @Test
    public void testInsertLog() {
        // Given: 테스트용 데이터 세팅
        ActivityLogVO log = new ActivityLogVO();
        // 주의: Employee 테이블에 1번 사원이 존재해야 무결성 제약조건에 걸리지 않습니다.
        // 사원이 없다면 null 로 세팅하거나 DB에 임의의 사원을 먼저 생성해 주세요.
        log.setEmployee_id(1L); 
        log.setAction("Mapper 단독 테스트: 로그 삽입 확인");

        // When: 매퍼 실행
        int count = mapper.insertLog(log);

        // Then: 결과 확인
        System.out.println("====== Mapper 테스트 시작 ======");
        System.out.println("INSERT 실행 결과 (영향받은 행의 수): " + count);
        System.out.println("====== Mapper 테스트 종료 ======");
    }
}
