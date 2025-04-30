<%@page import="java.util.Map"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="pageTitle" value="좌석/실 예약" />

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>${pageTitle}</title>
  <style>
    body { font-family: 'Noto Sans KR', sans-serif; margin: 0; background: #f9f9f9; }
    .menu-tabs { display: flex; width: 100%; margin: 0; padding: 0; background: #cc0033; list-style: none; }
    .menu-tabs li { flex: 1; text-align: center; }
    .menu-tabs a { display: block; padding: 15px 0; color: white; text-decoration: none; font-weight: bold; }
    .menu-tabs a.active, .menu-tabs a:hover { background: #a00028; }
    .container { padding: 20px; }
    .date-search { display: flex; align-items: center; margin-bottom: 20px; padding: 15px; background: white; border: 1px solid #ddd; border-radius: 4px; }
    .date-search label { margin-right: 10px; font-weight: bold; }
    .date-search input[type="date"] { padding: 8px 12px; border: 1px solid #ddd; border-radius: 4px; margin-right: 10px; }
    .date-search button { padding: 8px 15px; background: #333; color: white; border: none; border-radius: 4px; cursor: pointer; }
    table { width: 100%; border-collapse: collapse; background: white; margin-bottom: 20px; }
    th, td { border: 1px solid #ddd; text-align: center; padding: 12px 8px; }
    th { background: #f2f2f2; font-weight: bold; }
    .time-bar { position: relative; width: 100%; height: 22px; background: #eee; display: flex; }
    .hour-labels { display: flex; justify-content: space-between; width: 100%; margin-bottom: 5px; }
    .hour-label { flex: 1; text-align: center; font-size: 12px; color: #666; }
    .time-slot { flex: 1; height: 100%; border-right: 1px solid #ddd; }
    .time-slot.reserved { background-color: #ffc107 !important; border: 1px solid #999; }
    .btn-reserve { padding: 6px 15px; background: white; color: #cc0033; border: 1px solid #cc0033; border-radius: 4px; font-weight: bold; cursor: pointer; }
    .btn-reserve:hover { background: #cc0033; color: white; }
    .btn-disabled { background: #f0f0f0; color: #aaa; border-color: #ddd; cursor: not-allowed; }
    footer { padding: 15px 0; text-align: center; font-size: 12px; color: #666; background: white; border-top: 1px solid #ddd; margin-top: 30px; }
  </style>
  <script>
    function openReservationPopup(seatNo) {
      const ctx = '${ctx}';
      const nowDate = document.getElementById('selectedDate').value;
      const roomName = '${roomName}';
      const url = ctx + "/ReservationPopup.do?seatNo=" + seatNo + "&nowDate=" + nowDate + "&roomName=" + roomName + "&userNo=1";
      window.open(url, "reservationPopup", "width=500,height=600,scrollbars=no");
    }
    window.onload = function() {
      const dateInput = document.getElementById('selectedDate');
      if (!dateInput.value) {
        const today = new Date();
        const year = today.getFullYear();
        const month = String(today.getMonth() + 1).padStart(2, '0');
        const day = String(today.getDate()).padStart(2, '0');
        dateInput.value = `${year}-${month}-${day}`;
      }
    };
  </script>
</head>
<body>

<ul class="menu-tabs">
  <li><a href="${ctx}/readingMain.do">HOME</a></li>
  <li><a href="${ctx}/seatList.do?roomName=일반열람실" class="${roomName == '일반열람실' ? 'active' : ''}">일반열람실</a></li>
  <li><a href="javascript:window.close();">미디어 열람실(공사중)</a></li>
  <li><a href="${ctx}/myReservation.do">MY예약현황</a></li>
</ul>

<div class="container">

  <div class="date-search">
    <form action="${ctx}/seatList.do" method="get">
      <input type="hidden" name="roomName" value="${roomName}" />
      <input type="hidden" name="userNo" value="1" />
      <label for="selectedDate">기준일자:</label>
      <input type="date" id="selectedDate" name="selectedDate" value="${nowDate}" required />
      <button type="submit">조회</button>
    </form>
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
    <c:if test="${not empty seatList}">
      <c:forEach var="seat" items="${seatList}" varStatus="vs">
        <tr>
          <td>${vs.index + 1}</td>
          <td>${roomName}</td>
          <td>${seat.seatNo}번</td>
          <td>
  <div class="hour-labels">
    <c:forEach begin="9" end="18" var="h">
      <span class="hour-label">${h}</span>
    </c:forEach>
  </div>

  <div class="time-bar">
    <%-- 🔽 여기 이 블록 안에 넣으세요 --%>
    <%
      Map<String, boolean[]> reservationMap = (Map<String, boolean[]>) request.getAttribute("reservationMap");
      String seatKey = String.valueOf(((kr.or.ddit.vo.ReadingSeatsVo)pageContext.getAttribute("seat")).getSeatNo());
      boolean[] reserved = reservationMap.get(seatKey);
      if (reserved == null) reserved = new boolean[54];

      for (int i = 0; i < 54; i++) {
    %>
        <div class="time-slot <%= reserved[i] ? "reserved" : "" %>" title="<%= reserved[i] ? "예약됨" : "예약가능" %>"></div>
    <%
      }
    %>
  </div>
</td>

          <td>
            <c:choose>
              <c:when test="${seat.isAvailable eq 'Y'}">
                <button type="button" class="btn-reserve" onclick="openReservationPopup('${seat.seatNo}')">예약</button>
              </c:when>
              <c:otherwise>
                <button type="button" class="btn-reserve btn-disabled" disabled>이용불가</button>
              </c:otherwise>
            </c:choose>
          </td>
        </tr>
      </c:forEach>
    </c:if>
    <c:if test="${empty seatList}">
      <tr>
        <td colspan="5" style="padding: 30px; text-align: center;">조회된 좌석 정보가 없습니다.</td>
      </tr>
    </c:if>
    </tbody>
  </table>
</div>

<footer>
  <div>© National Library of Korea, Sejong. All right reserved.</div>
</footer>

</body>
</html>
