package com.backoffice.mapper;

import java.util.List;

import com.backoffice.model.AuthorVO;

public interface AuthorMapper {
	
	// 저자 전체 목록 조회
	public List<AuthorVO> getList();
	
	// 저자 등록
	public void insert(AuthorVO author);
	
	// 특정 저자 단건 조회
	public AuthorVO read(Long author_id);
	
	// 저자 정보 수정
	public int update(AuthorVO author);
	
	// 저자 삭제
	public int delete(Long author_id);

}

