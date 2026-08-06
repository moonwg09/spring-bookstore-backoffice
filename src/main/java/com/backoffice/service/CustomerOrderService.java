package com.backoffice.service;

import java.util.List;
import com.backoffice.model.CustomerOrderVO;

public interface CustomerOrderService {
    // 1. 고객 주문 접수 (마스터 + 상세 품목 일괄 등록 트랜잭션)
    public void registerOrder(CustomerOrderVO orderVO);
    
    // 2. 전체 주문 목록 조회 (상세 품목 리스트 함께 결합)
    public List<CustomerOrderVO> getOrderList();
    
    // 3. 특정 주문 단건 상세 조회
    public CustomerOrderVO getOrder(Long order_id);
    
    // 4. [핵심 물류 연동] 주문 상태 변경 및 창고 재고 자동 차감/원복 (ACID 트랜잭션)
    public boolean modifyOrderStatus(Long order_id, String targetStatus, Long employee_id, String reason);
}