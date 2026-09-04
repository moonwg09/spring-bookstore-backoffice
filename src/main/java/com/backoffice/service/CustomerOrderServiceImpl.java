package com.backoffice.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backoffice.mapper.CustomerOrderMapper;
import com.backoffice.model.CustomerOrderVO;
import com.backoffice.model.InventoryVO;
import com.backoffice.model.OrderItemVO;

@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {

    @Autowired
    private CustomerOrderMapper orderMapper;
    
    @Autowired
    private InventoryHistoryService historyService; // 2단계에서 만든 물류 통합 서비스 주입

    @Transactional
    @Override
    public void registerOrder(CustomerOrderVO orderVO) {
        // 1. 주문 마스터 등록 (po_id처럼 생성된 order_id가 VO에 자동 주입됨)
        orderMapper.insertCustomerOrder(orderVO);
        
        // 2. 상세 품목 리스트 등록
        if (orderVO.getItemList() != null) {
            for (OrderItemVO item : orderVO.getItemList()) {
                item.setOrder_id(orderVO.getOrder_id());
                orderMapper.insertOrderItem(item);
            }
        }
    }

    @Override
    public List<CustomerOrderVO> getOrderList() {
        List<CustomerOrderVO> list = orderMapper.selectCustomerOrderList();
        // 각 주문 마스터마다 하위 상세 품목 리스트를 DB에서 조회해 채워줌
        for (CustomerOrderVO order : list) {
            order.setItemList(orderMapper.selectOrderItemsByOrderId(order.getOrder_id()));
        }
        return list;
    }

    @Override
    public CustomerOrderVO getOrder(Long order_id) {
        List<OrderItemVO> items = orderMapper.selectOrderItemsByOrderId(order_id);
        CustomerOrderVO orderVO = new CustomerOrderVO();
        orderVO.setOrder_id(order_id);
        orderVO.setItemList(items);
        return orderVO;
    }

    @Transactional(rollbackFor = Exception.class) // 재고 부족 등 예외 발생 시 주문 상태 변경까지 전체 롤백
    @Override
    public boolean modifyOrderStatus(Long order_id, String targetStatus, Long employee_id, String reason) {
        // 1. 주문 상태 업데이트
        CustomerOrderVO orderVO = new CustomerOrderVO();
        orderVO.setOrder_id(order_id);
        orderVO.setStatus(targetStatus);
        int updateResult = orderMapper.updateOrderStatus(orderVO);
        
        if (updateResult == 0) return false;

        // 2. 물류 트랜잭션 연동 (출고완료 or 취소원복 일 때만 재고 변동 실행)
        if ("COMPLETED".equals(targetStatus) || "CANCELLED".equals(targetStatus)) {
            
            // 해당 주문에 포함된 도서 품목들 조회
            List<OrderItemVO> items = orderMapper.selectOrderItemsByOrderId(order_id);
            
            for (OrderItemVO item : items) {
                InventoryVO invVO = new InventoryVO();
                invVO.setBook_id(item.getBook_id());
                
                String logReason = "";
                
                if ("COMPLETED".equals(targetStatus)) {
                    // [출고 처리]: 주문 수량만큼 재고 마이너스(-) 차감
                    invVO.setCurrent_stock(-item.getQty()); 
                    logReason = "[주문출고] ORD-" + order_id + " 출고 완료 (" + reason + ")";
                } else if ("CANCELLED".equals(targetStatus)) {
                    // [취소 승인]: 빠져나갔던 수량만큼 재고 플러스(+) 원복
                    invVO.setCurrent_stock(item.getQty());  
                    logReason = "[주문취소/원복] ORD-" + order_id + " 취소 승인 (" + reason + ")";
                }
                
                // 2단계 물류 통합 서비스 호출 (실재고 증감 + Audit Log 동시 기록)
                historyService.addStockWithHistory(invVO, employee_id, logReason);
            }
        }
        
        return true;
    }
}
