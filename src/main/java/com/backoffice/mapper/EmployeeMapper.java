package com.backoffice.mapper;

import java.util.List;

import com.backoffice.model.EmployeeVO;

public interface EmployeeMapper {
	
	public EmployeeVO readEmployee(String loginId);
	
	//  [HR 관리용 추가 1] 전체 사원 계정 목록 조회
	public List<EmployeeVO> selectEmployeeList();
		
	//  [HR 관리용 추가 2] 사원 직급(Role) 변경
	public int updateEmployeeRole(EmployeeVO vo);

}
