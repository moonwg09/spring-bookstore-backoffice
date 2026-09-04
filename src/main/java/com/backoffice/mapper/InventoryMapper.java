package com.backoffice.mapper;

import java.util.List;

import com.backoffice.model.InventoryVO;

public interface InventoryMapper {
	
	// 1. 재고 초기 등록 (신규 도서 등록 시 또는 최초 입고 시 재고 레코드 생성)
    public int insertInventory(InventoryVO vo);
    
    // 2. 전체 재고 목록 조회 (도서명, ISBN 등 JOIN)
    public List<InventoryVO> selectInventoryList();
    
    // 3. 특정 도서(book_id)의 재고 단건 조회
    public InventoryVO selectInventoryByBookId(Long book_id);
    
    // 4. 재고 수량 증감 (입고 시 +수량, 출고/파손 시 -수량 더하기)
    public int updateAddStock(InventoryVO vo);
    
    // 5. 안전 재고 설정 변경
    public int updateSafetyStock(InventoryVO vo);

}

