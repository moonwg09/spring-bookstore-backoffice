package com.backoffice.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class CustomerOrderVO {

	private Long order_id;
	private Long member_id;
	private String status;
	private Long total_amount;
	private Date order_date;
	
	// join 조회용 화면 출력 필드
	private String member_name;
	
	private List<OrderItemVO> itemList;
}
