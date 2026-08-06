package com.backoffice.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backoffice.mapper.InventoryMapper;
import com.backoffice.model.InventoryVO;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryMapper invMapper;

    @Override
    public boolean registerInventory(InventoryVO vo) {
        return invMapper.insertInventory(vo) == 1;
    }

    @Override
    public List<InventoryVO> getInventoryList() {
        return invMapper.selectInventoryList();
    }

    @Override
    public InventoryVO getInventoryByBookId(Long book_id) {
        return invMapper.selectInventoryByBookId(book_id);
    }

    @Transactional // 추후 타겟 3(Inventory_History) 등록 기능과 1개의 트랜잭션으로 결합될 핵심 영역입니다.
    @Override
    public boolean modifyStock(InventoryVO vo) {
        // 실재고 수량 증감 반영
        return invMapper.updateAddStock(vo) == 1;
    }

    @Override
    public boolean modifySafetyStock(InventoryVO vo) {
        return invMapper.updateSafetyStock(vo) == 1;
    }
}