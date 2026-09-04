package com.backoffice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backoffice.mapper.ActivityLogMapper;
import com.backoffice.model.ActivityLogVO;

@Service
public class ActivityLogServiceImpl implements ActivityLogService {
	
	@Autowired
	private ActivityLogMapper mapper;
	
	@Override
	public void registerLog(Long employeeId, String action) {
		ActivityLogVO log = new ActivityLogVO();
		log.setEmployee_id(employeeId);
		log.setAction(action);
		
		mapper.insertLog(log);
	}

}

