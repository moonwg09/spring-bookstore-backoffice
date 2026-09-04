package com.backoffice.mapper;

import com.backoffice.model.MemberVO;

public interface MemberMapper {
	
	public int insertMember(MemberVO vo);
	
	public MemberVO selectMemberById(String loginId);
	
	public MemberVO selectMemberByKakaoId(String kakaoId);

}

