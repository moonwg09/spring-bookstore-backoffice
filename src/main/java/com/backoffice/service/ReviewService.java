package com.backoffice.service;

import java.util.List;

import com.backoffice.model.BookReviewVO;

public interface ReviewService {
	
	public boolean registerReview(BookReviewVO review);
	public List<BookReviewVO> getReviewList(Long book_id);
	public boolean removeReview(Long review_id);

}

