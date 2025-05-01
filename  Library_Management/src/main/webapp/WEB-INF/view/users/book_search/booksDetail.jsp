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
		  justify-content: flex-start; /* 왼쪽 정렬 */
		  align-items: flex-start; /* 상단 정렬 */
		  gap: 20px; /* 책 표지와 정보 사이 간격 */
		}
		
		.book-cover {
		  flex-shrink: 0; /* 이미지 크기가 줄어들지 않도록 */
		  max-width: 150px; /* 책 표지의 최대 크기 */
		  height: auto;
		}
		
		.book-cover img {
		  width: 100%;
		  height: auto;
		  border-radius: 8px;
		  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
		}
		
		.book-info {
		  flex: 1;
		  padding-left: 30px;
		  max-width: 600px; /* 책 정보 영역의 최대 너비 */
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
		
		.reserve-table {
		  width: 100%;
		  border-collapse: collapse;
		  margin-top: 10px;
		  background-color: #f9f9f9;
		  font-size: 14px;
		}
		
		.reserve-table th {
		  color: white;
		  border: 1px solid #ccc;
		  padding: 10px;
		  text-align: center;
		}
		
		.reserve-table td {
		  border: 1px solid #ccc;
		  padding: 10px;
		  text-align: center;
		}
		
		.reserve-table th {
		  background-color: #8d6e63;
		  font-weight: bold;
		}
		
		.reserve-table tr:hover {
		  background-color: #f1f1f1;
		}

  </style>
  <script>
  	const loggedInUserNo = ${sessionScope.userNo != null ? sessionScope.userNo : 'null'};

  
  	function insertReview(){
  	  if (loggedInUserNo == null) {
          alert("로그인 후 리뷰를 등록할 수 있습니다.");
          return;
        }
  	  
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
                		      <div style="display: flex; align-items: center; gap: 10px;">
                		        <strong>별점: \${item.rating}점</strong>
                		  `;
                		  
                		  if (loggedInUserNo !== null && loggedInUserNo === item.userNo) {
                		    html += `
                		    	 <button class="editReview" data-reviewno="\${item.revNo}">수정</button>
                		        <button class="deleteReview" data-reviewno="\${item.revNo}">삭제</button>
                		    `;
                		  }
                		  
                		  html += `
                		      </div> <!-- flex 끝 -->
                		      <p>내용: \${item.revContent}</p>
                		      <small>작성일 : \${item.revDate}</small><br>
                		      <small>작성자 : \${item.name}</small>
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
    
    //모달창 띄우기
    function openModal(reviewNo,rating,revContent) {
    	 $('#editReviewNo').val(reviewNo);
    	  $('#editRating').val(rating);
    	  $('#editRevContent').val(revContent);
    	  $('#editReviewModal').show();
    }
    
    //모달창 닫기
    function closeModal() {
    	  $('#editReviewModal').hide();
   	}
    
    //리뷰 수정버튼 이벤트
    $(document).on('click','.editReview',function(){
   	 	const parentDiv = $(this).closest('div');
	   	const ratingText = parentDiv.find('strong').text(); // "별점: 5점" 이런식
	    const rating = ratingText.match(/\d+/)[0]; // 별점 숫자만 추출
	    const revContent = parentDiv.find('p').text().replace('내용: ', '');
	    const reviewNo = $(this).data('reviewno');
	    
	    console.log("수정 눌렀을때 no : "+reviewNo)
	    openModal(reviewNo, rating, revContent);
    	
    })
    
    //리뷰 수정
    function updateReview() {
    	  const reviewNo = $('#editReviewNo').val();
    	  const rating = $('#editRating').val();
    	  const revContent = $('#editRevContent').val();
    	  
    	  if (!rating || !revContent) {
    		    alert("별점과 리뷰 내용을 모두 입력해주세요.");
    		    return;
   		  }
    	  
    	  $.ajax({
    		  url: "detail/reviewUpdate",
    		  type: "post",
    		  data: {
    			  revNo: reviewNo,
    			  rating: rating,
    			  revContent: revContent
    		  },
    		  success: function(res){
    			  alert("리뷰가 수정되었습니다.");
    			  closeModal();
    			  loadReviewList();
    		  },
    		  error: function(xhr){
    			  alert("수정 실패")
    		  }
    	  });
    }
    
    //리뷰삭제 
    $(document).on('click','.deleteReview',function(){
    	
    	const revNo = $(this).data('reviewno');
    	$.ajax({
    		url : "detail/reviewDelete",
    		type : "get",
    		data : {
    			revNo: revNo
    		},
    		success: function(res){
    			alert("리뷰 삭제");
  			  	loadReviewList();
    		}
    	})
    })
    
    function BookFavorite(){
    	const userNo = ${sessionScope.userNo != null ? sessionScope.userNo : 'null'}
    	const bookNo = $('#BookFavorite').val();
    	
    	if (userNo == null) {
            alert("로그인 후 이용할 수 있습니다.");
            return;
        }
    	
    	
     	$.ajax({
    		url: "detail/favoriteInsert",
    		type: "get",
    		data : {
    			userNo : userNo,
    			bookNo : bookNo
    		},
    		success : function(res){
    			if (res === "insert") {
                    alert("관심 도서 등록 완료!");
                    $('#BookFavorite')
                    .text("관심 도서 취소")
                    .css("background-color", "red")
                    .data("favorited", true);
                } else if (res === "delete") {
                    alert("관심 도서가 삭제되었습니다.");
                    $('#BookFavorite')
                    .text("관심 도서 추가")
                    .css("background-color", "#4CAF50")
                    .data("favorited", false);
                } else {
                    alert("처리 실패");
                }
    		},
    		error: function(xhr){
    			alert(xhr.status);
    		}
    	}) 
    }
    
   function requestLoan() {
   	  const userNo = ${sessionScope.userNo != null ? sessionScope.userNo : 'null'};
   	  const bookNo = $('#BookLoan').val();
   	  
   	  if (userNo == null) {
   		    alert("로그인 후 대출 신청이 가능합니다.");
   		    return;
  		  }
   	  
   	  $.ajax({
   		    url: "detail/loanInsert", 
   		    type: "post",
   		    data: {
   		      userNo: userNo,
   		      bookNo: bookNo
   		    },
   		    success: function(res) {
   		      if (res === "success") {
   		        alert("대출 신청이 완료되었습니다.");
   		      } else if (res === "notAvailable") {
   		        alert("대여 가능한 도서가 없습니다.");
   		      } else if (res === "alreadyLoaned") {
   		    	alert("이미 대출중인 도서입니다.");
   		      } else if (res === "suspended") {
   		    	alert("정지 상태에선 대출할 수 없습니다.");
   		      } else {
   		        alert("대출 신청 실패");
   		      }
   		    },
   		    error: function(xhr) {
   		      alert("오류 발생: " + xhr.status);
   		    }
   		  });
  		}
   
   function loadFavorite() {
	   
 	const userNo = ${sessionScope.userNo != null ? sessionScope.userNo : 'null'}
   	const bookNo = $('#BookFavorite').val();
	   
   	//관심도서 체크후 있으면 버튼 변경
   	$.ajax({
   		url : "detail/favoriteInsert",
   		type: 'post',
   		data: {
   			userNo : userNo,
   			bookNo : bookNo
   		},
   		success : function(res){
   			if (res === "exists") {
                   $('#BookFavorite')
                       .text("관심 도서 취소")
                       .css("background-color", "red")
                       .data("favorited", true);
               } else {
                   $('#BookFavorite')
                       .text("관심 도서 추가")
                       .css("background-color", "#4CAF50")
                       .data("favorited", false);
               }
   		},
   		error : function(xhr){
   			alert(xhr.status);
   		}
   	})
   }
   
   function requestReservation() {
		
		const userNo = ${sessionScope.userNo != null ? sessionScope.userNo : 'null'}
		const bookNo = $('#bookNo').val();
		
		$.ajax({
	   		url : "detail/reserInsert",
	   		type: 'get',
	   		data: {
	   			userNo : userNo,
	   			bookNo : bookNo
	   		},
	   		success : function(res){
	   			if (res === "reserveInsert") {
	   				alert("예약성공");
	   				$('#BookLoan')
				    .text("예약중"); 
	                  
               } else if (res === "alreadyreserve"){
            	   alert("이미 예약중입니다.");
					
            	   
               } else {
            	   alert("예약실패");
               }
	   		},
	   		error : function(xhr){
	   			alert(xhr.status);
	   		}
	   	})
	}
    
   function loadReservation() {
	   const userNo = ${sessionScope.userNo != null ? sessionScope.userNo : 'null'};
	   const bookNo = $('#bookNo').val();
	   
	   	$.ajax({
	   		url : "detail/reserCheck",
	   		type: 'get',
	   		data: {
	   			userNo : userNo,
	   			bookNo : bookNo
	   		},
	   		success : function(res){
					if (res ==="reservation"){
						$('#BookLoan')
					    .text("예약신청")
					    .css("background-color", "#4CAF50")
					    .removeAttr('onclick')
					    .on('click', function(){
					    	requestReservation();
					    }); // 함수 이름만 전달
					}else if(res ==="alreadyreserve"){
						$('#BookLoan')
					    .text("예약중")
					    .css("background-color", "#4CAF50")
					    .removeAttr('onclick')
					    .on('click', function(){
					    	requestReservation();
					    }); // 함수 이름만 전달
					}
	   		},
	   		error : function(xhr){
	   			alert(xhr.status);
	   		}
	   	})
   }
   
   let isReserveVisible = false; // 예약리스트 보이게
   
   function reserveList(){
	   const userNo = ${sessionScope.userNo != null ? sessionScope.userNo : 'null'};
	   const bookNo = $('#bookNo').val();
	   
	   	if (userNo == null) {
	        alert("로그인 후 이용할 수 있습니다.");
	        return;
	    }
		if (isReserveVisible) {
			$("#reserveTableContainer").hide();
			isReserveVisible = false;
			return;
		}
	   
	   	$.ajax({
	   		url : "detail/reserCheck",
	   		type: 'post',
	   		data: {
	   			userNo : userNo,
	   			bookNo : bookNo
	   		},
	   		success: function(res) {
	   		    let html = '<table class="reserve-table" border="1">';
	   		    html += '<tr>';
	   		    html += '<th>예약 번호</th>';
	   		    html += '<th>예약 일자</th>';
	   		    html += '<th>상태</th>';
	   		    html += '<th>사용자 이름</th>';
	   		    html += '<th>책 제목</th>';
	   		    html += '</tr>';

	   		    if (res.length === 0) {
	   		        html += '<tr><td colspan="5">예약 정보가 없습니다.</td></tr>';
	   		    } else {
	   		        res.forEach(function(item) {
	   		            html += '<tr>';
	   		            html += `<td>\${item.reserveNo}</td>`;
	   		            html += `<td>\${item.reserveDate}</td>`;
	   		            html += `<td>\${item.reserveStatus}</td>`;
	   		            html += `<td>\${item.bookNo}</td>`;
	   		            html += `<td>\${item.userNo}</td>`;
	   		            html += '</tr>';
	   		        });

	   		    }

	   		    html += '</table>';

				$('#reserveTableContainer').html(html).show();
				isReserveVisible = true;
	   		},
	   		error : function(xhr){
	   			alert(xhr.status);
	   		}
	   	})
	   
   }

    
    $(document).ready(function() {
    loadReviewList(); // 리뷰 불러오기
    
    if(${sessionScope.userNo != null}){
    	loadReservation();// 대출인지 예약인지 체크하기
    	loadFavorite(); // 관심도서 체크하기
    }
    
    
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
        
        
        <div style="margin-top: 20px; text-align: right;">
          <button class="button" onclick="requestLoan()" value="${bookDetail.bookNo}" id="BookLoan">대출 신청</button>
		  <button class="button" onclick="BookFavorite()" value="${bookDetail.bookNo}" id="BookFavorite"}>관심 도서 추가</button>
		</div>
        <!-- 책 상세 설명을 위한 링크 -->
       
        	
      	<div id="reserveTableContainer" style="display: flex; flex-direction: row-reverse; gap: 10px"></div>
        <div class="back-link">
          <a href="javascript:history.back()">목록으로 돌아가기</a> <br>
          <a onclick="reserveList()" value="${bookDetail.bookNo}" id="reserveList">예약리스트 확인</a>
          
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
            <textarea name="reviewContent" id="reviewContent" rows="4" cols="30" required style="width: 482px; height: 80px; resize: none"></textarea>
            <input type="hidden" name="bookNo" id="bookNo" value="${bookDetail.bookNo}">
            <!-- <input type="hidden" name="userId" id="userId" value="${user.userId}"> 로그인 구현시 보여줌 -->
			    <button type="button" onclick="insertReview()" id="reviewBtn">등록</button>         
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


	<div id="editReviewModal" style="display:none; position:fixed; top:50%; left:50%; transform:translate(-50%, -50%);
    background:white; padding:20px; border:1px solid #ccc; border-radius:8px; box-shadow:0 2px 10px rgba(0,0,0,0.2); z-index:9999;">
    	
    	<h3>리뷰 수정</h3>
    	<input type="hidden" id="editReviewNo">
    	
    	<label for="editRating">별점 (1~5):</label>
    	<select id="editRating">
    		<option value="1">1점</option>
    		<option value="2">2점</option>
    		<option value="3">3점</option>
    		<option value="4">4점</option>
    		<option value="5">5점</option>
    	
    	</select><br><br>
    	
    	
    	 <label for="editRevContent">리뷰내용</label><br>
         <textarea name="editRevContent" id="editRevContent" rows="4" cols="30"></textarea><br><br>
         
            
		    <button onclick="updateReview()">수정 완료</button>         
		    <button onclick="closeModal()">취소</button>         
    </div>
</body>
</html>
