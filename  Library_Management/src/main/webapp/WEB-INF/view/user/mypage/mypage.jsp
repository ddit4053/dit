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
                <a href="${pageContext.request.contextPath}/user/mypage.do">마이페이지</a> >
                <span>${breadcrumbTitle}</span>
            </div>
        </div>
        
        <div class="content-layout">
        
            <jsp:include page="../../sidebar.jsp" />
            
            <div class="main-content-area">
	            <jsp:include page="${contentPage}" />
            </div>
        </div>
    </div>
</div>

<jsp:include page="../../footer.jsp" />