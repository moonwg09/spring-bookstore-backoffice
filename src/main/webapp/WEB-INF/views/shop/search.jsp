<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>VBook - 검색 결과</title>
<style>
    body { font-family: 'Malgun Gothic'; background: #f8f9fa; padding: 40px; }
    .search-container { max-width: 1300px; margin: 0 auto; background: #fff; padding: 40px; border-radius: 8px; box-shadow: 0 4px 15px rgba(0,0,0,0.05); }
    h2 { margin-bottom: 20px; color: #222; }
    .book-grid { display: grid; grid-template-columns: repeat(4, 1fr); gap: 25px; margin-top: 20px; }
    .book-card { border: 1px solid #eaeaea; border-radius: 8px; padding: 15px; background: #fff; cursor: pointer; transition: transform 0.2s; }
    .book-card:hover { transform: translateY(-5px); box-shadow: 0 6px 20px rgba(0,0,0,0.08); }
    .book-thumb { width: 100%; height: 220px; object-fit: contain; background: #f8f9fa; border-radius: 4px; margin-bottom: 12px; }
    .book-title { font-size: 15px; font-weight: bold; color: #222; margin-bottom: 6px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; }
    .book-price { font-size: 16px; font-weight: 900; color: #007bff; margin-top: 8px; }
    .btn-back { background: #e9ecef; color: #333; display: inline-block; padding: 10px 20px; border-radius: 4px; text-decoration: none; font-weight: bold; margin-bottom: 20px; }
</style>
</head>
<body>
    <div class="search-container">
        <a href="${pageContext.request.contextPath}/shop/main" class="btn-back">⬅ 메인으로 돌아가기</a>
        <h2>🔍 '&lt;span style="color:#007bff;"&gt;${keyword}&lt;/span&gt;' 검색 결과</h2>
        
        <c:choose>
            <c:when test="${empty searchList}">
                <p style="padding: 40px 0; text-align: center; color: #777;">검색된 도서가 없습니다.</p>
            </c:when>
            <c:otherwise>
                <div class="book-grid">
                    <c:forEach items="${searchList}" var="book">
                    <div class="book-card" onclick="location.href='${pageContext.request.contextPath}/shop/detail?book_id=${book.book_id}'">
                        <img src="${book.cover_image}" alt="책 표지" class="book-thumb">
                        <div class="book-title">${book.title}</div>
                        <div style="font-size: 13px; color: #777; margin-bottom: 4px;">${book.publisher_name}</div>
                        <div class="book-price"><fmt:formatNumber value="${book.price}" pattern="#,###"/>원</div>
                    </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>