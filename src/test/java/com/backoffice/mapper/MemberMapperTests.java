package com.backoffice.mapper;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.backoffice.model.MemberVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class MemberMapperTests {
	
	@Autowired
    private MemberMapper memberMapper;

    @Test
    public void testSelectMemberById() {
        // 사전에 입력된 admin 계정 조회 테스트
        MemberVO vo = memberMapper.selectMemberById("admin");
        assertNotNull(vo);
        System.out.println("조회된 회원 이름: " + vo.getName());
    }

}
