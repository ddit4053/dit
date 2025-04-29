<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/users/users_boardDetail.css">
<div class="container">
	<!-- 게시글 정보 영역 -->
	<div class="board-detail">
	    <div class="board-header">
	        <h3 class="board-title">${board.title}</h3>
	        <div class="board-info">
	            <span class="writer">작성자: ${board.writer}</span>
	            <span class="date">작성일: ${board.writtenDate}</span>
	            <span class="views">조회수: ${board.views}회</span>
	            <span class="comments">댓글수: ${board.commentsCount}개</span>
	        </div>
	    </div>
	    
	    <!-- 게시글 내용 영역 -->
	    <div class="board-content">
	        ${board.content}
	    </div>
	    
	    <!-- 첨부파일이 있는 경우 표시 -->
	    <c:if test="${board.fileGroupNum > 0}">
	    	<div class="board-files">
		    	<h4>첨부파일</h4>
		    	<div class="file-list" id="fileList" data-file-group="${board.fileGroupNum}">
		    		<!-- 파일 목록은 JavaScript로 로드 -->
		            <div class="loading">파일 로딩 중...</div>
		       </div>
	    	</div>
	   	</c:if>
	   	
        <!-- 작성자만 보이는 버튼 영역 -->
        <c:if test="${isAuthor}">
            <div class="board-actions">
                <button class="btn btn-update" onclick="location.href='${pageContext.request.contextPath}/Update?boardNo=${board.boardNo}'">
                    수정
                </button>
                <button class="btn btn-delete" onclick="deleteBoard(${board.boardNo})">
                    삭제
                </button>
            </div>
        </c:if>
    </div>
    
	    <!-- 댓글 영역 -->
	    <div class="comments-section">
	    	<h3>댓글 (${board.commentsCount})</h3>
	    	
	        <!-- 댓글 작성 폼 -->
	        <c:if test="${not empty sessionScope.user}">
		        <div class="comment-form">
		        	<form id ="commentForm">
		        		<input type="hidden" name="boardNo" value="${board.boardNo}">
		        		<input type="hidden" name="userNo" value="${sessionScope.user.userNo}">
		        		<textarea name="cmContent" placeholder="댓글을 작성하세요..." required></textarea>
		        		<button type="submit" class="btn btn-comment">댓글 등록</button>
		    		</form>
		        </div>
	        </c:if>
	        
	        <!-- 댓글 목록 -->
	        <div class="comment-list">
	        	<c:if test="${empty board.comments}">
	            	<div class="no-comments">등록된 댓글이 없습니다.</div>
	            </c:if>
	            
	            <c:forEach items="${board.comments}" var="comment">
	            	<div class="comment" id="comment-${comment.cmNo}">
	            		<div class="comment-info">
	            			<span class="comment-writer">${comment.cmWriter}</span>
	                        <span class="comment-date">${comment.cmWrittenDate}</span>
	                    </div>
	                    <div class="comment-content">${comment.cmContent}</div>
	                   
	                    <!-- 댓글 작성자와 로그인 사용자가 같은 경우에만 수정/삭제 버튼 표시 -->
	                    <c:if test="${sessionScope.user.userNo == comment.userNo}">
	                        <div class="comment-actions">
	                            <button class="btn-edit" onclick="showEditForm(${comment.cmNo}, '${comment.cmContent}')">수정</button>
	                            <button class="btn-delete" onclick="deleteComment(${comment.cmNo})">삭제</button>
	                        </div>
	                    </c:if>
	                    
	                    <!-- 댓글에 대한 답글 입력 폼 토글 버튼 (로그인한 경우만) -->
                        <c:if test="${not empty sessionScope.loginUser}">
                            <button class="btn-reply" onclick="toggleReplyForm(${comment.cmNo})">답글</button>
                            <div class="reply-form" id="reply-form-${comment.cmNo}" style="display: none;">
                                <form onsubmit="submitReply(${comment.cmNo}, ${board.boardNo}); return false;">
                                    <textarea placeholder="답글을 작성하세요..." required></textarea>
                                    <button type="submit" class="btn btn-reply-submit">답글 등록</button>
                                </form>
                            </div>
                        </c:if>
                        
                        <!-- 대댓글 목록 -->
                        <c:if test="${not empty comment.cm2List}">
                        	<div class="replies">
                        		<c:forEach items="${comment.cm2List}" var="reply">
                        			<div class="reply" id="comment-${reply.cmNo}">
                        				<div class="reply-info">
                                            <span class="reply-icon">↪</span>
                                            <span class="reply-writer">${reply.cmWriter}</span>
                                            <span class="reply-date">
                                                <fmt:parseDate value="${comment.cmWrittenDate}" pattern="yyyy-MM-dd HH:mm:ss" var="parsedDate" />
												<fmt:formatDate value="${parsedDate}" pattern="yyyy-MM-dd HH:mm" />
                                            </span>
                                        </div>
                                        <div class="reply-content">${reply.cmContent}</div>
                                        
                                        <!-- 답글 작성자와 로그인 사용자가 같은 경우에만 수정/삭제 버튼 표시 -->
                                        <c:if test="${sessionScope.user.userNo == reply.userNo}">
                                            <div class="reply-actions">
                                                <button class="btn-edit" onclick="showEditForm(${reply.cmNo}, '${reply.cmContent}')">수정</button>
                                                <button class="btn-delete" onclick="deleteComment(${reply.cmNo})">삭제</button>
                                            </div>
                                        </c:if>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:if>
                    </div>                
				</c:forEach>			            
	        </div>
	    </div>
	</div>
	
	<!-- 댓글 수정 모달 -->
    <div id="commentEditModal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <h3>댓글 수정</h3>
            <form id="commentEditForm">
                <input type="hidden" id="editCommentId" name="cmNo">
                <textarea id="editCommentContent" name="cmContent" required></textarea>
                <button type="submit" class="btn btn-update">수정하기</button>
            </form>
        </div>
    </div>
    <script src="${pageContext.request.contextPath}/resource/js/users/user_boardDetail.js"></script>