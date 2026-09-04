package com.backoffice.model;


import lombok.Data;

@Data
public class PurchaseOrderItemVO {
	
	private Long po_item_id;		// 발주 상세 번호
	private Long po_id;				// 발주 마스터 번호
	private Long book_id;			// 도서 번호
	private int order_qty;			// 발주 신청 수량
	private int receive_qty;		// 실제 입고 수량
	
	// join 조회용 화면 출력 필드
	private String book_title;
	private String isbn; 
}

