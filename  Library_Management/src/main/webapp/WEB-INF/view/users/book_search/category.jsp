<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="${pageContext.request.contextPath}/resource/js/jquery-3.7.1.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/users/book_search/search.css">

<script>
document.addEventListener('DOMContentLoaded', function() {
    const mainCategory = document.querySelector('select[name="mainCategory"]');
    const midCategory = document.querySelector('select[name="midCategory"]');
    const subCategory = document.querySelector('select[name="subCategory"]');

    function fetchCategories(parentId, targetSelect) {
        fetch(`${pageContext.request.contextPath}/books/categoryList?parentId=\${parentId}`)
            .then(response => response.json())
            .then(data => {
                targetSelect.innerHTML = '<option value="">선택하세요</option>';
                data.forEach(category => {
                    const option = document.createElement('option');
                    option.value = category.categoryNo;
                    option.textContent = category.categoryName;
                    targetSelect.appendChild(option);
                });
            });
    }

    mainCategory.addEventListener('change', function() {
        const parentId = this.value;

        if (parentId) {
            fetchCategories(parentId, midCategory);
        } else {
            midCategory.innerHTML = '<option value="">선택하세요</option>';
            subCategory.innerHTML = '<option value="">선택하세요</option>';
        }
    });

    midCategory.addEventListener('change', function() {
        const parentId = this.value;

        if (parentId) {
            fetchCategories(parentId, subCategory);
        } else {
            subCategory.innerHTML = '<option value="">선택하세요</option>';
        }
    });

    // 초기 대분류 카테고리 로딩
    fetchCategories('', mainCategory);
    
	const form = document.querySelector('.search-form');
	const hiddenCategoryInput = document.getElementById('selectedCategoryId');
	
	form.addEventListener('submit', function(e) {
	    const mainVal = mainCategory.value;
	    const midVal = midCategory.value;
	    const subVal = subCategory.value;
	
	    let selected = '';
	    if (subVal) {
	        selected = subVal;
	    } else if (midVal) {
	        selected = midVal;
	    } else if (mainVal) {
	        selected = mainVal;
	    }
	
	    $('#selectedCategoryId').val(selected);
	});
});


</script>

<div class="search-container">
    <div class="search-box">
        <h2 class="search-title">카테고리 자료 검색</h2>
        <div class="search-description">원하시는 카테고리를 검색하여 찾아보세요.</div>
        
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
                    <label class="search-option-label">1차 카테고리</label>
                    <select name="mainCategory" class="search-option-select">

                    </select>
                </div>
                
                <div class="search-option-group">
                    <label class="search-option-label">2차 카테고리</label>
                    <select name="midCategory" class="search-option-select">

                    </select>
                </div>
                
                <div class="search-option-group">
                    <label class="search-option-label">3차 카테고리</label>
                    <select name="subCategory" class="search-option-select">

                    </select>
                </div>
            </div>
            <input type="hidden" name="selectedCategoryId" id="selectedCategoryId">
        </form>
    </div>
    
</div>

<!-- Font Awesome CDN 추가 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/js/all.min.js"></script>