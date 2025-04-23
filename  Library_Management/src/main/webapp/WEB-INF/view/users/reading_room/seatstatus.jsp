<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>${roomName} - 좌석 목록</title></head>
<body>
<h2>${roomName} 좌석 목록</h2>
<table border="1">
  <tr><th>좌석 번호</th><th>예약하기</th></tr>
  <c:forEach var="seat" items="${seats}">
    <tr>
      <td>${seat.seatNo}</td>
      <td>
        <form action="/reserveSeat" method="post">
          <input type="hidden" name="seatNo" value="${seat.seatNo}">
          <input type="text" name="userId" placeholder="사용자 ID" required>
          <input type="text" name="timeSlot" placeholder="예: 09:00~10:00" required>
          <input type="submit" value="예약">
        </form>
      </td>
    </tr>
  </c:forEach>
</table>
<a href="/index.jsp">← 메인으로</a>
</body>
</html>
