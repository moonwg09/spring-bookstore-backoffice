package com.backoffice.mapper;

import java.util.List;
import com.backoffice.model.InventoryHistoryVO;

public interface InventoryHistoryMapper {
    // 1. 재고 변동 내역 기록 (INSERT 오직 1개)
    public int insertHistory(InventoryHistoryVO vo);
    
    // 2. 전체 재고 변동 히스토리 조회 (최신순 정렬)
    public List<InventoryHistoryVO> selectHistoryList();
    
    // 3. 특정 도서(book_id)의 입출고 변동 이력만 조회
    public List<InventoryHistoryVO> selectHistoryByBookId(Long book_id);
}
