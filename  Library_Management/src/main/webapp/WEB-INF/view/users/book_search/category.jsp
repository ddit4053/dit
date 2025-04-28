<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/users/book_search/search.css">

<div class="search-container">
    <div class="search-box">
        <h2 class="search-title">카테고리 자료 검색</h2>
        <div class="search-description">원하시는 카테고리를 검색하여 찾아보세요.</div>
        
        <form class="search-form" action="${pageContext.request.contextPath}/books/search/result" method="get">
            <div class="search-input-group">
                <select name="searchType" class="search-select">
                    <option value="all">전체</option>
                    <option value="title">제목</option>
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
                    <label class="search-option-label">1차 카테고리</label>
                    <select name="year" class="search-option-select">
                        <option value="">전체</option>
                        <c:forEach var="year" begin="2000" end="2025" step="1" varStatus="status">
                            <option value="${2025 - status.index}">${2025 - status.index}년</option>
                        </c:forEach>
                    </select>
                </div>
                
                <div class="search-option-group">
                    <label class="search-option-label">2차 카테고리</label>
                    <select name="bookType" class="search-option-select">
                        <option value="">전체</option>
                        <option value="book">단행본</option>
                        <option value="ebook">E-Book</option>
                        <option value="journal">학술지</option>
                    </select>
                </div>
                
                <div class="search-option-group">
                    <label class="search-option-label">3차 카테고리</label>
                    <select name="sortBy" class="search-option-select">
                        <option value="newest">최신순</option>
                        <option value="popular">인기순</option>
                        <option value="title">제목순</option>
                    </select>
                </div>
            </div>
        </form>
    </div>
    
</div>

<!-- Font Awesome CDN 추가 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/js/all.min.js"></script>