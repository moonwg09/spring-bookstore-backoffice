package com.backoffice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.backoffice.model.BookVO;

public interface BookMapper {

	public List<BookVO> getList();
	
	public void insert(BookVO book);
	
	public BookVO read(Long book_id);
	
	public int update(BookVO book);
	
	public int delete(Long book_id);
	
	// 도서와 저자 연결 데이터 등록
	public void insertBookAuthor(@Param("book_id") Long book_id, @Param("author_id") Long author_id);
	
	// 특정 도서와 연결 데이터 모두 삭제(수정 시 기존 매핑을 날리고 새로 넣기 위함)
	public void deleteBookAuthor(Long book_id);
	
	// 특정 도서에 연결된 저자 ID 목록만 쏙 뽑아오기
	public List<Long> getAuthorIdsByBook(Long book_id);
	
	// 평점순 상품 조회(top 4)
	public List<BookVO> getBooksByRating();
	
	// 쇼핑몰 상세 페이지 조회(재고, 출판사, 카테고리 포함)
	public BookVO getBookDetailShop(Long book_id);
	
	// 도서 통합 검색(제목, 저자, 출판사)
	public List<BookVO> searchBooks(@Param("searchType") String searchType, @Param("keyword") String keyword);
	
	// 카테고리별 도서 조회
	public List<BookVO> getBooksByCategory(Long categoryId);
	
}

