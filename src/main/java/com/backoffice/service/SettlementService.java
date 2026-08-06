package com.backoffice.service;

import java.util.List;

import com.backoffice.model.DashboardVO;
import com.backoffice.model.SalesRankVO;
import com.backoffice.model.SettlementVO;

public interface SettlementService {
    // 1. 월별 출판사 매입 정산 데이터 생성 및 등록
    public boolean registerSettlement(SettlementVO vo);
    
    // 2. 전체 정산 내역 목록 조회 (대시보드 출력용)
    public List<SettlementVO> getSettlementList();
    
    // 3. 특정 월(YYYY-MM) 기준 정산 내역 조회
    public List<SettlementVO> getSettlementByMonth(String month);
    
    // 4. 정산 대금 지급 확정 및 마감 처리 ('READY' -> 'PROCESSED')
    public boolean processSettlement(Long settlement_id);
    
    public DashboardVO getDashboardKPI();
    
    public List<SalesRankVO> getTop3SalesRanking();
    
    public List<SettlementVO> getCurrentMonthSettlement();
}