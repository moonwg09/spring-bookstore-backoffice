package com.backoffice.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backoffice.mapper.SettlementMapper;
import com.backoffice.model.SettlementVO;

@Service
public class SettlementServiceImpl implements SettlementService {

    @Autowired
    private SettlementMapper settlementMapper;

    @Override
    public boolean registerSettlement(SettlementVO vo) {
        return settlementMapper.insertSettlement(vo) == 1;
    }

    @Override
    public List<SettlementVO> getSettlementList() {
        return settlementMapper.selectSettlementList();
    }

    @Override
    public List<SettlementVO> getSettlementByMonth(String month) {
        return settlementMapper.selectSettlementByMonth(month);
    }

    @Transactional
    @Override
    public boolean processSettlement(Long settlement_id) {
        SettlementVO vo = new SettlementVO();
        vo.setSettlement_id(settlement_id);
        vo.setStatus("PROCESSED"); // 지급 완료 상태로 변경
        return settlementMapper.updateSettlementStatus(vo) == 1;
    }
    
    @Override
    public com.backoffice.model.DashboardVO getDashboardKPI() {
        return settlementMapper.selectDashboardKPI();
    }
    
    @Override
    public List<com.backoffice.model.SalesRankVO> getTop3SalesRanking() {
        return settlementMapper.selectTop3SalesRanking();
    }

    @Override
    public List<com.backoffice.model.SettlementVO> getCurrentMonthSettlement() {
        return settlementMapper.selectCurrentMonthSettlement();
    }
}
