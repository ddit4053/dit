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
      background-color: #f9f9f9;
    }
    header {
      background: #cc0033;
      color: white;
      padding: 12px 24px;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
    header h1 {
      margin: 0;
      font-size: 20px;
    }
    .nav-buttons {
      display: flex;
      gap: 10px;
    }
    .nav-buttons a {
      padding: 8px 16px;
      font-weight: bold;
      border: 2px solid #fff;
      border-radius: 5px;
      color: #fff;
      text-decoration: none;
    }
    .nav-buttons a.active-tab {
      background-color: #fff;
      color: #cc0033;
    }
    .container {
      padding: 30px;
    }
    .date-section {
      margin-bottom: 20px;
      display: flex;
      align-items: center;
      gap: 10px;
    }
    .date-section input[type="date"] {
      padding: 6px 10px;
      font-size: 15px;
    }
    .date-section button {
      padding: 6px 14px;
      background-color: #cc0033;
      color: #fff;
      border: none;
      border-radius: 4px;
      cursor: pointer;
    }
    table {
      width: 100%;
      border-collapse: collapse;
      background-color: white;
      box-shadow: 0 0 5px rgba(0,0,0,0.1);
    }
    th, td {
      border: 1px solid #ddd;
      text-align: center;
      padding: 10px 0;
    }
    th {
      background-color: #f0f0f0;
    }
    .bar {
      display: flex;
      justify-content: center;
      gap: 1px;
    }
    .slot {
      width: 3.3%;
      height: 14px;
      border-radius: 2px;
      background-color: #ffc107;
    }
    .slot.reserved {
      background-color: #999;
    }
    .reserve-btn {
      padding: 6px 12px;
      border: 1px solid #cc0033;
      background-color: #fff;
      color: #cc0033;
      font-weight: bold;
      border-radius: 4px;
      cursor: pointer;
    }
    .reserve-btn:disabled {
      background-color: #eee;
      color: #999;
      border: none;
      cursor: not-allowed;
    }
    .reservation-summary-box {
      margin-top: 20px;
      background-color: #fff8f8;
      border: 1px solid #ddd;
      padding: 15px;
      border-radius: 8px;
      color: #333;
    }
    .reservation-summary-box h3 {
      margin-top: 0;
      font-size: 18px;
      color: #cc0033;
    }
  </style>

  <script>
    function openReservationPopup(seatNo) {
      const ctx = '${ctx}';
      const roomName = '${roomName}'.replace(/ /g, '%20');
      const url = ctx + "/ReservationPopup.do?seatNo=" + seatNo + "&roomName=" + roomName;
      window.open(url, "reservationPopup", "width=700,height=600,scrollbars=no");
    }
  </script>
</head>
<body>
<header>
  <h1>${roomName} 좌석 예약</h1>
  <div class="nav-buttons">
    <a href="${ctx}/readingMain.jsp" class="${empty roomName ? 'active-tab' : ''}">HOME</a>
    <a href="${ctx}/seatList.do?roomName=디지털열람실" class="${roomName eq '디지털열람실' ? 'active-tab' : ''}">디지털열람실</a>
    <a href="${ctx}/seatList.do?roomName=미디어열람실" class="${roomName eq '미디어열람실' ? 'active-tab' : ''}">미디어열람실</a>
    <a href="${ctx}/myReservation.do" class="${fn:contains(pageContext.request.requestURI, 'myReservation') ? 'active-tab' : ''}">MY예약현황</a>
  </div>
</header>

<div class="container">
  <form class="date-section" action="${ctx}/seatList.do" method="get">
    <input type="hidden" name="roomName" value="${roomName}" />
    <label>기준일자:
      <input type="date" name="selectedDate" value="${nowDate}" />
    </label>
    <button type="submit">조회</button>
  </form>

  <div class="reservation-summary-box">
    <h3>선택한 예약 정보</h3>
    <p>📅 날짜: <span id="selected-date">선택 전</span></p>
    <p>🕓 시간: <span id="selected-time">선택 전</span></p>
    <p>💺 좌석: <span id="selected-seat">선택 전</span></p>
  </div>

  <table>
    <thead>
      <tr>
        <th>번호</th>
        <th>공간</th>
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
          <td>${seat.seatNumber}번</td>
          <td>
            <div class="bar">
              <c:set var="reservedHours" value="${reservationMap[seat.seatNo]}" />
              <c:forEach begin="9" end="18" var="h">
                <div class="slot ${not empty reservedHours and fn:contains(reservedHours, h) ? 'reserved' : ''}"></div>
              </c:forEach>
            </div>
          </td>
          <td>
            <c:choose>
              <c:when test="${not empty reservedHours and fn:length(reservedHours) == 10}">
                <button class="reserve-btn" disabled>이용불가</button>
              </c:when>
              <c:otherwise>
                <button type="button" class="reserve-btn" onclick="openReservationPopup('${seat.seatNo}')">예약</button>
              </c:otherwise>
            </c:choose>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</div>
</body>
</html>
