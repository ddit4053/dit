<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>도서관 열람실 예약</title>
    <style>
        body {
            background: linear-gradient(to bottom right, #1e3c72, #2a5298);
            color: white;
            font-family: Arial, sans-serif;
            text-align: center;
            padding-top: 100px;
        }
        .title {
            font-size: 36px;
            margin-bottom: 50px;
        }
        .menu {
            display: flex;
            justify-content: center;
            gap: 40px;
        }
        .menu a {
            display: block;
            width: 180px;
            height: 180px;
            background-color: white;
            color: black;
            border-radius: 10px;
            text-decoration: none;
            font-size: 20px;
            padding-top: 70px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.3);
            transition: transform 0.2s;
        }
        .menu a:hover {
            transform: scale(1.05);
        }
    </style>
</head>
<body>
    <div class="title">National Library of Korea, Team3<br>BOOKING SYSTEM</div>
    <div class="menu">
        <a href="/seatList?roomName=디지털열람실">디지털 열람석</a>
        <a href="/seatList?roomName=미디어열람실">미디어 열람석</a>
        <a href="/myReservation.jsp">My 예약현황</a>
    </div>
    <br><br>
    <p>*** 님 환영합니다.</p>
</body>
</html>