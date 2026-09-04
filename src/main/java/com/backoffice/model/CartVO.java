package com.backoffice.model;

import java.util.Date;

import lombok.Data;

@Data
public class CartVO {

	private Long cart_id;
	private Long member_id;
	private Long book_id;
	private Integer quantity;
	private Date created_at;
	
	private String title;
	private Integer price;
	private String cover_image;
	private String publisher_name;
	
}

