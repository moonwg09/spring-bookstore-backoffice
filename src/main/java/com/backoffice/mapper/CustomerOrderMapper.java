package com.backoffice.mapper;

import java.util.List;

import com.backoffice.model.CustomerOrderVO;
import com.backoffice.model.OrderItemVO;

public interface CustomerOrderMapper {
	
	// 1. 주문 마스터 등록
	public int insertCustomerOrder(CustomerOrderVO vo);
	
	// 2. 주문 상세 품목 등록
	public int insertOrderItem(OrderItemVO itemVO);
	
	// 3. 주문 마스터 전체 목록 조회
	public List<CustomerOrderVO> selectCustomerOrderList();
	
	// 4. 특정 주문(order_id)에 속한 상세 품목 목록 조회
	public List<OrderItemVO> selectOrderItemsByOrderId(Long order_id);
	
	// 5. 주문 상태 변경('pending'=> 'completed' 등)
	public int updateOrderStatus(CustomerOrderVO vo);

}

