<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- 현재 페이지 URL 가져오기 --%>
<c:set var="currentUrl" value="${pageContext.request.requestURI}" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/sidebar.css">
<div class="sidebar">
    <h2 class="sidebar-title">이용안내</h2>
    <ul class="sidebar-menu">
        <li class="sidebar-menu-item ${fn:contains(currentUrl, '/board/info/notice') ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/board/info/notice" class="sidebar-link">1. 공지사항</a>
            <ul class="sidebar-submenu">
                <li class="sidebar-submenu-item ${fn:contains(currentUrl, '/board/info/notice/list') ? 'active' : ''}">
                    <a href="${pageContext.request.contextPath}/board/info/notice/list" class="sidebar-sublink">공지사항</a>
                </li>
                <li class="sidebar-submenu-item ${fn:contains(currentUrl, '/board/info/notice/event') ? 'active' : ''}">
                    <a href="${pageContext.request.contextPath}/board/info/notice/event" class="sidebar-sublink">교육/행사</a>
                </li>
            </ul>
        </li>
        <li class="sidebar-menu-item ${fn:contains(currentUrl, '/board/info/site') ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/board/info/site" class="sidebar-link">2. 시설 이용 안내</a>
            <ul class="sidebar-submenu">
                <li class="sidebar-submenu-item ${fn:contains(currentUrl, '/board/info/site/loan') ? 'active' : ''}">
                    <a href="${pageContext.request.contextPath}/board/info/site/loan" class="sidebar-sublink">도서 대출/반납 안내</a>
                </li>
                <li class="sidebar-submenu-item ${fn:contains(currentUrl, '/board/info/site/reading-room') ? 'active' : ''}">
                    <a href="${pageContext.request.contextPath}/board/info/site/reading-room" class="sidebar-sublink">열람실 이용 안내</a>
                </li>
            </ul>
        </li>
        <li class="sidebar-menu-item ${fn:contains(currentUrl, '/board/info/faq') ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/board/info/faq" class="sidebar-link">3. 자주 묻는 질문</a>
            <ul class="sidebar-submenu">
                <li class="sidebar-submenu-item ${fn:contains(currentUrl, '/board/info/faq/book') ? 'active' : ''}">
                    <a href="${pageContext.request.contextPath}/board/info/faq/member" class="sidebar-sublink">도서 FAQ</a>
                </li>
                <li class="sidebar-submenu-item ${fn:contains(currentUrl, '/board/info/faq/loan') ? 'active' : ''}">
                    <a href="${pageContext.request.contextPath}/board/info/faq/loan" class="sidebar-sublink">대출/반납 FAQ</a>
                </li>
                <li class="sidebar-submenu-item ${fn:contains(currentUrl, '/board/info/faq/reading-room') ? 'active' : ''}">
                    <a href="${pageContext.request.contextPath}/board/info/faq/reading-room" class="sidebar-sublink">열람실 FAQ</a>
                </li>
                <li class="sidebar-submenu-item ${fn:contains(currentUrl, '/board/info/faq/site') ? 'active' : ''}">
                    <a href="${pageContext.request.contextPath}/board/info/faq/site" class="sidebar-sublink">사이트 이용 FAQ</a>
                </li>
            </ul>
        </li>
        <li class="sidebar-menu-item ${fn:contains(currentUrl, '/board/info/qa') ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/board/info/qa" class="sidebar-link">1:1 문의</a>
            <ul class="sidebar-submenu">
                <li class="sidebar-submenu-item ${fn:contains(currentUrl, '/board/info/qa/write') ? 'active' : ''}">
                    <a href="${pageContext.request.contextPath}/board/info/qa/write" class="sidebar-sublink">1:1 문의 작성</a>
                </li>
                <li class="sidebar-submenu-item ${fn:contains(currentUrl, '/board/info/qa/list') ? 'active' : ''}">
                    <a href="${pageContext.request.contextPath}/board/info/qa/list" class="sidebar-sublink">1:1 문의 내역</a>
                </li>
            </ul>
        </li>
    </ul>
</div>