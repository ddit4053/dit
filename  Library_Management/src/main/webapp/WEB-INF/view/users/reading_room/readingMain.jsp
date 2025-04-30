<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>책GPT 도서관 시스템 - 홈</title>
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

        .banner {
            background-color: #8d6e63;
            text-align: center;
            padding: 60px 20px;
            color: white;
        }
        .banner h2 {
            font-size: 36px;
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
            width: 220px;
            height: 200px;
            background: #efebe9;
            border-radius: 10px;
            text-decoration: none;
            color: #4e342e;
            font-weight: bold;
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
            width: 200px;
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
    <div class="nav">
        <a href="${ctx}/readingMain.do">HOME</a>
        <a href="${ctx}/seatList.do?roomName=일반열람실">일반열람실</a>
        <a href="${ctx}/seatList.do?roomName=미디어열람실">미디어열람실</a>
        <a href="${ctx}/myReservation.do">MY예약현황</a>
    </div>
</div>

<div class="banner">
    <h2>열람실 예약 시스템입니다.</h2>
</div>

<div class="menu">
    <a href="${ctx}/seatList.do?roomName=일반열람실">
        <img src="${ctx}/resource/img/일반 열람실.png" alt="일	반 열람실">
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
