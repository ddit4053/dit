<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원가입</title>
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
    </style>
</head>
<body>
    <div class="container">
        <h2>회원가입</h2>
        
        <!-- 오류 메시지 표시 -->
        <c:if test="${not empty errorMessage}">
            <div class="error-message">${errorMessage}</div>
        </c:if>
        
        <!-- 회원가입 폼 -->
        <form action="${pageContext.request.contextPath}/member/register.do" method="post" id="registerForm" onsubmit="return validateForm()">
            <div class="form-group">
                <label for="memberId">아이디 *</label>
                <div style="display: flex; gap: 10px;">
                    <input type="text" id="memberId" name="memberId" maxlength="12" required style="flex: 1;">
                    <button type="button" class="btn" onclick="checkIdDuplicate()" style="width: 100px;">중복확인</button>
                </div>
                <p id="memberIdError" class="error-text">아이디는 영문자와 숫자를 혼합하여 4~12자 이내로 입력해주세요.</p>
                <input type="hidden" id="idChecked" name="idChecked" value="false">
            </div>
            
            <div class="form-group">
                <label for="memberPw">비밀번호 *</label>
                <input type="password" id="memberPw" name="memberPw" maxlength="12" required>
                <p id="memberPwError" class="error-text">비밀번호는 영문자, 숫자, 특수문자를 혼합하여 8~12자 이내로 입력해주세요.</p>
            </div>
            
            <div class="form-group">
                <label for="memberPwConfirm">비밀번호 확인 *</label>
                <input type="password" id="memberPwConfirm" name="memberPwConfirm" maxlength="12" required>
                <p id="memberPwConfirmError" class="error-text">비밀번호가 일치하지 않습니다.</p>
            </div>
            
            <div class="form-group">
                <label for="memberName">이름 *</label>
                <input type="text" id="memberName" name="memberName" required>
                <p id="memberNameError" class="error-text">이름을 입력해주세요.</p>
            </div>
            
            <div class="form-group">
                <label for="memberEmail">이메일 *</label>
                <input type="email" id="memberEmail" name="memberEmail" required>
                <p id="memberEmailError" class="error-text">유효한 이메일 주소를 입력해주세요.</p>
            </div>
            
            <div class="form-group">
                <label for="memberTel">전화번호</label>
                <input type="text" id="memberTel" name="memberTel" placeholder="예: 010-1234-5678">
                <p id="memberTelError" class="error-text">유효한 전화번호 형식이 아닙니다.</p>
            </div>
            
            <div class="btn-container">
                <button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/'" style="background-color: #ccc;">취소</button>
                <button type="submit" class="btn">가입하기</button>
            </div>
        </form>
    </div>
    
    <script>
        // 유효성 검사 함수
        function validateInput(inputField, errorId, pattern, message) {
            const value = inputField.value;
            const errorElement = document.getElementById(errorId);
            
            if (!pattern.test(value)) {
                inputField.classList.add('input-error');
                errorElement.textContent = message;
                errorElement.style.display = 'block';
                return false;
            }
            
            inputField.classList.remove('input-error');
            errorElement.style.display = 'none';
            return true;
        }
        
        // 아이디 유효성 검사
        function validateId() {
            const idInput = document.getElementById('memberId');
            const pattern = /^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{4,12}$/;
            return validateInput(idInput, 'memberIdError', pattern, 
                '아이디는 영문자와 숫자를 혼합하여 4~12자 이내로 입력해주세요.');
        }
        
        // 비밀번호 유효성 검사
        function validatePassword() {
            const pwInput = document.getElementById('memberPw');
            const pattern = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,12}$/;
            return validateInput(pwInput, 'memberPwError', pattern, 
                '비밀번호는 영문자, 숫자, 특수문자를 혼합하여 8~12자 이내로 입력해주세요.');
        }
        
        // 비밀번호 확인 유효성 검사
        function validatePasswordConfirm() {
            const pwInput = document.getElementById('memberPw');
            const pwConfirmInput = document.getElementById('memberPwConfirm');
            const errorElement = document.getElementById('memberPwConfirmError');
            
            if (pwInput.value !== pwConfirmInput.value) {
                pwConfirmInput.classList.add('input-error');
                errorElement.style.display = 'block';
                return false;
            }
            
            pwConfirmInput.classList.remove('input-error');
            errorElement.style.display = 'none';
            return true;
        }
        
        // 이메일 유효성 검사
        function validateEmail() {
            const emailInput = document.getElementById('memberEmail');
            const pattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
            return validateInput(emailInput, 'memberEmailError', pattern, 
                '유효한 이메일 주소를 입력해주세요.');
        }
        
        // 전화번호 유효성 검사 (선택 필드이므로 값이 있을 때만 검사)
        function validatePhone() {
            const phoneInput = document.getElementById('memberTel');
            if (phoneInput.value.trim() === '') {
                return true; // 값이 없으면 통과
            }
            
            const pattern = /^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$/;
            return validateInput(phoneInput, 'memberTelError', pattern, 
                '유효한 전화번호 형식이 아닙니다 (예: 010-1234-5678).');
        }
        
        // 폼 전체 유효성 검사
        function validateForm() {
            const isIdValid = validateId();
            const isPwValid = validatePassword();
            const isPwConfirmValid = validatePasswordConfirm();
            const isEmailValid = validateEmail();
            const isPhoneValid = validatePhone();
            const idChecked = document.getElementById('idChecked').value === 'true';
            
            if (!idChecked) {
                alert('아이디 중복 확인을 해주세요.');
                return false;
            }
            
            return isIdValid && isPwValid && isPwConfirmValid && isEmailValid && isPhoneValid;
        }
        
        // 아이디 중복 확인
        function checkIdDuplicate() {
            const idInput = document.getElementById('memberId');
            const idCheckedInput = document.getElementById('idChecked');
            
            if (!validateId()) {
                return;
            }
            
            // AJAX를 사용하여 서버에 아이디 중복 확인 요청
            const xhr = new XMLHttpRequest();
            xhr.open('GET', '${pageContext.request.contextPath}/member/checkId.do?memberId=' + idInput.value, true);
            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4) {
                    if (xhr.status === 200) {
                        const response = JSON.parse(xhr.responseText);
                        if (response.available) {
                            alert('사용 가능한 아이디입니다.');
                            idCheckedInput.value = 'true';
                            idInput.readOnly = true; // 중복 확인 후 아이디 변경 방지
                        } else {
                            alert('이미 사용 중인 아이디입니다.');
                            idCheckedInput.value = 'false';
                         }
                    } else {
                        alert('서버 오류가 발생했습니다. 다시 시도해주세요.');
                    }
                }
            };
            xhr.send();
        }
        
        // 이벤트 리스너 등록
        window.onload = function() {
            document.getElementById('memberId').addEventListener('input', function() {
                document.getElementById('idChecked').value = 'false'; // 아이디 변경 시 중복 확인 초기화
                validateId();
            });
            
            document.getElementById('memberPw').addEventListener('input', validatePassword);
            document.getElementById('memberPwConfirm').addEventListener('input', validatePasswordConfirm);
            document.getElementById('memberEmail').addEventListener('input', validateEmail);
            document.getElementById('memberTel').addEventListener('input', validatePhone);
        };
    </script>
</body>
</html>