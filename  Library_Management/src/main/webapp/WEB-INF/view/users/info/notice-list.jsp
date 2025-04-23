<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/users/info/notice-list.css">
<div class="info-content">
    <div class="info-content-header">
        <h2 class="content-title">공지사항</h2>
        <p class="content-description">
            도서관의 중요한 공지사항을 확인하실 수 있습니다.
        </p>
    </div>
    
    <div class="notice-section">
        <!-- 공지사항 게시판 내용 -->
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
                <tbody>
                    <tr>
                        <td>5</td>
                        <td class="title"><a href="#">2025년 설 연휴 도서관 휴관 안내</a> <span class="new-badge">NEW</span></td>
                        <td>관리자</td>
                        <td>2025.01.15</td>
                        <td>45</td>
                    </tr>
                    <tr>
                        <td>4</td>
                        <td class="title"><a href="#">겨울방학 독서 캠프 참가자 모집</a></td>
                        <td>관리자</td>
                        <td>2025.01.10</td>
                        <td>132</td>
                    </tr>
                    <tr>
                        <td>3</td>
                        <td class="title"><a href="#">전자책 서비스 확대 안내</a></td>
                        <td>관리자</td>
                        <td>2025.01.05</td>
                        <td>87</td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td class="title"><a href="#">도서관 회원 정보 업데이트 안내</a></td>
                        <td>관리자</td>
                        <td>2024.12.28</td>
                        <td>103</td>
                    </tr>
                    <tr>
                        <td>1</td>
                        <td class="title"><a href="#">연말 도서 반납 연장 서비스 안내</a></td>
                        <td>관리자</td>
                        <td>2024.12.20</td>
                        <td>215</td>
                    </tr>
                </tbody>
            </table>
            
            <div class="board-pagination">
                <a href="#" class="page-btn prev">이전</a>
                <a href="#" class="page-num active">1</a>
                <a href="#" class="page-num">2</a>
                <a href="#" class="page-num">3</a>
                <a href="#" class="page-num">4</a>
                <a href="#" class="page-num">5</a>
                <a href="#" class="page-btn next">다음</a>
            </div>
            
            <div class="board-search">
                <select class="search-type">
                    <option value="title">제목</option>
                    <option value="content">내용</option>
                    <option value="both">제목+내용</option>
                </select>
                <input type="text" class="search-input" placeholder="검색어를 입력하세요">
                <button class="search-btn">검색</button>
            </div>
        </div>
    </div>
</div>