package com.backoffice.mapper;

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
public class BookMapperTests {

    @Autowired
    private BookMapper mapper;

    @Test
    public void testInsertBookAndAuthor() {
        System.out.println("====== [테스트] 도서 등록 및 저자 연결 ======");
        
        // 1. 도서 정보 세팅
        BookVO book = new BookVO();
        book.setTitle("스프링 부트 백엔드 프로그래밍");
        book.setPrice(35000);
        book.setPublish_date(new Date()); // 현재 시간으로 세팅
        
        //  [매우 중요] DB에 실제로 존재하는 출판사 ID와 카테고리 ID를 적어주세요!
        book.setPublisher_id(1L); 
        book.setCategory_id(2L); // 가급적 소분류 카테고리 ID 권장
        
        // 2. 도서 등록 (이 과정에서 XML의 selectKey가 작동하여 book.book_id에 새 번호가 채워집니다)
        mapper.insert(book);
        System.out.println(" 새로 발급된 도서 번호: " + book.getBook_id());
        
        // 3. 도서-저자 매핑 테이블 등록 (방금 발급받은 도서 번호와 기존 저자 번호 연결)
        //  DB에 실제로 존재하는 저자 ID를 적어주세요!
        Long authorId1 = 1L; 
        
        mapper.insertBookAuthor(book.getBook_id(), authorId1);
        
        System.out.println(" 도서-저자 매핑 완료");
    }

    @Test
    public void testGetList() {
        System.out.println("====== [테스트] 도서 목록 조회 ======");
        mapper.getList().forEach(book -> System.out.println(book));
    }

    @Test
    public void testRead() {
        System.out.println("====== [테스트] 도서 단건 조회 ======");
        Long targetId = 1L; // 실제 등록된 도서 ID로 변경
        
        BookVO book = mapper.read(targetId);
        System.out.println(" 조회된 도서: " + book);
        
        // 매핑된 저자 목록도 같이 조회해보기
        List<Long> authorIds = mapper.getAuthorIdsByBook(targetId);
        System.out.println(" 이 책에 연결된 저자 ID 목록: " + authorIds);
    }

    @Test
    public void testUpdate() {
        System.out.println("====== [테스트] 도서 정보 수정 ======");
        BookVO book = new BookVO();
        book.setBook_id(1L); // 실제 등록된 도서 ID로 변경
        book.setTitle("수정된 스프링 백엔드");
        book.setPrice(40000);
        book.setPublish_date(new Date());
        book.setPublisher_id(1L);
        book.setCategory_id(2L);
        
        int count = mapper.update(book);
        System.out.println(" UPDATE 반영 건수: " + count);
    }

    @Test
    public void testDelete() {
        System.out.println("====== [테스트] 도서 삭제 ======");
        Long targetId = 1L; // 삭제할 도서 ID로 변경
        
        //  만약 DB의 Book_Author 테이블 설계 시 'ON DELETE CASCADE'를 안 걸어두셨다면,
        // 도서를 지우기 전에 자식 데이터(매핑)를 먼저 지워야 무결성 제약조건 에러가 안 납니다!
        // mapper.deleteBookAuthor(targetId); 
        
        int count = mapper.delete(targetId);
        System.out.println(" DELETE 반영 건수: " + count);
    }
}