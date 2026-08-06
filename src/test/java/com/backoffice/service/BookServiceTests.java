package com.backoffice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.backoffice.model.BookVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class BookServiceTests {

    @Autowired
    private BookService service;

    @Test
    public void testRegister() {
        System.out.println("[Service Test] 도서 등록 및 저자 다중 매핑");
        
        BookVO book = new BookVO();
        book.setTitle("스프링 트랜잭션 관리");
        book.setPrice(25000);
        book.setPublish_date(new Date());
        
        // DB에 존재하는 출판사와 카테고리 ID 입력
        book.setPublisher_id(1L); 
        book.setCategory_id(2L); 
        
        // 여러 명의 저자 ID 세팅 (DB에 존재하는 저자 ID 입력)
        List<Long> authorIds = new ArrayList<>();
        authorIds.add(1L); 
        //authorIds.add(2L); // 두 번째 저자도 있다고 가정 (없으면 1L만 유지)
        book.setAuthorIds(authorIds);
        
        // Service 메서드 하나만 호출하면 도서 등록 + 매핑이 모두 처리됩니다.
        service.registerBook(book);
        System.out.println("Service를 통한 다중 매핑 도서 등록 완료");
    }

    @Test
    public void testGet() {
        System.out.println("[Service Test] 도서 및 연결된 저자 목록 단건 조회");
        Long targetId = 3L; // 실제 존재하는 도서 ID로 변경
        
        BookVO book = service.getBook(targetId);
        System.out.println("조회된 도서 정보: " + book);
        System.out.println("연결된 저자 ID 리스트: " + book.getAuthorIds());
    }
}