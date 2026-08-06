package com.backoffice.model;

import lombok.Data;

@Data
public class OrderItemVO {
    private Long order_item_id;  // 주문 상세 번호 (PK)
    private Long order_id;       // 주문 마스터 번호 (FK)
    private Long book_id;        // 도서 번호 (FK)
    private Integer qty;             // 주문 수량
    private Long price;           // 주문 당시 도서 단가
    
    // JOIN 조회용 화면 출력 필드
    private String book_title;   // 도서 제목
    private String isbn;         // ISBN
    private String cover_image;
}