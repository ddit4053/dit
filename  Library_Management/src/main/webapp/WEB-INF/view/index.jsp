<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="header.jsp" />
<jsp:include page="nav.jsp" />

<main class="main-content">
    <!-- 배너 섹션 -->
    <section class="banner-section">
        <div class="banner-slider" id="bannerSlider">
            <div class="slide active">
                <img src="${pageContext.request.contextPath}/resource/img/banner1.svg" alt="도서관 배너 1">
                <div class="slide-content">
                    <%-- <h2>도서관에 오신 것을 환영합니다</h2>
                    <p>다양한 도서와 함께 지식의 세계로 여행하세요</p> --%>
                </div>
            </div>
            <div class="slide">
                <img src="${pageContext.request.contextPath}/resource/img/banner2.svg" alt="도서관 배너 2">
                <div class="slide-content">
                    <%-- <h2>온라인 도서 예약</h2>
                    <p>집에서 편하게 도서를 예약하고 대출하세요</p> --%>
                </div>
            </div>
            <div class="slide">
                <img src="${pageContext.request.contextPath}/resource/img/banner3.svg" alt="도서관 배너 3">
                <div class="slide-content">
                    <%-- <h2>문화 프로그램 및 이벤트</h2>
                    <p>다양한 문화 프로그램과 이벤트에 참여하세요</p> --%>
                </div>
            </div>
            <button class="prev-btn" id="prevSlide">&#10094;</button>
            <button class="next-btn" id="nextSlide">&#10095;</button>
            <div class="dots-container" id="dotsContainer"></div>
        </div>
    </section>
	
	<!-- 메인 검색 섹션 -->
	<section class="main-search-section">
	    <div class="main-search-container">
	        <h2>자료 검색</h2>
	        <div class="main-search-box">
	            <input type="text" id="mainSearchInput" placeholder="찾으시는 도서명, 저자, 출판사를 입력하세요">
	            <button id="mainSearchButton">검색</button>
	        </div>
	        <div class="search-options">
	            <span>인기 검색어:</span>
	            <a href="#" class="popular-keyword">소설</a>
	            <a href="#" class="popular-keyword">자기계발</a>
	            <a href="#" class="popular-keyword">IT</a>
	            <a href="#" class="popular-keyword">역사</a>
	            <a href="#" class="popular-keyword">과학</a>
	        </div>
	    </div>
	</section>
	
    <!-- 신착 도서 섹션 -->
    <section class="new-books-section">
        <div class="section-header">
            <h2>신착 도서</h2>
            <a href="${pageContext.request.contextPath}/books/new" class="view-more">더보기</a>
        </div>
        <div class="books-container" id="newBooksContainer">
            <!-- 서블릿에서 데이터를 가져와 JavaScript로 채울 예정 -->
            <div class="loading">도서 정보를 불러오는 중...</div>
        </div>
    </section>

    <!-- 추천 도서 섹션 -->
    <section class="recommended-books-section">
        <div class="section-header">
            <h2>추천 도서</h2>
            <a href="${pageContext.request.contextPath}/books/recommend" class="view-more">더보기</a>
        </div>
        <div class="books-container" id="recommendedBooksContainer">
            <!-- 서블릿에서 데이터를 가져와 JavaScript로 채울 예정 -->
            <div class="loading">도서 정보를 불러오는 중...</div>
        </div>
    </section>

    <!-- 공지사항 및 이벤트 섹션 -->
    <section class="notice-section">
        <div class="half-section">
            <div class="section-header">
                <h2>인기 토론</h2>
                <a href="${pageContext.request.contextPath}/board/community/discussion/list" class="view-more">더보기</a>
            </div>
            <ul class="event-list" id="eventList">
                <!-- 서블릿에서 데이터를 가져와 JavaScript로 채울 예정 -->
                <li class="loading">토론 정보를 불러오는 중...</li>
            </ul>
        </div>
        <div class="half-section">
            <div class="section-header">
                <h2>공지사항</h2>
                <a href="${pageContext.request.contextPath}/board/info/notice" class="view-more">더보기</a>
            </div>
            <ul class="notice-list" id="noticeList">
                <!-- 서블릿에서 데이터를 가져와 JavaScript로 채울 예정 -->
                <li class="loading">공지사항을 불러오는 중...</li>
            </ul>
        </div>
    </section>

    <!-- 열람실 현황 섹션 -->
    <section class="reading-room-section">
        <div class="section-header">
            <h2>열람실 현황</h2>
            <a href="${pageContext.request.contextPath}/reading" class="view-more">예약하기</a>
        </div>
        <div class="room-status" id="roomStatus">
            <!-- 서블릿에서 데이터를 가져와 JavaScript로 채울 예정 -->
            <div class="loading">열람실 정보를 불러오는 중...</div>
        </div>
    </section>
</main>

<script src="${pageContext.request.contextPath}/resource/js/main.js"></script>
<jsp:include page="footer.jsp" />