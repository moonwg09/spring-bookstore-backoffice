<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>VBook - 마이룸</title>
<style>
    body { font-family: 'Malgun Gothic'; background: #f8f9fa; padding: 40px; }
    .room-container { max-width: 1000px; margin: 0 auto; background: #fff; padding: 40px; border-radius: 8px; box-shadow: 0 4px 15px rgba(0,0,0,0.05); }
    h2 { margin-bottom: 25px; color: #222; border-bottom: 2px solid #222; padding-bottom: 10px; }
    .profile-card { background: #f1f3f5; padding: 25px; border-radius: 8px; margin-bottom: 30px; display: flex; justify-content: space-between; align-items: center; }
    .profile-info div { margin-bottom: 8px; font-size: 15px; color: #333; }
    .section-title { font-size: 18px; font-weight: bold; margin: 30px 0 15px 0; color: #333; }
    table { width: 100%; border-collapse: collapse; }
    th, td { padding: 14px; border-bottom: 1px solid #ddd; text-align: center; font-size: 14px; }
    th { background: #f8f9fa; font-weight: bold; color: #333; }
    .btn-back { background: #e9ecef; color: #333; display: inline-block; padding: 10px 20px; border-radius: 4px; text-decoration: none; font-weight: bold; margin-bottom: 20px; }
</style>
</head>
<body>
    <div style="max-width: 1000px; margin: 0 auto;">
        <a href="${pageContext.request.contextPath}/shop/main" class="btn-back">⬅ 메인으로 돌아가기</a>
    </div>

    <div class="room-container">
        <h2>👤 마이룸 (회원 정보 및 주문 내역)</h2>
        
        <!-- 회원 프로필 요약 카드 -->
        <div class="profile-card">
            <div class="profile-info">
                <div><strong>이름:</strong> ${sessionScope.loginUser.name}</div>
                <div><strong>이메일:</strong> ${sessionScope.loginUser.email}</div>
                <div><strong>보유 충전금:</strong> <span style="color: #007bff; font-weight: bold;"><fmt:formatNumber value="${sessionScope.loginUser.balance}" pattern="#,###"/>원</span></div>
            </div>
        </div>

        <div class="section-title">📦 나의 주문 내역</div>
        
        <c:choose>
            <c:when test="${empty orderList}">
                <p style="text-align: center; color: #777; padding: 40px 0;">주문 내역이 없습니다.</p>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                        <tr>
                            <th>주문번호</th>
                            <th>주문일시</th>
                            <th>총 결제금액</th>
                            <th>주문 상태</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${orderList}" var="order">
                        <tr>
                            <td style="font-weight: bold;">#${order.order_id}</td>
                            <td><fmt:formatDate value="${order.order_date}" pattern="yyyy-MM-dd HH:mm"/></td>
                            <td style="color: #007bff; font-weight: bold;"><fmt:formatNumber value="${order.total_amount}" pattern="#,###"/>원</td>
                            <td>
                                <span style="background: #d4edda; color: #155724; padding: 4px 10px; border-radius: 4px; font-weight: bold; font-size: 12px;">
                                    ${order.status}
                                </span>
                            </td>
                        </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>