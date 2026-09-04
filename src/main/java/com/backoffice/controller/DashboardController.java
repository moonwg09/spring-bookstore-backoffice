package com.backoffice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.backoffice.service.SettlementService;

@Controller
@RequestMapping("/admin")
public class DashboardController {

    @Autowired
    private SettlementService settlementService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // 1. 상단 실시간 통계 KPI 집계
        model.addAttribute("kpi", settlementService.getDashboardKPI());
        
        //  [신규 추가!] 2. 실시간 도서 판매 랭킹 Top 3
        model.addAttribute("rankList", settlementService.getTop3SalesRanking());
        
        //  [수정 완료!] 3. '당월 기준' 출판사 매입 대금 정산 내역
        model.addAttribute("settlementList", settlementService.getCurrentMonthSettlement());
        
        return "admin/dashboard/index";
    }

    @PostMapping("/settlement/process")
    public String processSettlement(@RequestParam("settlement_id") Long settlement_id) {
        settlementService.processSettlement(settlement_id);
        return "redirect:/admin/dashboard";
    }
}
