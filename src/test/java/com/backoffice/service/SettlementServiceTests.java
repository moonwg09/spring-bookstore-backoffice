package com.backoffice.service;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.backoffice.model.SettlementVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class SettlementServiceTests {

    @Autowired
    private SettlementService service;

    @Test
    public void testSettlementFlow() {
        // 1. 신규 정산 데이터 등록 (예: 1번 출판사, 당월 기준 정산액 406만원)
        SettlementVO vo = new SettlementVO();
        vo.setPublisher_id(1L);               //  실제 존재하는 출판사 ID
        vo.setSettlement_month("2026-07");    // 당월 기준 정산월
        vo.setTotal_amount(4060000);          // 지급 대상액
        
        service.registerSettlement(vo);
        System.out.println(">>> [Step 4] 서비스 정산 등록 성공! ID: " + vo.getSettlement_id());

        // 2. 전체 정산 목록 조회
        List<SettlementVO> list = service.getSettlementList();
        System.out.println(">>> [정산 내역 전체 조회] 총 " + list.size() + "건");

        // 3. 정산 지급 마감 처리 트랜잭션 검증 (READY -> PROCESSED)
        if (!list.isEmpty()) {
            Long targetId = list.get(0).getSettlement_id();
            boolean isProcessed = service.processSettlement(targetId);
            System.out.println(">>> [Step 4] 정산 ID (" + targetId + "번) 지급 마감 성공 여부: " + isProcessed);
        }
    }
}