<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>${pageTitle}</title>
  <!-- 공통 CSS -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/common.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/main.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/sidebar.css"/>
  <!-- 차트 전용 CSS -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/admin/loan_return/chart.css"/>
 <!--  <script>
    // sidebar.js 에서 사용할 전역 변수
    var contextPath = '${pageContext.request.contextPath}';
    var currentURL  = window.location.pathname;       // EL 대신 실제 브라우저 URL
    var isAdmin     = '${sessionScope.role}' === 'ADMIN';
  </script> -->
</head>
<body>

  <!-- header / top-nav -->
  <jsp:include page="/WEB-INF/view/header.jsp"/>
  <jsp:include page="/WEB-INF/view/nav.jsp"/>

  <!-- 드롭다운 메뉴 아래 여백 -->
  <div class="dropdown-spacer"></div>

  <!-- 좌측 사이드바 -->
  <jsp:include page="/WEB-INF/view/sidebar.jsp"/>

  <!-- 우측 메인컨텐츠 -->
  <main class="main-content">
    <div class="container">
      <!-- 페이지 타이틀 + breadcrumb -->
      <div class="page-header">
        <h1>${pageTitle}</h1>
        <div class="breadcrumb">
          <a href="${contextPath}/main.do">홈</a> &gt;
          <a href="${contextPath}/admin/loans">대출/반납 관리</a> &gt;
          <span>${breadcrumbTitle}</span>
        </div>
      </div>
      <!-- 차트/캘린더 탭 -->
      <div class="subnav">
        <ul>
          <li class="${activeTab=='chart'?'active':''}">
            <a href="${contextPath}/admin/loans/stats/chart">차트</a>
          </li>
          <li class="${activeTab=='calendar'?'active':''}">
            <a href="${contextPath}/admin/loans/stats/calendar">캘린더</a>
          </li>
        </ul>
      </div>
      <!-- 실제 content include -->
      <div class="content-layout">
        <div class="main-content-area">
          <jsp:include page="${contentPage}"/>
        </div>
      </div>
    </div>
  </main>

  <!-- footer -->
  <jsp:include page="/WEB-INF/view/footer.jsp"/>
  //<script src="${contextPath}/resource/js/sidebar.js"></script>
</body>
</html>
