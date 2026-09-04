package com.backoffice.service;

import java.util.List;
import com.backoffice.model.InventoryHistoryVO;
import com.backoffice.model.InventoryVO;

public interface InventoryHistoryService {
    // 1. [통합 물류 로직] 실재고 증감 + 변동 히스토리 동시 기록 (트랜잭션)
    public boolean addStockWithHistory(InventoryVO invVO, Long employee_id, String reason);
    
    // 2. 전체 재고 변동 히스토리 조회
    public List<InventoryHistoryVO> getHistoryList();
    
    // 3. 특정 도서(book_id)의 변동 히스토리 조회
    public List<InventoryHistoryVO> getHistoryByBookId(Long book_id);
}
