<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <script src="${pageContext.request.contextPath}/resource/js/jquery-3.7.1.js"></script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
 <header>
    <h1>알라딘 도서 리스트</h1>
  </header>
  <div class="container">
<!--     <input type="text" id="query" placeholder="책 제목을 입력하세요" value="자바스크립트">
    <button id="searchBtn">검색</button> -->
    <ul id="results"></ul>
  </div>
<script type="text/javascript">

	$.ajax({
		url: '${pageContext.request.contextPath}/books/bookList.do',
	    method: 'GET',
	    dataType: 'json',
	    success: function (data) {
	      console.log(data);
		
	      $('#results').empty(); // 이전 결과 지우기
	      if (data && data.length > 0) {
	        data.forEach(book => {
	          console.log(book);
	          $('#results').append(`<li> <img src=\${book.cover}> <strong>\${book.title}</strong> <p>저자- \${book.author} 출판사-\${book.publisher} isbn-\${book.isbn} 발행년도-\${book.pubDate} 
	          카테고리id - \${book.categoryId}</p></li>`);
	        });
	      } else {
	        $('#results').append('<li class="no-results">검색 결과가 없습니다.</li>');
	      }
	    },
	    error: function (xhr) {
	      alert(xhr.status);
	      alert('API 요청 중 에러가 발생했습니다.');
	    }
	  });
</script>
</body>
</html>