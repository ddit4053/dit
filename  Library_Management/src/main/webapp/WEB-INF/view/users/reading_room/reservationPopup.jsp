<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>좌석 예약</title>
    <style>
        body { 
            font-family: 'Noto Sans KR', sans-serif; 
            background: #f9f9f9; 
            margin: 0; 
            padding: 20px;
        }
        
        .popup-container {
            max-width: 500px;
            margin: 0 auto;
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            padding: 30px;
        }
        
        h2 {
            margin-top: 0;
            margin-bottom: 25px;
            color: #cc0033;
            font-size: 22px;
            text-align: center;
            padding-bottom: 10px;
            border-bottom: 1px solid #eee;
        }
        
        .form-group {
            margin-bottom: 20px;
        }
        
        label {
            display: block;
            font-weight: bold;
            margin-bottom: 8px;
            font-size: 15px;
        }
        
        input[type="date"],
        select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 15px;
        }
        
        .time-group {
            display: flex;
            align-items: center;
            gap: 10px;
        }
        
        .time-group select {
            flex: 1;
        }
        
        .time-separator {
            font-weight: bold;
            padding: 0 5px;
        }
        
        .seat-info {
            background: #f5f5f5;
            padding: 15px;
            border-radius: 4px;
            margin-bottom: 20px;
        }
        
        .seat-number {
            font-weight: bold;
            color: #cc0033;
        }
        
        .button-group {
            display: flex;
            justify-content: center;
            gap: 15px;
            margin-top: 30px;
        }
        
        .btn {
            padding: 12px 25px;
            font-size: 16px;
            font-weight: bold;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: all 0.2s;
        }
        
        .btn-primary {
            background: #cc0033;
            color: white;
        }
        
        .btn-primary:hover {
            background: #a00028;
        }
        
        .btn-secondary {
            background: #f0f0f0;
            color: #<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>좌석 예약</title>
    <style>
        body { 
            font-family: 'Noto Sans KR', sans-serif; 
            background: #f9f9f9; 
            margin: 0; 
            padding: 20px;
        }
        
        .popup-container {
            max-width: 500px;
            margin: 0 auto;
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            padding: 30px;
        }
        
        h2 {
            margin-top: 0;
            margin-bottom: 25px;
            color: #cc0033;
            font-size: 22px;
            text-align: center;
            padding-bottom: 10px;
            border-bottom: 1px solid #eee;
        }
        
        .form-group {
            margin-bottom: 20px;
        }
        
        label {
            display: block;
            font-weight: bold;
            margin-bottom: 8px;
            font-size: 15px;
        }
        
        input[type="date"],
        select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 15px;
        }
        
        .time-group {
            display: flex;
            align-items: center;
            gap: 10px;
        }
        
        .time-group select {
            flex: 1;
        }
        
        .time-separator {
            font-weight: bold;
            padding: 0 5px;
        }
        
        .seat-info {
            background: #f5f5f5;
            padding: 15px;
            border-radius: 4px;
            margin-bottom: 20px;
        }
        
        .seat-number {
            font-weight: bold;
            color: #cc0033;
        }
        
        .button-group {
            display: flex;
            justify-content: center;
            gap: 15px;
            margin-top: 30px;
        }
        
        .btn {
            padding: 12px 25px;
            font-size: 16px;
            font-weight: bold;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: all 0.2s;
        }
        
        .btn-primary {
            background: #cc0033;
            color: white;
        }
        
        .btn-primary:hover {
            background: #a00028;
        }
        
        .btn-secondary {
            background: #f0f0f0;
            color: #333;
        }
        
        .btn-secondary:hover {
            background: #ddd;
        }
        
        .notice {
            margin-top: 20px;
            font-size: 13px;
            color: #666;
            line-height: 1.5;
        }
        
        .notice strong {
            color: #cc0033;
        }
        
        /* 모달 스타일 */
        .seat-layout-modal { 
            display: none; 
            position: fixed; 
            top: 0; 
            left: 0; 
            width: 100vw; 
            height: 100vh; 
            background: rgba(0,0,0,0.5); 
            justify-content: center; 
            align-items: center; 
            z-index: 1000; 
        }
        
        .seat-layout-content { 
            background: white; 
            padding: 30px; 
            border-radius: 10px; 
            width: 90%; 
            max-width: 800px; 
            max-height: 90vh; 
            overflow-y: auto; 
            text-align: center; 
        }
        
        .seat-container { 
            display: grid; 
            grid-template-columns: repeat(6, 1fr); 
            gap: 15px; 
            margin-top: 20px; 
        }
        
        .seat { 
            width: 60px; 
            height: 60px; 
            background: #eee; 
            display: flex; 
            justify-content: center; 
            align-items: center; 
            border-radius: 10px; 
            font-weight: bold; 
            cursor: pointer; 
            transition: 0.3s; 
        }
        
        .seat.selected { 
            background: #cc0033; 
            color: white; 
        }
        
        .seat.booked { 
            background: #bbb; 
            color: white; 
            cursor: not-allowed; 
        }
    </style>
</head>

<body>
    <div class="popup-container">
        <h2>좌석 예약</h2>
        
        <form action="${ctx}/reservation.do" method="post" onsubmit="return validateForm()">
            <input type="hidden" name="seatNo" id="seatNo" value="${seatNo}" />
            <input type="hidden" name="userNo" value="1" /> <!-- 실제로는 세션에서 가져와야 함 -->
            <input type="hidden" name="roomName" value="${roomName}" />
            
            <div class="seat-info">
                <p>선택하신 좌석은 <span class="seat-number">${roomName} ${seatNo}번</span> 입니다.</p>
            </div>
            
            <div class="form-group">
                <label for="reserveDate">예약일자</label>
                <input type="date" id="reserveDate" name="reserveDate" value="${nowDate}" required />
            </div>
            
            <div class="form-group">
                <label>사용시간</label>
                <div class="time-group">
                    <select name="startTime" id="startTime" required>
                        <c:forEach begin="9" end="17" var="h">
                            <option value="${h}:00">${h}:00</option>
                            <option value="${h}:10">${h}:10</option>
                            <option value="${h}:20">${h}:20</option>
                            <option value="${h}:30">${h}:30</option>
                            <option value="${h}:40">${h}:40</option>
                            <option value="${h}:50">${h}:50</option>
                        </c:forEach>
                    </select>
                    
                    <span class="time-separator">~</span>
                    
                    <select name="endTime" id="endTime" required>
                        <c:forEach begin="9" end="18" var="h">
                            <option value="${h}:00">${h}:00</option>
                            <option value="${h}:10">${h}:10</option>
                            <option value="${h}:20">${h}:20</option>
                            <option value="${h}:30">${h}:30</option>
                            <option value="${h}:40">${h}:40</option>
                            <option value="${h}:50">${h}:50</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            
            <div class="form-group">
                <label>좌석선택</label>
                <div style="display: flex; align-items: center; gap: 10px;">
                    <span id="selectedSeatText"><strong>${seatNo}번</strong></span>
                    <button type="button" class="btn btn-secondary" onclick="openSeatLayout()">좌석 배치도 보기</button>
                </div>
            </div>
            
            <div class="button-group">
                <button type="button" class="btn btn-secondary" onclick="window.close()">취소</button>
                <button type="submit" class="btn btn-primary">예약하기</button>
            </div>
            
            <div class="notice">
                <p><strong>유의사항:</strong> 예약 후 30분 내에 로그인하지 않을 경우 자동으로 예약이 취소됩니다. 예약은 1인당 1일 1회 1좌석만 가능합니다.</p>
            </div>
        </form>
    </div>
    
    <!-- 좌석 배치도 모달 -->
    <div id="seatLayoutModal" class="seat-layout-modal" onclick="closeModal(event)">
        <div class="seat-layout-content" onclick="event.stopPropagation()">
            <h3>좌석 선택</h3>
            <div class="seat-container">
                <c:forEach var="i" begin="1" end="36">
                    <div class="seat" data-seatno="${i}">${i}</div>
                </c:forEach>
            </div>
            <div style="margin-top: 20px;">
                <button type="button" class="btn btn-primary" onclick="closeSeatLayout()">선택 완료</button>
            </div>
        </div>
    </div>
    
    <script>
        // 페이지 로드 시 예약일자에 기본값 설정
        window.onload = function() {
            const dateInput = document.getElementById('reserveDate');
            if (!dateInput.value) {
                const today = new Date();
                const year = today.getFullYear();
                const month = String(today.getMonth() + 1).padStart(2, '0');
                const day = String(today.getDate()).padStart(2, '0');
                dateInput.value = `${year}-${month}-${day}`;
            }
            
            // 기본값으로 시작 시간 9시, 종료 시간 10시 설정
            document.getElementById('startTime').value = "9:00";
            document.getElementById('endTime').value = "10:00";
            
            // 좌석 선택 이벤트 리스너 추가
            const seats = document.querySelectorAll('.seat');
            seats.forEach(seat => {
                seat.addEventListener('click', function() {
                    if (seat.classList.contains('booked')) return;
                    
                    // 이전 선택 해제
                    const prevSelected = document.querySelector('.seat.selected');
                    if (prevSelected) {
                        prevSelected.classList.remove('selected');
                    }
                    
                    // 새로운 선택 적용
                    this.classList.add('selected');
                    
                    // 값 업데이트
                    document.getElementById('seatNo').value = this.dataset.seatno;
                    document.getElementById('selectedSeatText').innerHTML = 
                        '<strong>' + this.dataset.seatno + '번</strong>';
                });
            });
            
            // 현재 좌석 표시
            const currentSeatNo = document.getElementById('seatNo').value;
            if (currentSeatNo) {
                const currentSeat = document.querySelector(`.seat[data-seatno="${currentSeatNo}"]`);
                if (currentSeat) {
                    currentSeat.classList.add('selected');
                }
            }
        };
        
        // 좌석 배치도 모달 열기
        function openSeatLayout() {
            document.getElementById('seatLayoutModal').style.display = 'flex';
        }
        
        // 모달 닫기 (배경 클릭)
        function closeModal(event) {
            if (event) event.stopPropagation();
            document.getElementById('seatLayoutModal').style.display = 'none';
        }
        
        // 좌석 선택 완료 버튼 클릭
        function closeSeatLayout() {
            document.getElementById('seatLayoutModal').style.display = 'none';
        }
        
        // 폼 유효성 검사
        function validateForm() {
            const startTime = document.getElementById('startTime').value;
            const endTime = document.getElementById('endTime').value;
            
            // 시간 문자열을 분으로 변환하는 함수
            function timeToMinutes(timeStr) {
                const [hours, minutes] = timeStr.split(':').map(Number);
                return hours * 60 + minutes;
            }
            
            const startMinutes = timeToMinutes(startTime);
            const endMinutes = timeToMinutes(endTime);
            
            // 종료 시간이 시작 시간보다 이후인지 확인
            if (startMinutes >= endMinutes) {
                alert('종료 시간은 시작 시간보다 이후여야 합니다.');
                return false;
            }
            
            // 최소 예약 시간(30분) 확인
            if (endMinutes - startMinutes < 30) {
                alert('최소 예약 시간은 30분입니다.');
                return false;
            }
            
            // 최대 예약 시간(3시간) 확인
            if (endMinutes - startMinutes > 180) {
                alert('최대 예약 시간은 3시간입니다.');
                return false;
            }
            
            return true;
        }
    </script>
</body>
</html>