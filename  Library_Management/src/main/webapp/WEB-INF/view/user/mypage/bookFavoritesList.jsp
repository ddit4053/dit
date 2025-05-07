<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String contextPath = request.getContextPath();
    pageContext.setAttribute("contextPath", contextPath); 
    int userNo = (int)session.getAttribute("userNo");
    request.setAttribute("userNo", userNo);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>관심도서</title>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="${contextPath}/resource/js/user/mypage/pagination.js"></script>
    <script src="${contextPath}/resource/js/user/mypage/dataLoader.js"></script>
    <script src="${contextPath}/resource/js/user/mypage/searchFilter.js"></script>
    <script>
        
    	window.loadBookFavoritesList = null;
    	
	    function deleteFavorite(favoriteNo, buttonElem) {
	        if (!confirm("등록된 관심도서를 삭제 하시겠습니까?")) {
	            return;
	        }
	        
	        $.ajax({
	            url: '${contextPath}/user/mypage/deleteFavorite.do',
	            type: 'POST',
	            data: {
	            	favoriteNo: favoriteNo
	            },
	            dataType: 'json',
	            success: function(response) {
	                if (response.success) {
	                    
	                    loadBookFavoritesList($('.pagination-item.active').data('page') || 1);
	                    alert("관심도서에서 삭제 되었습니다.");
	                } else {
	                    alert("관심도서 삭제 처리 중 오류가 발생했습니다: " + response.message);
	                }
	            },
	            error: function(xhr, status, error) {
	                console.error('상태 코드:', xhr.status);
	                console.error('응답 텍스트:', xhr.responseText);
	                console.error('에러 메시지:', error);
	                alert('관심도서 삭제 처리 중 오류가 발생했습니다.');
	            }
	        });
	    }
    
        $(document).ready(function() {
            function updateBookFavoritesTable(bookFavoritesList) {
                let tableBody = $('.board-table tbody');
                tableBody.empty();
                
                if (bookFavoritesList && bookFavoritesList.length > 0) {
                    $.each(bookFavoritesList, function(index, favorite) {
                        let row = $('<tr>');
                        row.append($('<td>').text(favorite.RNUM));
                        row.append($('<td>').addClass('title').attr('title', favorite.BOOK_TITLE).text(favorite.BOOK_TITLE));
                        row.append($('<td>').addClass('title').attr('title', favorite.AUTHOR).text(favorite.AUTHOR));
                        row.append($('<td>').text(favorite.PUBLISHER));
                        row.append($('<td>').text(favorite.ISBN));
                        
                        let extensionCell = $('<td>');
                        extensionCell.html('<input type="button" value="삭제" onclick="deleteFavorite(' + favorite.FAVORITE_NO + ', this)" ' + 
                                'class="extension-btn" data-favorite-no="' + favorite.FAVORITE_NO + '">');
                        row.append(extensionCell);
                        tableBody.append(row);
                    });
                } else {
                    let row = $('<tr>');
                    row.append($('<td>').attr('colspan', '6').text('관심도서가 없습니다.'));
                    tableBody.append(row);
                }
            }
            
            const searchOptions = {
                'title': '도서명',
                'author': '저자',
                'isbn': '청구번호'
            };
            
            const searchFilterHandler = setupSearchFilter({
                searchOptions: searchOptions
            });
            
            window.loadBookFavoritesList = searchFilterHandler.createAdvancedDataLoader(
                '${contextPath}/user/mypage/bookFavoritesList.do', 
                updateBookFavoritesTable
            );
            
            window.loadBookFavoritesList(1);
            
            searchFilterHandler.setupAdvancedPaginationHandlers();
            
            $('.filter-button').addClass('period-btn');

        });
    </script>
    <style>
        .extension-btn {
            padding: 5px 10px;
            background-color: #dc3545;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .extension-btn:hover {
            background-color: #c82333;
        }
    </style>
</head>
<body>
    <div class="info-content">
        <div class="info-content-header">
            <h2 class="content-title">관심도서</h2>
            <p class="content-description">
                관심도서를 확인하실 수 있습니다.
            </p>
        </div>
        
        <div class="board-search">
            <select class="search-type">
               
            </select>
            <input type="text" class="search-input" placeholder="검색어를 입력하세요">
            <button class="search-btn">검색</button>
        </div>
        
        <div class="notice-section">
            <div class="board-list">
                <table class="board-table">
                    <thead>
                        <tr>
                            <th width="8%">번호</th>
                            <th width="37%">도서명</th>
                            <th width="10%">저자</th>
                            <th width="15%">출판사</th>
                            <th width="20%">청구번호</th>
                            <th width="10%">삭제</th>
                        </tr>
                    </thead>
                    <tbody>
                    
                    </tbody>
                </table>
                
                <div class="board-pagination">
                
                </div>
            </div>
        </div>
    </div>
</body>
</html>