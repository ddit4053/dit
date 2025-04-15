<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>비밀번호 재설정 완료</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/main.css">
    <style>
        .container {
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            text-align: center;
        }
        .success-message {
            color: #4CAF50;
            background-color: #e8f5e9;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        .btn {
            display: inline-block;
            padding: 10px 20px;
            background-color: #4A90E2;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            font-weight: bold;
            margin-top: 20px;
        }
        .btn:hover {
            background-color: #357ABD;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>비밀번호 재설정 완료</h2>
        
        <div class="success-message">
            비밀번호가 성공적으로 재설정되었습니다.<br>
            새 비밀번호로 로그인하세요.
        </div>
        
        <p>
            로그인 페이지로 이동하여 새로운 비밀번호로 로그인할 수 있습니다.
        </p>
        
        <a href="${pageContext.request.contextPath}/member/main.do" class="btn">로그인 페이지로 이동</a>
    </div>
</body>
</html>