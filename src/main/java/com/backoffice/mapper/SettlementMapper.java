package com.backoffice.mapper;

import java.util.List;

import com.backoffice.model.DashboardVO;
import com.backoffice.model.SalesRankVO;
import com.backoffice.model.SettlementVO;

public interface SettlementMapper {
    // 1. 월별 출판사 정산 데이터 신규 생성 (INSERT)
    public int insertSettlement(SettlementVO vo);
    
    // 2. 전체 정산 내역 목록 조회 (출판사명 JOIN, 최신 월 기준 정렬)
    public List<SettlementVO> selectSettlementList();
    
    // 3. 특정 월(YYYY-MM)의 출판사별 정산 목록 조회
    public List<SettlementVO> selectSettlementByMonth(String settlement_month);
    
    // 4. 정산 마감 상태 변경 ('READY' => 'PROCESSED')
    public int updateSettlementStatus(SettlementVO vo);
    
 // 기존 코드 아래에 추가
    public DashboardVO selectDashboardKPI();
    
    public List<SalesRankVO> selectTop3SalesRanking();
    
    public List<SettlementVO> selectCurrentMonthSettlement();
}	
