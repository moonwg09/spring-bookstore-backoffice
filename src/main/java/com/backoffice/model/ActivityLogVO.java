package com.backoffice.model;

import java.util.Date;

import lombok.Data;

@Data
public class ActivityLogVO {
	private Long log_id;
	private Long employee_id;
	private String action;
	private Date timestamp;
	
	private String employeeName;
	private String role;
	

}

