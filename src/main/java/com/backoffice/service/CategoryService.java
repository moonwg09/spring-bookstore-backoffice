package com.backoffice.service;

import java.util.List;

import com.backoffice.model.CategoryVO;

public interface CategoryService {
	
	// 카테고리 목록 조회
	public List<CategoryVO> getCategoryList();
	
	// 카테고리 등록
	public void registerCategory(CategoryVO category);
	
	// 카테고리 상세 조회
	public CategoryVO getCategory(Long category_id);
	
	// 카테고리 수정
	public boolean modifyCategory(CategoryVO category);
	
	// 카테고리 삭제
	public boolean removeCategory(Long category_id);

}

