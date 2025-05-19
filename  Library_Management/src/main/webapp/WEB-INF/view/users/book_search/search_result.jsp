<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="${pageContext.request.contextPath}/resource/js/jquery-3.7.1.js"></script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>도서 리스트</title>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/users/book_search/search_result.css">
<script src="${pageContext.request.contextPath}/resource/js/users/book_search/search_result.js"></script>
</head>
<body>
  <header>
    <h1>도서 리스트</h1>
  </header>
  <div class="container">
   	<c:if test="${empty SearchBookList}">
   		<p><strong>검색결과 없습니다</strong></p>
   	</c:if>
    <c:forEach var="book" items="${SearchBookList}">
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



<c:if test="${totalPages > 1}">
  <div class="pagination">
    <c:if test="${currentPage > 1}">
      <a href="${pageContext.request.contextPath}/books/search/result?searchType=${param.searchType}&keyword=${param.keyword}&year=${param.year}&selectedCategoryId=${param.selectedCategoryId}&page=${currentPage - 1}" class="page-btn">이전</a>
    </c:if>

    <c:set var="startPage" value="${currentPage - 3}" />
    <c:set var="endPage" value="${currentPage + 3}" />
    <c:if test="${startPage < 1}">
      <c:set var="startPage" value="1" />
    </c:if>
    <c:if test="${endPage > totalPages}">
      <c:set var="endPage" value="${totalPages}" />
    </c:if>

    <c:forEach var="i" begin="${startPage}" end="${endPage}">
      <c:choose>
        <c:when test="${i == currentPage}">
          <span class="page-btn active">${i}</span>
        </c:when>
        <c:otherwise>
          <a href="${pageContext.request.contextPath}/books/search/result?searchType=${param.searchType}&keyword=${param.keyword}&query=${param.query}&year=${param.year}&selectedCategoryId=${param.selectedCategoryId}&page=${i}" class="page-btn">${i}</a>
        </c:otherwise>
      </c:choose>
    </c:forEach>

    <c:if test="${currentPage < totalPages}">
      <a href="${pageContext.request.contextPath}/books/search/result?searchType=${param.searchType}&keyword=${param.keyword}&query=${param.query}&year=${param.year}&selectedCategoryId=${param.selectedCategoryId}&page=${currentPage + 1}" class="page-btn">다음</a>
    </c:if>
  </div>
</c:if>
		


  </div>
</body>
</html>
