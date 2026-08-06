<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>VBook - 로그인</title>
<style>
    body { display: flex; justify-content: center; align-items: center; height: 100vh; background: #f8f9fa; font-family: 'Malgun Gothic'; }
    .login-box { background: #fff; padding: 40px; border-radius: 8px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); width: 350px; text-align: center; }
    .login-box h2 { margin-bottom: 25px; color: #222; }
    .login-box input { width: 100%; padding: 12px; margin-bottom: 15px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }
    .login-box button { width: 100%; padding: 12px; background: #007bff; color: #fff; border: none; border-radius: 4px; font-weight: bold; cursor: pointer; }
    .login-box button:hover { background: #0056b3; }
    .kakao-btn { width: 100%; padding: 12px; background: #fee500; color: #3c1e1e; border: none; border-radius: 4px; font-weight: bold; cursor: pointer; margin-top: 10px; text-decoration: none; display: inline-block; box-sizing: border-box; }
    .link-box { margin-top: 15px; font-size: 13px; }
    .link-box a { color: #555; text-decoration: none; }
</style>
</head>
<body>
    <div class="login-box">
        <h2>VBook 로그인</h2>
        <form action="${pageContext.request.contextPath}/shop/login" method="post">
            <input type="text" name="loginId" placeholder="아이디" required>
            <input type="password" name="password" placeholder="비밀번호" required>
            <button type="submit">로그인</button>
        </form>
        
        <!-- 카카오 로그인 버튼 (카카오 인증 URL 연결) -->
        <a href="https://kauth.kakao.com/oauth/authorize?client_id=YOUR_REST_API_KEY&redirect_uri=http://localhost:8080/backoffice/shop/kakao/callback&response_type=code" class="kakao-btn">
            💬 카카오 3초 로그인/회원가입
        </a>

        <div class="link-box">
            <a href="${pageContext.request.contextPath}/shop/join">회원가입하기</a>
        </div>
    </div>
</body>
</html>