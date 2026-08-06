package com.backoffice.service;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.backoffice.model.InventoryVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class InventoryServiceTests {

    @Autowired
    private InventoryService service;

    // 1. 재고 수량 증감(입고 반영) 테스트
    @Test
    public void testModifyStock() {
        InventoryVO vo = new InventoryVO();
        
        //  [수정 필수] Step 2에서 이미 재고 테이블(Inventory)에 등록해 두셨던 실제 도서 ID를 넣으세요!
        vo.setBook_id(21L);      
        vo.setCurrent_stock(50); // 기존 수량에 +50권 추가 입고
        
        boolean result = service.modifyStock(vo);
        System.out.println(">>> [Step 4] 재고 수량 50권 증가 반영 성공 여부: " + result);
        
        // 반영 후 변경된 실재고 검증
        InventoryVO updated = service.getInventoryByBookId(vo.getBook_id());
        System.out.println(">>> [최종 실재고 수량]: " + updated.getCurrent_stock() + "권");
    }

    // 2. 안전 재고 변경 테스트
    @Test
    public void testModifySafetyStock() {
        InventoryVO vo = new InventoryVO();
        vo.setBook_id(8L);      // 위와 동일한 도서 ID
        vo.setSafety_stock(20);  // 안전 재고 기준을 20권으로 상향 조정
        
        service.modifySafetyStock(vo);
        System.out.println(">>> [Step 4] 안전 재고 20권으로 변경 완료");
    }

    // 3. 전체 재고 목록 서비스 조회 테스트
    @Test
    public void testGetList() {
        List<InventoryVO> list = service.getInventoryList();
        System.out.println(">>> [전체 재고 현황 (총 " + list.size() + "건)]");
        for (InventoryVO item : list) {
            System.out.println(" - 도서 ID: " + item.getBook_id() 
                             + " | 제목: " + item.getBook_title() 
                             + " | 실재고: " + item.getCurrent_stock());
        }
    }
}