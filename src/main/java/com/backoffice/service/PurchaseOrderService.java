package com.backoffice.service;

import java.util.List;

import com.backoffice.model.PurchaseOrderVO;

public interface PurchaseOrderService {
	
	public void registerPurchaseOrder(PurchaseOrderVO poVO);
	
	public List<PurchaseOrderVO> getPurchaseOrderList();
	
	public PurchaseOrderVO getPurchaseOrder(Long po_id);
	
	public boolean modifyOrderStatus(PurchaseOrderVO poVO);

}

