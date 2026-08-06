<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>마당서점 B2B - 창고 재고 및 물류 관리</title>
<!-- 공통 스타일시트 연동 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
<style>
    .search-panel { background: #1a1a1a; padding: 20px; border-radius: 12px; border: 1px solid #333; margin-bottom: 25px; display: flex; gap: 10px; align-items: center; }
    .search-panel input { padding: 10px; border-radius: 6px; border: 1px solid #333; background: #000; color: #fff; width: 300px; }
</style>
</head>
<body class="role-admin">
<aside class="sidebar">
    <div class="logo">마당서점 B2B</div>
    <ul class="nav-menu">
        <li><a href="${pageContext.request.contextPath}/admin/dashboard">대시보드 (통계)</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/hr/list">사원 및 보안 관리</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/book/list">기준 정보 관리</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/inventory/list" class="active">재고 및 물류 관리</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/order/list">고객 주문 관리</a></li>
    </ul>
</aside>
<main class="main-content">
    <div class="header">
        <h2>창고 재고 및 물류 관리 (WMS)</h2>
        <div class="header-user-zone">
            <select class="role-selector" onchange="document.body.className = this.value;">
                <option value="role-admin">👑 총괄관리자 뷰</option>
                <option value="role-staff">👤 일반직원 뷰</option>
            </select>
            <!-- 세션에 저장된 사원 이름 출력 -->
            <div class="user-info">${not empty loginUser ? loginUser.name : '홍길동'}님</div>
            <button class="btn-logout" onclick="alert('로그아웃 처리되었습니다.');">로그아웃</button>
        </div>
    </div>
    
    <h3 class="section-title" style="margin-top: 0;">창고 도서 실재고 현황</h3>
    <div class="search-panel">
        <input type="text" placeholder="도서명, 출판사 또는 ISBN 다중 검색...">
        <button class="btn">다중 조건 검색</button>
        <button class="btn" style="background:#facc15; color:#000; border-color:#facc15; font-weight:700;">
            ⚠️ 품절 임박 (안전재고 이하) 모아보기
        </button>
    </div>

    <!-- 1. 창고 도서 실재고 테이블 -->
    <table>
        <thead>
            <tr>
                <th>도서명 (ISBN)</th>
                <th>현재 창고 재고</th>
                <th>안전재고 기준</th>
                <th>알림 상태</th>
                <th>물류 처리(Action)</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${inventoryList}" var="inv">
            <tr>
                <td>${inv.book_title} <small style="color:#888;">(${inv.isbn})</small></td>
                <td>
                    <c:choose>
                        <c:when test="${inv.current_stock <= inv.safety_stock}">
                            <strong style="color:#f87171; font-size:18px;">${inv.current_stock} 권</strong>
                        </c:when>
                        <c:otherwise>
                            <strong style="font-size:18px;">${inv.current_stock} 권</strong>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>${inv.safety_stock} 권</td>
                <td>
                    <c:choose>
                        <c:when test="${inv.current_stock == 0}">
                            <span class="badge b-red">재고소진</span>
                        </c:when>
                        <c:when test="${inv.current_stock <= inv.safety_stock}">
                            <span class="badge b-yellow">품절임박</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge b-green">여유재고</span>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <button class="btn" style="margin-right: 5px;" onclick="openOrderModal(${inv.book_id}, '${inv.book_title}');">발주요청</button>
                    <!-- 수동 조정 JS 팝업 호출 -->
                    <button class="btn" style="border-color:#60a5fa; color:#60a5fa;" onclick="openAdjustModal(${inv.book_id}, '${inv.book_title}');">수동조정</button>
                </td>
            </tr>
            </c:forEach>
            <c:if test="${empty inventoryList}">
            <tr>
                <td colspan="5" style="text-align:center; padding:30px;">등록된 창고 재고 데이터가 없습니다.</td>
            </tr>
            </c:if>
        </tbody>
    </table>

    <!-- 2. 수동 재고 조정 이력 (관리자 전용 영역 - RBAC 반영) -->
    <div class="admin-only" style="margin-top: 40px;">
        <div class="section-title" style="display:flex; justify-content:space-between; align-items:center;">
            <span>수동 재고 조정 이력 (Stock Log)</span>
            <button class="btn" style="padding:4px 10px; font-size:14px;">엑셀 다운로드</button>
        </div>
        <table>
            <thead>
                <tr>
                    <th>조정 일시</th>
                    <th>도서명 (ISBN)</th>
                    <th>조정 수량</th>
                    <th>조정 사유 (Reason)</th>
                    <th>처리 담당자</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${historyList}" var="hist">
                <tr>
                    <td><fmt:formatDate value="${hist.created_at}" pattern="yyyy-MM-dd HH:mm"/></td>
                    <td>${hist.book_title} <small style="color:#888;">(${hist.isbn})</small></td>
                    <td>
                        <c:choose>
                            <c:when test="${hist.change_qty < 0}">
                                <span style="color:#f87171; font-weight:bold;">${hist.change_qty} 권</span>
                            </c:when>
                            <c:otherwise>
                                <span style="color:#4ade80; font-weight:bold;">+${hist.change_qty} 권</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>${hist.reason}</td>
                    <td>${not empty hist.employee_name ? hist.employee_name : 'ADMIN'}</td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- 3. 출판사 B2B 발주 및 입고 승인 대기 내역 -->
    <h3 class="section-title" style="margin-top: 40px;">출판사 B2B 발주 및 입고 승인 대기 내역</h3>
    <table>
        <thead>
            <tr>
                <th>발주번호</th>
                <th>도서명 / 주문내역</th>
                <th>발주 수량</th>
                <th>거래 출판사</th>
                <th>발주 요청일</th>
                <th>물류 처리(Action)</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${poList}" var="po">
            <tr>
                <td>PO-${po.po_id}</td>
                <td>
                    <!-- 1:N 품목 리스트 출력 -->
                    <c:forEach items="${po.itemList}" var="item" varStatus="status">
                        ${item.book_title} (${item.order_qty}권)<c:if test="${!status.last}"> / </c:if>
                    </c:forEach>
                </td>
                <td>총 수량 확인</td>
                <td>${po.publisher_name}</td>
                <td><fmt:formatDate value="${po.order_date}" pattern="yyyy-MM-dd"/></td>
                <td>
                    <c:choose>
                        <c:when test="${po.status eq 'REQUESTED'}">
                            <!-- 입고 승인 폼 전송 (첫 번째 아이템 기준 예시 처리) -->
                            <form action="${pageContext.request.contextPath}/admin/inventory/receive" method="post" style="display:inline;">
                                <input type="hidden" name="po_id" value="${po.po_id}">
                                <input type="hidden" name="book_id" value="${po.itemList[0].book_id}">
                                <input type="hidden" name="qty" value="${po.itemList[0].order_qty}">
                                <input type="hidden" name="login_emp_id" value="1"> <!-- 기본 사원 ID -->
                                <button type="submit" class="btn" style="background:#deff9a; color:#000; font-weight:700;" 
                                        onclick="return confirm('트랜잭션 시작: 발주 상태가 [입고완료]로 변경되며 창고 재고가 자동합산 처리됩니다. 진행하시겠습니까?');">
                                    입고 승인 (재고 자동합산)
                                </button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <span class="badge b-green">입고완료</span>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            </c:forEach>
        </tbody>
    </table>
</main>

<script>
function openAdjustModal(bookId, bookTitle) {
    let qtyStr = prompt("[" + bookTitle + "] 도서의 수동 조정 수량을 입력하세요.\n(예: 파손 출고는 -2, 누락 입고는 +5)");
    if (!qtyStr) return;
    
    let changeQty = parseInt(qtyStr);
    if (isNaN(changeQty) || changeQty === 0) {
        alert("0이 아닌 정확한 숫자를 입력해주세요.");
        return;
    }
    
    let reason = prompt("재고 조정 사유를 입력하세요.\n(예: 창고 누수로 인한 표지 파손 폐기)");
    if (!reason) {
        alert("조정 사유를 반드시 입력해야 Audit Log에 기록됩니다.");
        return;
    }
    
    // 폼 동적 생성 후 서버로 POST 전송
    let form = document.createElement("form");
    form.setAttribute("method", "post");
    form.setAttribute("action", "${pageContext.request.contextPath}/admin/inventory/adjust");
    
    let params = {
        book_id: bookId,
        change_qty: changeQty,
        reason: reason,
        login_emp_id: 1 // 관리자 ID 기본값
    };
    
    for (let key in params) {
        let input = document.createElement("input");
        input.setAttribute("type", "hidden");
        input.setAttribute("name", key);
        input.setAttribute("value", params[key]);
        form.appendChild(input);
    }
    
    document.body.appendChild(form);
    form.submit();
} // 🚨 [원인 해결!] 여기에 닫는 괄호(})가 빠져 있어서 아래 함수가 실행되지 않았던 것입니다!

function openOrderModal(bookId, bookTitle) {
    // 1. 발주 수량 입력받기
    let qtyStr = prompt("[" + bookTitle + "] 도서의 발주 신청 수량을 입력하세요.\n(예: 50)");
    if (!qtyStr) return;
    let qty = parseInt(qtyStr);
    if (isNaN(qty) || qty <= 0) {
        alert("정확한 발주 수량(숫자)을 입력해주세요.");
        return;
    }
    
    // 2. 거래처(출판사) ID 입력받기 (현재 테스트용으로 1번 출판사 권장)
    let pubStr = prompt("거래할 출판사 ID 번호를 입력하세요.\n(예: DB에 존재하는 1 입력)");
    if (!pubStr) return;
    let pubId = parseInt(pubStr);
    if (isNaN(pubId)) {
        alert("정확한 출판사 ID(숫자)를 입력해주세요.");
        return;
    }
    
    // 3. 동적 폼 생성 및 서버로 POST 전송
    let form = document.createElement("form");
    form.setAttribute("method", "post");
    form.setAttribute("action", "${pageContext.request.contextPath}/admin/inventory/order");
    
    let params = {
        book_id: bookId,
        publisher_id: pubId,
        qty: qty,
        login_emp_id: 1 // 관리자 기본 ID
    };
    
    for (let key in params) {
        let input = document.createElement("input");
        input.setAttribute("type", "hidden");
        input.setAttribute("name", key);
        input.setAttribute("value", params[key]);
        form.appendChild(input);
    }
    
    document.body.appendChild(form);
    form.submit();
}
</script>
</body>
</html>