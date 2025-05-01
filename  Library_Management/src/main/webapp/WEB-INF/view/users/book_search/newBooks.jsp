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
    max-width: 900px;
    margin: 0 auto;
  }

  .book-card {
    display: flex;
    background-color: #fff;
    border-radius: 15px;
    box-shadow: 0 8px 15px rgba(0, 0, 0, 0.1);
    padding: 20px;
    margin-bottom: 30px;
    align-items: center;
    transition: transform 0.3s ease, box-shadow 0.3s ease;
  }

  .book-card:hover {
    transform: translateY(-10px);
    box-shadow: 0 12px 20px rgba(0, 0, 0, 0.2);
  }

  .book-card img {
    width: 150px;
    height: auto;
    margin-right: 30px;
    border-radius: 8px;
    transition: transform 0.3s ease;
  }

  .book-card img:hover {
    transform: scale(1.05);
  }

  .book-info {
    display: flex;
    flex-direction: column;
    justify-content: center;
    width: 100%;
  }

  .book-info h3 {
    margin: 0 0 15px 0;
    font-size: 22px;
    color: #333;
    line-height: 1.4;
  }

  .book-info .subtitle {
    font-size: 18px;
    color: #888;
    font-style: italic;
  }

  .book-info p {
    margin: 6px 0;
    color: #555;
    font-size: 16px;
  }

  .book-info p strong {
    color: #222;
  }
</style>
<script type="text/javascript">
		//const contextPath = "${pageContext.request.contextPath}";
		
		console.log(contextPath);
			$(function() {
			  $('.book-card').on('click', function() {
			    const bookno = $(this).data('bookno');
			    console.log(bookno);
			    location.href = `\${contextPath}/books/detail?bookNo=\${bookno}`;
			  });
		});
</script>
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