<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>백오피스 시스템 - 메인 대시보드</title>
<style>
    body {
        font-family: 'Malgun Gothic', sans-serif;
        margin: 0;
        background-color: #f8f9fa;
    }
    .navbar {
        background-color: #2C3E50;
        padding: 15px 30px;
        color: white;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }
    .navbar h1 { margin: 0; font-size: 20px; }
    .user-info { font-size: 14px; }
    .logout-btn {
        background-color: #E74C3C;
        color: white;
        padding: 6px 12px;
        text-decoration: none;
        border-radius: 4px;
        margin-left: 15px;
        font-weight: bold;
    }
    .logout-btn:hover { background-color: #C0392B; }
    .content {
        padding: 40px;
        max-width: 1200px;
        margin: 0 auto;
    }
    .welcome-box {
        background-color: white;
        padding: 30px;
        border-radius: 8px;
        box-shadow: 0 2px 8px rgba(0,0,0,0.05);
    }
</style>
</head>
<script>
	window.onpagehow = function(event){
		if(event.persisted || (window.performance && window.performance.navigation.type == 2)){
			log.info("캐시 진입 감지! 화면을 강제로 새로고침합니다.");
			window.location.reload();
		}
	}
</script>
<body>

<div class="navbar">
    <h1>BACKOFFICE MANAGEMENT SYSTEM</h1>
    <div class="user-info">
         <b>${loginUser.name}</b> 님 로그인 중
        <a href="/backoffice/employee/logout" class="logout-btn">로그아웃</a>
    </div>
</div>

<div class="content">
    <div class="welcome-box">
        <h2>안녕하세요, ${loginUser.name} 님!</h2>
        <p>백오피스 관리자 대시보드에 성공적으로 접속하셨습니다.</p>
        <hr style="border: 0; height: 1px; background: #eee; margin: 20px 0;">
        <ul>
            <li><b>사원 번호:</b> ${loginUser.employeeId}</li>
        </ul>
    </div>
</div>

</body>
</html>