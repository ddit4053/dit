<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- 현재 페이지 URL 가져오기 --%>
<c:set var="currentURL" value="${requestScope['jakarta.servlet.forward.request_uri']}" />
<c:if test="${empty currentURL}">
    <c:set var="currentURL" value="${pageContext.request.requestURI}" />
</c:if>

<%-- 관리자 여부 확인 --%>
<c:set var="isAdmin" value="${sessionScope.role eq 'ADMIN'}" />

<%-- 사이드바 컨테이너 --%>
<div class="sidebar">
    <div id="sidebarNav">
        <%-- 사이드바 내용은 JavaScript로 동적 생성 --%>
    </div>
</div>

<%-- JavaScript 변수 초기화: 현재 URL과 관리자 여부 --%>
<script>
    // 전역변수 설정 - 현재 URL과 관리자 여부를 JavaScript 변수로 설정
    const isAdmin = ${isAdmin};
    const currentURL = '${currentURL}';
    const contextPath = '${pageContext.request.contextPath}';
</script>

<%-- CSS와 JavaScript 파일 포함 --%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/sidebar.css">
<script src="${pageContext.request.contextPath}/resource/js/sidebar.js"></script>