<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="${pageContext.request.contextPath}/resource/js/jquery-3.7.1.js"></script>
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
      display: inline-block;
      max-width: 100%;
      height: auto;
      margin-right: 20px;
    }

    .book-cover img {
      width: auto;
      height: auto;
      max-width: 100%;
      max-height: 500px;
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

    .subtitle {
      font-size: 16px;
      color: gray;
    }
  </style>
  <script>
  	function insertReview(){
  	  const rating = $('#rating').val();
      const reviewContent = $('#reviewContent').val();
      const bookNo = $('#bookNo').val();
      
      if (!rating || !reviewContent) {
		alert("별점과 리뷰를 모두 작성해주세요")
		return;
      }
      
      $.ajax({
        url: "detail/reviewInsert",
        type:'post',
        data:{
          rating: rating,
          revContent: reviewContent,
          bookNo: bookNo
          //userNo: userNo
        },
        success: function(res){
          alert("리뷰등록");
          $('#rating').val('');
          $('#reviewContent').val('');
          loadReviewList();
        },
        error: function(xhr) {
        }
      })
  	}
  
    function loadReviewList(){
      const bookNo = $('#bookNo').val();
      
      $.ajax({
        url: "detail/reviewList?bookNo=" + bookNo,
        type: 'get',
        dataType: 'json',
        success : function(list){
        	 let html = '';
             if(list.length == 0){
                 html = "<p>등록된 리뷰가 없습니다.</p>";
             } else {
                 list.forEach(function(item) {
                     html += `
                         <div style="margin-bottom:15px;">
                             <strong>별점: \${item.rating}점</strong><br>
                             <p>내용: \${item.revContent}</p>
                             <small>작성일 : \${item.revDate}</small>
                             <small>작성자 : \${item.userNo}</small>
                             <hr>
                         </div>
                     `;
                 });
             }
             $('#reviewList').html(html);
        },
        error: function(xhr){
        }
      })
    }

    // 페이지 로딩시 리뷰 불러오기기
    $(document).ready(function() {
    loadReviewList();
    });
  </script>
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
        <h2>
          <c:out value="${fn:split(bookDetail.bookTitle, '-')[0]}"/> 
          <span class="subtitle">
            <c:out value="${fn:split(bookDetail.bookTitle, '-')[1]}"/>
          </span>
        </h2>
        <p><strong>저자:</strong> ${bookDetail.author}</p>
        <p><strong>출판사:</strong> ${bookDetail.publisher}</p>
        <p><strong>출판일:</strong> ${bookDetail.pubdate}</p>
        <p><strong>ISBN:</strong> ${bookDetail.isbn}</p>
        <p><strong>카테고리 번호:</strong> ${bookDetail.categoryNo}</p>
        
        <!-- 책 상세 설명을 위한 링크 -->
        <div class="back-link">
          <a href="javascript:history.back()">목록으로 돌아가기</a>
        </div>
        
        <!-- 도서리뷰 등록 밑 출력 -->
        <div class="book-review">
			    <h3>도서 리뷰 작성</h3>    
          <form id="reviewForm">
            <label for="rating">별점 (1~5):</label>
            <select name="rating" id="rating" required>
              <option value="">선텍</option>
              <option value="1">1점</option>
              <option value="2">2점</option>
              <option value="3">3점</option>
              <option value="4">4점</option>
              <option value="5">5점</option>
            </select><br><br>
            
            <label for="reviewContent">리뷰내용</label><br>
            <textarea name="reviewContent" id="reviewContent" rows="4" cols="30" required></textarea>
            <input type="hidden" name="bookNo" id="bookNo" value="${bookDetail.bookNo}">
            <!-- <input type="hidden" name="userId" id="userId" value="${user.userId}"> 로그인 구현시 보여줌 -->
			    <button type="button" onclick="insertReview()">등록</button>         
          </form>    

          <hr>

          <!-- 등록된 리뷰 -->
           <h3>등록된 리뷰</h3>
           <div id="reviewList">

           </div>
        </div>
      </div>
    </div>
  </div>

</body>
</html>
