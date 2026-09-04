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
            return member; // 占싸깍옙占쏙옙 占쏙옙占쏙옙
        }
        return null; // 占싸깍옙占쏙옙 占쏙옙占쏙옙
    }

    @Override
    public MemberVO kakaoLoginOrRegister(String kakaoId, String nickname, String email) {
        // 1. 占싱뱄옙 占쏙옙占쌉듸옙 카카占쏙옙 회占쏙옙占쏙옙占쏙옙 占쏙옙회
        MemberVO member = memberMapper.selectMemberByKakaoId(kakaoId);
        if (member != null) {
            return member; // 占쏙옙占쏙옙 회占쏙옙 占싸깍옙占쏙옙 처占쏙옙
        }
        
        // 2. 占신깍옙 카카占쏙옙 회占쏙옙 占쌘듸옙 회占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙
        MemberVO newMember = new MemberVO();
        newMember.setKakao_Id(kakaoId);
        newMember.setName(nickname);
        newMember.setEmail(email != null ? email : kakaoId + "@kakao.com");
        newMember.setLogin_Id("kakao_" + kakaoId);
        newMember.setPassword("KAKAO_SOCIAL_LOGIN"); // 占쌀쇽옙 회占쏙옙占쏙옙 占쏙옙占?占쏙옙占쏙옙
        
        memberMapper.insertMember(newMember);
        return memberMapper.selectMemberByKakaoId(kakaoId);
    }

}

