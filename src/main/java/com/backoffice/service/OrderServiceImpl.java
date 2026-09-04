package com.backoffice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backoffice.mapper.OrderMapper;
import com.backoffice.model.CartVO;
import com.backoffice.model.CustomerOrderVO;
import com.backoffice.model.MemberVO;
import com.backoffice.model.OrderItemVO;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Transactional
    @Override
    public boolean processOrder(CustomerOrderVO order, MemberVO loginUser, List<CartVO> cartList) {
        // 1. 잔액 검증
        long totalAmount = 0;
        for (CartVO cart : cartList) {
            totalAmount += (long) cart.getPrice() * cart.getQuantity();
        }

        

        // 2. 주문 마스터 등록
        order.setMember_id(loginUser.getMember_Id());
        order.setTotal_amount(totalAmount);
        order.setStatus("COMPLETED");
        
        // 주문 마스터 등록
        orderMapper.insertOrder(order);

        // 3. 주문 상세 등록 및 재고 차감
        for (CartVO cart : cartList) {
            OrderItemVO item = new OrderItemVO();
            item.setOrder_id(order.getOrder_id());
            item.setBook_id(cart.getBook_id());
            item.setQty(cart.getQuantity());
            item.setPrice((long) cart.getPrice());

            orderMapper.insertOrderItem(item);
            orderMapper.updateInventoryStock(item);
        }


        // 5. 장바구니 비우기
        orderMapper.clearCart(loginUser.getMember_Id());


        return true;
    }

    @Override
    public List<CustomerOrderVO> getOrderHistory(Long member_id) {
        return orderMapper.getOrderList(member_id);
    }
}
