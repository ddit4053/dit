// /resource/js/user/mypage/pagination.js
function updatePagination(currentPage, totalPages, startPage, endPage, currentBlock, totalBlocks) {
    let pagination = $('.board-pagination');
    pagination.empty();
    
    // 이전 블록 버튼
    let prevBlockBtn = $('<a>')
        .addClass('page-btn prev')
        .html('&lt;&lt;')
        .attr('data-page', startPage - 1);
        
    if (currentBlock <= 1) {
        prevBlockBtn.addClass('disabled');
    }
    pagination.append(prevBlockBtn);
    
    // 이전 페이지 버튼
    let prevPageBtn = $('<a>')
        .addClass('page-btn prev')
        .html('&lt;')
        .attr('data-page', parseInt(currentPage) - 1);
        
    if (currentPage <= 1) {
        prevPageBtn.addClass('disabled');
    }
    pagination.append(prevPageBtn);
    
    // 페이지 번호들
    for (let i = startPage; i <= endPage; i++) {
        let pageLink = $('<a>')
            .addClass('page-num')
            .text(i)
            .attr('data-page', i);
            
        if (i == currentPage) {
            pageLink.addClass('active');
        }
        pagination.append(pageLink);
    }
    
    // 다음 페이지 버튼
    let nextPageBtn = $('<a>')
        .addClass('page-btn next')
        .html('&gt;')
        .attr('data-page', parseInt(currentPage) + 1);
        
    if (currentPage >= totalPages) {
        nextPageBtn.addClass('disabled');
    }
    pagination.append(nextPageBtn);
    
    // 다음 블록 버튼
    let nextBlockBtn = $('<a>')
        .addClass('page-btn next')
        .html('&gt;&gt;')
        .attr('data-page', endPage + 1);
        
    if (currentBlock >= totalBlocks) {
        nextBlockBtn.addClass('disabled');
    }
    pagination.append(nextBlockBtn);
}

// 페이지네이션 이벤트 처리를 위한 함수
function setupPaginationHandlers(loadDataFunc) {
    $(document).on('click', '.page-num, .page-btn:not(.disabled)', function(e) {
        e.preventDefault();
        
        let pageNum;
        if ($(this).hasClass('page-num')) {
            pageNum = $(this).text();
        } else {
            pageNum = $(this).attr('data-page');
        }
        
        if (pageNum) {
            loadDataFunc(pageNum);
        }
        
        return false;
    });
}