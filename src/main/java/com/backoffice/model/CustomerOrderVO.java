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
	
	// PortOne 결제 정보(포트원이 발급한 결제 고유번호, 우리 사이트에서 만드는 주문번호)
	private String imp_uid;
	private String merchant_uid;
	
	// join 조회용 화면 출력 필드
	private String member_name;
	
	private List<OrderItemVO> itemList;
}

