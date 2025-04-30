<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원정보 수정</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/user/mypage/updateInfo.css">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
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
            
            <form action="${pageContext.request.contextPath}/user/mypage/updateInfo.do" method="post" id="updateForm" onsubmit="return validateForm()">
              
                <div class="form-group">
                    <label for="userId">아이디</label>
                    <input type="text" id="userId" name="userId" class="form-control readonly aa" value="${user.userId}" readonly>
                </div>
                
                <div class="form-group">
                    <label for="name">이름</label>
                    <input type="text" id="name" name="name" class="form-control" value="${user.name}" required>
                </div>
                
                <div class="form-group">
                    <label for="birth">생년월일</label>
                    <input type="date" id="birth" name="birth" class="form-control" value="${user.birth}">
                </div>
                
                <div class="form-group">
                    <label>성별</label>
                    <div class="gender-group">
                        <div>
                            <input type="radio" id="genderMale" name="gender" value="M" ${user.gender eq 'M' ? 'checked' : ''}>
                            <label for="genderMale">남자</label>
                        </div>
                        <div>
                            <input type="radio" id="genderFemale" name="gender" value="F" ${user.gender eq 'F' ? 'checked' : ''}>
                            <label for="genderFemale">여자</label>
                        </div>
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="email">이메일</label>
                    <input type="email" id="email" name="email" class="form-control" value="${user.email}" required>
                </div>
                
                <div class="form-group">
                    <label for="phone">전화번호</label>
                    <input type="tel" id="phone" name="phone" class="form-control" value="${user.phone}" placeholder="예: 010-1234-5678">
                </div>
                
                <div class="form-group">
                    <label for="zipcode">주소</label>
                    <div class="address-group">
                        <input type="text" id="zipcode" name="zipcode" class="form-control" placeholder="우편번호" readonly>
                        <button type="button" class="btn btn-secondary" onclick="searchAddress()">주소 검색</button>
                    </div>
                    <input type="text" id="address1" name="address1" class="form-control" placeholder="기본주소" readonly style="margin-top: 5px;">
                    <input type="text" id="address2" name="address2" class="form-control" placeholder="상세주소" style="margin-top: 5px;">
                    <input type="hidden" id="fullAddress" name="address">
                </div>
                
                <div class="form-group" style="text-align: center; margin-top: 20px;">
                    <button type="submit" class="btn btn-primary">정보 수정</button>
                    <button type="button" class="btn btn-secondary" onclick="history.back()">취소</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        $(document).ready(function() {
            const fullAddress = "${user.address}";
            console.log("원본 주소:", fullAddress);
            
            if (fullAddress && fullAddress.trim() !== "") {
                const zipcodeMatch = fullAddress.match(/\((\d+)\)/);
                if (zipcodeMatch) {
                    $("#zipcode").val(zipcodeMatch[1]);
                    
                    let remainingAddress = fullAddress.replace(/\(\d+\)\s*/, "").trim();
                    
                    const lastSpaceIndex = remainingAddress.lastIndexOf(" ");
                    if (lastSpaceIndex !== -1) {
                        const basicAddress = remainingAddress.substring(0, lastSpaceIndex);
                        const detailAddress = remainingAddress.substring(lastSpaceIndex + 1);
                        
                        $("#address1").val(basicAddress);
                        $("#address2").val(detailAddress);
                    } else {
                        $("#address1").val(remainingAddress);
                    }
                } else {
                    $("#address1").val(fullAddress);
                }
            }
            
            updateFullAddress();
        });
        
        function searchAddress() {
            new daum.Postcode({
                oncomplete: function(data) {
                    $("#zipcode").val(data.zonecode);
                    $("#address1").val(data.address);
                    
                    $("#address2").focus();
                    
                    updateFullAddress();
                }
            }).open();
        }
        
        function updateFullAddress() {
            const zipcode = $("#zipcode").val();
            const address1 = $("#address1").val();
            const address2 = $("#address2").val();
            
            let fullAddress = "";
            
            if (zipcode) {
                fullAddress += `(\${zipcode}) `;
            }
            
            if (address1) {
                fullAddress += address1;
            }
            
            if (address2 && address2.trim() !== "") {
                fullAddress += ` \${address2}`;
            }
            
            $("#fullAddress").val(fullAddress.trim());
            console.log("통합 주소 업데이트:", fullAddress.trim());
        }
        
        function validateForm() {
            updateFullAddress();
            
            console.log("폼 제출 직전 주소 정보:");
            console.log("우편번호:", $('#zipcode').val());
            console.log("기본주소:", $('#address1').val());
            console.log("상세주소:", $('#address2').val());
            console.log("통합주소:", $('#fullAddress').val());
            
            updateFullAddress();
            
            return true;
        }
    </script>
</body>
</html>