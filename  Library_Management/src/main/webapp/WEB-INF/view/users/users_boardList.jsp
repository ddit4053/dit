<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/users/users_boardList.css">
<script src="${pageContext.request.contextPath}/resource/js/users/users_boardList.js"></script>
<div class="info-content">
    <div class="info-content-header">
        <h2 class="content-title">${pageTitle}</h2>
        <p class="content-description">${pageDescription}</p>
        <div class="boardTopOption">
        	<div class="hideNotice">
        		<input id="isNoticeVisible" type="checkbox" class="input_check">
        		<label for="isNoticeVisible" class="label">공지 숨기기
        		</label>
        	</div>
	        <div class="order-search">
	        	<select class="order-type">
	        		<option value="latest">최신순</option>
	        		<option value="oldest">과거순</option>
	        		<option value="views">조회수순</option>
	        		<option value="comments">댓글순</option>
	        	</select>
	        </div>
	        <div class="block-size">
	        	<select class="block-select">
	        		<option value="5">5개씩</option>
	        		<option value="10">10개씩</option>
	        		<option value="15">15개씩</option>
	        		<option value="30">30개씩</option>
	        		<option value="50">50개씩</option>
	        	</select>
	        </div>
    	</div>
    </div>
    <div class="board-section">
        <!-- 상단 고정 공지사항 출력 -->
        <div class="board-list">
            <table class="board-table">
                <thead>
                    <tr>
                        <th width="10%">번호</th>
                        <th width="50%">제목</th>
                        <th width="15%">작성자</th>
                        <th width="15%">등록일</th>
                        <th width="10%">조회수</th>
                    </tr>
                </thead>
                <tbody id=boardTableBody>
                <!-- 내용은 비동기로 동적 추가 -->
                </tbody>
            </table>
            
            <!-- 페이징 영역 -->
            <div class="board-pagination" id="paginationArea">
            </div>
            
            <!-- 검색 폼 -->
            <div class="board-search">
            	<form id="searchForm" onsubmit="return false;">
	                <select name="searchType" class="search-type">
	                    <option value="title">제목</option>
	                    <option value="content">내용</option>
	                    <option value="writer">작성자</option>
	                    <option value="titleContent">제목+내용</option>
	                </select>
	                <input type="text" name="searchKeyword" class="search-input" placeholder="검색어를 입력하세요">
	                <button type="button" class="search-btn" onclick="searchBoard()">검색</button>
            	</form>
            	<!-- 글쓰기 에디터 진입 -->
            	<div class="board-editor">
            	<button type="button" class="go-to-editor">글쓰기</button>
            	</div>
            </div>
        </div>
    </div>
</div>