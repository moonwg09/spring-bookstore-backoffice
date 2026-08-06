package com.backoffice.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.backoffice.model.PublisherVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class PublisherMapperTests {

    @Autowired
    private PublisherMapper mapper;

    // 1. 출판사 등록 테스트
    @Test
    public void testInsert() {
        System.out.println("====== [테스트] 출판사 등록 ======");
        PublisherVO publisher = new PublisherVO();
        publisher.setName("테스트 출판사");
        publisher.setContact("02-1234-5678");
        
        mapper.insert(publisher);
        System.out.println(" 등록 완료: " + publisher.getName());
    }

    // 2. 출판사 목록 조회 테스트
    @Test
    public void testGetList() {
        System.out.println("====== [테스트] 출판사 목록 조회 ======");
        List<PublisherVO> list = mapper.getList();
        
        for(PublisherVO pub : list) {
            System.out.println(pub);
        }
        System.out.println(" 총 " + list.size() + "개의 출판사가 조회되었습니다.");
    }

    // 3. 특정 출판사 단건 조회 테스트
    // 주의: 테스트 전에 데이터베이스에 1번(publisher_id=1) 출판사가 존재하는지 확인해 주세요.
    @Test
    public void testRead() {
        System.out.println("====== [테스트] 출판사 단건 조회 ======");
        Long targetId = 1L; // 조회할 출판사 PK
        
        PublisherVO publisher = mapper.read(targetId);
        if(publisher != null) {
            System.out.println(" 조회 성공: " + publisher);
        } else {
            System.out.println(" 해당 ID의 출판사가 없습니다.");
        }
    }

    // 4. 출판사 정보 수정 테스트
    // 주의: 실제 존재하는 publisher_id로 테스트해야 합니다.
    @Test
    public void testUpdate() {
        System.out.println("====== [테스트] 출판사 정보 수정 ======");
        PublisherVO publisher = new PublisherVO();
        publisher.setPublisher_id(1L); // 수정할 대상 PK
        publisher.setName("수정된 출판사명");
        publisher.setContact("010-9999-8888");
        
        int count = mapper.update(publisher);
        System.out.println(" UPDATE 반영된 데이터 건수: " + count);
    }

    // 5. 출판사 삭제 테스트
    // 주의: 삭제할 대상 PK를 지정하세요. (테스트용으로 방금 넣은 데이터를 지워보시면 좋습니다)
    @Test
    public void testDelete() {
        System.out.println("====== [테스트] 출판사 삭제 ======");
        Long targetId = 1L; // 삭제할 대상 PK (DB에 존재하는지 확인 후 변경)
        
        int count = mapper.delete(targetId);
        System.out.println(" DELETE 반영된 데이터 건수: " + count);
    }
}