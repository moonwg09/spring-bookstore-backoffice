package com.backoffice.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.backoffice.model.PublisherVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class PublisherServiceTests {

    @Autowired
    private PublisherService service;

    @Test
    public void testRegister() {
        System.out.println("====== [Service 테스트] 출판사 등록 ======");
        PublisherVO publisher = new PublisherVO();
        publisher.setName("서비스 테스트 출판사");
        publisher.setContact("02-1111-2222");
        
        service.registerPublisher(publisher);
        System.out.println(" Service를 통한 등록 완료");
    }

    @Test
    public void testGetList() {
        System.out.println("====== [Service 테스트] 목록 조회 ======");
        service.getPublisherList().forEach(publisher -> System.out.println(publisher));
    }

    @Test
    public void testGet() {
        System.out.println("====== [Service 테스트] 단건 조회 ======");
        // 방금 1번을 지우셨으므로, testRegister 실행 후 생성된 새로운 ID를 입력해야 합니다. (예: 2L, 3L 등)
        Long targetId = 2L; 
        PublisherVO publisher = service.getPublisher(targetId);
        System.out.println(" 조회된 데이터: " + publisher);
    }

    @Test
    public void testModify() {
        System.out.println("====== [Service 테스트] 수정 ======");
        PublisherVO publisher = new PublisherVO();
        publisher.setPublisher_id(2L); // 존재하는 ID로 변경 필요
        publisher.setName("수정된 서비스 출판사");
        publisher.setContact("010-0000-0000");
        
        System.out.println(" 수정 결과: " + service.modifyPublisher(publisher));
    }

    @Test
    public void testRemove() {
        System.out.println("====== [Service 테스트] 삭제 ======");
        Long targetId = 2L; // 삭제할 ID로 변경 필요
        System.out.println("삭제 결과: " + service.removePublisher(targetId));
    }
}