<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>알라딘 도서 리스트</title>
<script src="${pageContext.request.contextPath}/resource/js/jquery-3.7.1.js"></script>
<style>
    body {
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        background-color: #f5f5f5;
        margin: 0;
        padding: 20px;
    }

    header h1 {
        text-align: center;
        color: #4e342e;
        margin-bottom: 30px;
    }

    table {
        width: 100%;
        border-collapse: collapse;
        background-color: #ffffff;
        box-shadow: 0 4px 10px rgba(0,0,0,0.1);
        border-radius: 8px;
        overflow: hidden;
    }

    thead {
        background-color: #8d6e63;
        color: white;
    }

    thead th {
        padding: 12px;
	    font-size: 16px;
	    white-space: nowrap; /* 줄바꿈 방지 */
    }

    tbody td {
        padding: 10px;
        text-align: center;
        border-bottom: 1px solid #ddd;
    }

    tbody tr:hover {
        background-color: #f1f1f1;
    }

    img {
        border-radius: 4px;
        box-shadow: 0 2px 6px rgba(0,0,0,0.2);
    }
</style>
<script type="text/javascript">
	$(function() {
	    // 행에 커서 스타일 추가
	    $(".bookList").css("cursor", "pointer");
	
	    // 클릭 이벤트 설정
	    $(".bookList").on("click", function() {
	        var bookNo = $(this).data("bookno");
	        location.href = "${pageContext.request.contextPath}/admin/books/detailList?bookNo=" + bookNo;
	    });
	});
</script>
</head>
<body>
    <header>
        <h1>알라딘 도서 리스트</h1>
    </header>

    <table>
        <thead>
            <tr>
                <th>도서번호</th>
                <th>제목</th>
                <th>ISBN</th>
                <th>출판일</th>
                <th>표지</th>
                <th>상태</th>
                <th>저자</th>
                <th>출판사</th>
                <th>카테고리</th>
                <th>등록일</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="book" items="${bookList}">
                <tr class="bookList" data-bookno="${book.bookNo}">
                    <td>${book.bookNo}</td>
                    <td><c:out value="${fn:split(book.bookTitle, '-')[0]}"/> </td>
                    <td>${book.isbn}</td>
                    <td>${book.pubdate}</td>
                    <td><img src="${book.cover}" alt="cover" width="60px"/></td>
                    <td>${book.bookStatus}</td>
                    <td>${book.author}</td>
                    <td>${book.publisher}</td>
                    <td>${book.categoryNo}</td>
                    <td>${book.insertDate}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
