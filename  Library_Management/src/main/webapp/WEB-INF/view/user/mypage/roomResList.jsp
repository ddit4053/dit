<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String contextPath = request.getContextPath();
    pageContext.setAttribute("contextPath", contextPath);
    int userNo = (int)session.getAttribute("userNo");
    request.setAttribute("userNo", userNo);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>열람실 예약현황</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/resource/css/user/mypage/mypage-list.css">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="${contextPath}/resource/js/user/mypage/pagination.js"></script>
    <script src="${contextPath}/resource/js/user/mypage/dataLoader.js"></script>
    <script>
        $(document).ready(function() {
            function updateRoomTable(roomList) {
                let tableBody = $('.board-table tbody');
                tableBody.empty();
                
                if (roomList && roomList.length > 0) {
                    $.each(roomList, function(index, room) {
                        let row = $('<tr>');
                        row.append($('<td>').text(room.RNUM));
                        row.append($('<td>').text(room.ROOM_NAME));
                        row.append($('<td>').text(room.SEAT_NUMBER));
                        row.append($('<td>').text(room.START_TIME));
                        row.append($('<td>').text(room.RESTIME));
                        row.append($('<td>').text(room.R_RESERVE_STATUS));
                        tableBody.append(row);
                    });
                } else {
                    let row = $('<tr>');
                    row.append($('<td>').attr('colspan', '6').text('예약현황이 없습니다.'));
                    tableBody.append(row);
                }
            }

            const loadRoomResList = createDataLoader('${contextPath}/user/mypage/roomResList.do', updateRoomTable);
            
            loadRoomResList(1);
            
            setupPaginationHandlers(loadRoomResList);
        });
    </script>
</head>
<body>
    <div class="info-content">
        <div class="info-content-header">
            <h2 class="content-title">열람실 예약현황</h2>
            <p class="content-description">
                열람실 예약현황을 확인하실 수 있습니다.
            </p>
        </div>
        
        <div class="notice-section">
            <div class="board-list">
                <table class="board-table">
                    <thead>
                        <tr>
                            <th width="8%">번호</th>
                            <th width="20%">열람실이름</th>
                            <th width="15%">좌석번호</th>
                            <th width="30%">예약시간</th>
                            <th width="15%">신청시간</th>
                            <th width="12%">예약상태</th>
                        </tr>
                    </thead>
                    <tbody>
                    
                    </tbody>
                </table>
                
                <div class="board-pagination">
                
                </div>
            </div>
        </div>
    </div>
</body>
</html>