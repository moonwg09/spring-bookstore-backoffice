package com.backoffice.mapper;

import java.util.List;


import com.backoffice.model.CustomerOrderVO;
import com.backoffice.model.OrderItemVO;

public interface OrderMapper {
	
	// 주문 마스터 생성
	public void insertOrder(CustomerOrderVO order);
	
	// 주문 상세 품목 생성
	public void insertOrderItem(OrderItemVO item);
	
	
	// 도서 재고 참가 업데이트
	public int updateInventoryStock(OrderItemVO item);
	
	// 회원의 주문 내역 목록 조회
	public List<CustomerOrderVO> getOrderList(Long member_id);
	
	// 장바구니 비우기
	public void clearCart(Long member_id);

}

