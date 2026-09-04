package com.backoffice.model;

import java.util.Date;
import lombok.Data;

@Data
public class InventoryHistoryVO {
    private Long history_id;     // 히스토리 번호 (PK)
    private Long book_id;        // 도서 번호 (FK - Book)
    private Long employee_id;    // 처리자/담당자 번호 (FK - Employee)
    private int change_qty;      // 변동 수량 (양수: +입고/조정, 음수: -출고/파손)
    private String reason;       // 변동 사유 (예: '발주 입고', '고객 주문 출고', '파손 폐기', '재고 실사 조정')
    private Date created_at;     // 변동 일시
    
    // JOIN 조회용 화면 출력 필드
    private String book_title;   // 도서명
    private String isbn;         // ISBN
    private String employee_name;// 담당자 이름
}
