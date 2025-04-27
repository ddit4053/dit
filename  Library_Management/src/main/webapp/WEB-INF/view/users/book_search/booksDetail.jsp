<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <style>
    body {
      font-family: 'Arial', sans-serif;
      background-color: #f4f4f9;
      margin: 0;
      padding: 20px;
    }

    .container {
      max-width: 1000px;
      margin: 0 auto;
      background-color: #fff;
      padding: 20px;
      box-shadow: 0 2px 5px rgba(0,0,0,0.1);
      border-radius: 8px;
    }

    .book-detail {
      display: flex;
      flex-direction: row;
      justify-content: space-between;
      align-items: center;
    }

    .book-cover {
      /* 이미지를 원본 크기 그대로 표시할 수 있도록 설정 */
      display: inline-block;
      max-width: 100%;
      height: auto;
      margin-right: 20px;
    }

    .book-cover img {
      width: auto;  /* 이미지의 너비를 자동으로 설정 */
      height: auto; /* 이미지의 높이를 자동으로 설정 */
      max-width: 100%;  /* 이미지가 넘지 않도록 설정 */
      max-height: 500px; /* 이미지가 너무 커지지 않게 최대 높이 설정 */
      border-radius: 8px;
      box-shadow: 0 2px 10px rgba(0,0,0,0.1);
    }

    .book-info {
      flex: 1;
      padding-left: 30px;
      max-width: 600px;
    }

    .book-info h2 {
      font-size: 24px;
      color: #333;
      margin-bottom: 10px;
    }

    .book-info p {
      font-size: 16px;
      color: #555;
      margin-bottom: 10px;
    }

    .book-info strong {
      color: #333;
    }

    .button {
      display: inline-block;
      padding: 10px 20px;
      background-color: #4CAF50;
      color: white;
      text-decoration: none;
      border-radius: 5px;
      margin-top: 20px;
      font-size: 16px;
    }

    .button:hover {
      background-color: #45a049;
    }

    .back-link {
      margin-top: 20px;
      text-align: right;
    }

    .back-link a {
      text-decoration: none;
      color: #4CAF50;
      font-size: 16px;
    }

    .back-link a:hover {
      text-decoration: underline;
    }
  </style>
</head>
<body>

  <div class="container">
    <!-- 책 상세 정보 영역 -->
    <div class="book-detail">
      <!-- 책 표지 -->
      <div class="book-cover">
        <img src="${bookDetail.cover}" alt="책 표지">
      </div>

      <!-- 책 정보 -->
      <div class="book-info">
        <h2>${bookDetail.bookTitle}</h2>
        <p><strong>저자:</strong> ${bookDetail.author}</p>
        <p><strong>출판사:</strong> ${bookDetail.publisher}</p>
        <p><strong>출판일:</strong> ${bookDetail.pubdate}</p>
        <p><strong>ISBN:</strong> ${bookDetail.isbn}</p>
        <p><strong>카테고리 번호:</strong> ${bookDetail.categoryNo}</p>
        
        <!-- 책 상세 설명을 위한 링크 -->
        <div class="back-link">
          <a href="javascript:history.back()">목록으로 돌아가기</a>
        </div>
        
        <!-- 상세 정보 추가 버튼 (선택사항) -->
        <a href="${pageContext.request.contextPath}/books/otherDetails?bookNo=${bookDetail.bookNo}" class="button">더 많은 정보</a>
      </div>
    </div>
  </div>

</body>
</html>
