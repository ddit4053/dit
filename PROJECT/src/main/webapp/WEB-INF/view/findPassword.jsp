<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>비밀번호 찾기</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/main.css">
    <style>
        .input-error {
            border: 1px solid red !important;
        }
        .error-text {
            color: red;
            font-size: 12px;
            margin-top: 5px;
            display: none;
        }
        .btn-container {
            display: flex;
            justify-content: space-between;
            margin-top: 20px;
        }
        .btn-container .btn {
            width: 48%;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .result-container {
            margin-top: 20px;
            padding: 15px;
            background-color: #f5f5f5;
            border-radius: 5px;
            text-align: center;
        }
        .result-container.success {
            background-color: #e8f5e9;
        }
        .result-container.error {
            background-color: #ffebee;
        }
        .reset-container {
            margin-top: 20px;
        }
        .success-message {
            color: #4CAF50;
            background-color: #e8f5e9;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 15px;
        }
        .error-message {
            color: #f44336;
            background-color: #ffebee;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>비밀번호 찾기</h2>
        
        <!-- 성공/오류 메시지 -->
        <c:if test="${not empty successMessage}">
            <div class="success-message">${successMessage}</div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="error-message">${errorMessage}</div>
        </c:if>
        
        <c:choose>
            <%-- 1. 초기 단계: 아이디와 이메일 입력 --%>
            <c:when test="${empty emailSent and empty verified and empty found}">
                <p>회원가입 시 입력한 아이디와 이메일을 입력하세요.</p>
                <form action="${pageContext.request.contextPath}/member/findPassword.do" method="post" id="findPasswordForm" onsubmit="return validateFindForm()">
                    <input type="hidden" name="action" value="sendCode">
                    
                    <div class="form-group">
                        <label for="memberId">아이디</label>
                        <input type="text" id="memberId" name="memberId" required>
                        <p id="memberIdError" class="error-text">아이디를 입력해주세요.</p>
                    </div>
                    
                    <div class="form-group">
                        <label for="memberEmail">이메일</label>
                        <input type="email" id="memberEmail" name="memberEmail" required>
                        <p id="memberEmailError" class="error-text">유효한 이메일 주소를 입력해주세요.</p>
                    </div>
                    
                    <div class="btn-container">
                        <button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/'" style="background-color: #ccc;">취소</button>
                        <button type="submit" class="btn">인증 코드 발송</button>
                    </div>
                </form>
            </c:when>
            
            <%-- 2. 인증 코드 입력 단계 --%>
            <c:when test="${emailSent eq true and empty verified and empty found}">
                <p>이메일로 발송된 인증 코드를 입력하세요.</p>
                <form action="${pageContext.request.contextPath}/member/findPassword.do" method="post" id="verifyCodeForm">
                    <input type="hidden" name="action" value="verify">
                    <input type="hidden" name="memberId" value="${memberId}">
                    <input type="hidden" name="memberEmail" value="${memberEmail}">
                    
                    <div class="form-group">
                        <label for="verificationCode">인증 코드</label>
                        <input type="text" id="verificationCode" name="verificationCode" required>
                    </div>
                    
                    <div class="btn-container">
                        <button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/member/findPassword.do'" style="background-color: #ccc;">다시 시도</button>
                        <button type="submit" class="btn">확인</button>
                    </div>
                </form>
            </c:when>
            
            <%-- 3. 비밀번호 재설정 단계 --%>
            <c:when test="${(verified eq true or found eq true) and not empty memberNo and not empty verifyToken}">
                <p>새로운 비밀번호를 설정해주세요.</p>
                <form action="${pageContext.request.contextPath}/member/resetPassword.do" method="post" id="resetPasswordForm" onsubmit="return validateResetForm()">
                    <input type="hidden" name="memberNo" value="${memberNo}">
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
                        <button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/'" style="background-color: #ccc;">취소</button>
                        <button type="submit" class="btn">비밀번호 변경</button>
                    </div>
                </form>
            </c:when>
            
            <%-- 4. 기타 상황 --%>
            <c:otherwise>
                <c:if test="${not empty notFound}">
                    <div class="result-container error">
                        <h3>비밀번호 찾기 실패</h3>
                        <p>${notFound}</p>
                        <div class="btn-container">
                            <button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/member/register.do'">회원가입</button>
                            <button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/member/findPassword.do'">다시 시도</button>
                        </div>
                    </div>
                </c:if>
            </c:otherwise>
        </c:choose>
    </div>
    
    <script>
        // 아이디 유효성 검사
        function validateId() {
            const idInput = document.getElementById('memberId');
            const errorElement = document.getElementById('memberIdError');
            
            if (!idInput || !errorElement) return true; // 요소가 없으면 통과
            
            if (idInput.value.trim() === '') {
                idInput.classList.add('input-error');
                errorElement.style.display = 'block';
                return false;
            }
            
            idInput.classList.remove('input-error');
            errorElement.style.display = 'none';
            return true;
        }
        
        // 이메일 유효성 검사
        function validateEmail() {
            const emailInput = document.getElementById('memberEmail');
            const errorElement = document.getElementById('memberEmailError');
            
            if (!emailInput || !errorElement) return true; // 요소가 없으면 통과
            
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
        
        // 비밀번호 찾기 폼 유효성 검사
        function validateFindForm() {
            const isIdValid = validateId();
            const isEmailValid = validateEmail();
            
            return isIdValid && isEmailValid;
        }
        
        // 새 비밀번호 유효성 검사
        function validateNewPassword() {
            const pwInput = document.getElementById('newPassword');
            const errorElement = document.getElementById('newPasswordError');
            
            if (!pwInput || !errorElement) return true; // 요소가 없으면 통과
            
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
        
        // 비밀번호 확인 유효성 검사
        function validateConfirmPassword() {
            const pwInput = document.getElementById('newPassword');
            const confirmInput = document.getElementById('confirmPassword');
            const errorElement = document.getElementById('confirmPasswordError');
            
            if (!pwInput || !confirmInput || !errorElement) return true; // 요소가 없으면 통과
            
            if (pwInput.value !== confirmInput.value) {
                confirmInput.classList.add('input-error');
                errorElement.style.display = 'block';
                return false;
            }
            
            confirmInput.classList.remove('input-error');
            errorElement.style.display = 'none';
            return true;
        }
        
        // 비밀번호 재설정 폼 유효성 검사
        function validateResetForm() {
            const isPasswordValid = validateNewPassword();
            const isConfirmValid = validateConfirmPassword();
            
            return isPasswordValid && isConfirmValid;
        }
        
        // 이벤트 리스너 등록
        window.onload = function() {
            // 비밀번호 찾기 폼 이벤트 리스너
            const memberIdInput = document.getElementById('memberId');
            const memberEmailInput = document.getElementById('memberEmail');
            
            if (memberIdInput) {
                memberIdInput.addEventListener('input', validateId);
            }
            
            if (memberEmailInput) {
                memberEmailInput.addEventListener('input', validateEmail);
            }
            
            // 비밀번호 재설정 폼 이벤트 리스너
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