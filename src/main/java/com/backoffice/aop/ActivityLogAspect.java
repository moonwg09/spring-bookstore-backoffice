package com.backoffice.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.backoffice.service.ActivityLogService;

@Aspect
@Component
public class ActivityLogAspect {

    public ActivityLogAspect() {
        System.out.println("======================================");
        System.out.println("   ActivityLogAspect 빈 생성 성공!   ");
        System.out.println("======================================");
    }

    @Autowired
    private ActivityLogService logService;

    //  레이더 업그레이드: 경로를 직접 명시하고, 실행 전(@Before)에 무조건 낚아챕니다.
    @Before("@annotation(com.backoffice.aop.LogActivity)")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("[AOP 레이더 작동] 가로채기 성공!");
        
        try {
            // 1. 실행되는 메서드 정보 강제로 뜯어오기
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            
            // 2. 메서드에 붙은 @LogActivity 어노테이션 정보 가져오기
            LogActivity logActivity = method.getAnnotation(LogActivity.class);
            String action = logActivity.value();
            
            System.out.println("감지된 액션: " + action);

            // 3. DB 저장 로직 실행 (임시 사원번호 1번)
            Long employeeId = 1L;
            if (employeeId != null) {
                logService.registerLog(employeeId, action);
                System.out.println("[Activity Log 저장 성공] " + employeeId + "번 사원 - " + action);
            }

        } catch (Exception e) {
            System.out.println("AOP 내부 처리 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
