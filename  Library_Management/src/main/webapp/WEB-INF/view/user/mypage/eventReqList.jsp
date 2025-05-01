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
    <title>교육/행사 신청내역</title>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="${contextPath}/resource/js/user/mypage/pagination.js"></script>
    <script src="${contextPath}/resource/js/user/mypage/dataLoader.js"></script>
    <script src="${contextPath}/resource/js/user/mypage/searchFilter.js"></script>
    <script>
        $(document).ready(function() {
            function updateEventReqTable(eventReqList) {
                let tableBody = $('.board-table tbody');
                tableBody.empty();
                
                if (eventReqList && eventReqList.length > 0) {
                    $.each(eventReqList, function(index, eventReq) {
                        let row = $('<tr>');
                        row.append($('<td>').text(eventReq.RNUM));
                        row.append($('<td>').addClass('title').attr('title', eventReq.EV_TITLE).text(eventReq.EV_TITLE));
                        row.append($('<td>').text(eventReq.ACTIVITY_TYPE));
                        row.append($('<td>').text(eventReq.EV_DATE));
                        row.append($('<td>').text(eventReq.REQ_EV_DATE));
                        row.append($('<td>').text(eventReq.REQ_EV_STATUS));
                        tableBody.append(row);
                    });
                } else {
                    let row = $('<tr>');
                    row.append($('<td>').attr('colspan', '6').text('교육/행사 신청내역이 없습니다.'));
                    tableBody.append(row);
                }
            }
            
            const searchOptions = {
                'title': '교육/행사명',
                'type': '종류'
            };
            
            const searchFilterHandler = setupSearchFilter({
                searchOptions: searchOptions
            });
            
            const loadEventReqList = searchFilterHandler.createAdvancedDataLoader(
                '${contextPath}/user/mypage/eventReqList.do', 
                updateEventReqTable
            );
            
            loadEventReqList(1);
            
    		searchFilterHandler.setupAdvancedPaginationHandlers();
            
    		$('#period-select').change(function() {
       	       
    			loadEventReqList(1, {
    	            periodType: $(this).val()
    	        });
    	    });

        });
    </script>
</head>
<body>
    <div class="info-content">
        <div class="info-content-header">
            <h2 class="content-title">교육/행사 신청내역</h2>
            <p class="content-description">
               교육/행사 신청내역을 확인하실 수 있습니다.
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
                            <th width="27%">교육/행사명</th>
                            <th width="10%">종류</th>
                            <th width="20%">교육/행사일자</th>
                            <th width="20%">신청일자</th>
                            <th width="15%">신청상태</th>
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