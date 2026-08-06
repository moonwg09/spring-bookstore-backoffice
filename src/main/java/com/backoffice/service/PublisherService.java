package com.backoffice.service;

import java.util.List;

import com.backoffice.model.PublisherVO;

public interface PublisherService {
	
	// 출판사 목록 조회
	public List<PublisherVO> getPublisherList();
	
	// 출판사 등록
	public void registerPublisher(PublisherVO publisher);
	
	// 출판사 상세 조회
	public PublisherVO getPublisher(Long publisher_id);
	
	// 출판사 정보 수정
	public boolean modifyPublisher(PublisherVO publisher);
	
	// 출판사 삭제
	public boolean removePublisher(Long publisher_id);

}
