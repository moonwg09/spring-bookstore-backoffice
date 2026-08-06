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
            return member; // 로그인 성공
        }
        return null; // 로그인 실패
    }

    @Override
    public MemberVO kakaoLoginOrRegister(String kakaoId, String nickname, String email) {
        // 1. 이미 가입된 카카오 회원인지 조회
        MemberVO member = memberMapper.selectMemberByKakaoId(kakaoId);
        if (member != null) {
            return member; // 기존 회원 로그인 처리
        }
        
        // 2. 신규 카카오 회원 자동 회원가입 진행
        MemberVO newMember = new MemberVO();
        newMember.setKakao_Id(kakaoId);
        newMember.setName(nickname);
        newMember.setEmail(email != null ? email : kakaoId + "@kakao.com");
        newMember.setLogin_Id("kakao_" + kakaoId);
        newMember.setPassword("KAKAO_SOCIAL_LOGIN"); // 소셜 회원은 비번 무관
        
        memberMapper.insertMember(newMember);
        return memberMapper.selectMemberByKakaoId(kakaoId);
    }

}
