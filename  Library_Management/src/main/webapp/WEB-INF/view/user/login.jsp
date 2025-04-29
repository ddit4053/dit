<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>로그인</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/user/login.css">
</head>
<body>
    <div class="container">
        <h2>로그인</h2>
        
        <c:if test="${param.status eq 'register-success'}">
            <div class="success-message">회원가입이 완료되었습니다. 로그인해주세요.</div>
        </c:if>
        <c:if test="${param.status eq 'password-reset-success'}">
            <div class="success-message">비밀번호가 성공적으로 변경되었습니다. 로그인해주세요.</div>
        </c:if>
        
        <c:if test="${status eq 'fail'}">
            <div class="error-message">아이디 또는 비밀번호가 일치하지 않습니다.</div>
        </c:if>
        <c:if test="${status eq 'empty'}">
            <div class="error-message">${message}</div>
        </c:if>
        
        <form action="${pageContext.request.contextPath}/user/login.do" method="post" id="loginForm" onsubmit="return validateAndSaveId()">
            <div class="form-group">
                <label for="id">아이디</label>
                <input type="text" id="id" name="id" maxlength="12" required>
            </div>
            <div class="form-group">
                <label for="pass">비밀번호</label>
                <input type="password" id="pass" name="pass" maxlength="12" required>
            </div>
            <div class="checkbox-group">
                <input type="checkbox" id="saveId" name="saveId" value="Y">
                <label for="saveId" style="display: inline;">아이디 저장</label>
            </div>
            <button type="submit" class="btn">로그인</button>
        </form>
        
        <div class="links-container">
            <a href="${pageContext.request.contextPath}/user/findId.do">아이디 찾기</a>
            <span>|</span>
            <a href="${pageContext.request.contextPath}/user/findPassword.do">비밀번호 찾기</a>
            <span>|</span>
            <a href="${pageContext.request.contextPath}/user/register.do">회원가입</a>
        </div>
        
        <a href="${pageContext.request.contextPath}/kakao/login" class="btn-kakao">
            <img src="${pageContext.request.contextPath}/resource/img/KakaoTalk_logo.png" alt="Kakao Logo">
            카카오 계정으로 로그인
        </a>
    </div>
    
    <script>
        window.onload = function() {
          
            var savedId = getCookie("id");
            if (savedId) {
                document.getElementById('id').value = savedId;
                document.getElementById('saveId').checked = true;
            }
        }
        
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
        
        function setCookie(name, value, days) {
            var expires = "";
            if (days) {
                var date = new Date();
                date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
                expires = "; expires=" + date.toUTCString();
            }
           
            document.cookie = name + "=" + encodeURIComponent(value) + expires + "; path=/";
        }
        
        function validateInput(inputField, errorId) {
            const value = inputField.value;
            const errorElement = document.getElementById(errorId);
            const koreanPattern = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
            const englishPattern = /[a-zA-Z]/;
            const numberPattern = /[0-9]/;
            
            if (koreanPattern.test(value)) {
                inputField.classList.add('input-error');
                errorElement.style.display = 'block';
                return false;
            }
            
            if (value.length > 12) {
                inputField.classList.add('input-error');
                errorElement.style.display = 'block';
                return false;
            }
            
            if (!(englishPattern.test(value) && numberPattern.test(value))) {
                inputField.classList.add('input-error');
                errorElement.style.display = 'block';
                return false;
            }
            
            inputField.classList.remove('input-error');
            errorElement.style.display = 'none';
            return true;
        }
        
        function validateAndSaveId() {
            const idInput = document.getElementById('id');
            const passInput = document.getElementById('pass');
            const saveIdCheckbox = document.getElementById('saveId');
            
            const isIdValid = validateInput(idInput, 'idError');
            const isPassValid = validateInput(passInput, 'passError');
            
            if (isIdValid && isPassValid) {
            	
                if (saveIdCheckbox.checked) {
                	
                    setCookie("id", idInput.value, 365);
                } else {
                	
                    setCookie("id", "", -1);
                }
                return true;
            }
            
            return false;
        }
        function kakaoLogout() {
            if (typeof Kakao !== 'undefined' && Kakao.Auth.getAccessToken()) {
                Kakao.API.request({
                    url: '/v1/user/unlink',
                    success: function(response) {
                        console.log("카카오 연결 해제 성공", response);
                        location.href = "${pageContext.request.contextPath}/user/logout.do";
                    },
                    fail: function(error) {
                        console.log("카카오 연결 해제 실패", error);
                        location.href = "${pageContext.request.contextPath}/user/logout.do";
                    }
                });
            }
        }

        document.addEventListener("DOMContentLoaded", function() {
           
            if (typeof Kakao !== 'undefined' && !Kakao.isInitialized()) {
                Kakao.init('78f92474c2082285757621952de71283'); // 여기에 JavaScript 키 입력
                console.log("Kakao SDK initialized");
            }
        });
    </script>
</body>
</html>