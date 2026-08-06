package com.backoffice.mapper;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.backoffice.model.EmployeeVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class) // JUnit에게 스프링 환경에서 테스트를 실행하라고 지시
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml") // root-context.xml의 HikariCP 설정을 로드
@Log4j
public class EmployeeMapperTests {
	
	@Autowired
	private EmployeeMapper employeeMapper;
	
	@Test
	public void testReadEmployee() {
		log.info("---------------------------------------");
        log.info("[단위 테스트] readEmployee 쿼리 검증 시작");
        log.info("---------------------------------------");
        
        
        EmployeeVO vo = employeeMapper.readEmployee("admin");
        
        log.info("오라클 DB에서 수신된 결과 객체: " + vo);
        
  
        assertNotNull(vo); 
        
        log.info("---------------------------------------");
        log.info("[단위 테스트 성공] DB 데이터 수신 및 VO 바인딩 완료");
        log.info("---------------------------------------");
	}

}
