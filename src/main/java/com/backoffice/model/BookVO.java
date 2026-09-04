package com.backoffice.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class BookVO {

	private Long book_id;
	private String title;
	private Integer price;
	
	@DateTimeFormat(pattern="yyyy-mm-dd")
	private Date publish_date;
	
	private Long publisher_id;
	private Long category_id;
	
	private String isbn;
	private List<Long> authorIds;
	
	private String cover_image;  // 상품 표지 이미지 경로/ url
	
	private String publisher_name; 			// 출판사 이름
	private String category_name;			// 카테고리 이름
	private Double average_rating;			// 평점순 상품 정렬용 평균 평점
	private Integer current_stock;			// 상세 페이지 실시간 재고 표시용
}

