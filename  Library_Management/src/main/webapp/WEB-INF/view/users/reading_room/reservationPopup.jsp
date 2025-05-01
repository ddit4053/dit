<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>예약 창</title>
    <style>
        body {
            font-family: 'Noto Sans KR', sans-serif;
            background-color: #fff;
        }
        .container {
            margin: 30px auto;
            width: 600px;
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 24px;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        h2 {
            font-size: 22px;
            margin-bottom: 24px;
            color: #333;
        }
        .form-group {
            margin-bottom: 16px;
        }
        label {
            display: block;
            font-weight: bold;
            margin-bottom: 6px;
        }
        input[type="date"],
        input[type="time"],
        input[type="text"],
        select {
            width: 100%;
            padding: 8px;
            font-size: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        .time-select-row {
            display: flex;
            gap: 10px;
            align-items: center;
        }
        .time-select-row select {
            width: 48%;
        }
        .seat-row {
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .reserve-btn-row {
            margin-top: 30px;
            text-align: center;
        }
        .reserve-btn {
            background-color: #cc0033;
            color: white;
            border: none;
            padding: 10px 24px;
            font-size: 16px;
            border-radius: 4px;
            cursor: pointer;
        }
        .reserve-btn:hover {
            background-color: #a00028;
        }
        .error-msg {
            color: red;
            margin-bottom: 20px;
        }
        .seat-select-btn {
            padding: 6px 12px;
            font-size: 14px;
            background-color: #eee;
            border: 1px solid #ccc;
            border-radius: 4px;
            cursor: pointer;
        }
        .seat-select-btn:hover {
            background-color: #ddd;
        }
        .seat-layout-modal {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100vw;
            height: 100vh;
            background-color: rgba(0,0,0,0.5);
            justify-content: center;
            align-items: center;
            z-index: 1000;
        }
        .seat-layout-content {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            max-width: 90vw;
            max-height: 90vh;
            overflow: auto;
            text-align: center;
        }
        .seat-layout-image {
            max-width: 100%;
            height: auto;
            border: 1px solid #ccc;
            border-radius: 8px;
        }
    </style>
    <script>
        function selectSeat() {
            document.getElementById("seatLayoutModal").style.display = 'flex';
        }

        function chooseSeat(seatNo) {
            document.querySelector("input[name='seatNo']").value = seatNo;
            document.getElementById("selectedSeatText").innerText = seatNo + '번';
            document.getElementById("seatLayoutModal").style.display = 'none';
        }

        function closeModal() {
            document.getElementById("seatLayoutModal").style.display = 'none';
        }
    </script>
</head>
<body>
<div class="container">
    <h2>예약</h2>

    <c:if test="${not empty errorMsg}">
        <div class="error-msg">${errorMsg}</div>
    </c:if>

    <c:if test="${empty errorMsg}">
        <form action="${ctx}/reservation.do" method="post">
            <input type="hidden" name="seatNo" value="${seatNo}" />
            <input type="hidden" name="roomName" value="${roomName}" />

            <div class="form-group">
                <label>예약일자</label>
                <input type="date" name="reserveDate" value="${nowDate}" required />
            </div>

            <div class="form-group">
                <label>사용시간</label>
                <div class="time-select-row">
                    <select name="startTime" required>
                        <c:forEach begin="9" end="17" var="h">
                            <option value="${h}">${h}:00</option>
                        </c:forEach>
                    </select>
                    ~
                    <select name="endTime" required>
                        <c:forEach begin="10" end="18" var="h">
                            <option value="${h}">${h}:00</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label>좌석선택</label>
                <div class="seat-row">
                    <span id="selectedSeatText"><strong>${seatNo}번</strong></span>
                    <button type="button" class="seat-select-btn" onclick="selectSeat()">좌석 배치도 보기</button>
                </div>
            </div>

            <div class="form-group">
                <label>열람실명</label>
                <span><strong>${roomName}</strong></span>
            </div>

            <div class="reserve-btn-row">
                <button type="submit" class="reserve-btn">예약</button>
            </div>
        </form>
    </c:if>
</div>

<div id="seatLayoutModal" class="seat-layout-modal" onclick="closeModal(event)">
  <div class="seat-layout-content" onclick="event.stopPropagation()">
    <h3>좌석 배치도</h3>
    <img src="${ctx}/resources/img/layout-seats.png" alt="좌석배치도" class="seat-layout-image">
 

    <div style="text-align: right; margin-top: 10px;">
      <button onclick="closeModal()" class="seat-select-btn">닫기</button>
    </div>
  </div>
</div>
</body>
</html>
