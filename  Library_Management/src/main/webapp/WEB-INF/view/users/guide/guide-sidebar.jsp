<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- 현재 페이지 URL 가져오기 --%>
<c:set var="currentUrl" value="${pageContext.request.requestURI}" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/sidebar.css">

<div class="sidebar">
    <h2 class="sidebar-title">도서관 소개</h2>
    <ul class="sidebar-menu">
        <li class="sidebar-menu-item ${fn:contains(currentUrl, '/board/guide/greetings') ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/board/guide/greetings" class="sidebar-link">1. 인사말</a>
        </li>

        <li class="sidebar-menu-item ${fn:contains(currentUrl, '/board/guide/intro') ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/board/guide/intro" class="sidebar-link">2. 시설 소개</a>
        </li>

        <li class="sidebar-menu-item ${fn:contains(currentUrl, '/board/guide/facillities') ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/board/guide/facillities" class="sidebar-link">3. 편의 시설</a>
        </li>

        <li class="sidebar-menu-item ${fn:contains(currentUrl, '/board/guide/path') ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/board/guide/path" class="sidebar-link">4. 오시는 길</a>
        </li>
    </ul>
</div>