<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>좌석 예약</title>
    <style>
        body { font-family: 'Noto Sans KR', sans-serif; background: #fff; }
        .container { margin: 30px auto; width: 600px; padding: 24px; border: 1px solid #ddd; border-radius: 8px; background: #fff; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        .form-group { margin-bottom: 16px; }
        label { display: block; font-weight: bold; margin-bottom: 6px; }
        input[type="date"], select { width: 100%; padding: 8px; font-size: 15px; border: 1px solid #ccc; border-radius: 4px; }
        .reserve-btn-row { margin-top: 30px; text-align: center; }
        .reserve-btn { background: #cc0033; color: white; border: none; padding: 10px 24px; font-size: 16px; border-radius: 4px; cursor: pointer; }
        .reserve-btn:hover { background: #a00028; }
        .seat-layout-modal { display: none; position: fixed; top: 0; left: 0; width: 100vw; height: 100vh; background: rgba(0,0,0,0.5); justify-content: center; align-items: center; z-index: 1000; }
        .seat-layout-content { background: #fff; padding: 20px; border-radius: 8px; max-width: 800px; max-height: 90vh; overflow: auto; text-align: center; }
        .seat-container { display: grid; grid-template-columns: repeat(6, 1fr); gap: 10px; margin-top: 20px; }
        .seat { width: 60px; height: 60px; background: #eee; display: flex; justify-content: center; align-items: center; font-weight: bold; border-radius: 8px; cursor: pointer; transition: background-color 0.3s, color 0.3s; }
        .seat.selected { background: #6c5ce7; color: #fff; }
        .seat.booked { background: #bbb; color: white; cursor: not-allowed; }
        .seat-select-btn { background: #eee; border: 1px solid #ccc; padding: 6px 12px; border-radius: 5px; cursor: pointer; font-size: 14px; }
        .seat-select-btn:hover { background: #ddd; }
    </style>
</head>

<body>

<div class="container">
    <h2>좌석 예약</h2>

    <form action="${ctx}/reservation.do" method="post" onsubmit="return validateForm();">
        <input type="hidden" name="seatNo" id="seatNo" value="${seatNo}" />
       <input type="hidden" name="userNo" value="1" />
        <input type="hidden" name="roomName" value="${roomName}" />

        <div class="form-group">
            <label>예약일자</label>
            <input type="date" name="reserveDate" value="${nowDate}" required />
        </div>

        <div class="form-group">
            <label>사용시간</label>
            <div style="display: flex; gap: 10px;">
                <select name="startTime" required>
                    <c:forEach begin="9" end="17" var="h">
                        <option value="${h}:00">${h}:00</option>
                    </c:forEach>
                </select>
                ~
                <select name="endTime" required>
                    <c:forEach begin="10" end="18" var="h">
                        <option value="${h}:00">${h}:00</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="form-group">
            <label>좌석선택</label>
            <div>
                <span id="selectedSeatText"><strong>${seatNo}번</strong></span>
                <button type="button" class="seat-select-btn" onclick="openSeatLayout()">좌석 배치도 보기</button>
            </div>
        </div>

        <div class="form-group">
            <label>열람실명</label>
            <span><strong>${roomName}</strong></span>
        </div>

        <div class="reserve-btn-row">
            <button type="submit" class="reserve-btn">예약하기</button>
        </div>
    </form>
</div>

<!-- 좌석배치도 모달 -->
<div id="seatLayoutModal" class="seat-layout-modal" onclick="closeModal(event)">
    <div class="seat-layout-content" onclick="event.stopPropagation()">
        <h3>좌석 선택</h3>
        <div class="seat-container">
            <c:forEach var="i" begin="1" end="36">
                <div class="seat" data-seatno="${i}">${i}</div>
            </c:forEach>
        </div>
        <div style="margin-top: 20px;">
            <button type="button" class="reserve-btn" onclick="closeSeatLayout()">선택 완료</button>
        </div>
    </div>
</div>

<script>
function openSeatLayout() {
    document.getElementById('seatLayoutModal').style.display = 'flex';
}

function closeModal(event) {
    if (event) event.stopPropagation();
    document.getElementById('seatLayoutModal').style.display = 'none';
}

function closeSeatLayout() {
    document.getElementById('seatLayoutModal').style.display = 'none';
}

// 좌석 클릭 시 seatNo 반영
let selectedSeat = null;
window.onload = function() {
    const seats = document.querySelectorAll('.seat');
    seats.forEach(seat => {
        seat.addEventListener('click', function() {
            if (seat.classList.contains('booked')) return;

            if (selectedSeat) {
                selectedSeat.classList.remove('selected');
            }
            selectedSeat = this;
            selectedSeat.classList.add('selected');

            document.getElementById('seatNo').value = selectedSeat.dataset.seatno;
            document.getElementById('selectedSeatText').innerText = selectedSeat.dataset.seatno + '번';
        });
    });
};

// 모든 필수 입력값 체크
function validateForm() {
    const seatNo = document.getElementById('seatNo').value;
    const userNo = document.querySelector('input[name="userNo"]').value;
    const roomName = document.querySelector('input[name="roomName"]').value;
    const reserveDate = document.querySelector('input[name="reserveDate"]').value;
    const startTime = document.querySelector('select[name="startTime"]').value;
    const endTime = document.querySelector('select[name="endTime"]').value;

    if (!seatNo || seatNo.trim() === '') {
        alert("좌석을 선택해주세요.");
        return false;
    }
    if (!userNo || userNo.trim() === '') {
        alert("로그인 정보가 없습니다.");
        return false;
    }
    if (!roomName || roomName.trim() === '') {
        alert("열람실 정보가 없습니다.");
        return false;
    }
    if (!reserveDate || reserveDate.trim() === '') {
        alert("예약일자를 선택해주세요.");
        return false;
    }
    if (!startTime || !endTime) {
        alert("예약시간을 선택해주세요.");
        return false;
    }

    // ⭐ 문자열 파싱 후 숫자로 시간 비교
    const startHour = parseInt(startTime.split(":")[0], 10);
    const endHour = parseInt(endTime.split(":")[0], 10);

    if (startHour >= endHour) {
        alert("시작시간은 종료시간보다 빨라야 합니다.");
        return false;
    }

    return true;
}
</script>

</body>
</html>
