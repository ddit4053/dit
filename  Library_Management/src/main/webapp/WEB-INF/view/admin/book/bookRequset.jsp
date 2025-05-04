<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="${pageContext.request.contextPath}/resource/js/jquery-3.7.1.js"></script>
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
	  
	.approve-btn {
	  padding: 10px 18px;
	  background-color: #5d4037;
	  color: white;
	  border: none;
	  border-radius: 6px;
	  font-size: 14px;
	  cursor: pointer;
	  transition: background-color 0.3s, transform 0.2s;
	}
	
	.approve-btn:hover {
	  background-color: #8d6e63;
	  transform: translateY(-2px);
	}
	
	.approve-btn:active {
	  background-color: #3e8e41;
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
          
          
		<input type="hidden" id="reqBookNo-${book.reqBookNo}" value="${book.reqBookNo}" />
		<c:if test="${book.reqBookStatus eq '접수'}">
  			<button type="button" class="approve-btn" onclick="approveBook(${book.reqBookNo},${book.reqIsbn})">신청 승인</button>
		</c:if>


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
<script>
function approveBook(reqBookNo,reqIsbn) {
	  console.log(reqBookNo);
	  console.log(reqIsbn);
	  if (!confirm("정말로 신청 도서를 승인하시겠습니까?")) {
	    return;
	  }

	  $.ajax({
	    url: "approveRequest.do",
	    method: "GET",
	    data: { reqBookNo: reqBookNo , reqIsbn: reqIsbn},
	    dataType: "json",
	    success: function(data) {
	      if (data.success) {
	        alert("신청이 승인되었습니다.");
	        location.reload(); // 또는 DOM 업데이트
	      } else {
	        alert("승인에 실패했습니다: " + data.message);
	      }
	    },
	    error: function(xhr, status, error) {
	      console.error(xhr.status);
	      alert("요청 중 오류가 발생했습니다.");
	    }
	  });
	}
</script>