package com.backoffice.mapper;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.backoffice.model.PurchaseOrderVO;
import com.backoffice.model.PurchaseOrderItemVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class PurchaseOrderMapperTests {

    @Autowired
    private PurchaseOrderMapper mapper;

    @Test
    public void testInsertPurchaseOrderWithItems() {
        // 1. 발주 마스터 생성 (예: 1번 출판사에, 1번 관리자가 발주 신청)
        PurchaseOrderVO po = new PurchaseOrderVO();
        po.setPublisher_id(1L); // 실제 DB에 존재하는 publisher_id 입력
        po.setEmployee_id(1L);  // 아까 등록한 admin 사원(ID: 1) 입력
        
        mapper.insertPurchaseOrder(po);
        System.out.println(">>> [마스터 등록 완료] 생성된 발주 번호 po_id: " + po.getPo_id());

        // 2. 발주 상세 품목 2건 등록 (생성된 po_id를 활용)
        PurchaseOrderItemVO item1 = new PurchaseOrderItemVO();
        item1.setPo_id(po.getPo_id());
        item1.setBook_id(2L);   // 실제 DB에 존재하는 book_id 입력
        item1.setOrder_qty(50); // 50권 발주
        mapper.insertPurchaseOrderItem(item1);

        PurchaseOrderItemVO item2 = new PurchaseOrderItemVO();
        item2.setPo_id(po.getPo_id());
        item2.setBook_id(2L);   // 실제 DB에 존재하는 다른 book_id 입력
        item2.setOrder_qty(30); // 30권 발주
        mapper.insertPurchaseOrderItem(item2);

        System.out.println(">>> [상세 품목 등록 완료] 2개 도서 품목 발주 신청 성공!");
    }

    @Test
    public void testSelectPurchaseOrderList() {
        List<PurchaseOrderVO> list = mapper.selectPurchaseOrderList();
        System.out.println(">>> [발주 목록 전체 조회]");
        for (PurchaseOrderVO po : list) {
            System.out.println("■ 발주 마스터: " + po);
            
            // 각 발주 번호에 속한 품목 조회
            List<PurchaseOrderItemVO> items = mapper.selectPurchaseOrderItemsByPoId(po.getPo_id());
            for (PurchaseOrderItemVO item : items) {
                System.out.println("    └─ 상세 품목: " + item);
            }
        }
    }
}