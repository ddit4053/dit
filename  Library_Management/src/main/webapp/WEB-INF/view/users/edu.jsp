<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>신청ㆍ참여 > 교육ㆍ행사 > 교육 프로그램 > 전체 | 책 GPT 도서관</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/users/education_program.css">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <div class="container edu-program-page">

        <!-- 페이지 제목 -->
        <div class="page-title">
            <h2>${pageTitle}</h2>
            <p>${pageDescription}</p>
        </div>

        <!-- 월별 네비게이션 (BIFF arc_history_nav 스타일) -->
        <div class="arc_history_nav">
            <ul>
                <li class="on"><a href="#"><em>전</em>체</a></li>
                <li><a href="#"><em>1</em>월</a></li>
                <li><a href="#"><em>2</em>월</a></li>
                <li><a href="#"><em>3</em>월</a></li>
                <li><a href="#"><em>4</em>월</a></li>
                <li><a href="#"><em>5</em>월</a></li>
                <li><a href="#"><em>6</em>월</a></li>
                <li><a href="#"><em>7</em>월</a></li>
                <li><a href="#"><em>8</em>월</a></li>
                <li><a href="#"><em>9</em>월</a></li>
                <li><a href="#"><em>10</em>월</a></li>
                <li><a href="#"><em>11</em>월</a></li>
                <li><a href="#"><em>12</em>월</a></li>
            </ul>
        </div>

        <!-- 탭 메뉴 -->
        <div class="tab-menu">
            <ul>
                <li class="active"><a href="#" data-type="all">전체</a></li>
                <li><a href="#" data-type="edu">교육 프로그램</a></li>
                <li><a href="#" data-type="event">행사 프로그램</a></li>
            </ul>
        </div>

        <!-- 검색 영역 -->
        <div class="search-area">
            <div class="search-options">
                <select class="search-type">
                    <option value="title">제목</option>
                    <option value="content">내용</option>
                </select>
                <input type="text" class="search-input" placeholder="검색어를 입력하세요">
                <button class="search-btn">검색</button>
            </div>
        </div>

        <!-- 프로그램 목록 -->
        <div class="program-list">
            <!-- 프로그램 항목들이 여기에 동적으로 로드됩니다 -->
        </div>

        <!-- 페이지네이션 -->
        <div class="board-pagination pagination">
            <!-- 페이지네이션이 여기에 동적으로 생성됩니다 -->
        </div>
    </div>
    
    <!-- JS 파일 로드 -->
    <script src="${pageContext.request.contextPath}/resource/js/user/mypage/dataLoader.js"></script>
    <script src="${pageContext.request.contextPath}/resource/js/user/mypage/pagination.js"></script>
    <script src="${pageContext.request.contextPath}/resource/js/user/mypage/searchFilter.js"></script>
    
    <script>
        $(document).ready(function() {
            // 현재 선택된 월
            let currentMonth = '전';
            // 현재 선택된 프로그램 타입
            let currentType = 'all';
            
            // 검색 필터 설정
            const searchFilterObj = setupSearchFilter({
			    searchOptions: {
			        'title': '제목',
			        'target_audience': '대상'
			    }
			});
            
            console.log("데이터 로드 요청 URL:", '${pageContext.request.contextPath}/support/events/api/list');
            // 데이터 로더 설정
            const loadEduEvents = searchFilterObj.createAdvancedDataLoader(
                '${pageContext.request.contextPath}/support/events/api/list',
                updateEduEventList
            );
            
            // 페이지네이션 핸들러 설정
            searchFilterObj.setupAdvancedPaginationHandlers();
            
            // 월별 네비게이션 이벤트 설정
            $('.arc_history_nav li').on('click', function(e) {
                e.preventDefault();
                
                // 이미 active 상태인 항목을 다시 클릭한 경우는 무시
                if ($(this).hasClass('on')) {
                    return;
                }
                
                // 기존 active 항목 제거
                $('.arc_history_nav li.on').removeClass('on');
                
                // 클릭한 항목을 active로 설정
                $(this).addClass('on');
                
                // 클릭한 월 값 가져오기
                let monthText = $(this).find('a em').text();
                currentMonth = monthText === '전' ? '전' : parseInt(monthText);
                
                console.log("선택한 월:", currentMonth);
                
                // 중요: searchFilterObj의 내부 객체에 month 값 추가
                const currentParams = searchFilterObj.getCurrentParams();
                currentParams.month = currentMonth;
                
                // 직접 AJAX 호출
                $.ajax({
                    url: '${pageContext.request.contextPath}/support/events/api/list',
                    type: 'GET',
                    data: {
                        page: 1,
                        month: currentMonth,
                        type: currentType.trim(),
                        // 기존 검색 파라미터도 함께 전달
                        searchType: currentParams.searchType,
                        searchKeyword: currentParams.searchKeyword,
                        periodType: currentParams.periodType
                    },
                    dataType: 'json',
                    headers: {
                        "X-Requested-With": "XMLHttpRequest"
                    },
                    success: function(response) {
                        console.log("응답 성공:", response);
                        updateEduEventList(response.eduEvents);
                        
                        // 페이지네이션 처리
                        if (response.pagingVo) {
                            const pagingVo = response.pagingVo;
                            updatePagination(
                                pagingVo.currentPage, 
                                pagingVo.totalPages, 
                                pagingVo.startPage, 
                                pagingVo.endPage,
                                Math.ceil(pagingVo.currentPage / pagingVo.pageBlockSize),
                                Math.ceil(pagingVo.totalPages / pagingVo.pageBlockSize)
                            );
                        }
                    },
                    error: function(xhr, status, error) {
                        console.error('상태 코드:', xhr.status);
                        console.error('응답 텍스트:', xhr.responseText);
                        console.error('에러 메시지:', error);
                        alert('데이터를 불러오는데 실패했습니다.');
                    }
                });
            });
            
            // 탭 메뉴 이벤트 설정
            $('.tab-menu li a').on('click', function(e) {
                e.preventDefault();
                
                // 이미 active 상태인 항목을 다시 클릭한 경우는 무시
                if ($(this).parent().hasClass('active')) {
                    return;
                }
                
                // 기존 active 항목 제거
                $('.tab-menu li.active').removeClass('active');
                
                // 클릭한 항목을 active로 설정
                $(this).parent().addClass('active');
                
                // 클릭한 타입 값 가져오기
                currentType = $(this).data('type');
                console.log("정확한 타입 값:", $(this).data('type'));
                
                console.log("선택한 타입:", currentType);
                
                // 중요: searchFilterObj의 내부 객체에 type 값 추가
                const currentParams = searchFilterObj.getCurrentParams();
                currentParams.type = currentType;
                
                // 직접 AJAX 호출
                $.ajax({
                    url: '${pageContext.request.contextPath}/support/events/api/list',
                    type: 'GET',
                    data: {
                        page: 1,
                        month: currentMonth,
                        type: currentType.trim(),
                        // 기존 검색 파라미터도 함께 전달
                        searchType: currentParams.searchType,
                        searchKeyword: currentParams.searchKeyword,
                        periodType: currentParams.periodType
                    },
                    dataType: 'json',
                    headers: {
                        "X-Requested-With": "XMLHttpRequest"
                    },
                    success: function(response) {
                        console.log("응답 성공:", response);
                        updateEduEventList(response.eduEvents);
                        
                        // 페이지네이션 처리
                        if (response.pagingVo) {
                            const pagingVo = response.pagingVo;
                            updatePagination(
                                pagingVo.currentPage, 
                                pagingVo.totalPages, 
                                pagingVo.startPage, 
                                pagingVo.endPage,
                                Math.ceil(pagingVo.currentPage / pagingVo.pageBlockSize),
                                Math.ceil(pagingVo.totalPages / pagingVo.pageBlockSize)
                            );
                        }
                    },
                    error: function(xhr, status, error) {
                        console.error('상태 코드:', xhr.status);
                        console.error('응답 텍스트:', xhr.responseText);
                        console.error('에러 메시지:', error);
                        alert('데이터를 불러오는데 실패했습니다.');
                    }
                });
            });
            
            // 페이지네이션 직접 재정의 - 기존 핸들러 대체
            $(document).off('click', '.page-num, .page-btn:not(.disabled)');
            $(document).on('click', '.page-num, .page-btn:not(.disabled)', function(e) {
                e.preventDefault();
                
                let pageNum;
                if ($(this).hasClass('page-num')) {
                    pageNum = $(this).text();
                } else {
                    pageNum = $(this).attr('data-page');
                }
                
                if (pageNum) {
                    // 모든 파라미터(월, 타입 포함)를 함께 전송
                    $.ajax({
                        url: '${pageContext.request.contextPath}/support/events/api/list',
                        type: 'GET',
                        data: {
                            page: pageNum,
                            month: currentMonth,
                            type: currentType.trim(),
                            searchType: searchFilterObj.getCurrentParams().searchType,
                            searchKeyword: searchFilterObj.getCurrentParams().searchKeyword,
                            periodType: searchFilterObj.getCurrentParams().periodType
                        },
                        dataType: 'json',
                        headers: {
                            "X-Requested-With": "XMLHttpRequest"
                        },
                        success: function(response) {
                            console.log("응답 성공:", response);
                            updateEduEventList(response.eduEvents);
                            
                            // 페이지네이션 처리
                            if (response.pagingVo) {
                                const pagingVo = response.pagingVo;
                                updatePagination(
                                    pagingVo.currentPage, 
                                    pagingVo.totalPages, 
                                    pagingVo.startPage, 
                                    pagingVo.endPage,
                                    Math.ceil(pagingVo.currentPage / pagingVo.pageBlockSize),
                                    Math.ceil(pagingVo.totalPages / pagingVo.pageBlockSize)
                                );
                            }
                        },
                        error: function(xhr, status, error) {
                            console.error('상태 코드:', xhr.status);
                            console.error('응답 텍스트:', xhr.responseText);
                            console.error('에러 메시지:', error);
                            alert('데이터를 불러오는데 실패했습니다.');
                        }
                    });
                }
                
                return false;
            });
            
            // 교육 이벤트 목록 업데이트 함수
			function updateEduEventList(events) {
			    const programList = $('.program-list');
			    let html = '';
			    
			    // 디버깅: 이벤트 데이터 로깅
			    console.log("받은 이벤트 데이터:", events);
			    
			    // 데이터가 비어있는 경우
			    if (!events || events.length === 0) {
			        html = '<div class="no-data">해당 조건에 맞는 프로그램이 없습니다.</div>';
			    } else {
			        // 프로그램 목록 생성
			        events.forEach(event => {
			            // 각 이벤트 객체 로깅
			            console.log("이벤트 항목:", event);
			            console.log("이벤트 제목:", event.EV_TITLE);
			            console.log("이벤트 대상:", event.TARGET_AUDIENCE);
			            
			            // 상태 배지 클래스 설정
			            const status = event.EV_STATUS ? event.EV_STATUS : '접수중';
			            const badgeClass = status === '접수마감' ? 'program-badge program-badge-closed' : 'program-badge';
			            
			            let imagePath;
			            
			            if (event.IMAGE_PATH) {
			                if (event.IMAGE_PATH.startsWith('test/')) {
			                    // 테스트 이미지인 경우
			                    imagePath = '${pageContext.request.contextPath}/resource/img/' + event.IMAGE_PATH.substring(5);
			                } else {
			                    // 실제 업로드된 이미지인 경우
			                    imagePath = '${pageContext.request.contextPath}/uploads/' + event.IMAGE_PATH;
			                }
			            } else {
			                // 이미지가 없는 경우
			                imagePath = '${pageContext.request.contextPath}/resource/img/no-image.jpg';
			            }
			            console.log("이미지 경로:", event.IMAGE_PATH);
			            console.log("최종 이미지 URL:", imagePath);
			            
			            // HTML 생성
			            html += '<div class="program-item">' +
			            '<div class="program-image">' +
			            '<img src="' + imagePath + '" alt="' + (event.EV_TITLE || '프로그램') + '">' +
			            '<span class="' + badgeClass + '">' + status + '</span>' +
			            '</div>' +
			            '<div class="program-info">' +
			            '<h3 class="program-title" onclick="location.href=\'${pageContext.request.contextPath}/support/events/api/detail/' + event.EV_NO + '\'">' + (event.EV_TITLE || '프로그램 제목') + '</h3>' +
			            '<p class="program-period">' + (event.EV_DATE || '') + '</p>' +
			            '<p class="program-target">대상: ' + (event.TARGET_AUDIENCE || '전체') + '</p>' +
			            '<div class="program-status">' +
			            '<span class="status-label">신청현황:</span>' +
			            '<span class="status-value">' + (event.CURRENT_PARTICIPANTS || 0) + '/' + (event.MAX_PARTICIPANTS || 0) + '명</span>' +
			            '</div>' +
			            '</div>' +
			            '</div>';
			        });
			    }
			    
			    programList.html(html);
			}
            
            // 초기 데이터 로드
            loadEduEvents(1, { month: currentMonth, type: currentType });
        });
    </script>
</body>
</html>