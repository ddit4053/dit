<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>좌석/실 예약 - HOME</title>
    <style>
        /* ─────────── 상단 헤더 ─────────── */
        .header {
            background-color: #cf202f;
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 0 20px;
            height: 80px;
        }
        .header .logo {
            display: flex;
            align-items: center;
        }
        .header .logo img {
            height: 40px;
        }
        .header .logo span {
            color: white;
            font-size: 20px;
            margin-left: 8px;
        }
        .nav {
            list-style: none;
            display: flex;
            margin: 0;
            padding: 0;
        }
        .nav-item {
            margin: 0 8px;
            border-radius: 4px;
            transition: background .2s;
        }
        .nav-item a {
            display: block;
            padding: 12px 16px;
            color: white;
            text-decoration: none;
            font-size: 16px;
            font-weight: bold;
        }
        .nav-item.active,
        .nav-item:hover {
            background-color: white;
        }
        .nav-item.active a,
        .nav-item:hover a {
            color: #cf202f;
        }
        .header .close img {
            width: 24px;
            cursor: pointer;
        }

        /* ─────────── 배너 영역 ─────────── */
        .banner {
            position: relative;
            background-image: url('${ctx}/resources/images/banner_bg.jpg');
            background-size: cover;
            background-position: center;
            text-align: center;
            color: white;
            padding: 100px 20px;
        }
        .banner h1 {
            margin: 0;
            font-size: 48px;
            text-shadow: 0 2px 4px rgba(0,0,0,0.6);
            line-height: 1.2;
        }

        /* ─────────── 메뉴 버튼 ─────────── */
        .menu {
            display: flex;
            justify-content: center;
            gap: 60px;
            margin-top: -60px; /* 배너 아래로 살짝 겹치도록 */
        }
        .menu a {
            width: 200px;
            height: 180px;
            background: linear-gradient(to bottom, #fafafa, #e0e0e0);
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.2);
            text-decoration: none;
            color: #333;
            font-size: 18px;
            font-weight: bold;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            transition: transform .2s;
        }
        .menu a:hover {
            transform: translateY(-4px);
        }
        .menu a img {
            width: 56px;
            margin-bottom: 12px;
        }

        /* ─────────── 환영 문구 ─────────── */
        .welcome {
            text-align: center;
            color: white;
            font-size: 20px;
            margin: 40px 0;
        }

        /* ─────────── 푸터 ─────────── */
        footer {
            text-align: center;
            color: #ddd;
            font-size: 14px;
            padding: 12px 0;
            background-color: rgba(0,0,0,0.5);
            position: fixed;
            bottom: 0;
            width: 100%;
        }
    </style>
</head>
<body>
    <!-- 상단 헤더 -->
    <div class="header">
        <div class="logo">
            <img src="${ctx}/resources/images/logo_sejong.png" alt="국립세종도서관 로고">
            <span>좌석/실 예약</span>
        </div>
        <ul class="nav">
            <li class="nav-item"><a href="${ctx}/ReadingMain.do">HOME</a></li>
            <li class="nav-item"><a href="${ctx}/seatList.do?roomName=디지털열람실">디지털열람석</a></li>
            <li class="nav-item"><a href="${ctx}/seatList.do?roomName=미디어열람실">미디어열람석</a></li>
            <li class="nav-item"><a href="${ctx}/myReservation.do">MY예약현황</a></li>
        </ul>
        <div class="close">
            <img src="${ctx}/resources/images/close_icon.png" id="closeBtn" alt="닫기">
        </div>
    </div>

    <!-- 배너 -->
    <div class="banner">
        <h1>책GPT<br>BOOKING SYSTEM</h1>
    </div>

    <!-- 메인 메뉴 -->
    <div class="menu">
        <a href="${ctx}/seatList.do?roomName=디지털열람실">
            <img src="${ctx}/resources/images/icon_digital.png" alt="디지털 열람석">
            디지털 열람석
        </a>
        <a href="${ctx}/seatList.do?roomName=미디어열람실">
            <img src="${ctx}/resources/images/icon_media.png" alt="미디어 열람석">
            미디어 열람석
        </a>
        <a href="${ctx}/myReservation.do">
            <img src="${ctx}/resources/images/icon_my.png" alt="My 예약현황">
            My 예약현황
        </a>
    </div>

    <!-- 환영 문구 (이름 마스킹 예시) -->
    <c:set var="name" value="${sessionScope.loginUser.name}" />
    <c:set var="first" value="${fn:substring(name,0,1)}" />
    <c:set var="masked" value="${first}*${fn:substring(name,2,fn:length(name))}" />
    <div class="welcome">${masked} 님 환영합니다.</div>

    <footer>© 책GPT. All rights reserved.</footer>

    <script>
        // JSP 안에서 컨텍스트 패스를 JS에서도 쓰기 위해
        const ctx = '${ctx}';
    </script>
    <script src="${ctx}/resources/js/users/reading_room/reading.js"></script>
</body>
</html>
