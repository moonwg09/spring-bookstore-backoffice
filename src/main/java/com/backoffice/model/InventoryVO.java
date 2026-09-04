package com.backoffice.model;

import lombok.Data;

@Data
public class InventoryVO {

	private Long inventory_id;
	private Long book_id;
	private int current_stock;
	private int safety_stock;
	
	// join 조회용 화면 출력 필드(도서 정보)
	private String book_title;
	private String isbn;
	private int price;
}

