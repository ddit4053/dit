<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/users/book_search/search.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/users/book_search/request.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/users/book_search/search1.css">
<script src="${pageContext.request.contextPath}/resource/js/users/book_search/search.js"></script>
<c:if test="${not empty requestSuccess}">
    <script>
        alert("도서 신청이 완료되었습니다.");
    </script>
</c:if>

<c:if test="${requestSuccess == false}">
    <script>
        alert("도서 신청에 실패하였습니다. 다시 시도해주세요.");
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
                <input type="text" name="keyword" class="search-input" placeholder="검색어를 입력하세요">
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
    
    <section class="new-books-section">
        <div class="section-header">
            <h2>신착 도서</h2>
            <a href="${pageContext.request.contextPath}/books/new" class="view-more">더보기</a>
        </div>
        <div class="books-container" id="newBooksContainer">
            <!-- 서블릿에서 데이터를 가져와 JavaScript로 채울 예정 -->
            <div class="loading">도서 정보를 불러오는 중...</div>
        </div>

		<div class="book-slider-controls">
		    <button id="prevBtn">이전</button>
		    <button id="nextBtn">다음</button>
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

<script type="text/javascript">

let allBooks = [];
let currentIndex = 0;
const booksPerPage = 4;

// 공통 fetch 함수 정의
function fetchData(url, callback) {
    fetch(url)
        .then(response => response.json())
        .then(data => callback(null, data))
        .catch(error => callback(error, null));
}

document.addEventListener("DOMContentLoaded", function () {
    
    // 신착 도서 불러오기
    function fetchNewBooks() {
        fetchData(`${pageContext.request.contextPath}/api/books/new`, function (err, data) {
            const container = document.getElementById("newBooksContainer");

            if (err || !data || data.length === 0) {
                container.innerHTML = '<div class="error">신착 도서가 없습니다.</div>';
                return;
            }

            allBooks = data;
            currentIndex = 0;
            renderBooks();
        });
    }

    // 신착 도서 렌더링
    function renderBooks() {
        const container = document.getElementById("newBooksContainer");
        container.innerHTML = "";

        const end = Math.min(currentIndex + booksPerPage, allBooks.length);
        for (let i = currentIndex; i < end; i++) {
            const book = allBooks[i];

            const bookItem = document.createElement("div");
            bookItem.className = "book-item";
            bookItem.innerHTML = `
                <div class="book-cover">
                    <img src="\${book.cover}" alt="${book.title}">
                </div>
                <div class="book-title">\${book.title}</div>
                <div class="book-author">\${book.author}</div>
            `;

            bookItem.addEventListener("click", function () {
                window.location.href = `${pageContext.request.contextPath}/books/detail?bookNo=\${book.bookNo}`;
            });

            container.appendChild(bookItem);
        }
    }

    // 신착 도서 페이징 버튼 이벤트
    document.getElementById("prevBtn").addEventListener("click", () => {
        if (currentIndex >= booksPerPage) {
            currentIndex -= booksPerPage;
            renderBooks();
        }
    });

    document.getElementById("nextBtn").addEventListener("click", () => {
        if (currentIndex + booksPerPage < allBooks.length) {
            currentIndex += booksPerPage;
            renderBooks();
        }
    });

    // 추천 도서 불러오기
    function fetchRecommendedBooks() {
        const container = document.getElementById("recommendedBooksContainer");

        fetchData(`${pageContext.request.contextPath}/api/books/recommended`, function (err, data) {
            if (err || !data || data.length === 0) {
                container.innerHTML = '<div class="error">추천 도서가 없습니다.</div>';
                return;
            }

            container.innerHTML = "";

            data.forEach((book) => {
                const bookItem = document.createElement("div");
                bookItem.className = "book-item";

                bookItem.innerHTML = `
                    <div class="book-cover">
                        <img src="\${book.cover}" alt="${book.title}">
                    </div>
                    <div class="book-title">\${book.title}</div>
                    <div class="book-author">\${book.author}</div>
                `;

                bookItem.addEventListener("click", function () {
                    window.location.href = `${pageContext.request.contextPath}/books/detail?bookNo=\${book.bookNo}`;
                });

                container.appendChild(bookItem);
            });
        });
    }

    // 초기 데이터 불러오기
    fetchNewBooks();
    fetchRecommendedBooks();
});
</script>

<!-- Font Awesome CDN 추가 -->

<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/js/all.min.js"></script>