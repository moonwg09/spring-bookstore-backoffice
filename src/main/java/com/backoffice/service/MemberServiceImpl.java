package com.backoffice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backoffice.mapper.MemberMapper;
import com.backoffice.model.MemberVO;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
    private MemberMapper memberMapper;

    @Override
    public boolean register(MemberVO vo) {
        return memberMapper.insertMember(vo) == 1;
    }

    @Override
    public MemberVO login(String loginId, String password) {
        MemberVO member = memberMapper.selectMemberById(loginId);
        if (member != null && member.getPassword().equals(password)) {
            return member; // �α��� ����
        }
        return null; // �α��� ����
    }

    @Override
    public MemberVO kakaoLoginOrRegister(String kakaoId, String nickname, String email) {
        // 1. �̹� ���Ե� īī�� ȸ������ ��ȸ
        MemberVO member = memberMapper.selectMemberByKakaoId(kakaoId);
        if (member != null) {
            return member; // ���� ȸ�� �α��� ó��
        }
        
        // 2. �ű� īī�� ȸ�� �ڵ� ȸ������ ����
        MemberVO newMember = new MemberVO();
        newMember.setKakao_Id(kakaoId);
        newMember.setName(nickname);
        newMember.setEmail(email != null ? email : kakaoId + "@kakao.com");
        newMember.setLogin_Id("kakao_" + kakaoId);
        newMember.setPassword("KAKAO_SOCIAL_LOGIN"); // �Ҽ� ȸ���� ��� ����
        
        memberMapper.insertMember(newMember);
        return memberMapper.selectMemberByKakaoId(kakaoId);
    }

}
