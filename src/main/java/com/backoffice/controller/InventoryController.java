package com.backoffice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.backoffice.model.InventoryVO;
import com.backoffice.model.PurchaseOrderVO;
import com.backoffice.service.InventoryHistoryService;
import com.backoffice.service.InventoryService;
import com.backoffice.service.PurchaseOrderService;

@Controller
@RequestMapping("/admin/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;
    
    @Autowired
    private PurchaseOrderService poService;
    
    @Autowired
    private InventoryHistoryService historyService;

    // 1. 창고 재고 및 물류 관리 (WMS) 메인 화면 이동
    @GetMapping("/list")
    public String inventoryList(Model model) {
        // ① 창고 도서 실재고 현황 조회
        model.addAttribute("inventoryList", inventoryService.getInventoryList());
        
        // ② 출판사 B2B 발주 대기/승인 내역 조회
        model.addAttribute("poList", poService.getPurchaseOrderList());
        
        // ③ 수동 재고 조정 이력 (Stock Log) 조회
        model.addAttribute("historyList", historyService.getHistoryList());
        
        return "admin/inventory/list";
    }

    // 2. 발주 입고 승인 처리 (프로토타입의 '입고 승인 (재고 자동합산)' 버튼 액션)
    @PostMapping("/receive")
    public String receiveOrder(@RequestParam("po_id") Long po_id, 
                               @RequestParam("book_id") Long book_id,
                               @RequestParam("qty") int qty,
                               @RequestParam("login_emp_id") Long login_emp_id) {
        
        // ① 발주 상태를 'RECEIVED(입고완료)'로 변경
        PurchaseOrderVO poVO = new PurchaseOrderVO();
        poVO.setPo_id(po_id);
        poVO.setStatus("RECEIVED");
        poService.modifyOrderStatus(poVO);
        
        // ② 창고 재고 증가 및 입고 히스토리 동시 기록 (ACID 트랜잭션 보장!)
        InventoryVO invVO = new InventoryVO();
        invVO.setBook_id(book_id);
        invVO.setCurrent_stock(qty); // 입고된 수량만큼 플러스
        
        historyService.addStockWithHistory(invVO, login_emp_id, "B2B 발주 입고 승인 [PO-" + po_id + "]");
        
        return "redirect:/admin/inventory/list";
    }

    // 3. 수동 재고 조정 처리 (프로토타입의 '수동조정' 팝업 액션)
    @PostMapping("/adjust")
    public String adjustStock(@RequestParam("book_id") Long book_id,
                              @RequestParam("change_qty") int change_qty,
                              @RequestParam("reason") String reason,
                              @RequestParam("login_emp_id") Long login_emp_id) {
        
        InventoryVO invVO = new InventoryVO();
        invVO.setBook_id(book_id);
        invVO.setCurrent_stock(change_qty); // 양수(+) 또는 음수(-) 입력값
        
        // 실재고 증감 및 Audit Log 동시 기록
        historyService.addStockWithHistory(invVO, login_emp_id, "[수동조정] " + reason);
        
        return "redirect:/admin/inventory/list";
    }
 // 4. 출판사 발주 요청 처리 (신규 추가)
    @PostMapping("/order")
    public String requestOrder(@RequestParam("book_id") Long book_id,
                               @RequestParam("publisher_id") Long publisher_id,
                               @RequestParam("qty") int qty,
                               @RequestParam("login_emp_id") Long login_emp_id) {
        
        // ① 발주 마스터 세팅
        PurchaseOrderVO poVO = new PurchaseOrderVO();
        poVO.setPublisher_id(publisher_id);
        poVO.setEmployee_id(login_emp_id);
        
        // ② 발주 품목 세팅 (1:N 구조)
        java.util.List<com.backoffice.model.PurchaseOrderItemVO> itemList = new java.util.ArrayList<>();
        com.backoffice.model.PurchaseOrderItemVO item = new com.backoffice.model.PurchaseOrderItemVO();
        item.setBook_id(book_id);
        item.setOrder_qty(qty);
        itemList.add(item);
        
        poVO.setItemList(itemList);
        
        // ③ 서비스 트랜잭션 호출 (마스터 + 상세 품목 DB 저장)
        poService.registerPurchaseOrder(poVO);
        
        return "redirect:/admin/inventory/list";
    }
    
}