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
    <title>독후감 작성내역</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/resource/css/user/mypage/mypage-list.css">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="${contextPath}/resource/js/user/mypage/pagination.js"></script>
    <script src="${contextPath}/resource/js/user/mypage/dataLoader.js"></script>
    <script src="${contextPath}/resource/js/user/mypage/searchFilter.js"></script>
    <script>
        $(document).ready(function() {
            // 테이블 업데이트 함수
            function updateBookReportTable(bookReportList) {
                let tableBody = $('.board-table tbody');
                tableBody.empty();
                
                if (bookReportList && bookReportList.length > 0) {
                    $.each(bookReportList, function(index, report) {
                        let row = $('<tr>');
                        row.append($('<td>').text(report.RNUM));
                        row.append($('<td>').addClass('title').attr('title', report.TITLE).text(report.TITLE));
                        row.append($('<td>').addClass('title').attr('title', report.CODE_NAME).text(report.CODE_NAME));
                        row.append($('<td>').text(report.WRITTEN_DATE));
                        row.append($('<td>').html('<input type="button" value="독후감수정" class="extension-btn" onclick="">'));
                        tableBody.append(row);
                    });
                } else { 
                    let row = $('<tr>');
                    row.append($('<td>').attr('colspan', '5').text('독후감 작성내역이 없습니다.'));
                    tableBody.append(row);
                }
            }
            
            const searchOptions = {
                'title': '도서명',
                'type': '분류'
            };
            
            const searchFilterHandler = setupSearchFilter({
                searchOptions: searchOptions
            });
            
            const loadBookReportList = searchFilterHandler.createAdvancedDataLoader(
                '${contextPath}/user/mypage/bookReportList.do', 
                updateBookReportTable
            );
            
            loadBookReportList(1);
            
        	searchFilterHandler.setupAdvancedPaginationHandlers();
            
            $('.filter-button').addClass('period-btn');
            
        });
    </script>
</head>
<body>
    <div class="info-content">
        <div class="info-content-header">
            <h2 class="content-title">독후감 작성내역</h2>
            <p class="content-description">
                독후감 작성내역을 확인하실 수 있습니다.
            </p>
        </div>
        
        <div class="filter-buttons">
            <button class="filter-button period-btn active" data-period="">전체</button>
            <button class="filter-button period-btn" data-period="week">1주일</button>
            <button class="filter-button period-btn" data-period="month">1개월</button>
            <button class="filter-button period-btn" data-period="year">1년</button>
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
                            <th width="32%">도서명</th>
                            <th width="20%">분류</th>
                            <th width="20%">작성일</th>
                            <th width="20%">독후감수정</th>
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