package com.backoffice.service;

import java.util.List;

import com.backoffice.model.BookVO;

public interface BookService {
	
	// 도서 목록 조회
	public List<BookVO> getBookList();
	
	// 도서 등록(도서 정보 + 저자 매핑)
	public void registerBook(BookVO book);
	
	// 도서 상세 조회(저자 매핑 정보 포함)
	public BookVO getBook(Long book_id);
	
	// 도서 수정(도서 정보 업데이트 + 기존 매핑 삭제 후 재등록)
	public boolean modifyBook(BookVO book);
	
	// 도서 삭제
	public boolean removeBook(Long book_id);
	
	// 평점순 상품 조회
	public List<BookVO> getBooksByRating();
	
	// 쇼핑몰 상세 페이지 조회
	public BookVO getBookDetailShop(Long book_id);
	
	// 도서 통합 검색
	public List<BookVO> searchBooks(String searchType, String keyword);
	
	// 카테고리별 도서 목록 조회
	public List<BookVO> getBooksByCategory(Long categoryId);

}

