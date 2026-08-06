<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>VBook - 회원가입</title>
<style>
    body { display: flex; justify-content: center; align-items: center; height: 100vh; background: #f8f9fa; font-family: 'Malgun Gothic'; }
    .join-box { background: #fff; padding: 40px; border-radius: 8px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); width: 380px; }
    .join-box h2 { margin-bottom: 25px; color: #222; text-align: center; }
    .join-box label { display: block; margin-bottom: 5px; font-weight: bold; font-size: 13px; color: #555; }
    .join-box input { width: 100%; padding: 10px 12px; margin-bottom: 15px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }
    .join-box button { width: 100%; padding: 12px; background: #28a745; color: #fff; border: none; border-radius: 4px; font-weight: bold; cursor: pointer; margin-top: 10px; }
    .join-box button:hover { background: #218838; }
    .link-box { margin-top: 15px; text-align: center; font-size: 13px; }
    .link-box a { color: #555; text-decoration: none; }
</style>
</head>
<body>
    <div class="join-box">
        <h2>VBook 회원가입</h2>
        <form action="${pageContext.request.contextPath}/shop/join" method="post">
            <label for="loginId">아이디</label>
            <input type="text" id="loginId" name="loginId" placeholder="사용할 아이디를 입력하세요" required>

            <label for="password">비밀번호</label>
            <input type="password" id="password" name="password" placeholder="비밀번호를 입력하세요" required>

            <label for="name">이름 (닉네임)</label>
            <input type="text" id="name" name="name" placeholder="이름을 입력하세요" required>

            <label for="email">이메일</label>
            <input type="email" id="email" name="email" placeholder="example@vbook.com" required>

            <button type="submit">회원가입 완료</button>
        </form>

        <div class="link-box">
            <a href="${pageContext.request.contextPath}/shop/login">이미 계정이 있으신가요? 로그인</a>
        </div>
    </div>
</body>
</html>