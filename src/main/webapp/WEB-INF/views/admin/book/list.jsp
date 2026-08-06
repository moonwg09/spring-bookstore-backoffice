<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>마당서점 백오피스 - 도서 관리</title>
<!-- 공통 다크모드 스타일 연동 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
<style>
    .sub-tabs { display: flex; gap: 10px; margin-bottom: 25px; border-bottom: 2px solid #333; padding-bottom: 15px; }
</style>
</head>
<body class="role-admin">
<!-- 좌측 공통 사이드바 -->
<aside class="sidebar">
    <div class="logo">마당서점 B2B</div>
    <ul class="nav-menu">
        <li><a href="/backoffice/admin/dashboard">대시보드 (통계)</a></li>
        <li><a href="/backoffice/admin/hr/list">사원 및 보안 관리</a></li>
        <li><a href="/backoffice/admin/book/list" class="active">기준 정보 관리</a></li>
        <li><a href="/backoffice/admin/inventory/list">재고 및 물류 관리</a></li>
        <li><a href="/backoffice/admin/order/list">고객 주문 관리</a></li>
    </ul>
</aside>
<main class="main-content">
    <div class="header">
        <h2>기준 정보 관리 (Master Data)</h2>
        <div class="header-user-zone">
            <select class="role-selector" onchange="document.body.className = this.value;">
                <option value="role-admin">👑 총괄관리자 뷰</option>
                <option value="role-staff">👤 일반직원 뷰</option>
            </select>
            <div class="user-info">${not empty loginUser ? loginUser.name : '홍길동'}님</div>
            <button class="btn-logout" onclick="alert('로그아웃 처리되었습니다.');">로그아웃</button>
        </div>
    </div>

    <!-- 4대 기준정보 이동을 위한 상단 서브 탭 -->
    <div class="sub-tabs">
        <a href="/backoffice/admin/book/list" class="btn" style="background:#deff9a; color:#000; font-weight:bold; text-decoration:none;">📖 도서 관리</a>
        <a href="/backoffice/admin/category/list" class="btn" style="background:#1a1a1a; color:#888; text-decoration:none;">🗂️ 카테고리 관리</a>
        <a href="/backoffice/admin/publisher/list" class="btn" style="background:#1a1a1a; color:#888; text-decoration:none;">🏢 출판사 관리</a>
        <a href="/backoffice/admin/author/list" class="btn" style="background:#1a1a1a; color:#888; text-decoration:none;">✍️ 저자 관리</a>
    </div>

    <!-- 기존에 만드신 본문 영역 시작 -->
    <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:15px;">
        <h3 class="section-title" style="margin:0;">📚 도서 목록 관리</h3>
        <a href="/backoffice/admin/book/register" class="btn" style="background:#deff9a; color:#000; font-weight:bold; text-decoration:none;">+ 새 도서 등록</a>
    </div>

    <table>
        <thead>
            <tr>
                <th>도서 번호</th>
                <th>책 제목</th>
                <th>가격</th>
                <th>ISBN</th>
                <th>출판일</th>
                <th class="admin-only">관리</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${list}" var="book">
                <tr>
                    <td>BK-${book.book_id}</td>
                    <td><strong>${book.title}</strong></td>
                    <td style="color:#deff9a; font-weight:bold;"><fmt:formatNumber value="${book.price}" pattern="#,###" />원</td>
                    <td>${book.isbn}</td>
                    <td><fmt:formatDate value="${book.publish_date}" pattern="yyyy-MM-dd"/></td>
                    <td class="admin-only">
                        <div style="display:flex; gap:5px; justify-content:center;">
                            <a href="/backoffice/admin/book/modify?book_id=${book.book_id}" class="btn" style="background:#ffc107; color:black; text-decoration:none;">수정</a>
                            
                            <form action="/backoffice/admin/book/delete" method="post" style="display:inline;">
                                <input type="hidden" name="book_id" value="${book.book_id}">
                                <button type="submit" onclick="return confirm('정말 이 도서를 삭제하시겠습니까?');" class="btn" style="background:#dc3545; color:white; border:none; cursor:pointer;">삭제</button>
                            </form>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            
            <c:if test="${empty list}">
                <tr>
                    <td colspan="6" style="padding:30px;">등록된 도서가 없습니다.</td>
                </tr>
            </c:if>
        </tbody>
    </table>
</main>
</body>
</html>