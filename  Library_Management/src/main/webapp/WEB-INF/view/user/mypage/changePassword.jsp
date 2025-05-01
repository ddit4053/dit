<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>비밀번호 변경</title>
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
            
            <form action="${pageContext.request.contextPath}/user/mypage/changePassword.do" method="post" id="passwordForm">
                
                <div class="form-group">
	                <label for="name">이름</label>
	                <p>${user.name}</p>
                </div>
                
                <div class="form-group">
	                <label for="userId">아이디</label>
	                <p>${user.userId}</p>
                </div>
                
                <div class="form-group">
                    <label for="currentPassword">현재 비밀번호</label>
                    <div class="form-input">
                        <input type="password" id="currentPassword" name="currentPassword" class="form-control" required>
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="newPassword">새 비밀번호</label>
                    <div class="form-input">
                        <input type="password" id="newPassword" name="newPassword" class="form-control" required>
                        <div id="passwordError" class="error-text">비밀번호는 영문자, 숫자, 특수문자를 포함하여 8~12자 이내로 입력해주세요.</div>
                    </div>
                    <div class="password-rules">
                        * 8~12자 이내의 영문자, 숫자, 특수문자를 포함하여 입력해주세요.
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="confirmPassword">새 비밀번호 확인</label>
                    <div class="form-input">
                        <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" required>
                        <div id="confirmPasswordError" class="error-text">비밀번호가 일치하지 않습니다.</div>
                    </div>
                </div>
                
                <div class="form-group" style="text-align: center; margin-top: 20px;">
                    <button type="submit" class="btn btn-primary">비밀번호 변경</button>
                    <button type="button" class="btn btn-secondary" onclick="history.back()">취소</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        $(document).ready(function() {
           
            $("#passwordForm").on("submit", function(e) {
                var currentPassword = $("#currentPassword").val().trim();
                var newPassword = $("#newPassword").val().trim();
                var confirmPassword = $("#confirmPassword").val().trim();
                
                if (currentPassword === "" || newPassword === "" || confirmPassword === "") {
                    alert("모든 필드를 입력해주세요.");
                    e.preventDefault();
                    return false;
                }
                if (currentPassword === newPassword) {
                    alert("새 비밀번호는 현재 비밀번호와 달라야 합니다.");
                    e.preventDefault();
                    return false;
                }
                
                var passwordRegex = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,12}$/;
                if (!passwordRegex.test(newPassword)) {
                    alert("새 비밀번호는 영문자, 숫자, 특수문자를 포함하여 8~12자 이내로 입력해주세요.");
                    e.preventDefault();
                    return false;
                }
                
                if (newPassword !== confirmPassword) {
                    alert("새 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
                    e.preventDefault();
                    return false;
                }
                
                return true;
            });
            
            $('#newPassword').on('input', function() {
                const password = $(this).val();
                const parent = $(this).closest('.form-input');
                
                const passwordPattern = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,12}$/;
                
                if (password && !passwordPattern.test(password)) {
                    parent.addClass('is-error');
                    $('#passwordError').show();
                } else {
                    parent.removeClass('is-error');
                    $('#passwordError').hide();
                }
                
                if ($('#confirmPassword').val()) {
                    checkPasswordMatch();
                }
            });
            
            $('#confirmPassword').on('input', function() {
                checkPasswordMatch();
            });
            
        });
        
        function checkPasswordMatch() {
            const password = $('#newPassword').val();
            const confirmPassword = $('#confirmPassword').val();
            const parent = $('#confirmPassword').closest('.form-input');
            
            if (confirmPassword && password !== confirmPassword) {
                parent.addClass('is-error');
                $('#confirmPasswordError').show();
            } else {
                parent.removeClass('is-error');
                $('#confirmPasswordError').hide();
            }
        }
    </script>
</body>
</html>