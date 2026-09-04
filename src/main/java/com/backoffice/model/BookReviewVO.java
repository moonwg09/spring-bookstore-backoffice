package com.backoffice.model;

import java.util.Date;

import lombok.Data;

@Data
public class BookReviewVO {

	private Long review_id;
	private Long book_id;
	private Long member_id;
	private String content;
	private Double rating;
	private Date created_at;
	
	private String member_name;
}

