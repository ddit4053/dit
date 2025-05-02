<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/users/book_search/search.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/users/book_search/request.css">
<c:if test="${not empty requestSuccess}">
    <script>
        alert("도서 신청이 완료되었습니다.");
    </script>
</c:if>
<div class="search-container">
    <div class="search-box">
	    <div class="search-header">
	        <h2 class="search-title">도서 자료 검색</h2>
	        <button type="button" class="request-book-button" onclick="openRequestModal()">원하는 책이 없으신가요?</button>
	    </div>
        <div class="search-description">원하시는 도서를 검색하여 찾아보세요.</div>
        
        <form class="search-form" action="${pageContext.request.contextPath}/books/search/result" method="get">
            <div class="search-input-group">
                <select name="searchType" class="search-select">
          	      	<option value="book_title">제목</option>
                    <option value="author">저자</option>
                    <option value="publisher">출판사</option>
                    <option value="isbn">ISBN</option>
                </select>
                <input type="text" name="keyword" class="search-input" placeholder="검색어를 입력하세요" required>
                <button type="submit" class="search-button">
                    <i class="fas fa-search"></i> 검색
                </button>
            </div>
            
            <div class="search-options">
                <div class="search-option-group">
                    <label class="search-option-label">발행연도</label>
                    <select name="year" class="search-option-select">
                        <option value="">전체</option>
                        <c:forEach var="year" begin="2000" end="2025" step="1" varStatus="status">
                            <option value="${2025 - status.index}">${2025 - status.index}년</option>
                        </c:forEach>
                    </select>
                </div>

                
<!--                 <div class="search-option-group">
                    <label class="search-option-label">정렬방식</label>
                    <select name="sortBy" class="search-option-select">
                        <option value="newest">최신순</option>
                        <option value="popular">인기순</option>
                        <option value="title">제목순</option>
                    </select>
                </div> -->
            </div>
        </form>
    </div>
    
    <!-- 추천 도서 섹션 -->
    <div class="recommended-books">
        <h3 class="recommended-title">
            <i class="fas fa-star"></i> 추천 도서
            <a href="${pageContext.request.contextPath}/books/recommend" class="more-link">더 보기 <i class="fas fa-chevron-right"></i></a>
        </h3>
        
        <div class="book-grid">
            <!-- 첫번째 책 -->
            <div class="book-item">
                <div class="book-cover">
                    <img src="${pageContext.request.contextPath}/resource/images/book_covers/book1.jpg" alt="가위는 왜 가위처럼 생겼을까">
                    <div class="book-hover">
                        <a href="${pageContext.request.contextPath}/books/detail/1" class="book-detail-link">상세보기</a>
                    </div>
                </div>
                <div class="book-info">
                    <h4 class="book-title">가위는 왜 가위처럼 생겼을까</h4>
                    <p class="book-author">다니카 마유키, 유키 치요코</p>
                    <p class="book-publisher">오아시스</p>
                </div>
            </div>
            
            <!-- 두번째 책 -->
            <div class="book-item">
                <div class="book-cover">
                    <img src="${pageContext.request.contextPath}/resource/images/book_covers/book2.jpg" alt="위대한 관찰">
                    <div class="book-hover">
                        <a href="${pageContext.request.contextPath}/books/detail/2" class="book-detail-link">상세보기</a>
                    </div>
                </div>
                <div class="book-info">
                    <h4 class="book-title">위대한 관찰 : 곤충학자의겨 거부했던 자연주의자 장</h4>
                    <p class="book-author">조르주 박토르 클로즈</p>
                    <p class="book-publisher">H(휴머니스트출판그룹)</p>
                </div>
            </div>
            
            <!-- 세번째 책 -->
            <div class="book-item">
                <div class="book-cover">
                    <img src="${pageContext.request.contextPath}/resource/images/book_covers/book3.jpg" alt="기술은 세상을 어떻게 바꾸는가">
                    <div class="book-hover">
                        <a href="${pageContext.request.contextPath}/books/detail/3" class="book-detail-link">상세보기</a>
                    </div>
                </div>
                <div class="book-info">
                    <h4 class="book-title">기술은 세상을 어떻게 바꾸는가</h4>
                    <p class="book-author">이정동</p>
                    <p class="book-publisher">김영사</p>
                </div>
            </div>
            
            <!-- 네번째 책 -->
            <div class="book-item">
                <div class="book-cover">
                    <img src="${pageContext.request.contextPath}/resource/images/book_covers/book4.jpg" alt="일생에 한번은 헌법을 읽어라">
                    <div class="book-hover">
                        <a href="${pageContext.request.contextPath}/books/detail/4" class="book-detail-link">상세보기</a>
                    </div>
                </div>
                <div class="book-info">
                    <h4 class="book-title">일생에 한번은 헌법을 읽어라</h4>
                    <p class="book-author">이효원</p>
                    <p class="book-publisher">현대지성</p>
                </div>
            </div>
        </div>
    </div>
    
    <!-- 신착 도서 섹션 -->
    <div class="new-books">
        <h3 class="new-books-title">
            <i class="fas fa-book"></i> 신착 도서
            <a href="${pageContext.request.contextPath}/books/new" class="more-link">더 보기 <i class="fas fa-chevron-right"></i></a>
        </h3>
        
        <div class="book-slider">
            <div class="book-list">
                <!-- 첫번째 책 -->
                <div class="book-slide-item">
                    <div class="book-cover-small">
                        <img src="${pageContext.request.contextPath}/resource/images/book_covers/book5.jpg" alt="완벽이라는 중독">
                    </div>
                    <div class="book-info-small">
                        <h5 class="book-title-small">완벽이라는 중독</h5>
                        <p class="book-author-small">토머스 커린</p>
                    </div>
                </div>
                
                <!-- 두번째 책 -->
                <div class="book-slide-item">
                    <div class="book-cover-small">
                        <img src="${pageContext.request.contextPath}/resource/images/book_covers/book6.jpg" alt="명상의 기술">
                    </div>
                    <div class="book-info-small">
                        <h5 class="book-title-small">명상의 기술</h5>
                        <p class="book-author-small">이윤재</p>
                    </div>
                </div>
                
                <!-- 세번째 책 -->
                <div class="book-slide-item">
                    <div class="book-cover-small">
                        <img src="${pageContext.request.contextPath}/resource/images/book_covers/book7.jpg" alt="알고리즘 사고법">
                    </div>
                    <div class="book-info-small">
                        <h5 class="book-title-small">알고리즘 사고법</h5>
                        <p class="book-author-small">김인수</p>
                    </div>
                </div>
                
                <!-- 네번째 책 -->
                <div class="book-slide-item">
                    <div class="book-cover-small">
                        <img src="${pageContext.request.contextPath}/resource/images/book_covers/book8.jpg" alt="푸코의 진실게임">
                    </div>
                    <div class="book-info-small">
                        <h5 class="book-title-small">푸코의 진실게임</h5>
                        <p class="book-author-small">심세광</p>
                    </div>
                </div>
                
                <!-- 다섯번째 책 -->
                <div class="book-slide-item">
                    <div class="book-cover-small">
                        <img src="${pageContext.request.contextPath}/resource/images/book_covers/book9.jpg" alt="철학의 즐거움">
                    </div>
                    <div class="book-info-small">
                        <h5 class="book-title-small">철학의 즐거움</h5>
                        <p class="book-author-small">김용석</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 신청 폼 모달 -->
<div id="requestModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeRequestModal()">&times;</span>
        <h3>도서 신청하기</h3>
        <form action="${pageContext.request.contextPath}/books/bookRequest" method="post">
            <label>도서 제목</label>
            <input type="text" name="title" required>

            <label>저자</label>
            <input type="text" name="author" required>

            <label>출판사</label>
            <input type="text" name="publisher" required>

            <label>ISBN</label>
            <input type="text" name="isbn" required>

            <label>기타 요청 사항</label>
            <textarea name="notes" rows="3"></textarea>
			
            <button type="submit" class="submit-button">신청하기</button>
        </form>
    </div>
</div>

<script>
    function openRequestModal() {
    	const userNo = ${sessionScope.userNo != null ? sessionScope.userNo : 'null'}
    	
    	if (userNo == null) {
            alert("로그인 후 이용할 수 있습니다.");
            return;
        }
        document.getElementById("requestModal").style.display = "block";
    }

    function closeRequestModal() {
        document.getElementById("requestModal").style.display = "none";
    }

    window.onclick = function(event) {
        const modal = document.getElementById("requestModal");
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
</script>

<!-- Font Awesome CDN 추가 -->

<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/js/all.min.js"></script>