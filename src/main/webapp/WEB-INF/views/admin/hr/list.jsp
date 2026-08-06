<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>마당서점 B2B - 사원 및 보안 관리</title>
<!-- 공통 다크모드 스타일 연동 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
<style>
    .admin-only-badge { 
        background-color: rgba(239, 68, 68, 0.15); color: #f87171; 
        border: 1px solid rgba(239, 68, 68, 0.3); padding: 4px 10px; 
        border-radius: 4px; font-size: 13px; font-weight: 700; 
    }
</style>
</head>
<body class="role-admin">
<!-- 좌측 공통 사이드바 -->
<aside class="sidebar">
    <div class="logo">마당서점 B2B</div>
    <ul class="nav-menu">
        <li><a href="/backoffice/admin/dashboard">대시보드 (통계)</a></li>
        <li><a href="/backoffice/admin/hr/list" class="active">사원 및 보안 관리</a></li>
        <li><a href="/backoffice/admin/book/list">기준 정보 관리</a></li>
        <li><a href="/backoffice/admin/inventory/list">재고 및 물류 관리</a></li>
        <li><a href="/backoffice/admin/order/list">고객 주문 관리</a></li>
    </ul>
</aside>
<main class="main-content">
    <div class="header">
        <h2>사원 및 권한 보안 관리 (HR & Security)</h2>
        <div class="header-user-zone">
            <select class="role-selector" onchange="document.body.className = this.value;">
                <option value="role-admin">👑 총괄관리자 뷰</option>
                <option value="role-staff">👤 일반직원 뷰</option>
            </select>
            <div class="user-info">${not empty loginUser ? loginUser.name : '홍길동'}님</div>
            <button class="btn-logout" onclick="alert('로그아웃 처리되었습니다.');">로그아웃</button>
        </div>
    </div>

    <!-- 1. 사원 계정 목록 영역 -->
    <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:15px;">
        <h3 class="section-title" style="margin:0;">👤 사원 계정 목록 및 권한 설정</h3>
        <button class="btn admin-only" style="background:#deff9a; color:#000; font-weight:bold;" 
                onclick="alert('신규 사원 등록은 인사 관리자에게 문의하세요.');">+ 신규 사원 등록</button>
    </div>

    <table>
        <thead>
            <tr>
                <th>사원 번호</th>
                <th>로그인 ID</th>
                <th>이름</th>
                <th>직급 (Role)</th>
                <th class="admin-only">권한 관리</th>
            </tr>
        </thead>
        <tbody>
            <!-- EmployeeVO 실제 필드명(employeeId, loginId, name, role) 매칭 -->
            <c:forEach items="${empList}" var="emp">
            <tr>
                <td>EMP-${emp.employeeId}</td>
                <td><strong style="color:#deff9a;">${emp.loginId}</strong></td>
                <td><strong>${emp.name}</strong></td>
                <td>
                    <c:choose>
                        <c:when test="${emp.role eq 'ADMIN' or emp.role eq 'admin'}">
                            <span style="color:#deff9a; font-weight:bold;">👑 총괄 관리자 (ADMIN)</span>
                        </c:when>
                        <c:otherwise>👤 일반 직원 (STAFF)</c:otherwise>
                    </c:choose>
                </td>
                <td class="admin-only">
                    <button class="btn" style="background:#ffc107; color:#000; font-weight:bold;" 
                            onclick="openRoleModal(${emp.employeeId}, '${emp.name}', '${emp.role}');">직급 수정</button>
                </td>
            </tr>
            </c:forEach>
            <c:if test="${empty empList}">
            <tr>
                <td colspan="5" style="text-align:center; padding:30px;">등록된 사원 계정이 없습니다.</td>
            </tr>
            </c:if>
        </tbody>
    </table>

    <!-- 2. 시스템 활동 로그 (Activity Log) 조회 영역 -->
    <div class="admin-only" style="margin-top: 50px;">
        <div style="display: flex; align-items: center; gap: 15px; margin-bottom: 15px;">
            <h3 class="section-title" style="margin: 0;">🛡️ 사원 시스템 활동 로그 조회 (Activity Log)</h3>
            <span class="admin-only-badge">AOP 자동 감지 로그 (최신 50건)</span>
        </div>
        <table>
            <thead>
                <tr>
                    <th>로그 ID</th>
                    <th>수행 사원</th>
                    <th>작업 액션 (Action)</th>
                    <th>일시 (Timestamp)</th>
                </tr>
            </thead>
            <tbody>
                <!-- ActivityLogVO 실제 필드명(log_id, employeeName, role, action, timestamp) 매칭 -->
                <c:forEach items="${logList}" var="log">
                <tr>
                    <td>LOG-${log.log_id}</td>
                    <td>
                        <strong>${not empty log.employeeName ? log.employeeName : log.employee_id}번 사원</strong> 
                        <small style="color:#888;">(${not empty log.role ? log.role : 'ADMIN'})</small>
                    </td>
                    <td>
                        <span style="color:#facc15; font-weight:bold;">${log.action}</span>
                    </td>
                    <td style="color:#aaa;">
                        <fmt:formatDate value="${log.timestamp}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                </tr>
                </c:forEach>
                <c:if test="${empty logList}">
                <tr>
                    <td colspan="4" style="text-align:center; padding:30px;">기록된 시스템 활동 로그가 없습니다. (화면을 이동하면 AOP가 로그를 적재합니다!)</td>
                </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</main>

<!-- 사원 직급 수정 자바스크립트 -->
<script>
function openRoleModal(empId, empName, currentRole) {
    let newRole = prompt("[" + empName + "] 사원의 변경할 직급을 입력하세요.\n(ADMIN 또는 STAFF)", currentRole);
    if (!newRole) return;
    
    let form = document.createElement("form");
    form.setAttribute("method", "post");
    form.setAttribute("action", "/backoffice/admin/hr/role");
    
    let params = { employeeId: empId, role: newRole.toUpperCase() };
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