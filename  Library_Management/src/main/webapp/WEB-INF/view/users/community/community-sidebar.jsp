<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- 현재 페이지 URL 가져오기 --%>
<c:set var="currentUrl" value="${pageContext.request.requestURI}" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/sidebar.css">
<div class="sidebar">
    <h2 class="sidebar-title">커뮤니티</h2>
    <ul class="sidebar-menu">
        <li class="sidebar-menu-item ${fn:contains(currentUrl, '/board/community') ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/board/community" class="sidebar-link">1. 독후감 게시판</a>
            <ul class="sidebar-submenu">
                <li class="sidebar-submenu-item ${fn:contains(currentUrl, '/board/community/review/list') ? 'active' : ''}">
                    <a href="${pageContext.request.contextPath}/board/community/review/list" class="sidebar-sublink">독후감 게시판</a>
                </li>
                <li class="sidebar-submenu-item ${fn:contains(currentUrl, '/board/community/review/my') ? 'active' : ''}">
                    <a href="${pageContext.request.contextPath}/board/community/review/my'" class="sidebar-sublink">나의 독후감</a>
                </li>
            </ul>
        </li>
        <li class="sidebar-menu-item ${fn:contains(currentUrl, ' /board/community/discussion') ? 'active' : ''}">
            <a href="${pageContext.request.contextPath} /board/community/discussion" class="sidebar-link">2. 토론 게시판</a>
            <ul class="sidebar-submenu">
                <li class="sidebar-submenu-item ${fn:contains(currentUrl, ' /board/community/discussion/list') ? 'active' : ''}">
                    <a href="${pageContext.request.contextPath} /board/community/discussion/list" class="sidebar-sublink">토론 게시판</a>
                </li>
                <li class="sidebar-submenu-item ${fn:contains(currentUrl, ' /board/community/discussion/participating') ? 'active' : ''}">
                    <a href="${pageContext.request.contextPath} /board/community/discussion/participating" class="sidebar-sublink">참여중인 토론</a>
                </li>
            </ul>
        </li>
        <li class="sidebar-menu-item ${fn:contains(currentUrl, '/board/community/recommend') ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/board/community/recommend" class="sidebar-link">3. 회원 도서 추천 게시판</a>
        </li>
        
    </ul>
</div>