package com.backoffice.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.backoffice.mapper.CartMapper;
import com.backoffice.model.CartVO;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Override
    public boolean addCart(CartVO cart) {
        // 이미 장바구니에 담긴 상품인지 확인
        CartVO existing = cartMapper.checkCart(cart);
        if (existing != null) {
            // 이미 존재한다면 기존 수량에 더해줌
            existing.setQuantity(existing.getQuantity() + cart.getQuantity());
            return cartMapper.updateCartQty(existing) == 1;
        } else {
            // 신규 담기
            return cartMapper.insertCart(cart) == 1;
        }
    }

    @Override
    public List<CartVO> getCartList(Long member_id) {
        return cartMapper.getCartList(member_id);
    }

    @Override
    public boolean modifyCartQty(CartVO cart) {
        return cartMapper.updateCartQty(cart) == 1;
    }

    @Override
    public boolean removeCart(Long cart_id) {
        return cartMapper.deleteCart(cart_id) == 1;
    }
}
