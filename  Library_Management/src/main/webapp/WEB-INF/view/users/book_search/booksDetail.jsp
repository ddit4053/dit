<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="${pageContext.request.contextPath}/resource/js/jquery-3.7.1.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<script>
  const loggedInUserNo = ${sessionScope.userNo != null ? sessionScope.userNo : 'null'};
  const userNo = ${sessionScope.userNo != null ? sessionScope.userNo : 'null'};
</script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/users/book_search/booksDetail.css">
<script src="${pageContext.request.contextPath}/resource/js/users/book_search/booksDetail.js"></script>
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
					<c:out value="${fn:split(bookDetail.bookTitle, '-')[0]}" />
					<span class="subtitle"> <c:out
							value="${fn:split(bookDetail.bookTitle, '-')[1]}" />
					</span>
				</h2>
				<p>
					<strong>저자:</strong> ${bookDetail.author}
				</p>
				<p>
					<strong>출판사:</strong> ${bookDetail.publisher}
				</p>
				<p>
					<strong>출판일:</strong> ${bookDetail.pubdate}
				</p>
				<p>
					<strong>ISBN:</strong> ${bookDetail.isbn}
				</p>
				<p>
					<strong>카테고리 번호:</strong> ${bookDetail.categoryNo}
				</p>


				<div style="margin-top: 20px; text-align: right;">
					<button id="BookFavorite" class="favorite-btn"
						value="${bookDetail.bookNo}" onclick="BookFavorite()">
						<i class="fa-regular fa-heart"></i>
					</button>
					<button class="button" onclick="requestLoan()"
						value="${bookDetail.bookNo}" id="BookLoan">대출 신청</button>
				</div>
				<!-- 책 상세 설명을 위한 링크 -->


				<div id="reserveTableContainer"
					style="display: flex; flex-direction: row-reverse; gap: 10px"></div>
				<div class="back-link">
					<a href="javascript:history.back()">목록으로 돌아가기</a> <br> <a
						onclick="reserveList()" value="${bookDetail.bookNo}"
						id="reserveList">예약리스트 확인</a>

				</div>

				<!-- 도서리뷰 등록 밑 출력 -->
				<div class="book-review">
					<h3>도서 리뷰 작성</h3>
					<form id="reviewForm">
						<div class="rating">
							<span class="star" data-value="1">&#9733;</span>
							<span class="star" data-value="2">&#9733;</span>
							<span class="star"data-value="3">&#9733;</span>
							<span class="star" data-value="4">&#9733;</span>
							<span class="star" data-value="5">&#9733;</span>
						</div>
						<br> <label for="reviewContent">리뷰내용</label><br>
						<textarea name="reviewContent" id="reviewContent" rows="4"
							cols="30" required
							style="width: 400px; height: 80px; resize: none"></textarea>
						<input type="hidden" name="bookNo" id="bookNo" value="${bookDetail.bookNo}"> 
						<input type="hidden" id="rating" name="rating" value="0">
						<button type="button" onclick="insertReview()" id="reviewBtn" class="btn btn-submit">등록</button>
					</form>

					<hr>

					<!-- 등록된 리뷰 -->
					<h3>등록된 리뷰</h3>
					<div id="reviewList"></div>
				</div>
			</div>
		</div>
	</div>


	<div id="editReviewModal"
	    style="display: none; position: fixed; top: 50%; left: 50%; transform: translate(-50%, -50%); background: white; padding: 20px; border: 1px solid #ccc; border-radius: 8px; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2); z-index: 9999;">
	    
	    <h3>리뷰 수정</h3>
	    <input type="hidden" id="editReviewNo">
	    
	    <!-- 별점 선택 영역 -->
	    <label for="editRating">별점 (1~5):</label>
	    <div class="rating">
	        <span class="star" data-value="1">&#9733;</span>
	        <span class="star" data-value="2">&#9733;</span>
	        <span class="star" data-value="3">&#9733;</span>
	        <span class="star" data-value="4">&#9733;</span>
	        <span class="star" data-value="5">&#9733;</span>
	    </div><br><br>
	    
	    <label for="editRevContent">리뷰내용</label><br>
	    <textarea name="editRevContent" id="editRevContent" rows="4" cols="30" required
	              style="width: 400px; height: 80px; resize: none"></textarea><br><br>
	    
	    <button onclick="updateReview()" class="btn btn-edit">수정 완료</button>
	    <button onclick="closeModal()" class="btn btn-delete">취소</button>
	</div>

</body>
</html>
