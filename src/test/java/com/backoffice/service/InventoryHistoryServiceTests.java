package com.backoffice.service;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.backoffice.model.InventoryHistoryVO;
import com.backoffice.model.InventoryVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class InventoryHistoryServiceTests {

    @Autowired
    private InventoryHistoryService historyService;
    
    @Autowired
    private InventoryService inventoryService;

    // 1. [통합 물류 테스트] 실재고 증감 + 히스토리 동시 등록 트랜잭션 검증
    @Test
    public void testAddStockWithHistory() {
        // 변경 전 실재고 조회
        Long targetBookId = 8L; // [수정 필수] 실제 DB에 존재하는 도서 ID 입력!
        Long adminId = 1L;       // [수정 필수] 실제 DB에 존재하는 사원 ID 입력!
        
        InventoryVO beforeInv = inventoryService.getInventoryByBookId(targetBookId);
        System.out.println(">>> [변경 전 실재고 수량]: " + beforeInv.getCurrent_stock() + "권");
        
        // 30권 입고(+) 통합 로직 실행
        InventoryVO addVO = new InventoryVO();
        addVO.setBook_id(targetBookId);
        addVO.setCurrent_stock(30); // 30권 증가
        
        boolean result = historyService.addStockWithHistory(addVO, adminId, "발주 도서 입고 완료 (트랜잭션 테스트)");
        System.out.println(">>> [Step 4] 실재고 반영 + 로그 기록 동시 성공 여부: " + result);
        
        // 변경 후 변경된 실재고 검증
        InventoryVO afterInv = inventoryService.getInventoryByBookId(targetBookId);
        System.out.println(">>> [변경 후 실재고 수량]: " + afterInv.getCurrent_stock() + "권 (30권 증가 확인!)");
    }

    // 2. 이력 조회 테스트
    @Test
    public void testGetHistoryList() {
        List<InventoryHistoryVO> list = historyService.getHistoryList();
        System.out.println(">>> [전체 재고 변동 히스토리 목록 (총 " + list.size() + "건)]");
        for (InventoryHistoryVO h : list) {
            System.out.println(" - 도서 ID: " + h.getBook_id() 
                             + " | 도서명: " + h.getBook_title() 
                             + " | 변동수량: " + h.getChange_qty() 
                             + " | 사유: " + h.getReason() 
                             + " | 담당자: " + h.getEmployee_name());
        }
    }
}