// /resource/js/user/mypage/dataLoader.js
function createDataLoader(url, updateTableFunc) {
    return function(page) {
        console.log("요청 URL:", url);
        
        $.ajax({
            url: url,
            type: 'GET',
            data: {
                page: page
            },
            dataType: 'json',
            headers: {
                "X-Requested-With": "XMLHttpRequest"
            },
            success: function(response) {
                console.log("응답 성공:", response);
                
                // 데이터 키 찾기 (pagingVo가 아닌 첫 번째 키)
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
}