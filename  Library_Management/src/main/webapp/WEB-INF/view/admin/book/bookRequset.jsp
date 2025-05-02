<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>신청 도서 리스트</title>
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
	

	  .book-info {
	    display: flex;
	    flex-direction: column;
	    justify-content: center;
	    width: 100%;
	  }
	
	  .book-info h3 {
	      font-size: 1.5em;
	      color: #5d4037;
	      margin-bottom: 10px;
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
	  .pagination {
	      text-align: center;
	      margin-top: 30px;
	  }
	
	  .page-btn {
	      display: inline-block;
	      margin: 0 5px;
	      padding: 8px 14px;
	      background-color: #eee;
	      border-radius: 6px;
	      text-decoration: none;
	      color: #333;
	      transition: background-color 0.3s;
	  }
	
	  .page-btn:hover {
	      background-color: #ccc;
	  }
	
	  .page-btn.active {
	      background-color: #8d6e63;
	      color: white;
	      font-weight: bold;
	  }
  </style>
</head>
<body>
  <h1>신청 도서 리스트</h1>
  <div class="container">
    <c:forEach var="book" items="${requestBookList}">
      <div class="book-card">
        <div class="book-info">
          <h3>${book.reqBookTitle}</h3>
          
          <p><strong>ISBN</strong> ${book.reqIsbn}</p>
          <p><strong>저자:</strong> ${book.reqBookAuthor}</p>
          <p><strong>출판사:</strong> ${book.reqBookPublisher}</p>
          <p><strong>신청일:</strong>${book.reqBookDate}</p>
          <p><strong>상태:</strong> ${book.reqBookStatus}</p>
          <p><strong>요청사유:</strong> ${book.reqBookComment}</p>
        </div>
      </div>
    </c:forEach>

    <c:if test="${totalPages > 1}">
      <div class="pagination">
        <c:if test="${currentPage > 1}">
          <a href="?page=${currentPage - 1}" class="page-btn">이전</a>
        </c:if>
        <c:forEach var="i" begin="1" end="${totalPages}">
          <c:choose>
            <c:when test="${i == currentPage}">
              <span class="page-btn active">${i}</span>
            </c:when>
            <c:otherwise>
              <a href="?page=${i}" class="page-btn">${i}</a>
            </c:otherwise>
          </c:choose>
        </c:forEach>
        <c:if test="${currentPage < totalPages}">
          <a href="?page=${currentPage + 1}" class="page-btn">다음</a>
        </c:if>
      </div>
    </c:if>
  </div>
</body>
</html>