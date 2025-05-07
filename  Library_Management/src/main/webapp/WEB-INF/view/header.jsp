<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>책GPT 도서관 시스템</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/common.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/main.css">
    <!-- 다크모드를 위한 아이콘 폰트 추가 -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>

<body data-role="${sessionScope.role != null ? sessionScope.role : 'USER'}">
    <header class="main-header">
        <div class="header-container">
            <div class="logo">
                <a href="${pageContext.request.contextPath}/main.do">
                    <h1>책GPT 도서관 시스템</h1>
                </a>
            </div>
			
            <div class="user-menu">
            <%--contextPath 이하 URL은 각 @WebServlet 어노테이션 URL에 맞춰 수정 --%>
            	  <c:choose>
				    <%-- 비로그인 상태 --%>
				    <c:when test="${empty sessionScope.user}">
				      <a href="${pageContext.request.contextPath}/user/login.do">로그인</a>
				      <a href="${pageContext.request.contextPath}/user/register.do">회원가입</a>
    				</c:when>
    				
    				<%--로그인 상태 --%>
				    <c:otherwise>
 		                <%-- 여러 환영 메시지 중 하나를 랜덤하게 선택 --%>
						<%
                        String[] greetings = {
                          "오늘의 독서 영웅",
                          "책의 세계로 오신 것을 환영합니다",
                          "반갑습니다! 독서 모험가",
                          "지식의 바다에 오신 걸 환영합니다",
                          "오늘은 어떤 책과 함께하실건가요"
                        };
                        int randomIndex = (int)(Math.random() * greetings.length);
                        pageContext.setAttribute("greeting", greetings[randomIndex]);
                      %>
                       <p class="welcome-message"><span class="user-name">${sessionScope.user.name}</span>님, ${greeting}</p>
				      <c:choose>
				        <%--관리자  --%> 
				        <c:when test="${sessionScope.role eq 'ADMIN'}">
				          <a href="${pageContext.request.contextPath}/user/logout.do">로그아웃</a>
				          <button class="theme-toggle" aria-label="테마 변경">
				          	<i class="fas fa-moon"></i>
				          </button>	
				        </c:when>
				
				        <%-- 일반 사용자 --%>
				        <c:otherwise>
				          <a href="${pageContext.request.contextPath}/user/mypage.do">마이페이지</a>
				          <a href="${pageContext.request.contextPath}/user/logout.do">로그아웃</a>
				        </c:otherwise>
				      </c:choose>
				    </c:otherwise>
				  </c:choose>
            </div>
        </div>
    </header>
    
<!-- 다크모드 토글 스크립트 -->
<c:if test="${sessionScope.role eq 'ADMIN'}">
<script>
  document.addEventListener("DOMContentLoaded", function() {
    const themeToggle = document.querySelector(".theme-toggle");
    
    // 저장된 테마 확인
    const savedTheme = localStorage.getItem("theme");
    
    // 초기 상태 설정
    if (savedTheme === "dark") {
      document.body.classList.add("dark-mode");
      themeToggle.innerHTML = '<i class="fas fa-sun"></i>';
    } else {
      themeToggle.innerHTML = '<i class="fas fa-moon"></i>';
    }
    
    // 클릭 이벤트 처리
    themeToggle.addEventListener("click", function() {
      // 바디에 트랜지션 클래스 추가
      document.body.classList.add("dark-mode-transition");
      
      // 다크모드 토글
      const isDarkMode = document.body.classList.toggle("dark-mode");
      
      // 아이콘 변경
      this.innerHTML = isDarkMode 
        ? '<i class="fas fa-sun"></i>' 
        : '<i class="fas fa-moon"></i>';
      
      // 테마 저장
      localStorage.setItem("theme", isDarkMode ? "dark" : "light");
      
      // 트랜지션 효과 후 클래스 제거
      setTimeout(() => {
        document.body.classList.remove("dark-mode-transition");
      }, 500);
    });
  });
</script>
</c:if>    