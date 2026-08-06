package com.backoffice.mapper;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.backoffice.model.InventoryVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class InventoryMapperTests {

    @Autowired
    private InventoryMapper mapper;

    // 1. 재고 초기 등록 테스트
    @Test
    public void testInsertInventory() {
        InventoryVO vo = new InventoryVO();
        
        //  [수정 필수] 실제 Book 테이블에 존재하면서, 아직 Inventory 테이블에 없는 book_id 입력
        vo.setBook_id(21L); 
        vo.setCurrent_stock(50); // 최초 재고 50권
        vo.setSafety_stock(10);  // 안전 재고 10권
        
        int result = mapper.insertInventory(vo);
        System.out.println(">>> [재고 초기 등록 결과(1=성공)]: " + result);
    }

    // 2. 재고 증감(입고/출고) 테스트
    @Test
    public void testUpdateAddStock() {
        InventoryVO vo = new InventoryVO();
        vo.setBook_id(8L);      // 위에서 등록한 도서 ID
        vo.setCurrent_stock(30); // 30권 입고 (기존 50권 + 30권 = 80권 예상)
        
        int result = mapper.updateAddStock(vo);
        System.out.println(">>> [재고 증감 반영 결과(1=성공)]: " + result);
        
        // 반영 후 실재고 확인
        InventoryVO updated = mapper.selectInventoryByBookId(8L);
        System.out.println(">>> [반영 후 현재 재고 수량]: " + updated.getCurrent_stock());
    }

    // 3. 전체 재고 현황 조회 테스트
    @Test
    public void testSelectList() {
        List<InventoryVO> list = mapper.selectInventoryList();
        System.out.println(">>> [전체 도서 재고 현황 목록]");
        for (InventoryVO item : list) {
            System.out.println("도서번호: " + item.getBook_id() 
                             + " | 도서명: " + item.getBook_title() 
                             + " | 현재재고: " + item.getCurrent_stock() 
                             + " | 안전재고: " + item.getSafety_stock());
        }
    }
}