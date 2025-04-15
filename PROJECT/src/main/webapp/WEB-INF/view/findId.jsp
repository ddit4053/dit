<!-- findId.jsp 예시 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>아이디 찾기</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/main.css">
    <style>
        /* 여기에 스타일 추가 */
    </style>
</head>
<body>
    <div class="container">
        <h2>아이디 찾기</h2>
        
        <!-- 성공/오류 메시지 -->
        <c:if test="${not empty successMessage}">
            <div class="success-message">${successMessage}</div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="error-message">${errorMessage}</div>
        </c:if>
        
        <c:choose>
            <%-- 1. 초기 단계: 이름과 이메일 입력 --%>
            <c:when test="${empty emailSent and empty verified and empty foundId}">
                <p>회원가입 시 입력한 이름과 이메일을 입력하세요.</p>
                <form action="${pageContext.request.contextPath}/member/findId.do" method="post" id="findIdForm">
                    <input type="hidden" name="action" value="sendCode">
                    
                    <div class="form-group">
                        <label for="memberName">이름</label>
                        <input type="text" id="memberName" name="memberName" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="memberEmail">이메일</label>
                        <input type="email" id="memberEmail" name="memberEmail" required>
                    </div>
                    
                    <div class="btn-container">
                        <button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/'" style="background-color: #ccc;">취소</button>
                        <button type="submit" class="btn">인증 코드 발송</button>
                    </div>
                </form>
            </c:when>
            
            <%-- 2. 인증 코드 입력 단계 --%>
            <c:when test="${emailSent eq true and empty verified and empty foundId}">
                <p>이메일로 발송된 인증 코드를 입력하세요.</p>
                <form action="${pageContext.request.contextPath}/member/findId.do" method="post" id="verifyCodeForm">
                    <input type="hidden" name="action" value="verify">
                    <input type="hidden" name="memberEmail" value="${memberEmail}">
                    
                    <div class="form-group">
                        <label for="verificationCode">인증 코드</label>
                        <input type="text" id="verificationCode" name="verificationCode" required>
                    </div>
                    
                    <div class="btn-container">
                        <button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/member/findId.do'" style="background-color: #ccc;">다시 시도</button>
                        <button type="submit" class="btn">확인</button>
                    </div>
                </form>
            </c:when>
            
            <%-- 3. 결과 표시 단계 --%>
            <c:when test="${not empty foundId or not empty verified}">
                <div class="result-container success">
                    <h3>아이디 찾기 결과</h3>
                    <p>찾은 아이디: <strong>${foundId}</strong></p>
                    <div class="btn-container">
                        <button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/member/main.do'" style="background-color: #4CAF50;">로그인 페이지로</button>
                    </div>
                </div>
            </c:when>
            
            <%-- 4. 기타 상황 --%>
            <c:otherwise>
                <div class="result-container error">
                    <h3>아이디 찾기 실패</h3>
                    <p>일치하는 회원 정보가 없습니다.</p>
                    <div class="btn-container">
                        <button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/member/register.do'">회원가입</button>
                        <button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/member/findId.do'">다시 시도</button>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>