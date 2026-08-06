package com.backoffice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backoffice.mapper.PurchaseOrderMapper;
import com.backoffice.model.PurchaseOrderItemVO;
import com.backoffice.model.PurchaseOrderVO;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
	
	@Autowired
	private PurchaseOrderMapper poMapper;
	
	@Transactional // 마스터와 상세 품목 등록 중 하나라도 실패하면 전체 롤백
    @Override
    public void registerPurchaseOrder(PurchaseOrderVO poVO) {
        // 1. 발주 마스터 등록 (등록과 동시에 poVO의 po_id 필드에 DB 시퀀스 값이 채워짐)
        poMapper.insertPurchaseOrder(poVO);
        
        // 2. 전달받은 상세 품목 리스트가 존재하는지 확인
        if (poVO.getItemList() == null || poVO.getItemList().size() == 0) {
            return;
        }
        
        // 3. 생성된 마스터의 po_id를 각 품목 VO에 매핑한 뒤 개별 INSERT
        for (PurchaseOrderItemVO item : poVO.getItemList()) {
            item.setPo_id(poVO.getPo_id()); // 부모 FK 주입
            poMapper.insertPurchaseOrderItem(item);
        }
    }

	@Override
    public List<PurchaseOrderVO> getPurchaseOrderList() {
        List<PurchaseOrderVO> list = poMapper.selectPurchaseOrderList();
        
        // [에러 해결 핵심] 각 발주 마스터마다 하위 상세 품목 리스트를 DB에서 조회해 VO에 채워줍니다!
        for (PurchaseOrderVO po : list) {
            po.setItemList(poMapper.selectPurchaseOrderItemsByPoId(po.getPo_id()));
        }
        
        return list;
    }

    @Override
    public PurchaseOrderVO getPurchaseOrder(Long po_id) {
        // 1. 특정 발주의 상세 품목 리스트를 조회
        List<PurchaseOrderItemVO> items = poMapper.selectPurchaseOrderItemsByPoId(po_id);
        
        // 2. 전체 목록 중 해당 po_id 마스터 찾기 (혹은 단건 조회 매퍼 활용 가능)
        // 여기서는 매퍼에서 조회한 아이템 리스트를 마스터 VO에 묶어서 반환하기 위해 구조화
        PurchaseOrderVO poVO = new PurchaseOrderVO();
        poVO.setPo_id(po_id);
        poVO.setItemList(items);
        
        return poVO;
    }

    @Override
    public boolean modifyOrderStatus(PurchaseOrderVO poVO) {
        return poMapper.updateOrderStatus(poVO) == 1;
    }

}
