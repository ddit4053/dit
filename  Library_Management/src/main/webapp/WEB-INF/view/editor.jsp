<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="header.jsp" />
<jsp:include page="nav.jsp" />

<!-- 스타일시트 로드 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/board.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/editor.css">


<main class="main-content">
    <div class="container">
        <!-- 페이지 헤더 및 경로 표시 -->
        <header class="page-header">
            <nav class="breadcrumb" aria-label="breadcrumb">
                <a href="${pageContext.request.contextPath}/main.do">홈</a> > 
                <span>${breadcrumbTitle}</span> > 
            </nav>
        </header>
        
        <div class="content-layout">
            <section class="main-content-area">
                
                <!-- 에디터 상단 영역 -->
                <div class="editor-top">
                	<span class="boardNo">번호: ${board.boardNo}</span>
                    <!-- 모드 및 게시글, 게시판 번호 정보 (hidden) -->
				    <input type="hidden" id="editorMode" name="editorMode" value="${mode}">
				    <input type="hidden" id="boardNo" name="boardNo" value="${board.boardNo}">
				    <input type="hidden" id="originalCodeNo" value="${board.codeNo}">
				    
                    <!-- 제목 입력 영역 -->
                    <div class="textarea-row">
                        <textarea 
                            placeholder="제목을 입력하세요." 
                            class="textarea-input" 
                            style="height: 38px;" 
                            id="titleInput"
                            aria-label="게시글 제목"
                        >${board.title}</textarea>
                    </div>
                    
                    <!-- 게시판 선택 드롭다운, 관리자만 공지사항 게시판으로 이동 가능-->
					<div class="board-select">
					    <select id="codeNoSelect" name="codeNo">
					        <c:forEach items="${codeList}" var="code">
					        		<c:choose>
					        			<c:when test="${code.codeNo ==4}">
					        				<c:if test="${isAdmin}">
					        					<option value="${code.codeNo}" ${code.codeNo eq board.codeNo ? 'selected' : ''}>${code.codeName}</option>
                   						</c:if>
                  					</c:when>
                  					<c:otherwise>
							            <option value="${code.codeNo}" ${code.codeNo eq board.codeNo ? 'selected' : ''}>${code.codeName}</option>
                						</c:otherwise>
					        		</c:choose>
					        </c:forEach>
					    </select>
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
                            <!-- <button type="button" class="code-tool" aria-label="코드 블록 삽입">코드</button> -->
                        </div>
                        
                        <!-- 텍스트 서식 도구 -->
                        <!-- <div class="text-tool">
                            <button type="button" class="h1Font" aria-label="제목 1">H1</button>
                            <button type="button" class="h2Font" aria-label="제목 2">H2</button>
                            <button type="button" class="h3Font" aria-label="제목 3">H3</button>
                            <button type="button" class="olFont" aria-label="번호 목록">OL</button>
                            <button type="button" class="ulFont" aria-label="글머리 기호 목록">UL</button>
                            <button type="button" class="boldFont" aria-label="굵게">B</button>
                            <button type="button" class="italicFont" aria-label="기울임">I</button>
                            <button type="button" class="strikeFont" aria-label="취소선">S</button>
                        </div> -->
                    </div>
                    <div class="attachment">
                    	 <!-- 첨부된 파일 목록 보여주고, 각 첨부파일 명 옆에 x버튼 클릭하여 등록 취소 가능 -->
                    </div>
                    
                    
                    <!-- 본문 에디터 -->
                    <!-- 본문에 첨부한 이미지파일과 URL을 렌더링해야함.
                    단, 렌더링 된 이미지나 URL을 조작하여 사이즈 조절 및 등록 취소가 가능해야함
                    본문 텍스트 영역을 변경하거나 침범해서는 안 됨. -->
                    <div class="editor-content">
                        <textarea 
                            placeholder="내용을 입력하세요." 
                            class="content-input" 
                            id="contentInput"
                            aria-label="게시글 내용"
                        >${board.content}</textarea>
                    </div>
                    
                    <!-- 에디터 버튼 영역 -->
                    <div class="editor-buttons">
                        <div class="right-buttons">
                            <button type="button" class="cancel-button">취소</button>
                            <button type="button" class="save-button">
                            <c:choose>
				                <c:when test="${mode eq 'update'}">수정하기</c:when>
				                <c:otherwise>등록하기</c:otherwise>
				            </c:choose>
                            </button>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </div>
</main>


<script>
    const contextPath = '${pageContext.request.contextPath}';
</script>
<!-- 스크립트 로드 -->
<script src="${pageContext.request.contextPath}/resource/js/breadcrumb.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/marked/4.3.0/marked.min.js"></script>
<!-- 에디터 기능 관련 스크립트 -->
<script src="${pageContext.request.contextPath}/resource/js/editor/editorAjax.js"></script>
<script src="${pageContext.request.contextPath}/resource/js/editor/editorToolbox.js"></script>


<jsp:include page="footer.jsp" />