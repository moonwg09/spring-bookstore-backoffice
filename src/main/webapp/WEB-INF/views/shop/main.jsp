<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>VBook - 대한민국 대표 인터넷 서점</title>
<style>
    * { box-sizing: border-box; margin: 0; padding: 0; font-family: 'Malgun Gothic', sans-serif; }
    body { background-color: #fff; color: #333; }
    
    .top-util { display: flex; justify-content: flex-end; gap: 20px; padding: 10px 50px; background: #fff; font-size: 13px; border-bottom: 1px solid #eee; }
    .top-util a { text-decoration: none; color: #555; font-weight: bold; }
    .top-util a:hover { color: #007bff; }

    .header-container { display: flex; justify-content: space-between; align-items: center; padding: 25px 50px; max-width: 1400px; margin: 0 auto; }
    .logo { font-size: 42px; font-weight: 900; color: #222; text-decoration: none; letter-spacing: -1px; }
    .logo span:nth-child(1) { color: #007bff; }
    .logo span:nth-child(2) { color: #28a745; }
    .logo span:nth-child(3) { color: #ffc107; }
    .logo span:nth-child(4) { color: #dc3545; }

    .search-box { display: flex; align-items: center; border: 2px solid #222; border-radius: 4px; overflow: hidden; background: #fff; width: 450px; }
    .search-box select { padding: 10px; border: none; background: #f8f9fa; font-weight: bold; outline: none; border-right: 1px solid #ddd; }
    .search-box input { flex: 1; padding: 10px 15px; border: none; outline: none; font-size: 14px; }
    .search-box button { padding: 10px 25px; background: #222; color: #fff; border: none; font-weight: bold; cursor: pointer; }
    .search-box button:hover { background: #444; }

    .user-panel { border: 1px solid #ccc; border-radius: 8px; padding: 12px 20px; width: 100%; background: #fafafa; font-size: 13px; box-shadow: 0 2px 5px rgba(0,0,0,0.05); }
    .user-panel .u-info { margin-bottom: 6px; font-weight: bold; color: #333; }
    .user-panel .u-money { color: #007bff; font-weight: bold; }
    .user-panel .u-point { color: #28a745; font-weight: bold; }
    .user-panel .btn-logout-mini { display: block; width: 100%; margin-top: 8px; padding: 4px; background: #e9ecef; border: 1px solid #ced4da; border-radius: 4px; text-align: center; text-decoration: none; color: #333; font-size: 11px; font-weight: bold; cursor: pointer; }

    /* 네비게이션바 및 드롭다운 스타일 */
    .nav-bar { background: #4f5d75; display: flex; padding: 0 50px; position: relative; z-index: 100; }
    .nav-item { color: #fff; text-decoration: none; padding: 14px 24px; font-weight: bold; font-size: 15px; display: inline-block; white-space: nowrap; }
    .nav-item:hover { background: #343a40; }

    /* 드롭다운 컨테이너 */
    .dropdown { position: relative; display: inline-block; }
    .dropdown-content { display: none; position: absolute; background-color: #343a40; min-width: 180px; box-shadow: 0px 8px 16px rgba(0,0,0,0.2); top: 100%; left: 0; z-index: 200; }
    .dropdown-content a { color: #fff; padding: 12px 20px; text-decoration: none; display: block; font-size: 14px; white-space: nowrap; }
    .dropdown-content a:hover { background-color: #495057; color: #61b5ff; }
    
    /* 마우스 올렸을 때 자식 메뉴 노출 */
    .dropdown:hover .dropdown-content { display: block; }

    .banner-section { background: #e9ecef; padding: 30px 0; text-align: center; margin-bottom: 40px; }
    .banner-container { max-width: 1000px; height: 320px; margin: 0 auto; background: linear-gradient(135deg, #b5c7f7 0%, #d1e7dd 100%); border-radius: 12px; display: flex; align-items: center; justify-content: space-between; padding: 0 60px; box-shadow: 0 4px 15px rgba(0,0,0,0.05); }
    .banner-text h3 { font-size: 15px; color: #555; margin-bottom: 8px; font-weight: normal; }
    .banner-text h1 { font-size: 36px; color: #222; font-weight: 900; }

    .content-container { max-width: 1300px; margin: 0 auto 80px auto; padding: 0 20px; }
    .section-heading { font-size: 24px; font-weight: bold; margin-bottom: 25px; border-bottom: 2px solid #222; padding-bottom: 10px; }
    
    .book-grid { display: grid; grid-template-columns: repeat(4, 1fr); gap: 25px; }
    .book-card { border: 1px solid #eaeaea; border-radius: 8px; padding: 15px; background: #fff; transition: transform 0.2s, box-shadow 0.2s; cursor: pointer; }
    .book-card:hover { transform: translateY(-5px); box-shadow: 0 6px 20px rgba(0,0,0,0.08); }
    .book-thumb { width: 100%; height: 220px; object-fit: contain; background: #f8f9fa; border-radius: 4px; margin-bottom: 12px; }
    .book-title { font-size: 15px; font-weight: bold; color: #222; margin-bottom: 6px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; }
    .book-price { font-size: 16px; font-weight: 900; color: #007bff; margin-top: 8px; }
</style>
</head>
<body>

    <div class="top-util">
        <c:choose>
            <c:when test="${not empty sessionScope.loginUser}">
                <a href="${pageContext.request.contextPath}/logout">로그아웃</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/shop/login">로그인</a>
            </c:otherwise>
        </c:choose>
        <a href="${pageContext.request.contextPath}/shop/myroom">마이룸</a>
        <a href="${pageContext.request.contextPath}/shop/cart">장바구니</a>
        <a href="${pageContext.request.contextPath}/shop/main">고객센터</a>
    </div>

    <div class="header-container">
        <a href="${pageContext.request.contextPath}/shop/main" class="logo">
            <span>M</span><span>B</span><span>o</span><span>o</span><span>k</span>
        </a>

        <!-- 검색 폼 -->
        <form action="${pageContext.request.contextPath}/shop/search" method="get" class="search-box">
            <select name="searchType">
                <option value="title">책 제목</option>
                <option value="author">저자</option>
                <option value="publisher">출판사</option>
            </select>
            <input type="text" name="keyword" placeholder="검색어를 입력하세요...">
            <button type="submit">검색</button>
        </form>

        <!-- 우측 고정 영역 -->
        <div style="width: 220px; display: flex; justify-content: flex-end; align-items: center;">
            <c:choose>
                <c:when test="${not empty sessionScope.loginUser}">
                    <div class="user-panel">
                        <div class="u-info">회원 : <strong>${sessionScope.loginUser.name}</strong></div>
                        <div class="u-info">충전금 : <span class="u-money"><fmt:formatNumber value="${sessionScope.loginUser.balance}" pattern="#,###"/>원</span></div>
                        <div class="u-info">포인트 : <span class="u-point"><fmt:formatNumber value="${sessionScope.loginUser.point}" pattern="#,###"/> P</span></div>
                        <a href="${pageContext.request.contextPath}/logout" class="btn-logout-mini">로그아웃</a>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="user-panel" style="text-align: center; padding: 18px 20px;">
                        <div class="u-info" style="margin-bottom: 10px; color: #555;">로그인이 필요합니다.</div>
                        <a href="${pageContext.request.contextPath}/shop/login" style="display: block; width: 100%; padding: 6px; background: #007bff; color: #fff; border-radius: 4px; text-decoration: none; font-weight: bold; font-size: 13px;">로그인</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <!-- 💡 부모 카테고리 노출 및 마우스 호버 드롭다운 네비게이션바 -->
    <nav class="nav-bar">
           
        <c:forEach items="${parentCategoryList}" var="parent">
            <div class="dropdown">
                <a href="${pageContext.request.contextPath}/shop/category?category_id=${parent.category_id}" class="nav-item">${parent.name}</a>
                
                <c:if test="${not empty parent.children}">
                    <div class="dropdown-content">
                        <c:forEach items="${parent.children}" var="child">
                            <a href="${pageContext.request.contextPath}/shop/category?category_id=${child.category_id}">${child.name}</a>
                        </c:forEach>
                    </div>
                </c:if>
            </div>
        </c:forEach>
    </nav>

    <div class="banner-section">
        <div class="banner-container">
            <div class="banner-text" style="text-align: left;">
                <h3 style="color:#4f5d75;">작가의 독서 가이드 X 완독 KIT</h3>
                <h1 style="color:#1d3557; margin-top:5px;">독서 P.T 완독 챌린지</h1>
            </div>
            <div class="banner-img">
                <div style="width: 140px; height: 180px; background: #1d3557; border-radius: 8px; display: flex; align-items: center; justify-content: center; color: #deff9a; font-weight: bold; box-shadow: 0 4px 10px rgba(0,0,0,0.2);">유켄두.</div>
            </div>
        </div>
    </div>

    <div class="content-container">
        <div class="section-heading">📚 도서 목록</div>

        <div class="book-grid">
            <c:choose>
                <c:when test="${empty ratingList}">
                    <p style="grid-column: span 4; text-align: center; color: #777; padding: 40px 0;">등록된 도서가 없습니다.</p>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${ratingList}" var="book">
                    <div class="book-card" onclick="location.href='${pageContext.request.contextPath}/shop/detail?book_id=${book.book_id}'">
                        <img src="${book.cover_image}" alt="책 표지" class="book-thumb">
                        <div class="book-title">${book.title}</div>
                        <div class="book-price"><fmt:formatNumber value="${book.price}" pattern="#,###"/>원</div>
                    </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

</body>
</html>