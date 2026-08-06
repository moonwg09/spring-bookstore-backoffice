<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>VBook - ${book.title}</title>
<style>
    body { font-family: 'Malgun Gothic'; background: #f8f9fa; padding: 40px; }
    .detail-container { max-width: 900px; margin: 0 auto; background: #fff; padding: 40px; border-radius: 8px; box-shadow: 0 4px 15px rgba(0,0,0,0.05); display: flex; gap: 40px; }
    .detail-img { width: 300px; height: 400px; object-fit: contain; border: 1px solid #ddd; border-radius: 4px; }
    .detail-info h2 { margin-bottom: 15px; color: #222; }
    .detail-info p { margin-bottom: 10px; font-size: 15px; color: #555; }
    .price { font-size: 24px; font-weight: bold; color: #007bff; margin: 20px 0; }
    .qty-box { margin: 15px 0; display: flex; align-items: center; gap: 10px; font-size: 14px; font-weight: bold; color: #333; }
    .qty-box input { width: 60px; padding: 8px; border: 1px solid #ddd; border-radius: 4px; text-align: center; font-size: 14px; }
    .btn-group { margin-top: 25px; display: flex; gap: 10px; }
    .btn { padding: 12px 25px; border: none; border-radius: 4px; font-weight: bold; cursor: pointer; text-decoration: none; text-align: center; }
    .btn-cart { background: #6c757d; color: #fff; }
    .btn-cart:hover { background: #5a6268; }
    .btn-order { background: #28a745; color: #fff; }
    .btn-order:hover { background: #218838; }
    .btn-back { background: #e9ecef; color: #333; display: inline-block; margin-bottom: 20px; }
</style>
<script>
    function handleAction(actionType) {
        const form = document.getElementById('bookForm');
        if (actionType === 'cart') {
            form.action = "${pageContext.request.contextPath}/shop/cart/add";
            form.submit();
        } else if (actionType === 'order') {
            // 바로 구매 시 장바구니에 담고 주문 페이지로 이동하는 로직을 위해 cart/add로 보낸 후 order로 유도하거나 바로 처리 가능
            form.action = "${pageContext.request.contextPath}/shop/cart/add";
            form.submit();
        }
    }
</script>
</head>
<body>
    <div style="max-width: 900px; margin: 0 auto;">
        <a href="${pageContext.request.contextPath}/shop/main" class="btn btn-back">⬅ 메인으로 돌아가기</a>
    </div>

    <div class="detail-container">
        <div>
            <img src="${book.cover_image}" alt="책 표지" class="detail-img">
        </div>
        <div class="detail-info">
            <h2>${book.title}</h2>
            <p><strong>출판사:</strong> ${book.publisher_name}</p>
            <p><strong>카테고리:</strong> ${book.category_name}</p>
            <p><strong>출간일:</strong> <fmt:formatDate value="${book.publish_date}" pattern="yyyy-MM-dd"/></p>
            <p><strong>ISBN:</strong> ${book.isbn}</p>
            <p><strong>창고 재고 상태:</strong> 
                <c:choose>
                    <c:when test="${book.current_stock > 0}">
                        <span style="color: #28a745; font-weight: bold;">여유 재고 (${book.current_stock}권)</span>
                    </c:when>
                    <c:otherwise>
                        <span style="color: #dc3545; font-weight: bold;">품절 / 재고 소진</span>
                    </c:otherwise>
                </c:choose>
            </p>
            <div class="price"><fmt:formatNumber value="${book.price}" pattern="#,###"/>원</div>
            
            <!-- 장바구니 및 구매 전송 폼 -->
            <form id="bookForm" method="post">
                <input type="hidden" name="book_id" value="${book.book_id}">
                
                <div class="qty-box">
                    <label for="quantity">수량:</label>
                    <input type="number" id="quantity" name="quantity" value="1" min="1" max="${book.current_stock > 0 ? book.current_stock : 1}">
                </div>

                <div class="btn-group">
                    <button type="button" class="btn btn-cart" onclick="handleAction('cart')">장바구니 담기</button>
                    <button type="button" class="btn btn-order" onclick="handleAction('cart'); location.href='${pageContext.request.contextPath}/shop/order';">바로 구매하기</button>
                </div>
            </form>
        </div>
    </div>

    <!-- 하단 리뷰 및 평점 영역 -->
    <div style="max-width: 900px; margin: 30px auto; background: #fff; padding: 40px; border-radius: 8px; box-shadow: 0 4px 15px rgba(0,0,0,0.05);">
        <h3 style="margin-bottom: 20px; border-bottom: 2px solid #222; padding-bottom: 10px;">⭐ 구매 고객 리뷰</h3>

        <!-- 리뷰 작성 폼 -->
        <form action="${pageContext.request.contextPath}/shop/review/add" method="post" style="background: #f8f9fa; padding: 20px; border-radius: 6px; margin-bottom: 30px;">
            <input type="hidden" name="book_id" value="${book.book_id}">
            <div style="display: flex; gap: 15px; align-items: center; margin-bottom: 10px;">
                <label style="font-weight: bold;">평점:</label>
                <select name="rating" style="padding: 6px; border: 1px solid #ddd; border-radius: 4px;">
                    <option value="5.0">⭐⭐⭐⭐⭐ (5.0)</option>
                    <option value="4.0">⭐⭐⭐⭐ (4.0)</option>
                    <option value="3.0">⭐⭐⭐ (3.0)</option>
                    <option value="2.0">⭐⭐ (2.0)</option>
                    <option value="1.0">⭐ (1.0)</option>
                </select>
            </div>
            <div style="display: flex; gap: 10px;">
                <input type="text" name="content" placeholder="솔직한 리뷰를 남겨주세요 (1000자 내외)" required style="flex: 1; padding: 10px; border: 1px solid #ddd; border-radius: 4px;">
                <button type="submit" style="padding: 10px 20px; background: #007bff; color: #fff; border: none; border-radius: 4px; font-weight: bold; cursor: pointer;">리뷰 등록</button>
            </div>
        </form>

        <!-- 리뷰 목록 -->
        <c:choose>
            <c:when test="${empty reviewList}">
                <p style="text-align: center; color: #777; padding: 20px 0;">작성된 리뷰가 없습니다. 첫 리뷰를 남겨보세요!</p>
            </c:when>
            <c:otherwise>
                <c:forEach items="${reviewList}" var="review">
                <div style="border-bottom: 1px solid #eee; padding: 15px 0;">
                    <div style="display: flex; justify-content: space-between; margin-bottom: 6px;">
                        <div>
                            <strong style="color: #333;">${review.member_name}</strong>
                            <span style="color: #f39c12; margin-left: 10px; font-weight: bold;">
                                <c:forEach begin="1" end="${review.rating}">⭐</c:forEach> (${review.rating})
                            </span>
                        </div>
                        <div style="font-size: 12px; color: #888;">
                            <fmt:formatDate value="${review.created_at}" pattern="yyyy-MM-dd HH:mm"/>
                            <c:if test="${sessionScope.loginUser.member_Id == review.member_id}">
                                <a href="${pageContext.request.contextPath}/shop/review/delete?review_id=${review.review_id}&book_id=${book.book_id}" style="color: #dc3545; margin-left: 10px; text-decoration: none;">삭제</a>
                            </c:if>
                        </div>
                    </div>
                    <div style="font-size: 14px; color: #555;">${review.content}</div>
                </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>