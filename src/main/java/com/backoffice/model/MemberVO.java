package com.backoffice.model;

import java.util.Date;

import lombok.Data;

@Data
public class MemberVO {

	private Long member_Id;
	private String login_Id;
	private String password;
	private String name;
	private String email;
	private String kakao_Id;
	private Long balance;   // 충전 금액
	private Long point;
	private String role;
	private Date created_at;	// 가입일
}
