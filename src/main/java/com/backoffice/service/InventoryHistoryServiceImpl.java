package com.backoffice.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backoffice.mapper.InventoryHistoryMapper;
import com.backoffice.mapper.InventoryMapper;
import com.backoffice.model.InventoryHistoryVO;
import com.backoffice.model.InventoryVO;

@Service
public class InventoryHistoryServiceImpl implements InventoryHistoryService {

    @Autowired
    private InventoryHistoryMapper historyMapper;
    
    @Autowired
    private InventoryMapper inventoryMapper;

    @Transactional
    @Override
    public boolean addStockWithHistory(InventoryVO invVO, Long employee_id, String reason) {
        // 1. Inventory 테이블의 실재고 수량 증감 반영 (UPDATE)
        int updateResult = inventoryMapper.updateAddStock(invVO);
        
        //  [실무 방어 로직 추가] 대상 도서의 재고 레코드가 없어서 업데이트가 안 되었다면?
        if (updateResult == 0) {
            // 에러를 내지 않고, 해당 book_id로 기초 재고(0권) 레코드를 새로 생성해 줍니다!
            InventoryVO newInv = new InventoryVO();
            newInv.setBook_id(invVO.getBook_id());
            newInv.setCurrent_stock(0);
            newInv.setSafety_stock(5); // 기본 안전재고 5권 세팅
            inventoryMapper.insertInventory(newInv);
            
            // 기초 레코드가 생겼으니 다시 한번 증감 반영을 시도합니다.
            updateResult = inventoryMapper.updateAddStock(invVO);
        }
        
        // 2. Inventory_History 테이블에 감사(Audit) 로그 기록 (INSERT)
        InventoryHistoryVO historyVO = new InventoryHistoryVO();
        historyVO.setBook_id(invVO.getBook_id());
        historyVO.setEmployee_id(employee_id);
        historyVO.setChange_qty(invVO.getCurrent_stock());
        historyVO.setReason(reason);
        
        int insertResult = historyMapper.insertHistory(historyVO);
        
        return (updateResult == 1 && insertResult == 1);
    }

    @Override
    public List<InventoryHistoryVO> getHistoryList() {
        return historyMapper.selectHistoryList();
    }

    @Override
    public List<InventoryHistoryVO> getHistoryByBookId(Long book_id) {
        return historyMapper.selectHistoryByBookId(book_id);
    }
}
