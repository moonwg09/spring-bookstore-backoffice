<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>마당서점 B2B - 기간별 통합 매출 대시보드</title>
<!-- 1. 공통 스타일시트 연동 (스프링 MVC 경로 적용) -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body class="role-admin">
<aside class="sidebar">
    <div class="logo">마당서점 B2B</div>
    <ul class="nav-menu">
        <!-- 2. 좌측 메뉴 링크를 스프링 컨트롤러 주소로 변경 -->
        <li><a href="${pageContext.request.contextPath}/admin/dashboard" class="active">대시보드 (통계)</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/hr/list">사원 및 보안 관리</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/master/list">기준 정보 관리</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/inventory/list">재고 및 물류 관리</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/order/list">고객 주문 관리</a></li>
    </ul>
</aside>
<main class="main-content">
    <div class="header">
        <h2>통합 경영 관리 대시보드</h2>
        <div class="header-user-zone">
            <select class="role-selector" onchange="document.body.className = this.value;">
                <option value="role-admin">👑 총괄관리자 뷰</option>
                <option value="role-staff">👤 일반직원 뷰</option>
            </select>
            <div class="user-info">${not empty loginUser ? loginUser.name : '홍길동'}님</div>
            <button class="btn-logout" onclick="alert('로그아웃 처리되었습니다.');">로그아웃</button>
        </div>
    </div>
    
    <!-- 실제 DB 트랜잭션 데이터가 실시간으로 반영되는 4대 핵심 KPI 카드 -->
    <div class="card-container">
        <div class="card">
            <h3>누적 총 매출액 (출고완료)</h3>
            <div class="value"><fmt:formatNumber value="${kpi.totalRevenue}" pattern="#,###"/> 원</div>
        </div>
        <div class="card">
            <h3>신규 주문 접수건</h3>
            <div class="value">${kpi.newOrderCount} 건</div>
        </div>
        <div class="card">
            <h3>주문 취소/반품건</h3>
            <div class="value b-red" style="display:inline-block;">${kpi.cancelCount} 건</div>
        </div>
        <div class="card">
            <h3>품절 임박 도서 (안전재고 이하)</h3>
            <div class="value b-yellow" style="display:inline-block;">${kpi.lowStockCount} 건</div>
        </div>
    </div>

    <!-- 실제 DB 매출 데이터가 반영되는 실시간 분기별 차트 -->
    <h3 class="section-title">기간별 통합 매출 추이 (<fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy"/>년 분기별 지표)</h3>
    <div class="chart-box">
        <div class="mock-chart">
            <!-- Q1 (1분기) -->
            <div class="chart-bar-group">
                <div class="chart-bar" style="height: ${kpi.q1Revenue > 0 ? (kpi.q1Revenue / 100000) + 20 : 15}px;">
                    <fmt:formatNumber value="${kpi.q1Revenue}" pattern="#,###"/>
                </div>
                <div class="chart-label">Q1 (1분기)</div>
            </div>
            <!-- Q2 (2분기) -->
            <div class="chart-bar-group">
                <div class="chart-bar" style="height: ${kpi.q2Revenue > 0 ? (kpi.q2Revenue / 100000) + 20 : 15}px;">
                    <fmt:formatNumber value="${kpi.q2Revenue}" pattern="#,###"/>
                </div>
                <div class="chart-label">Q2 (2분기)</div>
            </div>
            <!-- Q3 (3분기 : 현재 진행중 강조 테마 적용) -->
            <div class="chart-bar-group">
                <div class="chart-bar" style="height: ${kpi.q3Revenue > 0 ? (kpi.q3Revenue / 100000) + 20 : 15}px; background:#facc15;">
                    <fmt:formatNumber value="${kpi.q3Revenue}" pattern="#,###"/>
                </div>
                <div class="chart-label" style="color:#facc15; font-weight:bold;">Q3 (진행중)</div>
            </div>
            <!-- Q4 (4분기) -->
            <div class="chart-bar-group">
                <div class="chart-bar" style="height: ${kpi.q4Revenue > 0 ? (kpi.q4Revenue / 100000) + 20 : 15}px;">
                    <fmt:formatNumber value="${kpi.q4Revenue}" pattern="#,###"/>
                </div>
                <div class="chart-label">Q4 (4분기)</div>
            </div>
        </div>
    </div>

    <!-- 1. 카테고리 및 출판사별 실시간 판매 랭킹 (Top 3) -->
    <h3 class="section-title">카테고리 및 출판사별 판매 랭킹 (Top 3)</h3>
    <table>
        <thead>
            <tr>
                <th>순위</th>
                <th>도서명</th>
                <th>카테고리</th>
                <th>주요 거래처 (출판사)</th>
                <th>누적 판매량</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${rankList}" var="rank">
            <tr>
                <td><span class="badge b-yellow" style="padding:4px 8px;">${rank.rank}위</span></td>
                <td><strong>${rank.book_title}</strong></td>
                <td>${rank.category}</td>
                <td>${rank.publisher_name}</td>
                <td style="color:#deff9a; font-weight:bold;">${rank.total_sales_qty} 권</td>
            </tr>
            </c:forEach>
            <c:if test="${empty rankList}">
            <tr>
                <td colspan="5" style="text-align:center; padding:30px;">출고 완료된 도서 판매 실적 데이터가 없습니다.</td>
            </tr>
            </c:if>
        </tbody>
    </table>

    <!-- 2. 출판사별 월간 도서 매입 대금 정산 내역 (당월 SYSDATE 기준 필터링) -->
    <div class="admin-only">
        <h3 class="section-title">출판사별 월간 도서 매입 대금 정산 내역 (당월 기준)</h3>
        <table>
            <thead>
                <tr>
                    <th>정산월</th>
                    <th>출판사 코드</th>
                    <th>출판사명</th>
                    <th>총 매입액 (원가)</th>
                    <th>정산 대상액 (지급액)</th>
                    <th>현재 정산 상태</th>
                    <th>지급 처리 (Action)</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${settlementList}" var="settle">
                <tr>
                    <td><strong>${settle.settlement_month}</strong></td>
                    <td>PUB-00${settle.publisher_id}</td>
                    <td><strong>${settle.publisher_name}</strong></td>
                    <td><fmt:formatNumber value="${settle.total_purchase_amount}" pattern="#,###"/> 원</td>
                    <td style="color:#deff9a; font-weight:bold;"><fmt:formatNumber value="${settle.total_amount}" pattern="#,###"/> 원</td>
                    <td>
                        <c:choose>
                            <c:when test="${settle.status eq 'PROCESSED'}">
                                <span class="badge b-green">정산완료</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge b-yellow">정산대기</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:if test="${settle.status ne 'PROCESSED'}">
                            <form action="${pageContext.request.contextPath}/admin/settlement/process" method="post" style="display:inline;">
                                <input type="hidden" name="settlement_id" value="${settle.settlement_id}">
                                <button type="submit" class="btn" style="background:#deff9a; color:#000; font-weight:700;" 
                                        onclick="return confirm('[${settle.publisher_name}] 출판사에 ${settle.total_amount}원 지급을 완료하고 정산을 마감하시겠습니까?');">
                                    지급 마감
                                </button>
                            </form>
                        </c:if>
                        <c:if test="${settle.status eq 'PROCESSED'}">
                            <span style="color:#666; font-size:13px;">지급 완료됨</span>
                        </c:if>
                    </td>
                </tr>
                </c:forEach>
                <c:if test="${empty settlementList}">
                <tr>
                    <td colspan="7" style="text-align:center; padding:30px;">이번 달(<fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy-MM"/>)에 등록된 출판사 정산 데이터가 없습니다.</td>
                </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</main>
</body>
</html>