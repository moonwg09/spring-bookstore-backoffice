package com.backoffice.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.backoffice.model.CategoryVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class CategoryServiceTests {

    @Autowired
    private CategoryService service;

    @Test
    public void testRegister() {
        System.out.println("====== [Service 테스트] 대분류 카테고리 등록 ======");
        CategoryVO category = new CategoryVO();
        category.setParent_id(null);
        category.setName("서비스 테스트 대분류");
        
        service.registerCategory(category);
        System.out.println(" Service를 통한 대분류 등록 완료");
    }

    @Test
    public void testGetList() {
        System.out.println("====== [Service 테스트] 목록 조회 ======");
        service.getCategoryList().forEach(category -> System.out.println(category));
    }

    @Test
    public void testGet() {
        System.out.println("====== [Service 테스트] 단건 조회 ======");
        Long targetId = 2L; // 실제 존재하는 ID로 변경
        CategoryVO category = service.getCategory(targetId);
        System.out.println(" 조회된 카테고리: " + category);
    }

    @Test
    public void testModify() {
        System.out.println("====== [Service 테스트] 수정 ======");
        CategoryVO category = new CategoryVO();
        category.setCategory_id(2L); // 실제 존재하는 ID로 변경
        category.setParent_id(null);
        category.setName("수정된 대분류");
        
        System.out.println(" 수정 결과: " + service.modifyCategory(category));
    }

    @Test
    public void testRemove() {
        System.out.println("====== [Service 테스트] 삭제 ======");
        Long targetId = 3L; // 삭제할 ID로 변경
        System.out.println(" 삭제 결과: " + service.removeCategory(targetId));
    }
}