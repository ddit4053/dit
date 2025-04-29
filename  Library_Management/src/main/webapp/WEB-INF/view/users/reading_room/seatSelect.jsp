<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>좌석 선택</title>
    <style>
        body {
            font-family: 'Noto Sans KR', sans-serif;
            background: #f5f5f5;
            padding: 20px;
        }
        .seat-container {
            width: 800px;
            margin: 0 auto;
            display: grid;
            grid-template-columns: repeat(6, 1fr);
            gap: 15px;
        }
        .seat {
            width: 80px;
            height: 80px;
            background-color: #eee;
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: bold;
            font-size: 20px;
            border-radius: 10px;
            cursor: pointer;
            transition: background-color 0.3s, color 0.3s;
        }
        .seat.selected {
            background-color: #6c5ce7;
            color: white;
        }
        .btn-area {
            margin-top: 30px;
            text-align: center;
        }
        .btn-area button {
            padding: 10px 30px;
            font-size: 18px;
            background-color: #0984e3;
            border: none;
            border-radius: 8px;
            color: white;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        .btn-area button:hover {
            background-color: #74b9ff;
        }
    </style>
</head>
<body>

<h2 style="text-align: center;">좌석 선택</h2>

<form action="ReservationPopup.do" method="post">
    <div class="seat-container">
        <c:forEach var="i" begin="1" end="36">
            <div class="seat" data-seatno="${i}">${i}</div>
        </c:forEach>
    </div>

    <!-- 선택된 좌석번호를 저장하는 hidden input -->
    <input type="hidden" id="selectedSeatNo" name="seatNo" value="">

    <div class="btn-area">
        <button type="submit">예약하기</button>
    </div>
</form>

<script>
let selectedSeat = null;

window.onload = function() {
    const seats = document.querySelectorAll('.seat');
    seats.forEach(seat => {
        seat.addEventListener('click', function() {
            if (selectedSeat) {
                selectedSeat.classList.remove('selected');
            }
            selectedSeat = this;
            selectedSeat.classList.add('selected');

            document.getElementById('selectedSeatNo').value = selectedSeat.dataset.seatno;
        });
    });
}
</script>

</body>
</html>
