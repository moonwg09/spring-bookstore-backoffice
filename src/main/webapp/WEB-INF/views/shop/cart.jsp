<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>VBook - 장바구니</title>
<style>
    body { font-family: 'Malgun Gothic'; background: #f8f9fa; padding: 40px; }
    .cart-container { max-width: 1000px; margin: 0 auto; background: #fff; padding: 40px; border-radius: 8px; box-shadow: 0 4px 15px rgba(0,0,0,0.05); }
    h2 { margin-bottom: 25px; color: #222; border-bottom: 2px solid #222; padding-bottom: 10px; }
    table { width: 100%; border-collapse: collapse; margin-top: 20px; }
    th, td { padding: 15px; border-bottom: 1px solid #ddd; text-align: center; }
    th { background: #f8f9fa; font-weight: bold; color: #333; }
    .book-info { display: flex; align-items: center; gap: 15px; text-align: left; }
    .cart-thumb { width: 60px; height: 80px; object-fit: contain; border: 1px solid #ddd; border-radius: 4px; }
    .btn { padding: 8px 15px; border: none; border-radius: 4px; font-weight: bold; cursor: pointer; text-decoration: none; }
    .btn-del { background: #dc3545; color: #fff; font-size: 12px; }
    .btn-back { background: #e9ecef; color: #333; display: inline-block; margin-bottom: 20px; }
    .total-box { margin-top: 30px; text-align: right; font-size: 20px; font-weight: bold; color: #222; }
    .total-price { color: #007bff; }
</style>
</head>
<body>
    <div style="max-width: 1000px; margin: 0 auto;">
        <a href="${pageContext.request.contextPath}/shop/main" class="btn btn-back">⬅ 메인으로 돌아가기</a>
    </div>
    
    <div class="cart-container">
        <h2>🛒 장바구니 목록</h2>
        
        <c:choose>
            <c:when test="${empty cartList}">
                <p style="padding: 50px 0; text-align: center; color: #777;">장바구니가 비어 있습니다.</p>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                        <tr>
                            <th>상품 정보</th>
                            <th>판매가</th>
                            <th>수량</th>
                            <th>합계금액</th>
                            <th>관리</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:set var="totalSum" value="0"/>
                        <c:forEach items="${cartList}" var="cart">
                        <tr>
                            <td>
                                <div class="book-info">
                                    <img src="${cart.cover_image}" alt="표지" class="cart-thumb">
                                    <div>
                                        <div style="font-weight: bold; margin-bottom: 4px;">${cart.title}</div>
                                        <div style="font-size: 12px; color: #777;">${cart.publisher_name}</div>
                                    </div>
                                </div>
                            </td>
                            <td><fmt:formatNumber value="${cart.price}" pattern="#,###"/>원</td>
                            <td>${cart.quantity}권</td>
                            <td style="font-weight: bold; color: #007bff;">
                                <fmt:formatNumber value="${cart.price * cart.quantity}" pattern="#,###"/>원
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/shop/cart/delete?cart_id=${cart.cart_id}" class="btn btn-del">삭제</a>
                            </td>
                        </tr>
                        <c:set var="totalSum" value="${totalSum + (cart.price * cart.quantity)}"/>
                        </c:forEach>
                    </tbody>
                </table>

                <div class="total-box">
                    총 결제 예상 금액: <span class="total-price"><fmt:formatNumber value="${totalSum}" pattern="#,###"/>원</span>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>