<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>로그인</title>
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
        .links-container {
            margin-top: 20px;
            text-align: center;
        }
        .links-container a {
            margin: 0 10px;
            color: #555;
            text-decoration: none;
            font-size: 14px;
        }
        .links-container a:hover {
            text-decoration: underline;
        }
        .success-message {
            color: #4CAF50;
            background-color: #e8f5e9;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 15px;
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>로그인</h2>
        
        <!-- 성공 메시지 표시 -->
        <c:if test="${param.status eq 'register-success'}">
            <div class="success-message">회원가입이 완료되었습니다. 로그인해주세요.</div>
        </c:if>
        <c:if test="${param.status eq 'password-reset-success'}">
            <div class="success-message">비밀번호가 성공적으로 변경되었습니다. 로그인해주세요.</div>
        </c:if>
        
        <!-- 오류 메시지 표시 -->
        <c:if test="${status eq 'fail'}">
            <div class="error-message">아이디 또는 비밀번호가 일치하지 않습니다.</div>
        </c:if>
        <c:if test="${status eq 'empty'}">
            <div class="error-message">${message}</div>
        </c:if>
        
        <!-- 일반 로그인 폼 -->
        <form action="${pageContext.request.contextPath}/member/login.do" method="post" id="loginForm" onsubmit="return validateAndSaveId()">
            <div class="form-group">
                <label for="id">아이디</label>
                <input type="text" id="id" name="id" maxlength="12" required>
                <p id="idError" class="error-text">아이디는 영문자와 숫자를 혼합하여 12자 이내로 입력해주세요.</p>
            </div>
            <div class="form-group">
                <label for="pass">비밀번호</label>
                <input type="password" id="pass" name="pass" maxlength="12" required>
                <p id="passError" class="error-text">비밀번호는 영문자와 숫자를 혼합하여 12자 이내로 입력해주세요.</p>
            </div>
            <div class="checkbox-group">
                <input type="checkbox" id="saveId" name="saveId" value="Y">
                <label for="saveId" style="display: inline;">아이디 저장</label>
            </div>
            <button type="submit" class="btn">로그인</button>
        </form>
        
        <!-- 아이디/비밀번호 찾기 및 회원가입 링크 -->
        <div class="links-container">
            <a href="${pageContext.request.contextPath}/member/findId.do">아이디 찾기</a>
            <span>|</span>
            <a href="${pageContext.request.contextPath}/member/findPassword.do">비밀번호 찾기</a>
            <span>|</span>
            <a href="${pageContext.request.contextPath}/member/register.do">회원가입</a>
        </div>
        
        <!-- 카카오 로그인 버튼 -->
        <a href="${pageContext.request.contextPath}/kakao/login" class="btn-kakao">
            <img src="${pageContext.request.contextPath}/resource/img/KakaoTalk_logo.png" alt="Kakao Logo">
            카카오 계정으로 로그인
        </a>
    </div>
    
    <!-- 쿠키에서 아이디 가져와서 자동 입력 및 입력 유효성 검사 -->
    <script>
        window.onload = function() {
            // 쿠키에서 아이디 불러오기
            var savedId = getCookie("savedId");
            if (savedId) {
                document.getElementById('id').value = savedId;
                document.getElementById('saveId').checked = true;
            }
            
            // 입력 필드에 이벤트 리스너 추가
            const idInput = document.getElementById('id');
            const passInput = document.getElementById('pass');
            
            idInput.addEventListener('input', function() {
                validateInput(this, 'idError');
            });
            
            passInput.addEventListener('input', function() {
                validateInput(this, 'passError');
            });
        }
        
        // 쿠키 가져오기 함수
        function getCookie(name) {
            var nameEQ = name + "=";
            var cookies = document.cookie.split(';');
            for (var i = 0; i < cookies.length; i++) {
                var cookie = cookies[i].trim();
                if (cookie.indexOf(nameEQ) === 0) {
                    return decodeURIComponent(cookie.substring(nameEQ.length, cookie.length));
                }
            }
            return null;
        }
        
        // 쿠키 설정 함수
        function setCookie(name, value, days) {
            var expires = "";
            if (days) {
                var date = new Date();
                date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
                expires = "; expires=" + date.toUTCString();
            }
            document.cookie = name + "=" + encodeURIComponent(value) + expires + "; path=/";
        }
        
        // 입력값 유효성 검사 함수
        function validateInput(inputField, errorId) {
            const value = inputField.value;
            const errorElement = document.getElementById(errorId);
            const koreanPattern = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
            const englishPattern = /[a-zA-Z]/;
            const numberPattern = /[0-9]/;
            
            // 한글 체크
            if (koreanPattern.test(value)) {
                inputField.classList.add('input-error');
                errorElement.style.display = 'block';
                return false;
            }
            
            // 길이 체크 (12자 이내)
            if (value.length > 12) {
                inputField.classList.add('input-error');
                errorElement.style.display = 'block';
                return false;
            }
            
            // 영문자와 숫자 혼합 사용 체크
            if (!(englishPattern.test(value) && numberPattern.test(value))) {
                inputField.classList.add('input-error');
                errorElement.style.display = 'block';
                return false;
            }
            
            // 유효성 검사 통과
            inputField.classList.remove('input-error');
            errorElement.style.display = 'none';
            return true;
        }
        
        // 폼 제출 시 최종 유효성 검사 및 아이디 저장
        function validateAndSaveId() {
            const idInput = document.getElementById('id');
            const passInput = document.getElementById('pass');
            const saveIdCheckbox = document.getElementById('saveId');
            
            const isIdValid = validateInput(idInput, 'idError');
            const isPassValid = validateInput(passInput, 'passError');
            
            // 유효성 검사 통과 시 아이디 저장 처리
            if (isIdValid && isPassValid) {
                if (saveIdCheckbox.checked) {
                    // 아이디를 쿠키에 저장 (365일 동안 유지)
                    setCookie("savedId", idInput.value, 365);
                } else {
                    // 저장된 아이디 쿠키 삭제
                    setCookie("savedId", "", -1);
                }
                return true;
            }
            
            return false;
        }
    </script>
</body>
</html>