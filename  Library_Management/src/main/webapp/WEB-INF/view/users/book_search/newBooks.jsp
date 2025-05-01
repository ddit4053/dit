<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="${pageContext.request.contextPath}/resource/js/jquery-3.7.1.js"></script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>신착 도서 리스트</title>
<style>
  body {
    font-family: Arial, sans-serif;
    background-color: #f9f9f9;
    margin: 0;
    padding: 20px;
  }

  header h1 {
    text-align: center;
    color: #333;
  }

  .container {
    max-width: 800px;
    margin: 0 auto;
  }

  .book-card {
    display: flex;
    background-color: #fff;
    border-radius: 10px;
    box-shadow: 0 2px 5px rgba(0,0,0,0.1);
    padding: 15px;
    margin-bottom: 20px;
    align-items: center;
  }

  .book-card img {
    width: 100px;
    height: auto;
    margin-right: 20px;
    border-radius: 5px;
  }

  .book-info {
    display: flex;
    flex-direction: column;
  }

  .book-info h2 {
    margin: 0 0 10px 0;
    font-size: 18px;
    color: #222;
  }

  .book-info p {
    margin: 4px 0;
    color: #555;
  }
  
   .subtitle {
      font-size: 16px;
      color: gray;
    }
</style>
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
     </div>
</body>
</html>