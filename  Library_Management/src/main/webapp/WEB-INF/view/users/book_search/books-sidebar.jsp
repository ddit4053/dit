<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- 현재 페이지 URL 가져오기 --%>
<c:set var="currentUrl" value="${pageContext.request.requestURI}" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/sidebar.css">
<div class="sidebar">
    <h2 class="sidebar-title">자료 검색</h2>
    <ul class="sidebar-menu">
        <li class="sidebar-menu-item ${fn:contains(currentUrl, '/books/search') ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/books" class="sidebar-link">1. 도서 검색</a>
        </li>

        <li class="sidebar-menu-item ${fn:contains(currentUrl, '/books/new') ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/books/new" class="sidebar-link">2. 신착 도서</a>
        </li>

        <li class="sidebar-menu-item ${fn:contains(currentUrl, '/books/favor') ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/books/favor" class="sidebar-link">3. 관심 도서</a>
        </li>

        <li class="sidebar-menu-item ${fn:contains(currentUrl, '/books/recommend') ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/books/recommend" class="sidebar-link">4. 추천 도서</a>
        </li>
        
    </ul>
</div>