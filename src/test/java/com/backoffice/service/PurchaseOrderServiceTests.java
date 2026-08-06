package com.backoffice.service;

import java.util.ArrayList;
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
public class PurchaseOrderServiceTests {

    @Autowired
    private PurchaseOrderService service;

    @Test
    public void testRegisterPurchaseOrder() {
        // 1. 발주 마스터 세팅
        PurchaseOrderVO poVO = new PurchaseOrderVO();
        poVO.setPublisher_id(1L); // 실제 존재하는 출판사 ID
        poVO.setEmployee_id(1L);  // 실제 존재하는 사원 ID
        
        // 2. 발주 상세 품목 리스트 생성 및 세팅
        List<PurchaseOrderItemVO> itemList = new ArrayList<>();
        
        PurchaseOrderItemVO item1 = new PurchaseOrderItemVO();
        item1.setBook_id(2L);     // 실제 존재하는 도서 ID 1
        item1.setOrder_qty(100);
        itemList.add(item1);
        
        PurchaseOrderItemVO item2 = new PurchaseOrderItemVO();
        item2.setBook_id(3L);     // 실제 존재하는 도서 ID 2
        item2.setOrder_qty(50);
        itemList.add(item2);
        
        // 3. 마스터 VO에 품목 리스트 결합
        poVO.setItemList(itemList);
        
        // 4. 서비스 호출 (트랜잭션 일괄 등록 테스트)
        service.registerPurchaseOrder(poVO);
        System.out.println(">>> [Step 4 승인] 서비스 트랜잭션을 통한 1:N 발주 등록 성공!");
    }

    @Test
    public void testGetListAndDetail() {
        // 1. 전체 목록 조회
        List<PurchaseOrderVO> list = service.getPurchaseOrderList();
        System.out.println(">>> [전체 발주 건수]: " + list.size());
        
        // 2. 가장 최근 등록된 발주의 상세 품목 조회
        if (!list.isEmpty()) {
            Long latestPoId = list.get(0).getPo_id();
            PurchaseOrderVO detail = service.getPurchaseOrder(latestPoId);
            System.out.println(">>> [최근 발주(" + latestPoId + "번) 품목 리스트]:");
            for (PurchaseOrderItemVO item : detail.getItemList()) {
                System.out.println("    - 도서 ID: " + item.getBook_id() + ", 발주 수량: " + item.getOrder_qty());
            }
        }
    }
}