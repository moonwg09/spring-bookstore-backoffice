package com.backoffice.model;

import java.util.List;

import lombok.Data;

@Data
public class CategoryVO {
	
	private Long category_id;
	private Long parent_id; // 상위 카테고리 Id(대분류의 경우 null)
	private String name;
	
	private List<CategoryVO> children;

}
