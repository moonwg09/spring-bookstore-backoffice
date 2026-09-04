package com.backoffice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backoffice.mapper.BookMapper;
import com.backoffice.model.BookVO;

@Service
public class BookServiceImpl implements BookService{
	
	@Autowired
	private BookMapper mapper;
	
	@Override
    public List<BookVO> getBookList() {
        return mapper.getList();
    }

    // 도서 등록과 매핑 등록이 하나의 작업으로 묶이도록 트랜잭션 처리
    @Transactional
    @Override
    public void registerBook(BookVO book) {
        // 1. 도서 기본 정보 등록 (이때 book 객체 안에 새로운 book_id가 세팅됨)
        mapper.insert(book);
        
        // 2. 선택된 저자들이 있다면 다대다 매핑 테이블에 각각 등록
        if (book.getAuthorIds() != null && !book.getAuthorIds().isEmpty()) {
            for (Long authorId : book.getAuthorIds()) {
                mapper.insertBookAuthor(book.getBook_id(), authorId);
            }
        }
    }

    @Override
    public BookVO getBook(Long book_id) {
        // 1. 도서 기본 정보 조회
        BookVO book = mapper.read(book_id);
        
        // 2. 해당 도서에 연결된 저자 ID 목록을 조회하여 VO에 담아줌
        if (book != null) {
            book.setAuthorIds(mapper.getAuthorIdsByBook(book_id));
        }
        return book;
    }

    @Transactional
    @Override
    public boolean modifyBook(BookVO book) {
        // 1. 도서 기본 정보 업데이트
        boolean result = mapper.update(book) == 1;
        
        // 2. 기존 저자 매핑 정보 모두 삭제
        mapper.deleteBookAuthor(book.getBook_id());
        
        // 3. 화면에서 새로 넘어온 저자 목록으로 다시 매핑 정보 등록
        if (book.getAuthorIds() != null && !book.getAuthorIds().isEmpty()) {
            for (Long authorId : book.getAuthorIds()) {
                mapper.insertBookAuthor(book.getBook_id(), authorId);
            }
        }
        
        return result;
    }

    @Transactional
    @Override
    public boolean removeBook(Long book_id) {
        // 자식 테이블(매핑) 먼저 삭제 후 부모 테이블(도서) 삭제
        mapper.deleteBookAuthor(book_id);
        return mapper.delete(book_id) == 1;
    }
    
    @Override
    public List<BookVO> getBooksByRating() {
        return mapper.getBooksByRating();
    }

    @Override
    public BookVO getBookDetailShop(Long book_id) {
        return mapper.getBookDetailShop(book_id);
    }

    @Override
    public List<BookVO> searchBooks(String searchType, String keyword) {
        return mapper.searchBooks(searchType, keyword);
    }
    
    @Override
    public List<BookVO> getBooksByCategory(Long categoryId) {
        // bookMapper 또는 bookDAO를 통해 카테고리별 도서 조회 쿼리 호출
        return mapper.getBooksByCategory(categoryId);
    }

}

