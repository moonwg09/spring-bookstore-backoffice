<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>MBook - 주문/결제 (포트원)</title>
<!-- jQuery 및 포트원 결제 SDK 스크립트 -->
<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="https://cdn.iamport.kr/v1/iamport.js"></script>
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
	
	console.log("Mbbok portone click");

    var IMP = window.IMP;

    IMP.init("imp04328375");

    var merchantUid =
        "merchant_" + new Date().getTime();

    IMP.request_pay({

        channelKey: "channel-key-8fdbdfa7-1ae5-4386-8823-c22d89517778",
        pay_method: "card",

        merchant_uid: merchantUid,

        name: "MBook 도서 주문",

        amount: ${totalSum},

        buyer_email:
            "${sessionScope.loginUser.email}",

        buyer_name:
            "${sessionScope.loginUser.name}"

    }, function (rsp) {

        if (rsp.success) {

            jQuery.ajax({

                url:
                    "${pageContext.request.contextPath}/shop/order/pay",

                method: "POST",

                contentType: "application/json",

                data: JSON.stringify({

                    imp_uid: rsp.imp_uid,
                    merchant_uid: rsp.merchant_uid

                })

            }).done(function (data) {
            	
            	console.log("order/pay response =", data);

                if (data === "SUCCESS") {

                    alert("결제가 성공적으로 완료되었습니다!");

                    location.href =
                    	"${pageContext.request.contextPath}/shop/main";

                } else if (data === "AMOUNT_MISMATCH") {

                    alert("결제 금액 검증에 실패했습니다.");

                } else if (data === "MERCHANT_UID_MISMATCH") {

                    alert("주문번호 검증에 실패했습니다.");

                } else if (data === "PAYMENT_NOT_PAID") {

                    alert("정상적으로 완료된 결제가 아닙니다.");

                } else if (data === "PAYMENT_NOT_FOUND") {

                    alert("결제 정보를 확인할 수 없습니다.");

                } else if (data === "EMPTY") {

                    alert("장바구니에 상품이 없습니다.");

                } else if (data === "PAYMENT_VERIFY_ERROR") {

                    alert("결제 검증 중 오류가 발생했습니다.");

                } else {

                    alert("주문 처리에 실패했습니다.");
                }

            }).fail(function () {

                alert("서버와 통신 중 오류가 발생했습니다.");

            });

        } else {

            alert(
                "결제가 취소되었거나 실패했습니다.\n"
                + rsp.error_msg
            );
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
        <c:forEach items="${cartList}" var="cart">
            <tr>
                <td style="text-align: left; padding-left: 20px;">
                    ${cart.title}
                </td>

                <td>
                    <fmt:formatNumber
                        value="${cart.price}"
                        pattern="#,###"/>원
                </td>

                <td>
                    ${cart.quantity}권
                </td>

                <td style="font-weight: bold; color: #007bff;">
                    <fmt:formatNumber
                        value="${cart.price * cart.quantity}"
                        pattern="#,###"/>원
                </td>
            </tr>
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