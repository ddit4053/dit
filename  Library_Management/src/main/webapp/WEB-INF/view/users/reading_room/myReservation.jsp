<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="currentPage" value="my" />
<c:set var="pageTitle" value="MY 예약현황" />
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>나의 예약 현황</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script> <!-- jQuery 추가 -->
<style>
body { font-family: 'Noto Sans KR', sans-serif; background: #f8f8f8; padding: 0; margin: 0; }
.content-container { padding: 20px; }
h2 { color: #cf202f; }
table { width: 100%; border-collapse: collapse; background: white; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
th, td { border: 1px solid #ddd; padding: 10px; text-align: center; }
th { background: #8d6e63; color: white; }
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
.user-info { 
    display: flex; 
    align-items: center; 
    gap: 10px;
}
.user-info a {
    padding: 8px 16px;
    font-weight: bold;
    border: 2px solid #fff;
    border-radius: 5px;
    text-decoration: none;
    color: white;
    background: #8d6e63;
}
.status-active {
    background-color: #f8f4e6;
}
</style>
</head>
<body>
<header style="background: #5d4037; color: white; padding: 12px 24px; display: flex; justify-content: space-between; align-items: center;">
    <h1 style="margin: 0; font-size: 22px;">${pageTitle}</h1>
    <div style="display: flex; align-items: center;">
        <div class="nav-buttons" style="display: flex; gap: 10px; margin-right: 20px;">
            <a href="${ctx}/main.do"
               style="padding: 8px 16px; font-weight: bold; border: 2px solid #fff; border-radius: 5px; text-decoration: none;
               color:white;">메인으로</a>
               
            <a href="${ctx}/readingMain.do"
               style="padding: 8px 16px; font-weight: bold; border: 2px solid #fff; border-radius: 5px; text-decoration: none;
               ${currentPage eq 'home' ? 'background:white; color:#5d4037;' : 'color:white;'}">HOME</a>

            <a href="${ctx}/seatList.do?roomName=일반열람실"
               style="padding: 8px 16px; font-weight: bold; border: 2px solid #fff; border-radius: 5px; text-decoration: none;
               ${currentPage eq 'seat' ? 'background:white; color:#5d4037;' : 'color:white;'}">일반열람실</a>

            <a href="${ctx}/myReservation.do"
               style="padding: 8px 16px; font-weight: bold; border: 2px solid #fff; border-radius: 5px; text-decoration: none;
               ${currentPage eq 'my' ? 'background:white; color:#5d4037;' : 'color:white;'}">MY예약현황</a>
        </div>
        
        <!-- 로그인 정보 추가 - name 속성 사용 -->
        <div class="user-info">
            <c:choose>
                <c:when test="${not empty loginMember or not empty loginok or not empty user}">
                    <span>
                        <c:choose>
                            <c:when test="${not empty loginMember}">${loginMember.name}</c:when>
                            <c:when test="${not empty loginok}">${loginok.name}</c:when>
                            <c:when test="${not empty user}">${user.name}</c:when>
                        </c:choose>
                        님
                    </span>
                    <a href="${ctx}/user/logout.do">로그아웃</a>
                </c:when>
                <c:otherwise>
                    <a href="${ctx}/user/login.do?redirect=${pageContext.request.requestURI}">로그인</a>
                    <a href="${ctx}/user/join.do">회원가입</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</header>

<div class="content-container">
    <h2>나의 열람실 예약 현황</h2>

    <c:choose>
        <c:when test="${not empty reservations}">
            <table id="reservationTable">
                <thead>
                    <tr>
                        <th>예약번호</th>
                        <th>예약일</th>
                        <th>좌석번호</th>
                        <th>시작 시간</th>
                        <th>종료 시간</th>
                        <th>상태</th>
                        <th>예약취소</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- 예약완료 상태인 항목 먼저 표시 -->
                    <c:forEach var="res" items="${reservations}">
                        <c:if test="${res.rReserveStatus eq '예약완료'}">
                            <tr id="row-${res.rReserveNo}" class="status-active">
                                <td>${res.rReserveNo}</td>
                                <td>${res.reserveDate}</td>
                                <td>${res.seatNo}</td>
                                <td>${res.startTime}</td>
                                <td>${res.endTime}</td>
                                <td><strong>${res.rReserveStatus}</strong></td>
                                <td>
                                    <button class="cancel-btn" data-reserveno="${res.rReserveNo}">취소</button>
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                    
                    <!-- 나머지 상태의 항목 표시 -->
                    <c:forEach var="res" items="${reservations}">
                        <c:if test="${res.rReserveStatus ne '예약완료'}">
                            <tr id="row-${res.rReserveNo}">
                                <td>${res.rReserveNo}</td>
                                <td>${res.reserveDate}</td>
                                <td>${res.seatNo}</td>
                                <td>${res.startTime}</td>
                                <td>${res.endTime}</td>
                                <td>${res.rReserveStatus}</td>
                                <td>
                                    <!-- 취소완료인 경우 버튼 없음 -->
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p>예약 내역이 없습니다.</p>
        </c:otherwise>
    </c:choose>
</div>

<script>
$(document).ready(function() {
    $('.cancel-btn').click(function() {
        if (!confirm('정말 취소하시겠습니까?')) {
            return;
        }
        const rReserveNo = $(this).data('reserveno');

        $.ajax({
            type: 'POST',
            url: '${ctx}/cancelReservation.do',
            data: { rReserveNo: rReserveNo },
            success: function(response) {
                alert('예약이 취소되었습니다.');
                // 행 삭제 대신 상태 및 버튼 업데이트
                var row = $('#row-' + rReserveNo);
                row.removeClass('status-active');
                row.find('td:eq(5)').text('취소완료');
                row.find('td:eq(6)').empty(); // 취소 버튼 제거
            },
            error: function() {
                alert('취소 실패! 다시 시도해주세요.');
            }
        });
    });
});
</script>

</body>
</html>