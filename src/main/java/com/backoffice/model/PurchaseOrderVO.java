package com.backoffice.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class PurchaseOrderVO {
	
	private Long po_id;
	private Long publisher_id;
	private Long employee_id;
	private int quantity;
	private String status;
	private Date order_date;
	
	// join 조회용 추가 필드(도서명, 출판사명 등 화면 출력용)
	private String publisher_name;
	private String employee_name;
	
	// 1:N 관계의 상세 품목 리스트를 담을 컬렉션
	private List<PurchaseOrderItemVO> itemList;
	
}

