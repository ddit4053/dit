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
        
        .header-info {
            background: #5d4037;
            color: white;
            padding: 10px 15px;
            margin-bottom: 20px;
            border-radius: 5px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        
        .user-info {
            font-size: 14px;
        }
        
        /* 기존 스타일 유지 */
        .popup-container {
            max-width: 500px;
            margin: 0 auto;
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            padding: 30px;
        }
        
        /* 나머지 스타일 유지 */
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
        
        .seat-layout-heading {
            margin-top: 0;
            margin-bottom: 20px;
            color: #cc0033;
            font-size: 20px;
            text-align: center;
        }
        
        .seat-container { 
            display: grid; 
            grid-template-columns: repeat(6, 1fr); 
            gap: 15px; 
            margin: 20px auto;
            max-width: 600px;
        }
        
        .seat { 
            width: 60px; 
            height: 60px; 
            background-color: #eee; 
            display: flex; 
            justify-content: center; 
            align-items: center; 
            border-radius: 10px; 
            font-weight: bold; 
            cursor: pointer; 
            transition: all 0.3s ease; 
            margin: 0 auto;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        
        .seat:hover {
            transform: translateY(-3px);
            box-shadow: 0 5px 10px rgba(0,0,0,0.15);
        }
        
        .seat.booked { 
            background-color: #bbb;
            color: white; 
            cursor: not-allowed; 
            transform: none;
            box-shadow: none;
        }
        
        .seat-legend {
            display: flex;
            justify-content: center;
            gap: 20px;
            margin-top: 20px;
            margin-bottom: 20px;
        }
        
        .legend-item {
            display: flex;
            align-items: center;
            font-size: 14px;
        }
        
        .legend-color {
            width: 20px;
            height: 20px;
            border-radius: 4px;
            margin-right: 8px;
        }
        
        .legend-available { background: #eee; }
        .legend-selected { background: #cc0033; }
        .legend-booked { background: #bbb; }
    </style>
</head>

<body>
    <!-- 로그인 정보 표시 추가 - name 속성 사용 -->
    <div class="header-info">
        <div class="title">좌석 예약</div>
        <div class="user-info">
            <c:choose>
                <c:when test="${not empty loginMember or not empty loginok or not empty user}">
                    <c:choose>
                        <c:when test="${not empty loginMember}">${loginMember.name}</c:when>
                        <c:when test="${not empty loginok}">${loginok.name}</c:when>
                        <c:when test="${not empty user}">${user.name}</c:when>
                    </c:choose>
                    님 환영합니다!
                </c:when>
                <c:otherwise>
                    로그인 필요
                </c:otherwise>
            </c:choose>
        </div>
    </div>

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
                        <option value="9:00">9:00</option>
                        <option value="9:30">9:30</option>
                        <option value="10:00">10:00</option>
                        <option value="10:30">10:30</option>
                        <option value="11:00">11:00</option>
                        <option value="11:30">11:30</option>
                        <option value="12:00">12:00</option>
                        <option value="12:30">12:30</option>
                        <option value="13:00">13:00</option>
                        <option value="13:30">13:30</option>
                        <option value="14:00">14:00</option>
                        <option value="14:30">14:30</option>
                        <option value="15:00">15:00</option>
                        <option value="15:30">15:30</option>
                        <option value="16:00">16:00</option>
                        <option value="16:30">16:30</option>
                        <option value="17:00">17:00</option>
                        <option value="17:30">17:30</option>
                    </select>
                    
                    <span class="time-separator">~</span>
                    
                    <select name="endTime" id="endTime" required>
                        <option value="9:30">9:30</option>
                        <option value="10:00">10:00</option>
                        <option value="10:30">10:30</option>
                        <option value="11:00">11:00</option>
                        <option value="11:30">11:30</option>
                        <option value="12:00">12:00</option>
                        <option value="12:30">12:30</option>
                        <option value="13:00">13:00</option>
                        <option value="13:30">13:30</option>
                        <option value="14:00">14:00</option>
                        <option value="14:30">14:30</option>
                        <option value="15:00">15:00</option>
                        <option value="15:30">15:30</option>
                        <option value="16:00">16:00</option>
                        <option value="16:30">16:30</option>
                        <option value="17:00">17:00</option>
                        <option value="17:30">17:30</option>
                        <option value="18:00">18:00</option>
                    </select>
                </div>
            </div>
            
            <div class="form-group">
                <label>좌석선택</label>
                <div style="display: flex; align-items: center; gap: 10px;">
                    <span id="selectedSeatText"><strong>${seatNo}번</strong></span>
                    <button type="button" class="btn btn-secondary" onclick="openSeatModal()">좌석 배치도 보기</button>
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
    <div id="seatModal" class="seat-layout-modal">
        <div class="seat-layout-content">
            <h3 class="seat-layout-heading">좌석 선택</h3>
            
            <div class="seat-legend">
                <div class="legend-item">
                    <div class="legend-color legend-available"></div>
                    <span>이용 가능</span>
                </div>
                <div class="legend-item">
                    <div class="legend-color legend-selected"></div>
                    <span>선택 좌석</span>
                </div>
                <div class="legend-item">
                    <div class="legend-color legend-booked"></div>
                    <span>예약됨</span>
                </div>
            </div>
            
            <div class="seat-container" id="seatContainer">
                <!-- 여기에 좌석들이 JS로 동적 생성됩니다 -->
            </div>
            
            <div style="margin-top: 20px;">
                <button type="button" class="btn btn-primary" onclick="completeSeatSelection()">선택 완료</button>
            </div>
        </div>
    </div>
    
    <script>
        // 전역 변수 - 선택된 좌석
        let currentSelectedSeat = "${seatNo}";
        let tempSelectedSeat = null;
        
        // 초기화 함수
        window.onload = function() {
            // 기본 날짜 설정
            if (!document.getElementById('reserveDate').value) {
                const today = new Date();
                const year = today.getFullYear();
                const month = String(today.getMonth() + 1).padStart(2, '0');
                const day = String(today.getDate()).padStart(2, '0');
                document.getElementById('reserveDate').value = `${year}-${month}-${day}`;
            }
            
            // 기본 시간 설정
            document.getElementById('startTime').value = "9:00";
            document.getElementById('endTime').value = "10:00";
            
            // 모달 닫기 이벤트 (배경 클릭)
            document.getElementById('seatModal').addEventListener('click', function(e) {
                if (e.target === this) {
                    closeSeatModal();
                }
            });
        };
        function validateForm() {
            const startTime = document.getElementById('startTime').value;
            const endTime = document.getElementById('endTime').value;
            
            // 기존 시간 유효성 검사 코드 유지
            function timeToMinutes(timeStr) {
                const [hours, minutes] = timeStr.split(':').map(Number);
                return hours * 60 + minutes;
            }
            
            const startMinutes = timeToMinutes(startTime);
            const endMinutes = timeToMinutes(endTime);
            
            if (startMinutes >= endMinutes) {
                alert('종료 시간은 시작 시간보다 이후여야 합니다.');
                return false;
            }
            
            if (endMinutes - startMinutes < 30) {
                alert('최소 예약 시간은 30분입니다.');
                return false;
            }
            
            if (endMinutes - startMinutes > 180) {
                alert('최대 예약 시간은 3시간입니다.');
                return false;
            }
            
            // 오늘 이미 예약한 좌석이 있는지 확인 (AJAX로 서버 확인)
            let hasReservation = false;
            
            $.ajax({
                type: 'GET',
                url: '${ctx}/checkUserReservation.do',
                async: false, // 동기 요청으로 처리
                success: function(response) {
                    if (response === 'true') {
                        alert('이미 오늘 예약한 좌석이 있습니다. 하루에 한 좌석만 예약 가능합니다.');
                        hasReservation = true;
                    }
                },
                error: function() {
                    alert('서버 오류가 발생했습니다. 다시 시도해주세요.');
                    hasReservation = true;
                }
            });
            
            if (hasReservation) {
                return false;
            }
            
            return true;
        }
        // 좌석 배치도 생성
        function createSeats() {
            const container = document.getElementById('seatContainer');
            container.innerHTML = ''; // 기존 좌석 초기화
            
            // 36개 좌석 생성
            for (let i = 1; i <= 36; i++) {
                const seat = document.createElement('div');
                seat.className = 'seat';
                seat.textContent = i;
                seat.setAttribute('data-seatno', i);
                
                // 현재 선택된 좌석이면 빨간색으로 표시
                if (i.toString() === currentSelectedSeat) {
                    seat.style.backgroundColor = '#cc0033';
                    seat.style.color = 'white';
                    tempSelectedSeat = i.toString();
                }
                
                // 클릭 이벤트 추가
                seat.addEventListener('click', function() {
                    selectSeatDirect(this, i);
                });
                
                // 마우스 휠 이벤트 추가
                seat.addEventListener('wheel', function(e) {
                    if (!this.classList.contains('booked')) {
                        if (e.deltaY < 0) {
                            this.style.transform = 'scale(1.1) translateY(-5px)';
                            this.style.boxShadow = '0 8px 15px rgba(0,0,0,0.2)';
                        } else {
                            this.style.transform = 'scale(0.95) translateY(2px)';
                            this.style.boxShadow = '0 1px 3px rgba(0,0,0,0.1)';
                        }
                        
                        setTimeout(() => {
                            if (this.getAttribute('data-seatno') === tempSelectedSeat) {
                                this.style.transform = '';
                                this.style.boxShadow = '';
                            } else {
                                this.style.transform = '';
                                this.style.boxShadow = '';
                            }
                        }, 300);
                    }
                    e.preventDefault();
                });
                
                container.appendChild(seat);
            }
        }
        
        // 좌석 직접 선택 함수 (인라인 스타일 사용)
        function selectSeatDirect(seatElement, seatNo) {
            if (seatElement.classList.contains('booked')) return;
            
            // 이전 선택 초기화
            const allSeats = document.querySelectorAll('.seat');
            allSeats.forEach(seat => {
                seat.style.backgroundColor = '#eee';
                seat.style.color = 'black';
            });
            
            // 새 선택에 인라인 스타일 적용
            seatElement.style.backgroundColor = '#cc0033';
            seatElement.style.color = 'white';
            
            // 임시 선택 좌석 업데이트
            tempSelectedSeat = seatNo.toString();
        }
        
        // 좌석 배치도 모달 열기
        function openSeatModal() {
            // 좌석 생성
            createSeats();
            
            // 모달 표시
            document.getElementById('seatModal').style.display = 'flex';
        }
        
        // 좌석 배치도 모달 닫기
        function closeSeatModal() {
            document.getElementById('seatModal').style.display = 'none';
        }
        
        // 좌석 선택 완료
        function completeSeatSelection() {
            if (tempSelectedSeat) {
                // 히든 필드 업데이트
                document.getElementById('seatNo').value = tempSelectedSeat;
                
                // 표시 텍스트 업데이트
                document.getElementById('selectedSeatText').innerHTML = 
                    '<strong>' + tempSelectedSeat + '번</strong>';
                
                // 좌석 정보 업데이트
                const roomName = document.querySelector('input[name="roomName"]').value;
                document.querySelector('.seat-number').textContent = roomName + ' ' + tempSelectedSeat + '번';
                
                // 현재 선택 좌석 변수 업데이트
                currentSelectedSeat = tempSelectedSeat;
            }
            
            // 모달 닫기
            closeSeatModal();
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