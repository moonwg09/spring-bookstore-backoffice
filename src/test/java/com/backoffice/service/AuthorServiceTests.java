package com.backoffice.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.backoffice.model.AuthorVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class AuthorServiceTests {

    @Autowired
    private AuthorService service;

    @Test
    public void testRegister() {
        System.out.println("====== [Service 테스트] 저자 등록 ======");
        AuthorVO author = new AuthorVO();
        author.setName("서비스 테스트 저자");
        author.setBio("서비스 계층을 통한 등록 테스트입니다.");
        
        service.registerAuthor(author);
        System.out.println(" Service를 통한 저자 등록 완료");
    }

    @Test
    public void testGetList() {
        System.out.println("====== [Service 테스트] 목록 조회 ======");
        service.getAuthorList().forEach(author -> System.out.println(author));
    }

    @Test
    public void testGet() {
        System.out.println("====== [Service 테스트] 단건 조회 ======");
        Long targetId = 2L; // DB에 있는 실제 ID로 변경
        AuthorVO author = service.getAuthor(targetId);
        System.out.println(" 조회된 저자: " + author);
    }

    @Test
    public void testModify() {
        System.out.println("====== [Service 테스트] 수정 ======");
        AuthorVO author = new AuthorVO();
        author.setAuthor_id(2L); // DB에 있는 실제 ID로 변경
        author.setName("수정된 서비스 저자");
        author.setBio("내용이 성공적으로 수정되었습니다.");
        
        System.out.println(" 수정 결과: " + service.modifyAuthor(author));
    }

    @Test
    public void testRemove() {
        System.out.println("====== [Service 테스트] 삭제 ======");
        Long targetId = 2L; // 삭제할 실제 ID로 변경
        System.out.println("삭제 결과: " + service.removeAuthor(targetId));
    }
}