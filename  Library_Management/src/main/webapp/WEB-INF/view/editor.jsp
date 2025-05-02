<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="header.jsp" />
<jsp:include page="nav.jsp" />

<!-- 스타일시트 로드 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/board.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/editor.css">

<%-- 페이지 모드 확인 (작성 또는 수정) --%>
<c:set var="isEditMode" value="${not empty post}" />

<main class="main-content">
    <div class="container">
        <!-- 페이지 헤더 및 경로 표시 -->
        <header class="page-header">
            <nav class="breadcrumb" aria-label="breadcrumb">
                <a href="${pageContext.request.contextPath}/main.do">홈</a> > 
                <span>${breadcrumbTitle}</span> > 
                <span>${isEditMode ? '수정하기' : '작성하기'}</span>
            </nav>
        </header>
        
        <div class="content-layout">
            <section class="main-content-area">
                <!-- 에디터 모드 정보 -->
                <input type="hidden" id="editorMode" value="${isEditMode ? 'edit' : 'write'}">
                
                <!-- 수정 모드 정보 -->
                <c:if test="${isEditMode}">
                    <input type="hidden" id="postNo" value="${post.boardNo}">
                    <c:if test="${post.fileGroupNum > 0}">
                        <input type="hidden" id="fileGroupNum" value="${post.fileGroupNum}">
                    </c:if>
                </c:if>
                
                <!-- 에디터 상단 영역 -->
                <div class="editor-top">
                    <!-- 게시판 선택 -->
                    <div class="board-selecter">
                        <select class="board-code" id="boardCode" aria-label="게시판 선택">
                            <c:forEach var="item" items="${codeList}">
                                <option value="${item.codeNo}" ${isEditMode && post.codeNo eq item.codeNo ? 'selected' : ''}>${item.codeName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <!-- 제목 입력 영역 -->
                    <div class="textarea-row">
                        <textarea 
                            placeholder="제목을 입력하세요." 
                            class="textarea-input" 
                            style="height: 48px;" 
                            id="titleInput"
                            aria-label="게시글 제목"
                        >${isEditMode ? post.title : ''}</textarea>
                    </div>
                </div>
                
                <!-- 에디터 메인 영역 -->
                <div class="editor-main">
                    <!-- 툴바 영역 -->
                    <div class="editor-toolbar">
                        <!-- 미디어 관련 도구 -->
                        <div class="toolbox">
                            <button type="button" class="image-tool" aria-label="이미지 삽입">이미지</button>
                            <button type="button" class="url-tool" aria-label="링크 삽입">링크</button>
                            <button type="button" class="file-tool" aria-label="파일 삽입">파일</button>
                            <button type="button" class="code-tool" aria-label="코드 블록 삽입">코드</button>
                        </div>
                        
                        <!-- 텍스트 서식 도구 -->
                        <div class="text-tool">
                            <button type="button" class="h1Font" aria-label="제목 1">H1</button>
                            <button type="button" class="h2Font" aria-label="제목 2">H2</button>
                            <button type="button" class="h3Font" aria-label="제목 3">H3</button>
                            <button type="button" class="olFont" aria-label="번호 목록">OL</button>
                            <button type="button" class="ulFont" aria-label="글머리 기호 목록">UL</button>
                            <button type="button" class="boldFont" aria-label="굵게">B</button>
                            <button type="button" class="italicFont" aria-label="기울임">I</button>
                            <button type="button" class="strikeFont" aria-label="취소선">S</button>
                        </div>
                    </div>
                    
                    <!-- 본문 에디터 -->
                    <div class="editor-content">
                        <textarea 
                            placeholder="내용을 입력하세요." 
                            class="content-input" 
                            id="contentInput"
                            aria-label="게시글 내용"
                        >${isEditMode ? post.content : ''}</textarea>
                    </div>
                    
                    <!-- 에디터 버튼 영역 -->
                    <div class="editor-buttons">
                        <button type="button" class="preview-toggle-button">미리보기</button>
                        <div class="right-buttons">
                            <button type="button" class="cancel-button">취소</button>
                            <button type="button" class="save-button">${isEditMode ? '수정완료' : '등록하기'}</button>
                        </div>
                    </div>
                    
                    <!-- 미리보기 영역 -->
                    <div class="preview-content" style="display: none;" aria-live="polite"></div>
                </div>
            </section>
        </div>
    </div>
</main>

<!-- 스크립트 로드 -->
<script src="${pageContext.request.contextPath}/resource/js/breadcrumb.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/marked/4.3.0/marked.min.js"></script>
<script src="${pageContext.request.contextPath}/resource/js/editor.js"></script>

<jsp:include page="footer.jsp" />