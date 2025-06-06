<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="${pageContext.request.contextPath}/resource/js/jquery-3.7.1.js"></script>

<nav class="main-nav">
    <div class="nav-container">
    <%--contextPath 이하 URL은 각 @WebServlet 어노테이션 URL에 맞춰 수정 --%>
    	<c:choose>
    		<c:when test ="${sessionScope.role eq 'ADMIN'}">
	    		<%-- 관리자용 메뉴 --%>
	    		<ul>
	                <li><a href="${pageContext.request.contextPath}/admin/books/list">도서/자료 관리</a></li>
	                <li><a href="${pageContext.request.contextPath}/admin/loans/management/list">대출/반납 관리</a></li>
	                
	                <li><a href="${pageContext.request.contextPath}/admin/readingMain.do">열람실 관리</a></li>
	                <li><a href="${pageContext.request.contextPath}/admin/board">게시판 관리</a></li>
	           	</ul>
         	</c:when>
         	<c:otherwise>
	    		<%-- 일반 사용자용 메뉴 --%>
	        	<ul>
		            <li><a href="${pageContext.request.contextPath}/books/search">자료 검색</a></li>
		            <li><a href="${pageContext.request.contextPath}/readingMain.do">열람실 예약</a></li>
		            <li><a href="${pageContext.request.contextPath}/about/greetings">도서관 소개</a></li>
		            <li><a href="${pageContext.request.contextPath}/community/reviews">커뮤니티</a></li>
		            <li><a href="${pageContext.request.contextPath}/support/notices">이용안내</a></li>
	        	</ul>
       		</c:otherwise>
      	</c:choose>
    </div>
    <!-- 메가 드롭다운 메뉴는 JavaScript로 생성 -->
</nav>

<!-- 드롭다운 spacer -->
<div class="dropdown-spacer"></div>