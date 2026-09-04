package com.backoffice.service;

import java.util.List;

import com.backoffice.model.CartVO;

public interface CartService {
	
	public boolean addCart(CartVO cart);
    public List<CartVO> getCartList(Long member_id);
    public boolean modifyCartQty(CartVO cart);
    public boolean removeCart(Long cart_id);

}

