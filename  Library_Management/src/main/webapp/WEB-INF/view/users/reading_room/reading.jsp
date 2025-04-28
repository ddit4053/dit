<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="../../header.jsp" />
<jsp:include page="../../nav.jsp" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/board.css">

<div class="main-content">
    <div class="container">
        <div class="page-header">
            <h1 class="page-title">${pageTitle}</h1>
            <div class="breadcrumb">
                <a href="${pageContext.request.contextPath}/main.do">홈</a> > 
                <span>${breadcrumbTitle}</span>
            </div>
        </div>
        
        <div class="content-layout">
            <!-- 사이드바 포함 -->
            <jsp:include page="../../sidebar.jsp" />
            
            <!-- 메인 콘텐츠 영역 -->
            <div class="main-content-area">
                <jsp:include page="${contentPage}" /> 
            </div>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/resource/js/breadcrumb.js"></script>
<jsp:include page="../../footer.jsp" />