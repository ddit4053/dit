<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="${pageContext.request.contextPath}/resource/js/jquery-3.7.1.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/users/book_search/newBooks.css">
<script src="${pageContext.request.contextPath}/resource/js/users/book_search/newBooks.js"></script>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>신착 도서 리스트</title>

</head>
<body>
    <header>
        <h1>신착도서 리스트</h1>
    </header>
    <div class="container">
        <c:if test="${empty newBookList}">
            <p><strong>검색결과 없습니다</strong></p>
        </c:if>
        <c:forEach var="book" items="${newBookList}">
            <div class="book-card" data-bookno="${book.bookNo}">
                <img src="${book.cover}" alt="표지">
                <div class="book-info">
                    <h3>
                        <c:out value="${fn:split(book.bookTitle, '-')[0]}"/> 
                        <span class="subtitle">
                            <c:out value="${fn:split(book.bookTitle, '-')[1]}"/>
                        </span>
                    </h3>
                    <p><strong>저자:</strong> ${book.author}</p>
                    <p><strong>출판사:</strong> ${book.publisher}</p>
                </div>
            </div>
        </c:forEach>

        <!-- 페이징 추가 -->
        <div class="pagination">
            <c:if test="${page > 1}">
                <a href="?page=${page - 1}" class="page-btn">이전</a>
            </c:if>

            <c:forEach begin="1" end="${totalPages}" var="i">
                <a href="?page=${i}" class="page-btn ${i == page ? 'active' : ''}">${i}</a>
            </c:forEach>

            <c:if test="${page < totalPages}">
                <a href="?page=${page + 1}" class="page-btn">다음</a>
            </c:if>
        </div>
    </div>
</body>
</html>
