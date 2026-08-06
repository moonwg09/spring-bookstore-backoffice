package com.backoffice.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.backoffice.model.CategoryVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class CategoryMapperTests {

    @Autowired
    private CategoryMapper mapper;

    // 1. 카테고리 등록 테스트 (대분류 생성)
    @Test
    public void testInsertParent() {
        System.out.println("====== [테스트] 대분류 카테고리 등록 ======");
        CategoryVO category = new CategoryVO();
        // 대분류는 상위 카테고리가 없으므로 parent_id를 null로 둡니다. (또는 세팅하지 않음)
        category.setName("컴퓨터/IT");
        
        mapper.insert(category);
        System.out.println(" 대분류 등록 완료: " + category.getName());
    }

    // 2. 카테고리 등록 테스트 (소분류 생성)
    // 주의: testInsertParent 실행 후 생성된 대분류의 실제 DB ID를 parent_id로 넣어야 합니다!
    @Test
    public void testInsertChild() {
        System.out.println("====== [테스트] 소분류 카테고리 등록 ======");
        CategoryVO category = new CategoryVO();
        category.setParent_id(1L); // DB에 있는 실제 대분류 ID로 변경해 주세요!
        category.setName("백엔드 프로그래밍");
        
        mapper.insert(category);
        System.out.println(" 소분류 등록 완료: " + category.getName());
    }

    // 3. 카테고리 목록 조회 테스트
    @Test
    public void testGetList() {
        System.out.println("====== [테스트] 카테고리 목록 조회 ======");
        List<CategoryVO> list = mapper.getList();
        
        for(CategoryVO cat : list) {
            System.out.println(cat);
        }
        System.out.println(" 총 " + list.size() + "개의 카테고리가 조회되었습니다.");
    }

    // 4. 특정 카테고리 단건 조회 테스트
    @Test
    public void testRead() {
        System.out.println("====== [테스트] 카테고리 단건 조회 ======");
        Long targetId = 1L; // 조회할 카테고리 PK
        
        CategoryVO category = mapper.read(targetId);
        if(category != null) {
            System.out.println(" 조회 성공: " + category);
        } else {
            System.out.println(" 해당 ID의 카테고리가 없습니다.");
        }
    }

    // 5. 카테고리 정보 수정 테스트
    @Test
    public void testUpdate() {
        System.out.println("====== [테스트] 카테고리 정보 수정 ======");
        CategoryVO category = new CategoryVO();
        category.setCategory_id(1L); // 수정할 대상 PK
        category.setParent_id(null); // 그대로 대분류 유지
        category.setName("IT/모바일");
        
        int count = mapper.update(category);
        System.out.println(" UPDATE 반영된 데이터 건수: " + count);
    }

    // 6. 카테고리 삭제 테스트
    //  팁: DDL 설계 시 'ON DELETE CASCADE'를 적용하셨기 때문에, 대분류를 지우면 그 밑에 속한 소분류들도 자동으로 한 번에 삭제됩니다!
    @Test
    public void testDelete() {
        System.out.println("====== [테스트] 카테고리 삭제 ======");
        Long targetId = 1L; // 지우고자 하는 카테고리 ID
        
        int count = mapper.delete(targetId);
        System.out.println(" DELETE 반영된 데이터 건수: " + count);
    }
}