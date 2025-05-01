<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>${roomName} 좌석 예약</title>
  <style>
    body {
      font-family: 'Noto Sans KR', sans-serif;
      margin: 0;
      background: #fff;
    }
    header {
      background: #a00;
      color: white;
      padding: 10px 20px;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
    header h1 {
      margin: 0;
      font-size: 18px;
    }
    .nav-buttons {
      display: flex;
      gap: 10px;
    }
    .nav-buttons a {
      color: white;
      text-decoration: none;
      border: 1px solid white;
      padding: 4px 10px;
      border-radius: 4px;
      font-size: 14px;
    }
    .container {
      padding: 20px;
    }
    .date-section {
      margin-bottom: 20px;
    }
    .date-section input {
      font-size: 16px;
      padding: 4px 10px;
    }
    table {
      width: 100%;
      border-collapse: collapse;
      font-size: 14px;
    }
    th, td {
      border: 1px solid #ccc;
      text-align: center;
      padding: 8px;
    }
    th {
      background: #f0f0f0;
    }
    .bar {
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
    .bar .slot {
      background: #ffc107;
      height: 12px;
      width: 3%;
      margin: 0 0.5px;
      border-radius: 2px;
    }
    .reserve-btn {
      border: 1px solid #cf202f;
      color: #cf202f;
      background: white;
      padding: 4px 8px;
      border-radius: 4px;
      font-weight: bold;
      cursor: pointer;
    }
  </style>
  <script>
    function submitReservation(seatNo) {
      const form = document.createElement("form");
      form.method = "post";
      form.action = "${ctx}/reservation.do";

      const seatInput = document.createElement("input");
      seatInput.type = "hidden";
      seatInput.name = "seatNo";
      seatInput.value = seatNo;
      form.appendChild(seatInput);

      const userInput = document.createElement("input");
      userInput.type = "hidden";
      userInput.name = "userNo";
      userInput.value = "1"; // TODO: 실제 로그인 사용자 정보 반영
      form.appendChild(userInput);

      const roomInput = document.createElement("input");
      roomInput.type = "hidden";
      roomInput.name = "roomName";
      roomInput.value = "${roomName}";
      form.appendChild(roomInput);

      const now = new Date();
      const dateStr = now.toISOString().split('T')[0];

      const startTime = dateStr + 'T09:00';
      const endTime = dateStr + 'T10:00';

      const startInput = document.createElement("input");
      startInput.type = "hidden";
      startInput.name = "startTime";
      startInput.value = startTime.replace('T', ' ');
      form.appendChild(startInput);

      const endInput = document.createElement("input");
      endInput.type = "hidden";
      endInput.name = "endTime";
      endInput.value = endTime.replace('T', ' ');
      form.appendChild(endInput);

      document.body.appendChild(form);
      form.submit();
    }
  </script>
</head>
<body>
<header>
  <h1>${roomName} 좌석 예약</h1>
  <div class="nav-buttons">
    <a href="${ctx}/readingMain.jsp">HOME</a>
    <a href="${ctx}/seatList.do?roomName=디지털열람실">디지털열람실</a>
    <a href="${ctx}/seatList.do?roomName=미디어열람실">미디어열람실</a>
    <a href="${ctx}/myReservation.do">MY예약현황</a>
  </div>
</header>
<div class="container">
  <div class="date-section">
    <label>기준일자:
      <input type="date" value="${nowDate}" />
    </label>
  </div>
  <table>
    <thead>
      <tr>
        <th>순번</th>
        <th>열람실명</th>
        <th>좌석번호</th>
        <th>예약현황</th>
        <th>예약</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="seat" items="${seatList}" varStatus="vs">
        <tr>
          <td>${vs.index + 1}</td>
          <td>${roomName}</td>
          <td>${seat.seatNumber}</td>
          <td>
            <div class="bar">
              <c:set var="reservedHours" value="${reservationMap[seat.seatNo]}" />
              <c:forEach begin="9" end="18" var="h">
                <c:choose>
                  <c:when test="${not empty reservedHours and fn:contains(reservedHours, h)}">
                    <div class="slot" style="background: #999;"></div>
                  </c:when>
                  <c:otherwise>
                    <div class="slot" style="background: #ffc107;"></div>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </div>
          </td>
          <td>
            <button type="button" class="reserve-btn" onclick="submitReservation('${seat.seatNo}')">예약</button>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</div>
</body>
</html>
