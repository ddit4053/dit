<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="${pageContext.request.contextPath}/resource/js/jquery-3.7.1.js"></script>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>알라딘 API 테스트</title>
  <style>
    body {
      font-family: 'Arial', sans-serif;
      margin: 0;
      padding: 0;
      background-color: #f4f4f9;
      color: #333;
    }
    header {
      background-color: #4CAF50;
      color: white;
      text-align: center;
      padding: 20px;
    }
    h1 {
      margin: 0;
      font-size: 24px;
    }
    .container {
      width: 80%;
      margin: 20px auto;
      padding: 20px;
      background-color: white;
      border-radius: 8px;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }
    #query {
      width: calc(100% - 140px);
      padding: 10px;
      margin-right: 10px;
      font-size: 16px;
      border: 1px solid #ddd;
      border-radius: 4px;
    }
    #searchBtn {
      padding: 10px 20px;
      background-color: #4CAF50;
      color: white;
      border: none;
      font-size: 16px;
      cursor: pointer;
      border-radius: 4px;
    }
    #searchBtn:hover {
      background-color: #45a049;
    }
    #results {
      list-style: none;
      padding: 0;
    }
    #results li {
      background-color: #f9f9f9;
      margin: 8px 0;
      padding: 10px;
      border-radius: 4px;
      box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
    }
    #results li strong {
      color: #4CAF50;
    }
    .no-results {
      text-align: center;
      color: #888;
    }
  </style>
</head>
<body>
  <header>
    <h1>알라딘 도서 검색</h1>
  </header>
  <div class="container">
    <input type="text" id="query" placeholder="책 제목을 입력하세요" value="자바스크립트">
    <button id="searchBtn">검색</button>
    <ul id="results"></ul>
  </div>

  <script>
    $('#searchBtn').click(function () {
      const query = $('#query').val();

      $.ajax({
        url: '${pageContext.request.contextPath}/books/bookSearch.do',
        method: 'GET',
        data: { query: query },
        success: function (data) {
          console.log(data);

          $('#results').empty(); // 이전 결과 지우기
          if (data.item && data.item.length > 0) {
            data.item.forEach(book => {
              console.log(book);
              $('#results').append(`<li> <img src=\${book.cover}> <strong>\${book.title}</strong> - \${book.author}</li>`);
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
    });
  </script>
  
</body>
</html>
