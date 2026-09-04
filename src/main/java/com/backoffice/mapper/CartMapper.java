package com.backoffice.mapper;

import java.util.List;

import com.backoffice.model.CartVO;

public interface CartMapper {
	
	// 장바구니 담기(이미 담긴 책이면 수량 증가, 아니면 신규 등록 처리 가능)
	public int insertCart(CartVO cart);
	
	// 특정 회원의 장바구니 목록 조회(도서 정보 조인)
	public List<CartVO> getCartList(Long member_id);
	
	// 장바구니 수량 변경
	public int updateCartQty(CartVO cart);
	
	// 장바구니 개별 항목 삭제
	public int deleteCart(Long cart_id);
	
	// 동일한 회원이 같은 책을 이미 담았는지 확인 (중복 체크용)
	public CartVO checkCart(CartVO cart);

}

