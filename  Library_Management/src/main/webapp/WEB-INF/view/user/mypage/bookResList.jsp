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
    <title>도서 예약현황</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/resource/css/user/mypage/mypage-list.css">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="${contextPath}/resource/js/user/mypage/pagination.js"></script>
    <script src="${contextPath}/resource/js/user/mypage/dataLoader.js"></script>
    <script>
        $(document).ready(function() {
            function updateBookTable(bookList) {
                let tableBody = $('.board-table tbody');
                tableBody.empty();
                
                if (bookList && bookList.length > 0) {
                    $.each(bookList, function(index, book) {
                        let row = $('<tr>');
                        row.append($('<td>').text(book.RNUM));
                        row.append($('<td>').text(book.ISBN));
                        row.append($('<td>').addClass('title').attr('title', book.BOOK_TITLE).text(book.BOOK_TITLE));
                        row.append($('<td>').addClass('title').attr('title', book.AUTHOR).text(book.AUTHOR));
                        row.append($('<td>').text(book.RESERVE_DATE));
                        row.append($('<td>').text(book.RESERVE_STATUS));
                        tableBody.append(row);
                    });
                } else {
                    let row = $('<tr>');
                    row.append($('<td>').attr('colspan', '6').text('예약현황이 없습니다.'));
                    tableBody.append(row);
                }
            }

            const loadBookReservations = createDataLoader('${contextPath}/user/mypage/bookResList.do', updateBookTable);
            
            loadBookReservations(1);
            
            setupPaginationHandlers(loadBookReservations);
        });
    </script>
</head>
<body>
    <div class="info-content">
        <div class="info-content-header">
            <h2 class="content-title">도서 예약현황</h2>
            <p class="content-description">
                도서 예약현황을 확인하실 수 있습니다.
            </p>
        </div>
        
        <div class="notice-section">
            <div class="board-list">
                <table class="board-table">
                    <thead>
                        <tr>
                            <th width="8%">번호</th>
                            <th width="20%">청구번호</th>
                            <th width="32%">도서명</th>
                            <th width="10%">저자</th>
                            <th width="15%">예약일</th>
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