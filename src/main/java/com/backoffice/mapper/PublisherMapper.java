package com.backoffice.mapper;

import java.util.List;

import com.backoffice.model.PublisherVO;

public interface PublisherMapper {
	
	// 1. 출판사 전체 목록 조회
	public List<PublisherVO> getList();
	
	// 2. 출판사 등록
	public void insert(PublisherVO publisher);
	
	// 3. 특정 출판사 단건 조회
	public PublisherVO read(Long publisher_id);
	
	// 4. 출판사 정보 수정 (성공 시 1 반환)
	public int update(PublisherVO publisher);
	
	// 5. 출판사 삭제 (성공 시 1 반환)
	public int delete(Long publisher_id);

}
