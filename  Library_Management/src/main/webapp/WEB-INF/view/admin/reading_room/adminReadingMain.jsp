<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- 상대 경로로 수정 --%>
<jsp:include page="../../header.jsp" />
<jsp:include page="../../nav.jsp" />

<div class="main-content">
    <div class="container">
        <div class="page-header">
            <h1 class="page-title">관리자 - 전체 예약 현황</h1>
            
        </div>
        
        <div class="content-layout">
            
            
            <!-- 메인 콘텐츠 영역 -->
            <div class="main-content-area">
                <div class="reading-room-dashboard">
                    <!-- 날짜 선택 캘린더 -->
                    <div class="date-filter-section">
                        <h2>날짜별 예약 조회</h2>
                        <div class="date-picker-container">
                            <div class="date-picker-wrapper">
                                <label for="datePicker">날짜 선택:</label>
                                <input type="date" id="datePicker" class="date-picker">
                                <button id="searchByDateBtn" class="search-date-btn">조회</button>
                            </div>
                            
                        </div>
                    </div>
                    
                    <!-- 전체 예약 내역 (요일별) -->
                    <div class="my-reservations">
                        <h2>예약 내역</h2>
                        
                        <c:choose>
                            <c:when test="${empty reservationsByDay}">
                                <div class="no-reservations">
                                    <p>등록된 예약 내역이 없습니다.</p>
                                </div>
                            </c:when>
                            <c:otherwise>
                               
                                
                                <div class="day-tab-contents">
                                    <c:forEach var="dayEntry" items="${reservationsByDay}">
                                        <div class="day-content" id="day-${dayEntry.key}">
                                            <table class="reservation-table">
                                                <thead>
                                                    <tr>
                                                        <th>예약번호</th>
                                                        <th>사용자번호</th>
                                                        <th>예약일</th>
                                                        <th>좌석번호</th>
                                                        <th>시작 시간</th>
                                                        <th>종료 시간</th>
                                                        <th>상태</th>
                                                        <th>관리</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="res" items="${dayEntry.value}">
                                                        <tr id="row-${res.rReserveNo}" class="reservation-row" data-date="${res.reserveDate}">
                                                            <td>${res.rReserveNo}</td>
                                                            <td>${res.userNo}</td>
                                                            <td>${res.reserveDate}</td>
                                                            <td>${res.seatNo}</td>
                                                            <td>${res.startTime}</td>
                                                            <td>${res.endTime}</td>
                                                            <td>${res.rReserveStatus}</td>
                                                            <td>
                                                                <c:if test="${res.rReserveStatus eq '예약완료'}">
                                                                    <button class="cancel-btn" data-reserveno="${res.rReserveNo}">취소</button>
                                                                </c:if>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </c:forEach>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    
                    <!-- 열람실 관리 안내 -->
                    <div class="usage-guide">
                        <h2>열람실 관리 안내</h2>
                        <div class="guide-content">
                            <h3>관리자 권한</h3>
                            <ul>
                                <li>모든 사용자의 예약 정보를 조회할 수 있습니다.</li>
                                <li>예약 취소 및 상태 변경이 가능합니다.</li>
                                <li>실시간으로 열람실 좌석 현황을 모니터링할 수 있습니다.</li>
                            </ul>
                            
                            <h3>운영 규정</h3>
                            <ul>
                                <li>이용 시간: 평일/주말 09:00 ~ 18:00</li>
                                <li>1인당 1일 1좌석만 예약 가능합니다.</li>
                                <li>예약 후 30분 내 미입실 시 자동 취소됩니다.</li>
                                <li>최대 이용 시간은 3시간입니다.</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- jQuery 로드 명시적 추가 -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
$(document).ready(function() {
    // 오늘 날짜 설정
    const today = new Date();
    const formattedToday = today.toISOString().split('T')[0];
    $('#datePicker').val(formattedToday);
    
    // 기본적으로 첫 번째 탭 활성화
    const firstTab = $(".day-tab").first();
    if (firstTab.length > 0) {
        firstTab.addClass("active");
        const firstDay = firstTab.data("day");
        $("#day-" + firstDay).show();
    }
    
    // 탭 클릭 이벤트
    $(".day-tab").click(function() {
        const day = $(this).data("day");
        
        // 모든 탭과 내용 비활성화
        $(".day-tab").removeClass("active");
        $(".day-content").hide();
        
        // 선택한 탭과 내용 활성화
        $(this).addClass("active");
        $("#day-" + day).show();
    });
    
    // 날짜별 조회 버튼 클릭 이벤트
    $("#searchByDateBtn").click(function() {
        const selectedDate = $("#datePicker").val();
        if (!selectedDate) {
            alert("날짜를 선택해주세요.");
            return;
        }
        
        filterReservationsByDate(selectedDate);
    });
    
    // 전체 보기 버튼 클릭 이벤트
    $("#resetDateBtn").click(function() {
        $(".reservation-row").show();
        $(".day-content").each(function() {
            const visibleRows = $(this).find('.reservation-row:visible').length;
            if (visibleRows > 0) {
                $(this).show();
                const day = $(this).attr('id').replace('day-', '');
                $('.day-tab[data-day="' + day + '"]').show();
            } else {
                $(this).hide();
                const day = $(this).attr('id').replace('day-', '');
                $('.day-tab[data-day="' + day + '"]').hide();
            }
        });
        
        // 첫 번째 보이는 탭 활성화
        const firstVisibleTab = $(".day-tab:visible").first();
        if (firstVisibleTab.length > 0) {
            $(".day-tab").removeClass("active");
            firstVisibleTab.addClass("active");
            const firstDay = firstVisibleTab.data("day");
            $(".day-content").hide();
            $("#day-" + firstDay).show();
        } else {
            $(".no-reservations").show();
        }
    });
    
    // 날짜별 예약 필터링 함수
    function filterReservationsByDate(selectedDate) {
        let foundReservations = false;
        
        // 모든 탭과 내용 숨기기
        $(".day-tab").hide();
        $(".day-content").hide();
        
        // 모든 예약 행 숨기기
        $(".reservation-row").hide();
        
        // 선택된 날짜에 해당하는 예약만 표시
        $(".reservation-row").each(function() {
            const reservationDate = $(this).data("date");
            if (reservationDate === selectedDate) {
                $(this).show();
                foundReservations = true;
                
                // 해당 요일 탭 및 컨텐츠 표시
                const dayContentId = $(this).closest('.day-content').attr('id');
                const day = dayContentId.replace('day-', '');
                
                $('.day-tab[data-day="' + day + '"]').show();
                $("#" + dayContentId).show();
                
                // 해당 요일 탭 활성화
                $(".day-tab").removeClass("active");
                $('.day-tab[data-day="' + day + '"]').addClass("active");
            }
        });
        
        // 검색 결과가 없을 경우
        if (!foundReservations) {
            alert("선택한 날짜에 예약 내역이 없습니다.");
            $(".no-reservations").show();
        } else {
            $(".no-reservations").hide();
            
            // 첫 번째 보이는 탭 활성화
            const firstVisibleTab = $(".day-tab:visible").first();
            if (firstVisibleTab.length > 0) {
                firstVisibleTab.click();
            }
        }
    }
    
    // 예약 취소 버튼 클릭 이벤트
    $('.cancel-btn').click(function() {
        if (!confirm('정말 취소하시겠습니까?')) {
            return;
        }
        const rReserveNo = $(this).data('reserveno');

        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/cancelReservation.do',
            data: { rReserveNo: rReserveNo },
            success: function(response) {
                alert('예약이 취소되었습니다.');
                // 해당 행을 삭제하거나 상태 업데이트
                $('#row-' + rReserveNo).find('td:eq(6)').text('취소완료');
                $('#row-' + rReserveNo).find('td:eq(7)').empty();
            },
            error: function() {
                alert('취소 실패! 다시 시도해주세요.');
            }
        });
    });
});
</script>

<style>
.reading-room-dashboard {
    background: #fff;
    border-radius: 8px;
    box-shadow: 0 2px 10px rgba(0,0,0,0.1);
    padding: 30px;
}

.quick-access, .my-reservations, .usage-guide, .date-filter-section {
    margin-bottom: 40px;
}

.date-picker-container {
    display: flex;
    align-items: center;
    margin-top: 15px;
    gap: 10px;
}

.date-picker-wrapper {
    display: flex;
    align-items: center;
    gap: 10px;
}

.date-picker {
    padding: 8px 12px;
    border: 1px solid #ddd;
    border-radius: 4px;
    font-size: 16px;
}

.search-date-btn, .reset-date-btn {
    padding: 8px 16px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-weight: bold;
}

.search-date-btn {
    background-color: #8d6e63;
    color: white;
}

.reset-date-btn {
    background-color: #f0f0f0;
    color: #333;
}

.search-date-btn:hover {
    background-color: #795548;
}

.reset-date-btn:hover {
    background-color: #e0e0e0;
}

.room-buttons {
    display: flex;
    gap: 15px;
    margin-top: 15px;
}

.room-btn {
    background: #f0f0f0;
    color: #333;
    padding: 15px 25px;
    border-radius: 6px;
    text-decoration: none;
    font-weight: bold;
    text-align: center;
    flex: 1;
    transition: all 0.3s ease;
}

.room-btn:hover {
    background: #e0e0e0;
}

.room-btn.highlight {
    background: #8d6e63;
    color: white;
}

.room-btn.highlight:hover {
    background: #795548;
}

.day-tabs {
    display: flex;
    border-bottom: 2px solid #ddd;
    margin-bottom: 20px;
}

.day-tab {
    padding: 10px 20px;
    background: none;
    border: none;
    cursor: pointer;
    font-weight: bold;
    color: #666;
}

.day-tab.active {
    color: #8d6e63;
    border-bottom: 3px solid #8d6e63;
    margin-bottom: -2px;
}

.day-content {
    display: none;
}

.reservation-table {
    width: 100%;
    border-collapse: collapse;
}

.reservation-table th, .reservation-table td {
    border: 1px solid #ddd;
    padding: 12px;
    text-align: center;
}

.reservation-table th {
    background: #8d6e63;
    color: white;
}

.cancel-btn {
    background: #cc0033;
    color: white;
    padding: 6px 12px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

.cancel-btn:hover {
    background: #a00028;
}

.no-reservations {
    text-align: center;
    padding: 30px;
    background: #f9f9f9;
    border-radius: 6px;
}

.guide-content {
    background: #f9f9f9;
    padding: 20px;
    border-radius: 6px;
}

.guide-content h3 {
    color: #8d6e63;
    margin-top: 15px;
    margin-bottom: 10px;
}

.guide-content ul {
    padding-left: 20px;
}

.guide-content li {
    margin-bottom: 8px;
}
</style>

<%-- 상대 경로로 수정 --%>
<jsp:include page="../../footer.jsp" />