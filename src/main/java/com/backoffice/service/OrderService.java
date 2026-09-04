package com.backoffice.service;

import java.util.List;

import com.backoffice.model.CustomerOrderVO;
import com.backoffice.model.MemberVO;

public interface OrderService {
	
	public boolean processOrder(CustomerOrderVO order, MemberVO loginUser, List<com.backoffice.model.CartVO> cartList);
    public List<CustomerOrderVO> getOrderHistory(Long member_id);

}

