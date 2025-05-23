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
    <title>도서 대출현황</title>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="${contextPath}/resource/js/user/mypage/pagination.js"></script>
    <script src="${contextPath}/resource/js/user/mypage/dataLoader.js"></script>
    <script src="${contextPath}/resource/js/user/mypage/searchFilter.js"></script>
    <script>
       
    	window.loadBookLoansList;
    	
        function requestExtension(loanNo, buttonElem) {
            if (!confirm("선택한 도서의 대출 기간을 연장하시겠습니까?")) {
                return;
            }
            
            $.ajax({
                url: '${contextPath}/user/mypage/requestLoanExtension.do',
                type: 'POST',
                data: {
                    loanNo: loanNo
                },
                dataType: 'json',
                success: function(response) {
                    if (response.success) {
                       
                        $(buttonElem).parent().append('<span class="status-waiting"> 대기</span>');
                        $(buttonElem).prop('disabled', true);
                        $(buttonElem).addClass('disabled');
                        loadBookLoansList($('.pagination-item.active').data('page') || 1);
                        alert("연장신청이 접수되었습니다.");
                    } else {
                        alert("연장신청 처리 중 오류가 발생했습니다: " + response.message);
                    }
                },
                error: function(xhr, status, error) {
                    console.error('상태 코드:', xhr.status);
                    console.error('응답 텍스트:', xhr.responseText);
                    console.error('에러 메시지:', error);
                    alert('연장신청 처리 중 오류가 발생했습니다.');
                }
            });
        }
        
        $(document).ready(function() {
            
            function updateBookLoanTable(bookLoansList) {
                let tableBody = $('.board-table tbody');
                tableBody.empty();
                
                if (bookLoansList && bookLoansList.length > 0) {
                    $.each(bookLoansList, function(index, loan) {
                        let row = $('<tr>');
                        row.append($('<td>').text(loan.RNUM));
                        row.append($('<td>').addClass('title').attr('title', loan.BOOK_TITLE).text(loan.BOOK_TITLE));
                        row.append($('<td>').text(loan.LOAN_DATE));
                        row.append($('<td>').text(loan.DUE_DATE));
                        
                        let extensionCell = $('<td>');
                        if (loan.APPROVAL_STATUS == null) {
                            extensionCell.html('<input type="button" value="연장신청" onclick="requestExtension(' + loan.LOAN_NO + ', this)" ' + 
                                           'class="extension-btn" data-loan-no="' + loan.LOAN_NO + '">');
                        } else if (loan.APPROVAL_STATUS === '대기') {
                            extensionCell.html('<div class="status-container status-waiting"><span class="status-text">대기</span></div>');
                        } else if (loan.APPROVAL_STATUS === '승인') {
                            extensionCell.html('<div class="status-container status-approved"><span class="status-text">연장완료</span></div>');
                        } else if (loan.APPROVAL_STATUS === '반려') {
                            extensionCell.html('<div class="status-container status-rejected"><span class="status-text">반려</span></div>');
                        }
                        row.append(extensionCell);
                        tableBody.append(row);
                    });
                    
                    
                    '<div class="status-container status-waiting"><span class="status-text">대기</span></div>'
                } else {
                    let row = $('<tr>');
                    row.append($('<td>').attr('colspan', '5').text('대출현황이 없습니다.'));
                    tableBody.append(row);
                }
            }
            
            const searchOptions = {
                'title': '도서명'
            };
            
            const searchFilterHandler = setupSearchFilter({
                searchOptions: searchOptions
            });
            
            window.loadBookLoansList = searchFilterHandler.createAdvancedDataLoader(
                '${contextPath}/user/mypage/bookLoansList.do', 
                updateBookLoanTable
            );

            window.loadBookLoansList(1);
            
			searchFilterHandler.setupAdvancedPaginationHandlers();
            
			$('#period-select').change(function() {
	    	       
				loadBookLoansList(1, {
    	            periodType: $(this).val()
    	        });
    	    });
        });
    </script>
</head>
<body>
    <div class="info-content">
        <div class="info-content-header">
            <h2 class="content-title">도서 대출현황</h2>
            <p class="content-description">
                도서 대출현황을 확인하실 수 있습니다.
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
                            <th width="37%">도서명</th>
                            <th width="20%">대출일</th>
                            <th width="20%">반납예정일</th>
                            <th width="15%">연장신청</th>
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