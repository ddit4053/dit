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
    <title>열람실 이용내역</title>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="${contextPath}/resource/js/user/mypage/pagination.js"></script>
    <script src="${contextPath}/resource/js/user/mypage/dataLoader.js"></script>
    <script src="${contextPath}/resource/js/user/mypage/searchFilter.js"></script>
    <script>
        $(document).ready(function() {
            function updateResTable(resList) {
                let tableBody = $('.board-table tbody');
                tableBody.empty();
                
                if (resList && resList.length > 0) {
                    $.each(resList, function(index, res) {
                        let row = $('<tr>');
                        row.append($('<td>').text(res.RNUM));
                        row.append($('<td>').text(res.ROOM_NAME));
                        row.append($('<td>').text(res.SEAT_NUMBER));
                        row.append($('<td>').text(res.END_TIME));
                        row.append($('<td>').text(res.RESTIME));
                        tableBody.append(row);
                    });
                } else {
                    let row = $('<tr>');
                    row.append($('<td>').attr('colspan', '5').text('이용내역이 없습니다.'));
                    tableBody.append(row);
                }
            }
            
            const searchOptions = {
                'roomName': '열람실 이름',
                'seatNumber': '좌석번호'
            };
            
            const searchFilterHandler = setupSearchFilter({
                searchOptions: searchOptions
            });
            
            const loadResList = searchFilterHandler.createAdvancedDataLoader(
                '${contextPath}/user/mypage/resList.do', 
                updateResTable
            );
            
            loadResList(1);
            
    		searchFilterHandler.setupAdvancedPaginationHandlers();
            
    		$('#period-select').change(function() {
     	       
    			loadResList(1, {
    	            periodType: $(this).val()
    	        });
    	    });

        });
    </script>
</head>
<body>
    <div class="info-content">
        <div class="info-content-header">
            <h2 class="content-title">열람실 이용내역</h2>
            <p class="content-description">
                열람실 이용내역을 확인하실 수 있습니다.
            </p>
        </div>
        
        <div class="period-select-container">
            <select id="period-select" class="period-select">
                <option value="">전체</option>
                <option value="week">1주일</option>
                <option value="month">1개월</option>
                <option value="year">1년</option>
            </select>
        </div>
        
        <div class="board-search">
            <select class="search-type">
               
            </select>
            <input type="text" class="search-input" placeholder="검색어를 입력하세요">
            <button class="search-btn">검색</button>
        </div>
        
        <div class="notice-section">
            <div class="board-list">
                <table class="board-table">
                    <thead>
                        <tr>
                            <th width="8%">번호</th>
                            <th width="26%">열람실이름</th>
                            <th width="15%">좌석번호</th>
                            <th width="36%">이용일자</th>
                            <th width="15%">이용시간</th>
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