package com.backoffice.model;

import lombok.Data;

@Data
public class SalesRankVO {
    private int rank;             // 판매 순위 (1, 2, 3위)
    private String book_title;    // 도서명
    private String category;      // 카테고리 (예: IT/개발)
    private String publisher_name;// 주요 거래처 (출판사명)
    private int total_sales_qty;  // 누적 판매 수량
}
