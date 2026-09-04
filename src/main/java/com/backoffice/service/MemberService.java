package com.backoffice.service;

import com.backoffice.model.MemberVO;

public interface MemberService {
	
	public boolean register(MemberVO vo);
    public MemberVO login(String loginId, String password);
    public MemberVO kakaoLoginOrRegister(String kakaoId, String nickname, String email);

}

