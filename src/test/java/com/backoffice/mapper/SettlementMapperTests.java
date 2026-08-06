package com.backoffice.mapper;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.backoffice.model.SettlementVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class SettlementMapperTests {

    @Autowired
    private SettlementMapper mapper;

    // 1. 월별 정산 데이터 생성 테스트
    @Test
    public void testInsertSettlement() {
        SettlementVO vo = new SettlementVO();
        vo.setPublisher_id(1L);               //  [수정 필수] 실제 DB에 존재하는 출판사 ID 입력
        vo.setSettlement_month("2026-07");    // 이번 달 기준 정산월
        vo.setTotal_amount(3180000);          // 정산 지급액 (예: 318만 원)
        
        int result = mapper.insertSettlement(vo);
        System.out.println(">>> [Step 2] 정산 등록 결과(1=성공): " + result);
        System.out.println(">>> 생성된 정산 번호 ID: " + vo.getSettlement_id());
    }

    // 2. 정산 내역 목록 조회 테스트
    @Test
    public void testSelectList() {
        List<SettlementVO> list = mapper.selectSettlementList();
        System.out.println(">>> [출판사별 월간 대금 정산 목록 전체 조회]");
        for (SettlementVO s : list) {
            System.out.println(" - [정산월: " + s.getSettlement_month() + "] " 
                             + "출판사: " + s.getPublisher_name() 
                             + " | 지급 대상액: " + s.getTotal_amount() + "원"
                             + " | 상태: " + s.getStatus());
        }
    }

    // 3. 정산 마감 상태 변경(READY -> PROCESSED) 테스트
    @Test
    public void testUpdateStatus() {
        List<SettlementVO> list = mapper.selectSettlementList();
        if (!list.isEmpty()) {
            Long targetId = list.get(0).getSettlement_id();
            
            SettlementVO updateVO = new SettlementVO();
            updateVO.setSettlement_id(targetId);
            updateVO.setStatus("PROCESSED"); // 'PROCESSED'(정산완료)로 변경
            
            int result = mapper.updateSettlementStatus(updateVO);
            System.out.println(">>> [Step 2] 정산 ID (" + targetId + "번) 마감 처리 결과: " + result);
        }
    }
}