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
    <title>알림내역</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/resource/css/user/mypage/mypage-list.css">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="${contextPath}/resource/js/user/mypage/pagination.js"></script>
    <script src="${contextPath}/resource/js/user/mypage/dataLoader.js"></script>
    <script src="${contextPath}/resource/js/user/mypage/searchFilter.js"></script>
    <script>
        $(document).ready(function() {
            function updateNotiTable(notiList) {
                let tableBody = $('.board-table tbody');
                tableBody.empty();
                
                if (notiList && notiList.length > 0) {
                    $.each(notiList, function(index, noti) {
                        let row = $('<tr>');
                        row.append($('<td>').text(noti.RNUM));
                        row.append($('<td>').text(noti.NOTI_TYPE));
                        row.append($('<td>').addClass('title').attr('title', noti.MESSAGE).text(noti.MESSAGE));
                        row.append($('<td>').text(noti.SENT_DATE));
                        tableBody.append(row);
                    });
                } else {
                    let row = $('<tr>');
                    row.append($('<td>').attr('colspan', '4').text('알림내역이 없습니다.'));
                    tableBody.append(row);
                }
            }
            
            const searchOptions = {
                'type': '알림 종류'
            };
            
            const searchFilterHandler = setupSearchFilter({
                searchOptions: searchOptions
            });
            
            const loadNotiList = searchFilterHandler.createAdvancedDataLoader(
                '${contextPath}/user/mypage/notiList.do', 
                updateNotiTable
            );
            
            loadNotiList(1);
            
    		searchFilterHandler.setupAdvancedPaginationHandlers();
            
    		$('#period-select').change(function() {
    	       
    	        loadNotiList(1, {
    	            periodType: $(this).val()
    	        });
    	    });

        });
    </script>
</head>
<body>
    <div class="info-content">
        <div class="info-content-header">
            <h2 class="content-title">알림 내역</h2>
            <p class="content-description">
                알림 내역을 확인하실 수 있습니다.
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
                            <th width="32%">알림종류</th>
                            <th width="45%">메세지</th>
                            <th width="15%">전송일</th>
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