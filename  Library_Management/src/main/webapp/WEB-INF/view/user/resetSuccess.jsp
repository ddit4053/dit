<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>비밀번호 재설정 완료</title>
<%--     <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/user/login.css"> --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/user/resetSuccess.css">
</head>
<body>
    <div class="container">
        <h2>비밀번호 재설정 완료</h2>
        
        <div class="success-message">
            비밀번호가 성공적으로 재설정되었습니다.<br>
            새 비밀번호로 로그인하세요.
        </div>
        
        <p>
            로그인 페이지로 이동하여 새로운 <br> 비밀번호로 로그인할 수 있습니다.
        </p>
        <span id="btnspan">
        	<a href="${pageContext.request.contextPath}/user/login.do" class="btn" id="btn1">로그인 페이지로 이동</a>	
        </span>
    </div>
</body>
</html>