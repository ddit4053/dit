<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/users/book_search/bookRecommend.css">
<script src="${pageContext.request.contextPath}/resource/js/jquery-3.7.1.js"></script>
<script src="${pageContext.request.contextPath}/resource/js/users/book_search/bookRecommend.js"></script>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원 추천 도서</title>
</head>
<body>
    <header>
        <h1>📚 회원님을 위한 추천 도서</h1>
    </header>
    <div class="container">
        <c:if test="${empty recommendedBooks}">
            <p><strong>추천 도서가 없습니다. 더 많은 책을 대출해보세요!</strong></p>
        </c:if>
        <c:forEach var="book" items="${recommendedBooks}">
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
    </div>
</body>
</html>
