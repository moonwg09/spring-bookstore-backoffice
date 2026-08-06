package com.backoffice.mapper;

import java.util.List;

import com.backoffice.model.BookReviewVO;

public interface ReviewMapper {
	
	// 리뷰 등록
	public int insertReview(BookReviewVO review);
	
	// 특정 도서의 리뷰 목록 조회(작성자 이름 조인)
	public List<BookReviewVO> getReviewsByBookId(Long book_id);
	
	// 리뷰 삭제
	public int deleteReview(Long review_id);

}
