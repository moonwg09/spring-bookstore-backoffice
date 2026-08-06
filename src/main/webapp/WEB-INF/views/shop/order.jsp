<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>VBook - 주문/결제 (포트원)</title>
<!-- jQuery 및 포트원 결제 SDK 스크립트 -->
<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
<style>
    body { font-family: 'Malgun Gothic'; background: #f8f9fa; padding: 40px; }
    .order-container { max-width: 900px; margin: 0 auto; background: #fff; padding: 40px; border-radius: 8px; box-shadow: 0 4px 15px rgba(0,0,0,0.05); }
    h2 { margin-bottom: 25px; color: #222; border-bottom: 2px solid #222; padding-bottom: 10px; }
    .section-title { font-size: 18px; font-weight: bold; margin: 20px 0 10px 0; color: #333; }
    table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }
    th, td { padding: 12px; border-bottom: 1px solid #ddd; text-align: center; font-size: 14px; }
    th { background: #f8f9fa; font-weight: bold; color: #333; }
    .pay-box { background: #f1f3f5; padding: 20px; border-radius: 6px; margin-top: 20px; display: flex; justify-content: space-between; align-items: center; }
    .btn-pay { background: #28a745; color: #fff; padding: 15px 30px; border: none; border-radius: 4px; font-size: 16px; font-weight: bold; cursor: pointer; }
    .btn-pay:hover { background: #218838; }
</style>
<script>
    function requestPay() {
        var IMP = window.IMP; 
        IMP.init("imp인증가맹점식별코드"); // 포트원에서 발급받은 가맹점 식별코드 입력

        IMP.request_pay({
            pg: "html5_inicis", // PG사 (예: 이니시스)
            pay_method: "card", // 결제 수단 (신용카드)
            merchant_uid: "merchant_" + new Date().getTime(), // 주문 번호 (고유값)
            name: "VBook 도서 주문", // 주문명
            amount: ${totalSum}, // 결제 총 금액
            buyer_email: "${sessionScope.loginUser.email}",
            buyer_name: "${sessionScope.loginUser.name}",
        }, function (rsp) {
            if (rsp.success) {
                // 결제 성공 시 서버로 주문 완료 처리 요청 전송
                jQuery.ajax({
                    url: "${pageContext.request.contextPath}/shop/order/pay",
                    method: "POST",
                    contentType: "application/json",
                    data: JSON.stringify({
                        imp_uid: rsp.imp_uid,
                        merchant_uid: rsp.merchant_uid,
                        total_amount: rsp.paid_amount
                    })
                }).done(function (data) {
                    alert("결제가 성공적으로 완료되었습니다!");
                    location.href = "${pageContext.request.contextPath}/shop/order/success";
                });
            } else {
                alert("결제에 실패하였습니다. 에러 내용: " + rsp.error_msg);
            }
        });
    }
</script>
</head>
<body>
    <div class="order-container">
        <h2>💳 주문 및 결제 (포트원 PG 연동)</h2>
        
        <div class="section-title">주문 상품 정보</div>
        <table>
            <thead>
                <tr>
                    <th>상품명</th>
                    <th>판매가</th>
                    <th>수량</th>
                    <th>합계</th>
                </tr>
            </thead>
            <tbody>
                <c:set var="totalSum" value="0"/>
                <c:forEach items="${cartList}" var="cart">
                <tr>
                    <td style="text-align: left; padding-left: 20px;">${cart.title}</td>
                    <td><fmt:formatNumber value="${cart.price}" pattern="#,###"/>원</td>
                    <td>${cart.quantity}권</td>
                    <td style="font-weight: bold; color: #007bff;"><fmt:formatNumber value="${cart.price * cart.quantity}" pattern="#,###"/>원</td>
                </tr>
                <c:set var="totalSum" value="${totalSum + (cart.price * cart.quantity)}"/>
                </c:forEach>
            </tbody>
        </table>

        <div class="pay-box">
            <div>
                총 결제금액: <span style="font-size: 22px; font-weight: bold; color: #dc3545;"><fmt:formatNumber value="${totalSum}" pattern="#,###"/>원</span>
            </div>
            <button type="button" class="btn-pay" onclick="requestPay()">포트원 결제하기</button>
        </div>
    </div>
</body>
</html>