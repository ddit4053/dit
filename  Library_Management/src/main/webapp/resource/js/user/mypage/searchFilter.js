// /resource/js/user/mypage/searchFilter.js
function setupSearchFilter(options = {}) {
    
    let searchParams = {
        searchType: '',
        searchKeyword: '',
        periodType: ''
    };
    
    let loadDataFunc;
    
    if (options.searchOptions) {
        let searchTypeSelect = $('.search-type');
        searchTypeSelect.empty(); // 기존 옵션 제거
        
        $.each(options.searchOptions, function(value, text) {
            searchTypeSelect.append($('<option>').val(value).text(text));
        });
        
        if (options.searchOptions && Object.keys(options.searchOptions).length > 0) {
            searchParams.searchType = Object.keys(options.searchOptions)[0];
        }
    }
    
    $('.search-btn').on('click', function() {
        searchParams.searchType = $('.search-type').val();
        searchParams.searchKeyword = $('.search-input').val();
        
        if (loadDataFunc) {
            loadDataFunc(1, searchParams);
        }
    });
    
    $('.search-input').on('keypress', function(e) {
        if (e.which === 13) { // 엔터키 코드
            $('.search-btn').click();
            return false;
        }
    });
    
    $('.period-btn').on('click', function() {
        $('.period-btn').removeClass('active');
        $(this).addClass('active');
        
        searchParams.periodType = $(this).data('period');
        
        if (loadDataFunc) {
            loadDataFunc(1, searchParams);
        }
    });
    
    function createAdvancedDataLoader(url, updateTableFunc) {
        loadDataFunc = function(page, params = {}) {
            console.log("요청 URL:", url);
            
            let requestParams = {
                page: page
            };
            
            if (params.searchType && params.searchKeyword) {
                requestParams.searchType = params.searchType;
                requestParams.searchKeyword = params.searchKeyword;
				
                searchParams.searchType = params.searchType;
                searchParams.searchKeyword = params.searchKeyword;
            }
            
            if (params.periodType) {
                requestParams.periodType = params.periodType;
				
                searchParams.periodType = params.periodType;
            }
            
            $.ajax({
                url: url,
                type: 'GET',
                data: requestParams,
                dataType: 'json',
                headers: {
                    "X-Requested-With": "XMLHttpRequest"
                },
                success: function(response) {
                    console.log("응답 성공:", response);
                    
                    const dataKey = Object.keys(response).find(key => key !== 'pagingVo');
                    updateTableFunc(response[dataKey]);
                    
                    const pagingVo = response.pagingVo;
                    updatePagination(
                        pagingVo.currentPage, 
                        pagingVo.totalPages, 
                        pagingVo.startPage, 
                        pagingVo.endPage,
                        Math.ceil(pagingVo.currentPage / pagingVo.pageBlockSize),
                        Math.ceil(pagingVo.totalPages / pagingVo.pageBlockSize)
                    );
                },
                error: function(xhr, status, error) {
                    console.error('상태 코드:', xhr.status);
                    console.error('응답 텍스트:', xhr.responseText);
                    console.error('에러 메시지:', error);
                    alert('데이터를 불러오는데 실패했습니다.');
                }
            });
        };
        
        return loadDataFunc;
    }
    
    function setupAdvancedPaginationHandlers() {
        $(document).on('click', '.page-num, .page-btn:not(.disabled)', function(e) {
            e.preventDefault();
            
            let pageNum;
            if ($(this).hasClass('page-num')) {
                pageNum = $(this).text();
            } else {
                pageNum = $(this).attr('data-page');
            }
            
            if (pageNum && loadDataFunc) {
                loadDataFunc(pageNum, searchParams);
            }
            
            return false;
        });
    }
    
    return {
        createAdvancedDataLoader: createAdvancedDataLoader,
        setupAdvancedPaginationHandlers: setupAdvancedPaginationHandlers,
		getCurrentParams: function() {
		    return Object.assign({}, searchParams);
		}
    };
}