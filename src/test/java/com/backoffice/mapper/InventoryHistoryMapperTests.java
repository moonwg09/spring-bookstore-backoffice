package com.backoffice.mapper;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.backoffice.model.InventoryHistoryVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class InventoryHistoryMapperTests {

    @Autowired
    private InventoryHistoryMapper mapper;

    // 1. 재고 변동 히스토리 등록 테스트
    @Test
    public void testInsertHistory() {
        InventoryHistoryVO vo = new InventoryHistoryVO();
        
        //  [수정 필수] 실제 DB에 존재하는 도서 ID와 사원 ID를 넣으세요!
        vo.setBook_id(8L);      // 예: 이전 Step에서 테스트한 도서 ID
        vo.setEmployee_id(1L);   // 예: admin 사원 ID (1번)
        vo.setChange_qty(50);    // 50권 입고 (+)
        vo.setReason("발주 입고 (PO-1001번 건)");
        
        int result = mapper.insertHistory(vo);
        System.out.println(">>> [Step 2] 히스토리 등록 결과(1=성공): " + result);

        // 출고(음수) 테스트도 추가 진행
        InventoryHistoryVO vo2 = new InventoryHistoryVO();
        vo2.setBook_id(8L);
        vo2.setEmployee_id(1L);
        vo2.setChange_qty(-5);   // 5권 파손 출고 (-)
        vo2.setReason("배송 중 파손으로 인한 폐기 처리");
        mapper.insertHistory(vo2);
    }

    // 2. 특정 도서의 이력 조회 테스트
    @Test
    public void testSelectByBookId() {
        // 위에서 테스트한 실제 도서 ID 넣기
        List<InventoryHistoryVO> list = mapper.selectHistoryByBookId(8L);
        System.out.println(">>> [도서 ID: 10L의 입출고 변동 내역]");
        for (InventoryHistoryVO h : list) {
            System.out.println(" - [" + h.getCreated_at() + "] 변동수량: " 
                             + h.getChange_qty() + "권 | 사유: " + h.getReason() 
                             + " | 처리자: " + h.getEmployee_name());
        }
    }
}