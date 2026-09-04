package com.backoffice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backoffice.mapper.ReviewMapper;
import com.backoffice.model.BookReviewVO;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
    private ReviewMapper reviewMapper;

    @Override
    public boolean registerReview(BookReviewVO review) {
        return reviewMapper.insertReview(review) == 1;
    }

    @Override
    public List<BookReviewVO> getReviewList(Long book_id) {
        return reviewMapper.getReviewsByBookId(book_id);
    }

    @Override
    public boolean removeReview(Long review_id) {
        return reviewMapper.deleteReview(review_id) == 1;
    }
}

