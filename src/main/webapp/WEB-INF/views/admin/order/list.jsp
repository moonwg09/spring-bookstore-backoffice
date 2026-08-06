<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>마당서점 B2B - 고객 주문 내역 및 CS 트랜잭션</title>
<!-- 공통 스타일시트 연동 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
<style>
    .action-group { display: flex; gap: 5px; }
    .btn-primary { background: #333; color: #fff; border: 1px solid #555; }
    .btn-primary:hover { background: #deff9a; color: #000; }
    .btn-danger { background: transparent; color: #f87171; border: 1px solid #f87171; }
    .btn-danger:hover { background: rgba(239, 68, 68, 0.2); }
</style>
</head>
<body class="role-admin">
<aside class="sidebar">
    <div class="logo">마당서점 B2B</div>
    <ul class="nav-menu">
        <li><a href="${pageContext.request.contextPath}/admin/dashboard">대시보드 (통계)</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/hr/list">사원 및 보안 관리</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/book/list">기준 정보 관리</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/inventory/list">재고 및 물류 관리</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/order/list" class="active">고객 주문 관리</a></li>
    </ul>
</aside>
<main class="main-content">
    <div class="header">
        <h2>고객 주문 내역 및 CS 트랜잭션 처리</h2>
        <div class="header-user-zone">
            <select class="role-selector" onchange="document.body.className = this.value;">
                <option value="role-admin">👑 총괄관리자 뷰</option>
                <option value="role-staff">👤 일반직원 뷰</option>
            </select>
            <div class="user-info">${not empty loginUser ? loginUser.name : '홍길동'}님</div>
            <button class="btn-logout" onclick="alert('로그아웃 처리되었습니다.');">로그아웃</button>
        </div>
    </div>
    
    <div style="margin-bottom: 20px; display:flex; justify-content: space-between;">
        <div style="display:flex; gap:10px;">
            <button class="btn btn-primary" onclick="location.reload();">전체 보기</button>
            <button class="btn" style="background:#f87171; color:#fff; border:none;" onclick="alert('취소 요청건 집중 필터 기능입니다.');">취소/반품 요청건 집중 처리</button>
        </div>
        <input type="text" placeholder="주문번호 또는 고객명 검색..." style="padding:10px; border-radius:6px; border:1px solid #333; background:#000; color:#fff; width:250px;">
    </div>

    <table>
        <thead>
            <tr>
                <th>주문번호</th>
                <th>주문일시</th>
                <th>고객명</th>
                <th>주문 도서 (수량)</th>
                <th>총 주문금액</th>
                <th>물류 상태</th>
                <th>트랜잭션 (Action)</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${orderList}" var="order">
            <tr>
                <td><strong>ORD-${order.order_id}</strong></td>
                <td><fmt:formatDate value="${order.order_date}" pattern="yyyy-MM-dd HH:mm"/></td>
                <td>${not empty order.customer_name ? order.customer_name : '홍길동'}</td>
                <td>
                    <!-- 1:N 품목 리스트 출력 -->
                    <c:forEach items="${order.itemList}" var="item" varStatus="status">
                        ${item.book_title} (${item.qty}권)<c:if test="${!status.last}"> / </c:if>
                    </c:forEach>
                </td>
                <td><fmt:formatNumber value="${order.total_amount}" pattern="#,###"/> 원</td>
                <td>
                    <c:choose>
                        <c:when test="${order.status eq 'PENDING'}">
                            <span class="badge b-yellow">주문접수</span>
                        </c:when>
                        <c:when test="${order.status eq 'COMPLETED'}">
                            <span class="badge b-green">출고완료</span>
                        </c:when>
                        <c:when test="${order.status eq 'CANCELLED'}">
                            <span class="badge b-red">취소완료</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge" style="background:#333; color:#aaa;">${order.status}</span>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <div class="action-group">
                        <c:if test="${order.status eq 'PENDING'}">
                            <!-- [출고 처리]: 승인 시 재고 -차감 -->
                            <button class="btn btn-primary" onclick="changeOrderStatus(${order.order_id}, 'COMPLETED', '관리자 정상 출고 승인');">출고처리</button>
                            <!-- [강제 취소]: 재고 변동 없이 주문만 취소 -->
                            <button class="btn btn-danger admin-only" onclick="changeOrderStatus(${order.order_id}, 'CANCELLED', '관리자 직권 강제 취소');">강제취소</button>
                        </c:if>
                        <c:if test="${order.status eq 'COMPLETED'}">
                            <!-- [취소 승인]: 출고된 주문을 취소하고 빠져나간 재고를 +원복 -->
                            <button class="btn" style="background:#f87171; color:#fff; border:none;" onclick="changeOrderStatus(${order.order_id}, 'CANCELLED', '고객 반품/취소 승인 (재고 원복)');">취소 승인(재고원복)</button>
                        </c:if>
                        <c:if test="${order.status eq 'CANCELLED'}">
                            <span style="color:#666; font-size:13px;">처리 종료</span>
                        </c:if>
                    </div>
                </td>
            </tr>
            </c:forEach>
            <c:if test="${empty orderList}">
            <tr>
                <td colspan="7" style="text-align:center; padding:30px;">접수된 고객 주문 데이터가 없습니다.</td>
            </tr>
            </c:if>
        </tbody>
    </table>
</main>

<!-- 주문 상태 변경 및 동적 폼 전송 자바스크립트 -->
<script>
function changeOrderStatus(orderId, targetStatus, defaultReason) {
    let actionName = (targetStatus === 'COMPLETED') ? "출고 승인 (재고 차감)" : "주문 취소 (재고 원복)";
    let reason = prompt("[ORD-" + orderId + "] " + actionName + " 사유를 입력하세요.", defaultReason);
    
    if (!reason) {
        alert("사유를 입력해야 Audit Log 및 물류 히스토리에 기록됩니다.");
        return;
    }
    
    // 폼 동적 생성 후 서버로 POST 전송
    let form = document.createElement("form");
    form.setAttribute("method", "post");
    form.setAttribute("action", "${pageContext.request.contextPath}/admin/order/status");
    
    let params = {
        order_id: orderId,
        target_status: targetStatus,
        reason: reason,
        login_emp_id: 1 // 기본 관리자 ID
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