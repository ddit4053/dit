<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- 현재 페이지 URL 가져오기 --%>
<c:set var="currentUrl" value="${pageContext.request.requestURI}" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/sidebar.css">
<div class="sidebar">
    <h2 class="sidebar-title">열람실</h2>
    <ul class="sidebar-menu">
        <li class="sidebar-menu-item ${fn:contains(currentUrl, '/reading/room-status') ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/reading/room-status" class="sidebar-link">1. 열람실 현황</a>
        </li>

        <li class="sidebar-menu-item ${fn:contains(currentUrl, '/reading/room-booking') ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/reading/room-booking" class="sidebar-link">2. 열람실 예약</a>
        </li>
        
    </ul>
</div>