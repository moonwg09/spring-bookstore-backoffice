package com.backoffice.mapper;

import java.util.List;

import com.backoffice.model.PurchaseOrderItemVO;
import com.backoffice.model.PurchaseOrderVO;

public interface PurchaseOrderMapper {
	
	// 1. 발주 마스터 등록 (등록 후 po_id 반환)
    public int insertPurchaseOrder(PurchaseOrderVO vo);
    
    // 2. 발주 상세 품목 등록
    public int insertPurchaseOrderItem(PurchaseOrderItemVO itemVO);
    
    // 3. 발주 마스터 전체 목록 조회
    public List<PurchaseOrderVO> selectPurchaseOrderList();
    
    // 4. 특정 발주(po_id)에 속한 상세 품목 목록 조회
    public List<PurchaseOrderItemVO> selectPurchaseOrderItemsByPoId(Long po_id);
    
    // 5. 발주 상태 변경 (REQUESTED -> APPROVED 등)
    public int updateOrderStatus(PurchaseOrderVO vo);

}

