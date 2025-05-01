<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>나의 예약 현황</title>
    <style>
        body { font-family: 'Noto Sans KR', sans-serif; background: #f8f8f8; padding: 20px; }
        h2 { color: #cf202f; }
        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: center;
        }
        th {
            background: #cf202f;
            color: white;
        }
    </style>
</head>
<body>
    <h2>나의 예약 현황</h2>
    <c:choose>
        <c:when test="${not empty reservations}">
            <table>
                <thead>
                    <tr>
                        <th>예약번호</th>
                        <th>좌석번호</th>
                        <th>시작 시간</th>
                        <th>종료 시간</th>
                        <th>상태</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="res" items="${reservations}">
                        <tr>
                            <td>${res.rReserveNo}</td>
                            <td>${res.seatNo}</td>
                            <td>${res.startTime}</td>
                            <td>${res.endTime}</td>
                            <td>${res.rReserveStatus}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p>예약 내역이 없습니다.</p>
        </c:otherwise>
    </c:choose>
</body>
</html>
