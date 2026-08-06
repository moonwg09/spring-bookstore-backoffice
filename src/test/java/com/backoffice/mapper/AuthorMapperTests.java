package com.backoffice.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.backoffice.model.AuthorVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class AuthorMapperTests {

    @Autowired
    private AuthorMapper mapper;

    @Test
    public void testInsert() {
        System.out.println("====== [테스트] 저자 등록 ======");
        AuthorVO author = new AuthorVO();
        author.setName("홍길동");
        // bio는 null 상태로도 테스트해 보고, 문자열을 넣어서도 테스트해 보세요.
        author.setBio("조선 시대 의적 출신 작가입니다."); 
        
        mapper.insert(author);
        System.out.println(" 저자 등록 완료: " + author.getName());
    }

    @Test
    public void testGetList() {
        System.out.println("====== [테스트] 저자 목록 조회 ======");
        List<AuthorVO> list = mapper.getList();
        
        for(AuthorVO author : list) {
            System.out.println(author);
        }
    }

    @Test
    public void testRead() {
        System.out.println("====== [테스트] 저자 단건 조회 ======");
        Long targetId = 1L; // 실제 등록된 ID로 변경
        
        AuthorVO author = mapper.read(targetId);
        System.out.println(" 조회 결과: " + author);
    }

    @Test
    public void testUpdate() {
        System.out.println("====== [테스트] 저자 정보 수정 ======");
        AuthorVO author = new AuthorVO();
        author.setAuthor_id(1L); // 실제 등록된 ID로 변경
        author.setName("고길동");
        author.setBio("둘리를 키우는 평범한 직장인 작가입니다.");
        
        int count = mapper.update(author);
        System.out.println(" UPDATE 반영된 건수: " + count);
    }

    @Test
    public void testDelete() {
        System.out.println("====== [테스트] 저자 삭제 ======");
        Long targetId = 1L; // 삭제할 ID로 변경
        
        int count = mapper.delete(targetId);
        System.out.println(" DELETE 반영된 건수: " + count);
    }
}
