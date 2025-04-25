<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/users/reading_room/reading-status.css">

<div class="reading-room-container">
    <!-- 열람실 정보 섹션 -->
    <div class="room-info-section">
        <div class="room-selector">
            <label for="roomSelect">열람실 선택:</label>
            <select id="roomSelect" onchange="changeRoom(this.value)">
                <option value="room1" selected>제1열람실 (1층)</option>
                <option value="room2">제2열람실 (2층)</option>
                <option value="room3">노트북존 (3층)</option>
                <option value="room4">스터디룸 (4층)</option>
            </select>
        </div>
        
        <div class="room-stats">
            <div class="stat-item">
                <span class="stat-label">전체 좌석</span>
                <span class="stat-value" id="totalSeats">120</span>
            </div>
            <div class="stat-item">
                <span class="stat-label">사용 중</span>
                <span class="stat-value" id="usedSeats">87</span>
            </div>
            <div class="stat-item">
                <span class="stat-label">사용 가능</span>
                <span class="stat-value" id="availableSeats">33</span>
            </div>
            <div class="stat-item">
                <span class="stat-label">이용률</span>
                <span class="stat-value" id="usageRate">72.5%</span>
            </div>
        </div>
        
        <div class="room-status-legend">
            <div class="legend-item">
                <div class="seat-marker available"></div>
                <span>사용 가능</span>
            </div>
            <div class="legend-item">
                <div class="seat-marker in-use"></div>
                <span>사용 중</span>
            </div>
            <div class="legend-item">
                <div class="seat-marker reserved"></div>
                <span>예약됨</span>
            </div>
            <div class="legend-item">
                <div class="seat-marker disabled"></div>
                <span>사용 불가</span>
            </div>
        </div>
        
        <div class="room-refresh">
            <span class="last-update">마지막 업데이트: <span id="lastUpdateTime">2025.04.23 14:30:45</span></span>
            <button class="refresh-btn" onclick="refreshStatus()">
                <i class="fas fa-sync-alt"></i> 새로고침
            </button>
        </div>
    </div>
    
    <!-- 열람실 좌석 배치도 -->
    <div class="room-layout">
        <h3 class="room-name">제1열람실 (1층)</h3>
        
        <!-- 입구 표시 -->
        <div class="entrance-marker">
            <i class="fas fa-door-open"></i> 입구
        </div>
        
        <!-- 좌석 배치 -->
        <div class="seating-area">
            <!-- 왼쪽 좌석 구역 -->
            <div class="seat-section left-section">
                <div class="seat-row">
                    <div class="seat available" data-seat-id="101">
                        <span class="seat-number">101</span>
                    </div>
                    <div class="seat in-use" data-seat-id="102">
                        <span class="seat-number">102</span>
                    </div>
                    <div class="seat in-use" data-seat-id="103">
                        <span class="seat-number">103</span>
                    </div>
                    <div class="seat in-use" data-seat-id="104">
                        <span class="seat-number">104</span>
                    </div>
                </div>
                <div class="seat-row">
                    <div class="seat in-use" data-seat-id="105">
                        <span class="seat-number">105</span>
                    </div>
                    <div class="seat in-use" data-seat-id="106">
                        <span class="seat-number">106</span>
                    </div>
                    <div class="seat in-use" data-seat-id="107">
                        <span class="seat-number">107</span>
                    </div>
                    <div class="seat available" data-seat-id="108">
                        <span class="seat-number">108</span>
                    </div>
                </div>
                <div class="seat-row">
                    <div class="seat in-use" data-seat-id="109">
                        <span class="seat-number">109</span>
                    </div>
                    <div class="seat in-use" data-seat-id="110">
                        <span class="seat-number">110</span>
                    </div>
                    <div class="seat disabled" data-seat-id="111">
                        <span class="seat-number">111</span>
                    </div>
                    <div class="seat in-use" data-seat-id="112">
                        <span class="seat-number">112</span>
                    </div>
                </div>
                <div class="seat-row">
                    <div class="seat in-use" data-seat-id="113">
                        <span class="seat-number">113</span>
                    </div>
                    <div class="seat available" data-seat-id="114">
                        <span class="seat-number">114</span>
                    </div>
                    <div class="seat available" data-seat-id="115">
                        <span class="seat-number">115</span>
                    </div>
                    <div class="seat in-use" data-seat-id="116">
                        <span class="seat-number">116</span>
                    </div>
                </div>
            </div>
            
            <!-- 가운데 통로 -->
            <div class="aisle"></div>
            
            <!-- 오른쪽 좌석 구역 -->
            <div class="seat-section right-section">
                <div class="seat-row">
                    <div class="seat in-use" data-seat-id="117">
                        <span class="seat-number">117</span>
                    </div>
                    <div class="seat in-use" data-seat-id="118">
                        <span class="seat-number">118</span>
                    </div>
                    <div class="seat in-use" data-seat-id="119">
                        <span class="seat-number">119</span>
                    </div>
                    <div class="seat in-use" data-seat-id="120">
                        <span class="seat-number">120</span>
                    </div>
                </div>
                <div class="seat-row">
                    <div class="seat in-use" data-seat-id="121">
                        <span class="seat-number">121</span>
                    </div>
                    <div class="seat reserved" data-seat-id="122">
                        <span class="seat-number">122</span>
                    </div>
                    <div class="seat in-use" data-seat-id="123">
                        <span class="seat-number">123</span>
                    </div>
                    <div class="seat in-use" data-seat-id="124">
                        <span class="seat-number">124</span>
                    </div>
                </div>
                <div class="seat-row">
                    <div class="seat available" data-seat-id="125">
                        <span class="seat-number">125</span>
                    </div>
                    <div class="seat in-use" data-seat-id="126">
                        <span class="seat-number">126</span>
                    </div>
                    <div class="seat available" data-seat-id="127">
                        <span class="seat-number">127</span>
                    </div>
                    <div class="seat reserved" data-seat-id="128">
                        <span class="seat-number">128</span>
                    </div>
                </div>
                <div class="seat-row">
                    <div class="seat in-use" data-seat-id="129">
                        <span class="seat-number">129</span>
                    </div>
                    <div class="seat in-use" data-seat-id="130">
                        <span class="seat-number">130</span>
                    </div>
                    <div class="seat in-use" data-seat-id="131">
                        <span class="seat-number">131</span>
                    </div>
                    <div class="seat in-use" data-seat-id="132">
                        <span class="seat-number">132</span>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- 두번째 열 좌석 구역 -->
        <div class="seating-area second-row">
            <!-- 중앙 좌석 구역 -->
            <div class="seat-section center-section">
                <div class="seat-row">
                    <div class="seat in-use" data-seat-id="133">
                        <span class="seat-number">133</span>
                    </div>
                    <div class="seat in-use" data-seat-id="134">
                        <span class="seat-number">134</span>
                    </div>
                    <div class="seat in-use" data-seat-id="135">
                        <span class="seat-number">135</span>
                    </div>
                    <div class="seat available" data-seat-id="136">
                        <span class="seat-number">136</span>
                    </div>
                    <div class="seat in-use" data-seat-id="137">
                        <span class="seat-number">137</span>
                    </div>
                    <div class="seat in-use" data-seat-id="138">
                        <span class="seat-number">138</span>
                    </div>
                    <div class="seat available" data-seat-id="139">
                        <span class="seat-number">139</span>
                    </div>
                    <div class="seat in-use" data-seat-id="140">
                        <span class="seat-number">140</span>
                    </div>
                </div>
                <div class="seat-row">
                    <div class="seat available" data-seat-id="141">
                        <span class="seat-number">141</span>
                    </div>
                    <div class="seat in-use" data-seat-id="142">
                        <span class="seat-number">142</span>
                    </div>
                    <div class="seat in-use" data-seat-id="143">
                        <span class="seat-number">143</span>
                    </div>
                    <div class="seat in-use" data-seat-id="144">
                        <span class="seat-number">144</span>
                    </div>
                    <div class="seat in-use" data-seat-id="145">
                        <span class="seat-number">145</span>
                    </div>
                    <div class="seat in-use" data-seat-id="146">
                        <span class="seat-number">146</span>
                    </div>
                    <div class="seat in-use" data-seat-id="147">
                        <span class="seat-number">147</span>
                    </div>
                    <div class="seat available" data-seat-id="148">
                        <span class="seat-number">148</span>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- PC 구역 -->
        <div class="pc-area">
            <h4 class="area-label">PC 구역</h4>
            <div class="pc-seats">
                <div class="pc-row">
                    <div class="seat pc-seat in-use" data-seat-id="PC01">
                        <span class="seat-number">PC01</span>
                    </div>
                    <div class="seat pc-seat in-use" data-seat-id="PC02">
                        <span class="seat-number">PC02</span>
                    </div>
                    <div class="seat pc-seat available" data-seat-id="PC03">
                        <span class="seat-number">PC03</span>
                    </div>
                    <div class="seat pc-seat in-use" data-seat-id="PC04">
                        <span class="seat-number">PC04</span>
                    </div>
                </div>
                <div class="pc-row">
                    <div class="seat pc-seat available" data-seat-id="PC05">
                        <span class="seat-number">PC05</span>
                    </div>
                    <div class="seat pc-seat in-use" data-seat-id="PC06">
                        <span class="seat-number">PC06</span>
                    </div>
                    <div class="seat pc-seat disabled" data-seat-id="PC07">
                        <span class="seat-number">PC07</span>
                    </div>
                    <div class="seat pc-seat in-use" data-seat-id="PC08">
                        <span class="seat-number">PC08</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- 실시간 이용 현황 알림 섹션 -->
    <div class="usage-alert-section">
        <div class="alert-header">
            <i class="fas fa-bell"></i> 실시간 이용 현황 알림
        </div>
        <div class="alert-content">
            <div class="alert-message">
                <span class="alert-time">14:25</span>
                <span class="alert-text">109번 좌석이 사용 중으로 변경되었습니다.</span>
            </div>
            <div class="alert-message">
                <span class="alert-time">14:10</span>
                <span class="alert-text">115번 좌석이 사용 가능으로 변경되었습니다.</span>
            </div>
            <div class="alert-message">
                <span class="alert-time">14:05</span>
                <span class="alert-text">PC03이 사용 가능으로 변경되었습니다.</span>
            </div>
            <div class="alert-message">
                <span class="alert-time">13:52</span>
                <span class="alert-text">122번 좌석이 예약되었습니다.</span>
            </div>
            <div class="alert-message">
                <span class="alert-time">13:45</span>
                <span class="alert-text">101번 좌석이 사용 가능으로 변경되었습니다.</span>
            </div>
        </div>
    </div>
    
    <!-- 열람실 이용 안내 -->
    <div class="usage-guide-section">
        <h3 class="section-title">열람실 이용 안내</h3>
        <div class="guide-content">
            <div class="guide-item">
                <div class="guide-icon">
                    <i class="fas fa-clock"></i>
                </div>
                <div class="guide-text">
                    <h4>이용 시간</h4>
                    <p>평일: 09:00 - 22:00 / 주말: 09:00 - 18:00 / 공휴일: 휴실</p>
                </div>
            </div>
            <div class="guide-item">
                <div class="guide-icon">
                    <i class="fas fa-book-reader"></i>
                </div>
                <div class="guide-text">
                    <h4>이용 대상</h4>
                    <p>도서관 회원증을 소지한 모든 이용자</p>
                </div>
            </div>
            <div class="guide-item">
                <div class="guide-icon">
                    <i class="fas fa-exclamation-circle"></i>
                </div>
                <div class="guide-text">
                    <h4>유의 사항</h4>
                    <p>좌석 예약 후 20분 내 미입실 시 자동 취소됩니다. 퇴실 시 반드시 좌석 반납을 해주세요.</p>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 열람실 좌석 클릭 시 모달 -->
<div id="seatModal" class="seat-modal">
    <div class="modal-content">
        <span class="close-modal">&times;</span>
        <h3 class="modal-title">좌석 정보</h3>
        <div id="seatInfo" class="seat-info">
            <div class="info-item">
                <span class="info-label">좌석 번호:</span>
                <span id="modalSeatNumber" class="info-value">101</span>
            </div>
            <div class="info-item">
                <span class="info-label">상태:</span>
                <span id="modalSeatStatus" class="info-value available">사용 가능</span>
            </div>
            <div id="userInfoSection" style="display:none;">
                <div class="info-item">
                    <span class="info-label">이용 시작:</span>
                    <span id="modalStartTime" class="info-value">14:30</span>
                </div>
                <div class="info-item">
                    <span class="info-label">잔여 시간:</span>
                    <span id="modalTimeLeft" class="info-value">2시간 30분</span>
                </div>
            </div>
        </div>
        <div id="availableActions" class="modal-actions">
            <button id="bookSeatBtn" class="action-btn book-btn">
                <i class="fas fa-bookmark"></i> 좌석 예약
            </button>
        </div>
        <div id="bookedActions" class="modal-actions" style="display:none;">
            <button id="extendTimeBtn" class="action-btn extend-btn" disabled>
                <i class="fas fa-clock"></i> 시간 연장
            </button>
            <button id="cancelBookingBtn" class="action-btn cancel-btn">
                <i class="fas fa-times"></i> 예약 취소
            </button>
        </div>
    </div>
</div>

<!-- Font Awesome CDN -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/js/all.min.js"></script>

<!-- JavaScript for Room Status -->
<script>
// 좌석 클릭 이벤트
document.querySelectorAll('.seat').forEach(seat => {
    seat.addEventListener('click', function() {
        const seatId = this.getAttribute('data-seat-id');
        const seatStatus = this.classList.contains('available') ? '사용 가능' :
                          this.classList.contains('in-use') ? '사용 중' :
                          this.classList.contains('reserved') ? '예약됨' : '사용 불가';
        
        // 모달에 정보 설정
        document.getElementById('modalSeatNumber').textContent = seatId;
        document.getElementById('modalSeatStatus').textContent = seatStatus;
        document.getElementById('modalSeatStatus').className = 'info-value ' + 
            (this.classList.contains('available') ? 'available' : 
             this.classList.contains('in-use') ? 'in-use' : 
             this.classList.contains('reserved') ? 'reserved' : 'disabled');
        
        // 사용 중이거나 예약된 경우 사용자 정보 표시
        if(this.classList.contains('in-use') || this.classList.contains('reserved')) {
            document.getElementById('userInfoSection').style.display = 'block';
            document.getElementById('availableActions').style.display = 'none';
            document.getElementById('bookedActions').style.display = 'flex';
            
            // 예시 데이터 설정
            document.getElementById('modalStartTime').textContent = '13:45';
            document.getElementById('modalTimeLeft').textContent = '1시간 15분';
        } else if(this.classList.contains('available')) {
            document.getElementById('userInfoSection').style.display = 'none';
            document.getElementById('availableActions').style.display = 'block';
            document.getElementById('bookedActions').style.display = 'none';
        } else {
            // 사용 불가 좌석
            document.getElementById('userInfoSection').style.display = 'none';
            document.getElementById('availableActions').style.display = 'none';
            document.getElementById('bookedActions').style.display = 'none';
        }
        
        // 모달 표시
        document.getElementById('seatModal').style.display = 'block';
    });
});

// 모달 닫기 버튼
document.querySelector('.close-modal').addEventListener('click', function() {
    document.getElementById('seatModal').style.display = 'none';
});

// 모달 외부 클릭 시 닫기
window.addEventListener('click', function(event) {
    if (event.target == document.getElementById('seatModal')) {
        document.getElementById('seatModal').style.display = 'none';
    }
});

// 열람실 변경 함수
function changeRoom(roomId) {
    // 실제 구현에서는 AJAX 요청을 통해 선택한 열람실의 데이터를 가져오는 로직 추가
    alert(roomId + ' 열람실로 변경됩니다.');
}

// 상태 새로고침 함수
function refreshStatus() {
    // 실제 구현에서는 AJAX 요청을 통해 최신 상태를 가져오는 로직 추가
    const now = new Date();
    const timeString = now.getFullYear() + '.' + 
                      String(now.getMonth() + 1).padStart(2, '0') + '.' + 
                      String(now.getDate()).padStart(2, '0') + ' ' + 
                      String(now.getHours()).padStart(2, '0') + ':' + 
                      String(now.getMinutes()).padStart(2, '0') + ':' + 
                      String(now.getSeconds()).padStart(2, '0');
    
    document.getElementById('lastUpdateTime').textContent = timeString;
    alert('열람실 현황이 갱신되었습니다.');
}

// 좌석 예약 버튼 이벤트
document.getElementById('bookSeatBtn').addEventListener('click', function() {
    const seatNumber = document.getElementById('modalSeatNumber').textContent;
    alert(seatNumber + '번 좌석을 예약하시겠습니까? 로그인 후 이용 가능합니다.');
    document.getElementById('seatModal').style.display = 'none';
});

// 예약 취소 버튼 이벤트
document.getElementById('cancelBookingBtn').addEventListener('click', function() {
    const seatNumber = document.getElementById('modalSeatNumber').textContent;
    alert(seatNumber + '번 좌석 예약을 취소하시겠습니까?');
    document.getElementById('seatModal').style.display = 'none';
});
</script>