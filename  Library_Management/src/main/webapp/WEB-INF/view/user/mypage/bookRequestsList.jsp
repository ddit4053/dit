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
    <title>도서 요청현황</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/resource/css/user/mypage/mypage-list.css">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="${contextPath}/resource/js/user/mypage/pagination.js"></script>
    <script src="${contextPath}/resource/js/user/mypage/dataLoader.js"></script>
    <script>
        $(document).ready(function() {
            function updateBookReqTable(bookRequestsList) {
                let tableBody = $('.board-table tbody');
                tableBody.empty();
                
                if (bookRequestsList && bookRequestsList.length > 0) {
                    $.each(bookRequestsList, function(index, bookReq) {
                        let row = $('<tr>');
                        row.append($('<td>').text(bookReq.RNUM));
                        row.append($('<td>').addClass('title').attr('title', bookReq.REQ_BOOK_TITLE).text(bookReq.REQ_BOOK_TITLE));
                        row.append($('<td>').addClass('title').attr('title', bookReq.REQ_BOOK_AUTHOR).text(bookReq.REQ_BOOK_AUTHOR));
                        row.append($('<td>').text(bookReq.REQ_BOOK_DATE));
                        row.append($('<td>').text(bookReq.REQ_BOOK_STATUS));
                        tableBody.append(row);
                    });
                } else {
                    let row = $('<tr>');
                    row.append($('<td>').attr('colspan', '5').text('요청현황이 없습니다.'));
                    tableBody.append(row);
                }
            }

            const loadBookReqList = createDataLoader('${contextPath}/user/mypage/bookReqList.do', updateBookReqTable);
            
            loadBookReqList(1);
            
            setupPaginationHandlers(loadBookReqList);
        });
    </script>
</head>
<body>
    <div class="info-content">
        <div class="info-content-header">
            <h2 class="content-title">신규도서 신청현황</h2>
            <p class="content-description">
                신규도서 신청현황을 확인하실 수 있습니다.
            </p>
        </div>
        
        <div class="notice-section">
            <div class="board-list">
                <table class="board-table">
                    <thead>
                        <tr>
                            <th width="8%">번호</th>
                            <th width="42%">도서명</th>
                            <th width="15%">저자</th>
                            <th width="20%">예약일</th>
                            <th width="15%">예약상태</th>
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