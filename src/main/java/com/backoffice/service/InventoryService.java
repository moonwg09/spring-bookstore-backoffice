package com.backoffice.service;

import java.util.List;
import com.backoffice.model.InventoryVO;

public interface InventoryService {
    // 1. 재고 초기 등록 (신규 도서 등록 시 최초 실재고 0 또는 입고 수량으로 생성)
    public boolean registerInventory(InventoryVO vo);
    
    // 2. 전체 재고 현황 목록 조회
    public List<InventoryVO> getInventoryList();
    
    // 3. 특정 도서의 재고 단건 조회
    public InventoryVO getInventoryByBookId(Long book_id);
    
    // 4. 재고 입출고 수량 반영 (양수: 입고 증가, 음수: 출고/파손 감소)
    public boolean modifyStock(InventoryVO vo);
    
    // 5. 안전 재고 설정 변경
    public boolean modifySafetyStock(InventoryVO vo);
}
