package com.backoffice.model;

import lombok.Data;

@Data
public class SettlementVO {
    private Long settlement_id;       // 정산 관리 번호 (PK)
    private Long publisher_id;        // 출판사 번호 (FK)
    private String settlement_month;  // 정산 대상 월 (포맷: 'YYYY-MM', 예: '2026-07')
    private int total_amount;         // 정산 지급 대상액 (실제 지급할 금액)
    private String status;            // 정산 상태 ('READY': 정산대기, 'PROCESSED': 정산완료)
    
    // JOIN 조회용 화면 출력 필드 (index.html 대시보드 출력용)
    private String publisher_name;    // 출판사명
    private int total_purchase_amount;// 총 매입 원가 (참고용 정산액 기준액)
}
