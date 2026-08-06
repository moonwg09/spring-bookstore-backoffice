package com.backoffice.model;

import lombok.Data;

@Data
public class DashboardVO {
    private int totalRevenue;   // 이달의 총 매출 (출고완료 COMPLETED 기준 SUM)
    private int newOrderCount;  // 신규 주문 건수 (주문접수 PENDING 기준 COUNT)
    private int cancelCount;    // 취소 요청/완료 건수 (CANCELLED 기준 COUNT)
    private int lowStockCount;  // 품절 임박 도서 수 (현재재고 <= 안전재고 기준 COUNT)
    
    private int q1Revenue;      // 1분기 (1~3월) 매출
    private int q2Revenue;      // 2분기 (4~6월) 매출
    private int q3Revenue;      // 3분기 (7~9월) 매출
    private int q4Revenue;      // 4분기 (10~12월) 매출
}