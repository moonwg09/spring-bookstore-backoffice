package com.backoffice.mapper;

import java.util.List;

import com.backoffice.model.ActivityLogVO;

public interface ActivityLogMapper {
	
	public int insertLog(ActivityLogVO log);
	
	//  [HR 관리용 추가] 최신 활동 로그 목록 조회 (최신순 50건)
	public List<ActivityLogVO> selectActivityLogList();
}
