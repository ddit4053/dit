<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>비밀번호 찾기</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/user/login.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/user/findPassword.css">
   
</head>
<body>
    <div class="container">
        <h2>비밀번호 찾기</h2>
        
        <c:if test="${not empty successMessage}">
            <div class="success-message">${successMessage}</div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="error-message">${errorMessage}</div>
        </c:if>
        
        <c:choose>
            <c:when test="${empty emailSent and empty verified and empty found}">
                <p>회원가입 시 입력한 아이디와 이메일을 입력하세요.</p>
                <form action="${pageContext.request.contextPath}/user/findPassword.do" method="post" id="findPasswordForm" onsubmit="return validateFindForm()">
                    <input type="hidden" name="action" value="sendCode">
                    
                    <div class="form-group">
                        <label for="userId">아이디</label>
                        <input type="text" id="userId" name="userId" required>
                        <p id="userIdError" class="error-text">아이디를 입력해주세요.</p>
                    </div>
                    
                    <div class="form-group">
                        <label for="email">이메일</label>
                        <input type="email" id="email" name="email" required>
                        <p id="emailError" class="error-text">유효한 이메일 주소를 입력해주세요.</p>
                    </div>
                    
                    <div class="btn-container">
                        <button type="submit" class="btn">인증 코드 발송</button>
                        <button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/user/login.do'" style="background-color: #ccc;">취소</button>
                    </div>
                </form>
            </c:when>
            
            <c:when test="${emailSent eq true and empty verified and empty found}">
                <p>이메일로 발송된 인증 코드를 입력하세요.</p>
                <form action="${pageContext.request.contextPath}/user/findPassword.do" method="post" id="verifyCodeForm">
                    <input type="hidden" name="action" value="verify">
                    <input type="hidden" name="userId" value="${userId}">
                    <input type="hidden" name="email" value="${email}">
                    
                    <div class="form-group">
                        <label for="verificationCode">인증 코드</label>
                        <input type="text" id="verificationCode" name="verificationCode" required>
                    </div>
                    
                    <div class="btn-container">
                        <button type="submit" class="btn">확인</button>
                        <button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/user/findPassword.do'" style="background-color: #ccc;">다시 시도</button>
                    </div>
                </form>
            </c:when>
            
            <c:when test="${(verified eq true or found eq true) and not empty userNo and not empty verifyToken}">
                <p>새로운 비밀번호를 설정해주세요.</p>
                <form action="${pageContext.request.contextPath}/user/resetPassword.do" method="post" id="resetPasswordForm" onsubmit="return validateResetForm()">
                    <input type="hidden" name="userNo" value="${userNo}">
                    <input type="hidden" name="verifyToken" value="${verifyToken}">
                    
                    <div class="form-group">
                        <label for="newPassword">새 비밀번호</label>
                        <input type="password" id="newPassword" name="newPassword" required>
                        <p id="newPasswordError" class="error-text">비밀번호는 영문자, 숫자, 특수문자를 혼합하여 8~12자 이내로 입력해주세요.</p>
                    </div>
                    
                    <div class="form-group">
                        <label for="confirmPassword">비밀번호 확인</label>
                        <input type="password" id="confirmPassword" name="confirmPassword" required>
                        <p id="confirmPasswordError" class="error-text">비밀번호가 일치하지 않습니다.</p>
                    </div>
                    
                    <div class="btn-container">
                        <button type="submit" class="btn">비밀번호 변경</button>
                        <button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/user/findPassword.do'" style="background-color: #ccc;">취소</button>
                    </div>
                </form>
            </c:when>
            
            <c:otherwise>
                <c:if test="${not empty notFound}">
                    <div class="result-container error">
                        <h3>비밀번호 찾기 실패</h3>
                        <p>${notFound}</p>
                        <div class="btn-container">
                            <button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/user/register.do'">회원가입</button>
                            <button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/user/findPassword.do'">다시 시도</button>
                        </div>
                    </div>
                </c:if>
            </c:otherwise>
        </c:choose>
    </div>
    
    <script>
        function validateId() {
            const idInput = document.getElementById('userId');
            const errorElement = document.getElementById('userIdError');
            
            if (!idInput || !errorElement) return true; 
            
            if (idInput.value.trim() === '') {
                idInput.classList.add('input-error');
                errorElement.style.display = 'block';
                return false;
            }
            
            idInput.classList.remove('input-error');
            errorElement.style.display = 'none';
            return true;
        }
        
        function validateEmail() {
            const emailInput = document.getElementById('email');
            const errorElement = document.getElementById('emailError');
            
            if (!emailInput || !errorElement) return true; 
            
            const pattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
            
            if (!pattern.test(emailInput.value)) {
                emailInput.classList.add('input-error');
                errorElement.style.display = 'block';
                return false;
            }
            
            emailInput.classList.remove('input-error');
            errorElement.style.display = 'none';
            return true;
        }
        
        function validateFindForm() {
            const isIdValid = validateId();
            const isEmailValid = validateEmail();
            
            return isIdValid && isEmailValid;
        }
        
        function validateNewPassword() {
            const pwInput = document.getElementById('newPassword');
            const errorElement = document.getElementById('newPasswordError');
            
            if (!pwInput || !errorElement) return true;
            
            const pattern = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,12}$/;
            
            if (!pattern.test(pwInput.value)) {
                pwInput.classList.add('input-error');
                errorElement.style.display = 'block';
                return false;
            }
            
            pwInput.classList.remove('input-error');
            errorElement.style.display = 'none';
            return true;
        }
        
        function validateConfirmPassword() {
            const pwInput = document.getElementById('newPassword');
            const confirmInput = document.getElementById('confirmPassword');
            const errorElement = document.getElementById('confirmPasswordError');
            
            if (!pwInput || !confirmInput || !errorElement) return true;
            
            if (pwInput.value !== confirmInput.value) {
                confirmInput.classList.add('input-error');
                errorElement.style.display = 'block';
                return false;
            }
            
            confirmInput.classList.remove('input-error');
            errorElement.style.display = 'none';
            return true;
        }
        
        function validateResetForm() {
            const isPasswordValid = validateNewPassword();
            const isConfirmValid = validateConfirmPassword();
            const isPasswordNew = checkPasswordIsNew();
            
            return isPasswordValid && isConfirmValid && isPasswordNew;
            
        }
        
        function checkPasswordIsNew() {
            const newPasswordInput = document.getElementById('newPassword');
            const oldPasswordInput = document.getElementById('oldPassword'); 
            const errorElement = document.getElementById('newPasswordError');
            
            if (newPasswordInput.value === oldPasswordInput.value) {
                newPasswordInput.classList.add('input-error');
                errorElement.textContent = "새 비밀번호는 기존 비밀번호와 달라야 합니다.";
                errorElement.style.display = 'block';
                return false;
            }
            
            return true;
        }
        
        window.onload = function() {
            const userIdInput = document.getElementById('userId');
            const emailInput = document.getElementById('email');
            
            if (userIdInput) {
                userIdInput.addEventListener('input', validateId);
            }
            
            if (emailInput) {
                emailInput.addEventListener('input', validateEmail);
            }
            
            const newPasswordInput = document.getElementById('newPassword');
            const confirmPasswordInput = document.getElementById('confirmPassword');
            
            if (newPasswordInput) {
                newPasswordInput.addEventListener('input', validateNewPassword);
            }
            
            if (confirmPasswordInput) {
                confirmPasswordInput.addEventListener('input', validateConfirmPassword);
            }
        };
    </script>
</body>
</html>