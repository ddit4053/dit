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
    <title>도서 대출이력</title>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="${contextPath}/resource/js/user/mypage/pagination.js"></script>
    <script src="${contextPath}/resource/js/user/mypage/dataLoader.js"></script>
    <script src="${contextPath}/resource/js/user/mypage/searchFilter.js"></script>
    <script>
        $(document).ready(function() {
            function updateLoanTable(loansList) {
                let tableBody = $('.board-table tbody');
                tableBody.empty();
                
                if (loansList && loansList.length > 0) {
                    $.each(loansList, function(index, loan) {
                        let row = $('<tr>');
                        row.append($('<td>').text(loan.RNUM));
                        row.append($('<td>').addClass('title').attr('title', loan.BOOK_TITLE).text(loan.BOOK_TITLE));
                        row.append($('<td>').text(loan.LOAN_DATE));
                        row.append($('<td>').text(loan.DUE_DATE));
                        row.append($('<td>').text(loan.RETURN_DATE));
                        tableBody.append(row);
                    });
                } else {
                    let row = $('<tr>');
                    row.append($('<td>').attr('colspan', '5').text('대출이력이 없습니다.'));
                    tableBody.append(row);
                }
            }
            const searchOptions = {
                'title': '도서명'
            };
            
            const searchFilterHandler = setupSearchFilter({
                searchOptions: searchOptions
            });
            
            const loadLoansList = searchFilterHandler.createAdvancedDataLoader(
                '${contextPath}/user/mypage/loansList.do', 
                updateLoanTable
            );

            loadLoansList(1);
            
    		searchFilterHandler.setupAdvancedPaginationHandlers();
            
    		$('#period-select').change(function() {
        	       
    			loadLoansList(1, {
    	            periodType: $(this).val()
    	        });
    	    });

        });
    </script>
</head>
<body>
    <div class="info-content">
        <div class="info-content-header">
            <h2 class="content-title">도서 대출이력</h2>
            <p class="content-description">
                도서 대출이력을 확인하실 수 있습니다.
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
                            <th width="32%">도서명</th>
                            <th width="20%">대출일</th>
                            <th width="20%">반납예정일</th>
                            <th width="20%">실제반납일</th>
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