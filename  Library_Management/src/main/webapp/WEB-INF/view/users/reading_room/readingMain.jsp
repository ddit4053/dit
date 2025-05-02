<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>책GPT 도서관 시스템 - 열람실</title>
    <style>
        body {
            font-family: 'Noto Sans KR', sans-serif;
            margin: 0;
            background-color: #fdf8f4;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }
        .header {
            background-color: #5d4037;
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 0 30px;
            height: 70px;
        }
        .header .title {
            color: #fff;
            font-size: 24px;
            font-weight: bold;
        }
        .header .nav {
            display: flex;
            gap: 15px;
        }
        .header .nav a {
            color: #fff;
            text-decoration: none;
            padding: 8px 14px;
            border-radius: 4px;
            font-weight: bold;
            transition: background 0.2s;
        }
        .header .nav a:hover {
            background: #795548;
        }
        
        /* 로그인 정보 스타일 추가 */
        .user-info {
            display: flex;
            align-items: center;
            color: #fff;
            margin-left: 20px;
        }
        .user-info span {
            margin-right: 10px;
        }
        .user-info a {
            color: #fff;
            text-decoration: none;
            padding: 5px 10px;
            border-radius: 4px;
            background: #795548;
            transition: background 0.2s;
        }
        .user-info a:hover {
            background: #8d6e63;
        }

        .banner {
            background-color: #8d6e63;
            text-align: center;
            padding: 40px 20px;
            color: white;
        }
        .banner h2 {
            font-size: 50px;
            margin: 0;
        }

        .menu {
            display: flex;
            justify-content: center;
            gap: 60px;
            margin: 60px auto 100px;
        }
        .menu a {
            text-align: center;
            width: 280px;
            height: 260px;
            background: #efebe9;
            border-radius: 10px;
            text-decoration: none;
            color: #4e342e;
            font-weight: bold;
            font-size: 25px; 
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            box-shadow: 0 4px 12px rgba(0,0,0,0.12);
            transition: transform 0.2s;
        }
        .menu a:hover {
            transform: translateY(-5px);
        }
        .menu a img {
            width: 260px;
            margin-bottom: 20px;
        }

        footer {
            text-align: center;
            padding: 30px 0 40px;
            background: #5d4037;
            color: white;
            margin-top: auto;
        }
    </style>
</head>
<body>
<div class="header">
    <div class="title">책GPT 도서관 시스템</div>
    <div style="display: flex; align-items: center;">
        <div class="nav">
            <a href="${ctx}/index.jsp">HOME</a>
            <a href="${ctx}/seatList.do?roomName=일반열람실">일반열람실</a>
            <a href="${ctx}/seatList.do?roomName=미디어열람실">미디어열람실</a>
            <a href="${ctx}/myReservation.do">MY예약현황</a>
        </div>
        
        <!-- 로그인 정보 표시 부분 수정 - name 속성 사용 -->
        <div class="user-info">
            <c:choose>
                <c:when test="${not empty loginMember or not empty loginok or not empty user}">
                    <span>
                        <c:choose>
                            <c:when test="${not empty loginMember}">${loginMember.name}</c:when>
                            <c:when test="${not empty loginok}">${loginok.name}</c:when>
                            <c:when test="${not empty user}">${user.name}</c:when>
                        </c:choose>
                        님 환영합니다!
                    </span>
                    <a href="${ctx}/user/logout.do">로그아웃</a>
                </c:when>
                <c:otherwise>
                    <a href="${ctx}/user/login.do?redirect=${pageContext.request.requestURI}">로그인</a>
                    <a href="${ctx}/user/join.do">회원가입</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<div class="banner">
    <h2>열람실 예약 시스템에 오신 것을 환영합니다.</h2>
</div>

<div class="menu">
    <a href="${ctx}/seatList.do?roomName=일반열람실">
        <img src="${ctx}/resource/img/일반 열람실.png" alt="일반 열람실">
        일반 열람실
    </a>
    <a href="${ctx}/seatList.do?roomName=미디어열람실">
        <img src="${ctx}/resource/img/미디어열람실.png" alt="미디어 열람실">
        미디어 열람실(공사중)
    </a>
    <a href="${ctx}/myReservation.do">
    
        My 예약현황
    </a>
</div>

<footer>
    © 책GPT 도서관 시스템. All rights reserved.
</footer>
</body>
</html>