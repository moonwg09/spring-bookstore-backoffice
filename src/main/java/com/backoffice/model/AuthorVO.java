package com.backoffice.model;

import lombok.Data;

@Data
public class AuthorVO {
	
	private Long author_id;
	private String name;
	private String bio;		// CLOB 타입이지만 java에서는 String
}
