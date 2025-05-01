<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="header.jsp" />
<jsp:include page="nav.jsp" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/board.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/editor.css">

<%-- 페이지 모드 확인 (작성 또는 수정) --%>
<c:set var="isEditMode" value="${not empty post}" />

<div class="main-content">
    <div class="container">
        <div class="page-header">
            <div class="breadcrumb">
                <a href="${pageContext.request.contextPath}/main.do">홈</a> > 
                <span>${breadcrumbTitle}</span> > 
                <span>${isEditMode ? '수정하기' : '작성하기'}</span>
            </div>
        </div>
        
        <div class="content-layout">
            <!-- 메인 콘텐츠 영역 -->
            <div class="main-content-area">
                <%-- 현재 모드 (작성/수정) 정보를 저장하는 hidden input --%>
                <input type="hidden" id="editorMode" value="${isEditMode ? 'edit' : 'write'}">
                <%-- 수정 모드일 경우 게시글 번호 저장 --%>
                <c:if test="${isEditMode}">
                    <input type="hidden" id="postNo" value="${post.postNo}">
                </c:if>
                
                <div class="editor-top">
                    <div class="board-selecter">
                        <select class="board-code" id="boardCode">
                            <c:forEach var="item" items="${codeList}" varStatus="status">
                                <option value="${item.codeNo}" ${isEditMode && post.boardCode eq item.codeNo ? 'selected' : ''}>${item.codeName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <%-- 제목 입력 영역 --%>
                    <div class="textarea-row">
                        <textarea placeholder="제목을 입력하세요." class="textarea-input" style="height: 48px;" id="titleInput">${isEditMode ? post.title : ''}</textarea>
                    </div>
                </div>
                <div class="editor-main">
                    <%-- 에디터 툴바. 아이콘은 fontello 활용--%>
                    <div class="toolbox">
                        <button type="button" class="image-tool"></button>
                        <button type="button" class="url-tool"></button>
                        <button type="button" class="file-tool"></button>
                        <button type="button" class="code-tool"></button>
                    </div>
                    <div class="text-tool">
                        <button type="button" class="h1Font"></button>
                        <button type="button" class="h2Font"></button>
                        <button type="button" class="h3Font"></button>
                        <button type="button" class="olFont"></button>
                        <button type="button" class="ulFont"></button>
                        <button type="button" class="boldFont"></button>
                        <button type="button" class="italicFont"></button>
                        <button type="button" class="strikeFont"></button>
                    </div>
                    <%-- 본문 에디터 --%>
                    <div class="editor-content">
                        <textarea placeholder="내용을 입력하세요." class="content-input" id="contentInput">${isEditMode ? post.content : ''}</textarea>
                    </div>
                    
                    <%-- 버튼 영역 --%>
                    <div class="editor-buttons">
                        <button type="button" class="preview-toggle-button">미리보기</button>
                        <div class="right-buttons">
                            <button type="button" class="cancel-button">취소</button>
                            <button type="button" class="save-button">${isEditMode ? '수정완료' : '등록하기'}</button>
                        </div>
                    </div>
                    
                    <%-- 미리보기 영역 --%>
                    <div class="preview-content" style="display: none;"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/resource/js/breadcrumb.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/marked/4.3.0/marked.min.js"></script>
<script src="${pageContext.request.contextPath}/resource/js/editor.js"></script>
<jsp:include page="footer.jsp" />