package com.backoffice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.backoffice.service.CustomerOrderService;

@Controller
@RequestMapping("/admin/order")
public class OrderController {

    @Autowired
    private CustomerOrderService orderService;

    // 1. 고객 주문 내역 및 CS 관리 메인 화면 이동
    @GetMapping("/list")
    public String orderList(Model model) {
        // 주문 마스터 및 하위 품목 리스트를 함께 조회하여 화면에 전달
        model.addAttribute("orderList", orderService.getOrderList());
        return "admin/order/list";
    }

    // 2. 주문 상태 변경 및 트랜잭션 처리 (출고 차감, 취소 원복 등)
    @PostMapping("/status")
    public String changeStatus(@RequestParam("order_id") Long order_id,
                               @RequestParam("target_status") String target_status,
                               @RequestParam("reason") String reason,
                               @RequestParam(value = "login_emp_id", defaultValue = "1") Long login_emp_id) {
        
        // 서비스 트랜잭션 호출 (상태 변경 + 실재고 증감 + Audit Log 동시 기록)
        orderService.modifyOrderStatus(order_id, target_status, login_emp_id, reason);
        
        return "redirect:/admin/order/list";
    }
}
