package com.backoffice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.backoffice.mapper.EmployeeMapper;
import com.backoffice.model.EmployeeVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
//  @Log4j를 지우고, 자바 표준 SLF4J 팩토리를 사용해 로그 객체를 직접 생성합니다. 이러면 log4j 에러가 원천 차단됩니다.
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    
    private final EmployeeMapper employeeMapper;

    @Override
    public EmployeeVO login(String loginId, String password) {
        log.info("--- [서비스 레이어] 로그인 인증 시도 ID: {} ---", loginId);
        
        EmployeeVO vo = employeeMapper.readEmployee(loginId);
        
        if (vo != null && vo.getPassword().equals(password)) {
            log.info("인증 성공! 사원명: {}", vo.getName());
            return vo;
        }
        
        log.warn("인증 실패! ID가 없거나 비밀번호가 일치하지 않습니다.");
        return null;
    }
}
