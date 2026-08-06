<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>마당서점 백오피스 - 출판사 정보 수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
<style>
    .sub-tabs { display: flex; gap: 10px; margin-bottom: 25px; border-bottom: 2px solid #333; padding-bottom: 15px; }
    .form-group { margin-bottom: 20px; }
    label { display: block; margin-bottom: 8px; font-weight: bold; color: #fff; }
    input[type="text"] { 
        width: 100%; max-width: 500px; padding: 12px; 
        background: #000; border: 1px solid #333; color: #fff; 
        border-radius: 6px; font-size: 14px; 
    }
    input[type="text"]:focus { border-color: #deff9a; outline: none; }
    .btn-submit { padding: 10px 20px; background-color: #ffc107; color: black; font-weight:bold; border: none; border-radius: 6px; cursor: pointer; }
    .btn-cancel { padding: 10px 20px; background-color: #333; color: white; text-decoration: none; border-radius: 6px; margin-left: 10px; display:inline-block; }
</style>
</head>
<body class="role-admin">
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
    </div>

    <!-- 상단 서브 탭 -->
    <div class="sub-tabs">
        <a href="/backoffice/admin/book/list" class="btn" style="background:#1a1a1a; color:#888; text-decoration:none;">📖 도서 관리</a>
        <a href="/backoffice/admin/category/list" class="btn" style="background:#1a1a1a; color:#888; text-decoration:none;">🗂️ 카테고리 관리</a>
        <a href="/backoffice/admin/publisher/list" class="btn" style="background:#deff9a; color:#000; font-weight:bold; text-decoration:none;">🏢 출판사 관리</a>
        <a href="/backoffice/admin/author/list" class="btn" style="background:#1a1a1a; color:#888; text-decoration:none;">✍️ 저자 관리</a>
    </div>

    <h3 class="section-title">🛠️ 출판사 정보 수정</h3>
    
    <!-- 기존에 만드신 form 수정 로직 100% 그대로 유지 -->
    <form action="/backoffice/admin/publisher/modify" method="post" style="background:#1a1a1a; padding:30px; border-radius:12px; border:1px solid #333; max-width:600px;">
        
        <input type="hidden" name="publisher_id" value="${publisher.publisher_id}">
        
        <div class="form-group">
            <label>출판사명</label>
            <input type="text" name="name" value="${publisher.name}" required>
        </div>
        
        <div class="form-group">
            <label>연락처</label>
            <input type="text" name="contact" value="${publisher.contact}" required>
        </div>
        
        <div class="form-group" style="margin-top: 30px; margin-bottom: 0;">
            <button type="submit" class="btn-submit">수정 완료</button>
            <a href="/backoffice/admin/publisher/list" class="btn-cancel">취소</a>
        </div>
        
    </form>
</main>
</body>
</html>