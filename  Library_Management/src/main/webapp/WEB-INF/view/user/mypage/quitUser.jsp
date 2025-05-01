<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>회원탈퇴</title>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
	<div class="content-wrapper">
       
        <div class="tab-menu">
            <a href="${pageContext.request.contextPath}/user/mypage/updateInfo.do">회원정보 수정</a>
            <a href="${pageContext.request.contextPath}/user/mypage/changePassword.do">비밀번호 변경</a>
            <a href="${pageContext.request.contextPath}/user/mypage/quitUser.do">회원탈퇴</a>
        </div>
        
        <div class="form-container">
           
            <c:if test="${not empty message}">
                <div class="message success-message">${message}</div>
            </c:if>
            <c:if test="${not empty errorMessage}">
                <div class="message error-message">${errorMessage}</div>
            </c:if>
            
            <form action="${pageContext.request.contextPath}/user/mypage/quitUser.do" method="post" id="quitForm">
                
                <div class="form-group">
	                <label for="name">이름</label>
	                <p>${user.name}</p>
                </div>
                
                <div class="form-group">
	                <label for="userId">아이디</label>
	                <p>${user.userId}</p>
                </div>
                
                
                <div class="form-group">
                    <label for="currentPassword">비밀번호</label>
                    <div class="form-input">
                        <input type="password" id="currentPassword" name="currentPassword" class="form-control" required>
                    </div>
                </div>
                
                <div class="form-group" style="text-align: center; margin-top: 20px;">
                    <button type="submit" class="btn btn-primary">회원 탈퇴</button>
                    <button type="button" class="btn btn-secondary" onclick="history.back()">취소</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        $(document).ready(function() {
           
            $("#quitForm").on("submit", function(e) {
                var currentPassword = $("#currentPassword").val().trim();
                
                if (currentPassword === "") {
                    alert("모든 필드를 입력해주세요.");
                    e.preventDefault();
                    return false;
                }
                
                return true;
            });
            
        });
        
    </script>
</body>
</html>