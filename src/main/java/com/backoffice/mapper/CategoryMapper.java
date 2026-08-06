package com.backoffice.mapper;

import java.util.List;

import com.backoffice.model.CategoryVO;

public interface CategoryMapper {
	
	// 카테고리 전체 목록 조회
	public List<CategoryVO> getList();
	
	// 카테고리 등록
	public void insert(CategoryVO category);
	
	// 특정 카테고리 단건 조회
	public CategoryVO read(Long category_id);
	
	// 카테고리 정보 수정
	public int update(CategoryVO category);
	
	// 카테고리 삭제
	public int delete(Long category_id);

}
