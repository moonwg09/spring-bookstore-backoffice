<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>마당서점 백오피스 - 도서 등록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
<style>
    .sub-tabs { display: flex; gap: 10px; margin-bottom: 25px; border-bottom: 2px solid #333; padding-bottom: 15px; }
    .form-group { margin-bottom: 20px; }
    label { display: block; margin-bottom: 8px; font-weight: bold; color: #fff; }
    /* 다크모드 입력창 및 선택창 스타일 */
    input[type="text"], input[type="number"], input[type="date"], select { 
        width: 100%; max-width: 500px; padding: 12px; 
        background: #000; border: 1px solid #333; color: #fff; 
        border-radius: 6px; font-size: 14px; 
    }
    input[type="text"]:focus, input[type="number"]:focus, input[type="date"]:focus, select:focus { border-color: #deff9a; outline: none; }
    .checkbox-group { margin-top: 10px; background: #000; padding: 15px; border: 1px solid #333; border-radius: 6px; max-width: 500px; }
    .checkbox-group label { display: inline-block; font-weight: normal; margin-right: 15px; margin-bottom: 8px; color: #aaa; cursor: pointer; }
    .checkbox-group label:hover { color: #fff; }
    .btn-submit { padding: 10px 20px; background-color: #deff9a; color: #000; font-weight:bold; border: none; border-radius: 6px; cursor: pointer; }
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
        <a href="/backoffice/admin/book/list" class="btn" style="background:#deff9a; color:#000; font-weight:bold; text-decoration:none;">📖 도서 관리</a>
        <a href="/backoffice/admin/category/list" class="btn" style="background:#1a1a1a; color:#888; text-decoration:none;">🗂️ 카테고리 관리</a>
        <a href="/backoffice/admin/publisher/list" class="btn" style="background:#1a1a1a; color:#888; text-decoration:none;">🏢 출판사 관리</a>
        <a href="/backoffice/admin/author/list" class="btn" style="background:#1a1a1a; color:#888; text-decoration:none;">✍️ 저자 관리</a>
    </div>

    <h3 class="section-title">📝 새 도서 등록</h3>
    
    <!-- 기존에 만드신 form 전송 로직 100% 그대로 유지 -->
    <form action="/backoffice/admin/book/register" method="post" style="background:#1a1a1a; padding:30px; border-radius:12px; border:1px solid #333; max-width:600px;">
        
        <div class="form-group">
            <label>책 제목</label>
            <input type="text" name="title" required placeholder="도서명을 입력하세요">
        </div>
        
        <div class="form-group">
            <label>가격</label>
            <input type="number" name="price" required placeholder="가격을 입력하세요">
        </div>
        
        <div class="form-group">
            <label>출판일</label>
            <input type="date" name="publish_date" required>
        </div>
        
        <div class="form-group">
            <label>ISBN</label>
            <input type="text" name="isbn" placeholder="ISBN 번호를 입력하세요">
        </div>
        
        <div class="form-group">
            <label>출판사 선택</label>
            <select name="publisher_id" required>
                <option value="">--- 출판사 선택 ---</option>
                <c:forEach items="${publisherList}" var="pub">
                    <option value="${pub.publisher_id}">${pub.name}</option>
                </c:forEach>
            </select>
        </div>
        
        <div class="form-group">
            <label>카테고리 선택</label>
            <select name="category_id" required>
                <option value="">--- 카테고리 선택 ---</option>
                <c:forEach items="${categoryList}" var="cat">
                    <option value="${cat.category_id}">${cat.name}</option>
                </c:forEach>
            </select>
        </div>
        
        <div class="form-group">
            <label>저자 선택 (중복 선택 가능)</label>
            <div class="checkbox-group">
                <c:forEach items="${authorList}" var="author">
                    <label>
                        <input type="checkbox" name="authorIds" value="${author.author_id}"> ${author.name}
                    </label>
                </c:forEach>
            </div>
        </div>
        
        <div class="form-group" style="margin-top: 30px; margin-bottom: 0;">
            <button type="submit" class="btn-submit">등록 완료</button>
            <a href="/backoffice/admin/book/list" class="btn-cancel">취소</a>
        </div>
        
    </form>
</main>
</body>
</html>