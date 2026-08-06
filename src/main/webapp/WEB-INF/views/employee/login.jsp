<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 관리 시스템 - 로그인</title>
<style>
    body {
        font-family: 'Malgun Gothic', sans-serif;
        background-color: #f4f6f9;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        margin: 0;
    }
    .login-container {
        background-color: #ffffff;
        padding: 40px;
        border-radius: 8px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        width: 360px;
    }
    .login-container h2 {
        text-align: center;
        margin-bottom: 24px;
        color: #333333;
    }
    .form-group {
        margin-bottom: 16px;
    }
    .form-group label {
        display: block;
        margin-bottom: 8px;
        font-weight: bold;
        color: #555555;
    }
    .form-group input {
        width: 100%;
        padding: 10px;
        box-sizing: border-box;
        border: 1px solid #cccccc;
        border-radius: 4px;
        font-size: 14px;
    }
    .login-btn {
        width: 100%;
        padding: 12px;
        background-color: #4A90E2;
        border: none;
        color: white;
        font-size: 16px;
        font-weight: bold;
        border-radius: 4px;
        cursor: pointer;
        margin-top: 10px;
    }
    .login-btn:hover {
        background-color: #357ABD;
    }
    .error-msg {
        color: #D9534F;
        font-size: 14px;
        text-align: center;
        margin-bottom: 16px;
    }
</style>
</head>
<body>

<div class="login-container">
    <h2>BACKOFFICE LOGIN</h2>
    
    <c:if test="${not empty errorMsg}">
        <div class="error-msg" style="color: #D9534F; font-size: 14px; text-align: center; margin-bottom: 16px; font-weight: bold;">
             ${errorMsg}
        </div>
    </c:if>

    <form action="/backoffice/employee/login" method="post">
        <div class="form-group">
            <label for="loginId">사원 ID</label>
            <input type="text" id="loginId" name="loginId" required placeholder="아이디를 입력하세요">
        </div>
        <div class="form-group">
            <label for="password">비밀번호</label>
            <input type="password" id="password" name="password" required placeholder="비밀번호를 입력하세요">
        </div>
        <button type="submit" class="login-btn">로그인</button>
    </form>
</div>

</body>
</html>