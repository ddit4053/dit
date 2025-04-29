<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원가입</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/user/register.css">
    <link href="https://cdn.jsdelivr.net/gh/hiun/NanumSquare@1.0/nanumsquare.css" rel="stylesheet">
    
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</head>
<body>
    <div class="container">
        <h2>기본정보</h2>
        
        <c:if test="${not empty errorMessage}">
            <div class="error-message" style="display: block; margin-bottom: 10px; padding: 10px; background-color: #fff0f0; border: 1px solid #fcc; color: #f00; font-size: 12px;">
                ${errorMessage}
            </div>
        </c:if>
        
        <form action="${pageContext.request.contextPath}/user/register.do" method="post" id="registerForm" onsubmit="return validateForm()">
           
            <div class="form-row">
                <div class="form-label">
                    아이디<span class="form-required">*</span>
                </div>
                <div class="form-input">
                    <div class="input-with-btn">
                        <input type="text" id="userId" name="userId" class="form-control" maxlength="20" required>
                        <button type="button" class="btn" id="jung" onclick="checkIdAvailability()">중복확인</button>
                    </div>
                    <div id="userIdError" class="error-text">아이디는 영문자, 숫자를 포함하여 6~12자 이내로 입력해주세요.</div>
                    <div id="userIdSuccess" class="success-text">사용 가능한 아이디입니다.</div>
                    <input type="hidden" id="isIdChecked" name="isIdChecked" value="false">
                </div>
            </div>
            
            <div class="form-row">
                <div class="form-label">
                    비밀번호<span class="form-required">*</span>
                </div>
                <div class="form-input">
                    <input type="password" id="password" name="password" class="form-control" maxlength="20" required>
                    <div id="passwordError" class="error-text">비밀번호는 영문자, 숫자, 특수문자를 포함하여 8~12자 이내로 입력해주세요.</div>
                </div>
            </div>
            
            <div class="form-row">
                <div class="form-label">
                    비밀번호 확인<span class="form-required">*</span>
                </div>
                <div class="form-input">
                    <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" maxlength="20" required>
                    <div id="confirmPasswordError" class="error-text">비밀번호가 일치하지 않습니다.</div>
                </div>
            </div>
            
            <div class="form-row">
                <div class="form-label">
                    이름<span class="form-required">*</span>
                </div>
                <div class="form-input">
                    <input type="text" id="name" name="name" class="form-control" maxlength="50">
                </div>
            </div>
            
            <div class="form-row">
                <div class="form-label">
                    생년월일
                </div>
                <div class="form-input">
                    <input type="date" id="birth" name="birth" class="form-control">
                </div>
            </div>
            
            <div class="form-row">
                <div class="form-label">
                    성별
                </div>
                <div class="form-input">
                    <div class="gender-group">
                        <div class="gender-option">
                            <input type="radio" id="genderMale" name="gender" value="M">
                            <label for="genderMale">남자</label>
                        </div>
                        <div class="gender-option">
                            <input type="radio" id="genderFemale" name="gender" value="F">
                            <label for="genderFemale">여자</label>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="form-row">
                <div class="form-label">
                    이메일<span class="form-required">*</span>
                </div>
                <div class="form-input">
                    <div class="input-with-btn">
                        <input type="email" id="email" name="email" class="form-control" required>
                        <button type="button" class="btn" id="checkbtn" onclick="checkEmailAvailability()">중복확인</button>
                    </div>
                    <div id="emailError" class="error-text">유효한 이메일 주소를 입력해주세요.</div>
                    <div id="emailSuccess" class="success-text">사용 가능한 이메일입니다.</div>
                    <input type="hidden" id="isEmailChecked" name="isEmailChecked" value="false">
                </div>
            </div>
            
            <div class="form-row">
                <div class="form-label">
                    휴대전화
                </div>
                <div class="form-input">
                    <div class="phone-group">
                        <select id="phonePrefix" name="phonePrefix">
                            <option value="010">010</option>
                            <option value="011">011</option>
                            <option value="016">016</option>
                            <option value="017">017</option>
                            <option value="018">018</option>
                            <option value="019">019</option>
                        </select>
                        <span>-</span>
                        <input type="text" id="phoneMiddle" name="phoneMiddle" maxlength="4">
                        <span>-</span>
                        <input type="text" id="phoneSuffix" name="phoneSuffix" maxlength="4">
                        <input type="hidden" id="phone" name="phone">
                    </div>
                    <div id="phoneError" class="error-text">올바른 전화번호 형식이 아닙니다.</div>
                </div>
            </div>
            
            <div class="form-row">
                <div class="form-label">
                    주소
                </div>
                <div class="form-input">
                    <div class="input-with-btn">
                        <input type="text" id="zipcode" name="zipcode" class="form-control" placeholder="우편번호" readonly>
                        <button type="button" class="btn" onclick="searchAddress()">주소 검색</button>
                    </div>
                    <input type="text" id="address1" name="address1" class="form-control address-input" placeholder="기본주소" readonly>
                    <input type="text" id="address2" name="address2" class="form-control address-input" placeholder="상세주소">
                    <input type="hidden" id="fullAddress" name="address" value="">
                </div>
            </div>
            
            <button type="submit" class="btn btn-green btn-full">회원가입</button>
        </form>
    </div>

    <script>
    
        $(document).ready(function() {
           
            $('#userIdError').hide();
            $('#userIdSuccess').hide();
            $('#passwordError').hide();
            $('#confirmPasswordError').hide();
            $('#emailError').hide();
            $('#emailSuccess').hide();
            $('#phoneError').hide();
            
            $('#userId').on('input', function() {
                const userId = $(this).val();
                const parent = $(this).closest('.form-input');
                
                if (!userId) {
                    parent.removeClass('is-error').removeClass('is-success');
                    $('#userIdError').hide();
                    $('#userIdSuccess').hide();
                    $('#isIdChecked').val('false');
                    return;
                }
                
                const idPattern = /^[a-zA-Z0-9]{6,12}$/;
                
                if (!idPattern.test(userId)) {
                    parent.addClass('is-error').removeClass('is-success');
                    $('#userIdError').text('아이디는 영문자, 숫자를 포함하여 6~12자 이내로 입력해주세요.').show();
                    $('#userIdSuccess').hide();
                    $('#isIdChecked').val('false');
                } else {
                    parent.removeClass('is-error');
                    $('#userIdError').hide();
                    $('#isIdChecked').val('false');
                }
            });
            
            $('#password').on('input', function() {
                const password = $(this).val();
                const parent = $(this).closest('.form-input');
                
                if (!password) {
                    parent.removeClass('is-error');
                    $('#passwordError').hide();
                    return;
                }
                
                const passwordPattern = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,12}$/;
                
                if (!passwordPattern.test(password)) {
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
                
                if (!$(this).val()) {
                    $(this).closest('.form-input').removeClass('is-error');
                    $('#confirmPasswordError').hide();
                    return;
                }
                
                checkPasswordMatch();
            });
            
            $('#email').on('input', function() {
                const email = $(this).val();
                const parent = $(this).closest('.form-input');
                
                if (!email) {
                    parent.removeClass('is-error').removeClass('is-success');
                    $('#emailError').hide();
                    $('#emailSuccess').hide();
                    $('#isEmailChecked').val('false');
                    return;
                }
                
                const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
                
                if (!emailPattern.test(email)) {
                    parent.addClass('is-error').removeClass('is-success');
                    $('#emailError').text('유효한 이메일 주소를 입력해주세요.').show();
                    $('#emailSuccess').hide();
                    $('#isEmailChecked').val('false');
                } else {
                    parent.removeClass('is-error');
                    $('#emailError').hide();
                   
                    $('#isEmailChecked').val('false');
                }
            });
            
            $('#phoneMiddle, #phoneSuffix').on('input', function() {
                
                if (!$('#phoneMiddle').val() || !$('#phoneSuffix').val()) {
                    $('#phonePrefix').closest('.form-input').removeClass('is-error');
                    $('#phoneError').hide();
                    return;
                }
                
                formatPhoneNumber();
            });
            
            $('#phonePrefix').on('change', formatPhoneNumber);
            
            $('#address2').on('input', function() {
                updateFullAddress();
            });
        });
        
        function checkIdAvailability() {
            const userId = $('#userId').val();
            const parent = $('#userId').closest('.form-input');
            
            const idPattern = /^[a-zA-Z0-9]{6,12}$/;
            
            if (!idPattern.test(userId)) {
                parent.addClass('is-error').removeClass('is-success');
                $('#userIdError').text('아이디는 영문자, 숫자를 포함하여 6~12자 이내로 입력해주세요.').show();
                $('#userIdSuccess').hide();
                return;
            }
            
            $.ajax({
                url: '${pageContext.request.contextPath}/user/checkId.do',
                type: 'GET',
                data: { userId: userId },
                dataType: 'json',
                success: function(response) {
                    if (response.available) {
                        parent.removeClass('is-error').addClass('is-success');
                        $('#userIdError').hide();
                        $('#userIdSuccess').show();
                        $('#isIdChecked').val('true');
                    } else {
                        parent.addClass('is-error').removeClass('is-success');
                        $('#userIdError').text('이미 사용 중인 아이디입니다.').show();
                        $('#userIdSuccess').hide();
                        $('#isIdChecked').val('false');
                    }
                },
                error: function() {
                    alert('중복 확인 중 오류가 발생했습니다. 다시 시도해주세요.');
                }
            });
        }
        
        function checkPasswordMatch() {
            const password = $('#password').val();
            const confirmPassword = $('#confirmPassword').val();
            const parent = $('#confirmPassword').closest('.form-input');
            
            if (password !== confirmPassword) {
                parent.addClass('is-error');
                $('#confirmPasswordError').show();
            } else {
                parent.removeClass('is-error');
                $('#confirmPasswordError').hide();
            }
        }
        
        function checkEmailAvailability() {
            const email = $('#email').val();
            const parent = $('#email').closest('.form-input');
            
            const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
            
            if (!emailPattern.test(email)) {
                parent.addClass('is-error').removeClass('is-success');
                $('#emailError').text('유효한 이메일 주소를 입력해주세요.').show();
                $('#emailSuccess').hide();
                $('#isEmailChecked').val('false');
                return;
            }
            
            $.ajax({
                url: '${pageContext.request.contextPath}/user/checkEmail.do',
                type: 'GET',
                data: { email: email },
                dataType: 'json',
                success: function(response) {
                    if (response.available) {
                        parent.removeClass('is-error').addClass('is-success');
                        $('#emailError').hide();
                        $('#emailSuccess').show();
                        $('#isEmailChecked').val('true');
                    } else {
                        parent.addClass('is-error').removeClass('is-success');
                        $('#emailError').text('이미 사용 중인 이메일입니다.').show();
                        $('#emailSuccess').hide();
                        $('#isEmailChecked').val('false');
                    }
                },
                error: function() {
                    alert('이메일 중복 확인 중 오류가 발생했습니다. 다시 시도해주세요.');
                    parent.addClass('is-error').removeClass('is-success');
                    $('#emailError').text('중복 확인 중 오류가 발생했습니다.').show();
                    $('#emailSuccess').hide();
                    $('#isEmailChecked').val('false');
                }
            });
        }
        
        function formatPhoneNumber() {
            const prefix = $('#phonePrefix').val();
            const middle = $('#phoneMiddle').val();
            const suffix = $('#phoneSuffix').val();
            const parent = $('#phonePrefix').closest('.form-input');
            
            if (middle && suffix) {
                const numericPattern = /^\d+$/;
                
                if (!numericPattern.test(middle) || !numericPattern.test(suffix) || 
                    middle.length < 3 || middle.length > 4 || suffix.length !== 4) {
                    parent.addClass('is-error');
                    $('#phoneError').show();
                    $('#phone').val('');
                } else {
                    parent.removeClass('is-error');
                    $('#phoneError').hide();
                    $('#phone').val(prefix + '-' + middle + '-' + suffix);
                }
            } else {
                parent.removeClass('is-error');
                $('#phoneError').hide();
                $('#phone').val('');
            }
        }
        
        function searchAddress() {
            new daum.Postcode({
                oncomplete: function(data) {
                    $('#zipcode').val(data.zonecode);
                    $('#address1').val(data.address);
                    $('#address2').focus();
                    
                    updateFullAddress();
                }
            }).open();
        }
        
        function updateFullAddress() {
            const zipcode = $('#zipcode').val();
            const address1 = $('#address1').val();
            const address2 = $('#address2').val();
            
            let fullAddress = '';
            
            if (zipcode) {
                fullAddress += '(' + zipcode + ') '; // 괄호 안에 실제 우편번호 포함
            }
            
            if (address1) {
                fullAddress += address1;
            }
            
            if (address2) {
                fullAddress += ' ' + address2;
            }
            
            $('#fullAddress').val(fullAddress.trim());
            console.log("통합 주소 업데이트 수정: " + fullAddress.trim());
        }
        
        function validateForm() {
            let isValid = true;
            
            if ($('#isIdChecked').val() !== 'true') {
                alert('아이디 중복 확인을 해주세요.');
                $('#userId').focus();
                return false;
            }
            
            const password = $('#password').val();
            const passwordPattern = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,12}$/;
            
            if (!passwordPattern.test(password)) {
                alert('비밀번호는 영문자, 숫자, 특수문자를 포함하여 8~12자 이내로 입력해주세요.');
                $('#password').focus();
                return false;
            }
            
            if (password !== $('#confirmPassword').val()) {
                alert('비밀번호가 일치하지 않습니다.');
                $('#confirmPassword').focus();
                return false;
            }
            
            const email = $('#email').val();
            const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
            
            if (!emailPattern.test(email)) {
                alert('유효한 이메일 주소를 입력해주세요.');
                $('#email').focus();
                return false;
            }
            
            if ($('#isEmailChecked').val() !== 'true') {
                alert('이메일 중복 확인을 해주세요.');
                $('#email').focus();
                return false;
            }
            
            formatPhoneNumber();
            
            updateFullAddress();
            
//             console.log("폼 제출 직전 주소 정보:");
//             console.log("우편번호:", $('#zipcode').val());
//             console.log("기본주소:", $('#address1').val());
//             console.log("상세주소:", $('#address2').val());
//             console.log("통합주소:", $('#fullAddress').val());
            
            updateFullAddress();
            
            const fullAddress = $('#fullAddress').val();
//             console.log("폼 제출 시 최종 주소:", fullAddress);
            
            return isValid;
        }
    </script>
</body>
</html>