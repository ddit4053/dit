<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- 상단 헤더 인클루드 --%>
<jsp:include page="/WEB-INF/view/header.jsp" />

<%-- 네비게이션 인클루드 --%>
<jsp:include page="/WEB-INF/view/nav.jsp" />

<!-- 카카오맵 API 로드 -->
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=78f92474c2082285757621952de71283&libraries=services"></script>
<!-- Font Awesome (아이콘 사용) -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<!-- 카카오 SDK 로드 -->
<script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
<!-- AOS 라이브러리 추가 -->
<link rel="stylesheet" href="https://unpkg.com/aos@next/dist/aos.css" />
<script src="https://unpkg.com/aos@next/dist/aos.js"></script>
<script>
    // 카카오 SDK 초기화 (실제 앱 키로 변경 필요)
    Kakao.init('78f92474c2082285757621952de71283');
</script>

<div class="event-detail-container">
    <!-- 페이지 경로 표시 -->
    <div class="event-breadcrumb">
        <ul>
            <li><a href="${pageContext.request.contextPath}/">홈</a></li>
            <li><a href="${pageContext.request.contextPath}/support/events">교육/행사</a></li>
            <li class="current">${event.EV_TITLE}</li>
        </ul>
    </div>

    <!-- 이벤트 헤더 섹션 -->
    <div class="event-header">
        <div class="event-status-badge ${event.EV_STATUS == '접수마감' ? 'closed' : 'open'}">
            ${event.EV_STATUS == '접수마감' ? '접수마감' : '접수중'}
        </div>
        <h1 class="event-title">${event.EV_TITLE}</h1>
        <div class="event-type-tag">
            <c:choose>
                <c:when test="${event.EV_TYPE eq 'edu'}">교육 프로그램</c:when>
                <c:when test="${event.EV_TYPE eq 'event'}">행사 프로그램</c:when>
                <c:otherwise>${event.EV_TYPE}</c:otherwise>
            </c:choose>
        </div>
    </div>

    <!-- 이벤트 메인 콘텐츠 -->
    <div class="event-main-content">
        <!-- 이벤트 이미지 섹션 -->
        <div class="event-image-section">
            <c:if test="${not empty fileList}">
                <div class="main-image-container">
                    <c:choose>
                        <c:when test="${fileList[0].FILE_PATH eq 'test'}">
                            <!-- 테스트 이미지 -->
                            <img src="${pageContext.request.contextPath}/resource/img/${fileList[0].SAVE_NAME}" 
                                 alt="${event.EV_TITLE}" class="main-event-image">
                        </c:when>
                        <c:otherwise>
                            <!-- 실제 업로드된 이미지 -->
                            <img src="${pageContext.request.contextPath}/uploads/${fileList[0].FILE_PATH}/${fileList[0].SAVE_NAME}" 
                                 alt="${event.EV_TITLE}" class="main-event-image">
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:if>
        </div>
        
        <!-- 이벤트 정보 섹션 -->
        <div class="event-info-section">
            <div class="event-info-card">
                <h2>행사 정보</h2>
                <ul class="event-details-list">
                    <li>
                        <span class="detail-label">일시</span>
                        <span class="detail-value">
                            <fmt:formatDate value="${event.EV_DATE}" pattern="yyyy년 MM월 dd일" />
                        </span>
                    </li>
                    <li>
                        <span class="detail-label">장소</span>
                        <span class="detail-value">${event.LOCATION}</span>
                    </li>
                    <li>
                        <span class="detail-label">대상</span>
                        <span class="detail-value">${event.TARGET_AUDIENCE}</span>
                    </li>
                    <li>
                        <span class="detail-label">모집 인원</span>
                        <span class="detail-value participation-count">
                            <span class="current">${event.CURRENT_PARTICIPANTS}</span>
                            <span class="separator">/</span>
                            <span class="maximum">${event.MAX_PARTICIPANTS}</span>
                            <span class="unit">명</span>
                        </span>
                    </li>
                    <li>
                        <span class="detail-label">신청 마감일</span>
                        <span class="detail-value">
                            <fmt:formatDate value="${event.REGISTRATION_DEADLINE}" pattern="yyyy년 MM월 dd일" />
                        </span>
                    </li>
                </ul>
                
                <!-- 신청 버튼 영역 - 이 부분에 신청 취소 버튼 추가 -->
                <div class="application-actions">
                    <c:choose>
                        <c:when test="${isRegistrationOpen}">
                            <c:choose>
                                <c:when test="${empty sessionScope.userNo}">
                                    <a href="${pageContext.request.contextPath}/user/login.do?redirect=${pageContext.request.contextPath}/support/events/api/detail/${event.EV_NO}" 
                                       class="btn-login-to-apply">로그인 후 신청하기</a>
                                </c:when>
                                <c:when test="${hasApplied}">
                                    <button type="button" class="btn-cancel" id="btnCancelEvent">신청 취소하기</button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="btn-apply" id="btnApplyEvent">지금 신청하기</button>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <button type="button" class="btn-closed" disabled>신청이 마감되었습니다</button>
                        </c:otherwise>
                    </c:choose>
                    <a href="${pageContext.request.contextPath}/support/events" class="btn-back-to-list">목록으로 돌아가기</a>
                </div>
            </div>
            
            <!-- 이벤트 정보 카드 다음에 추가 -->
			<div class="share-buttons">
			    <div class="share-title">이 행사 공유하기</div>
			    <div class="share-icons">
			        <a href="javascript:void(0)" onclick="shareFacebook()" class="share-icon facebook">
			            <i class="fab fa-facebook-f"></i>
			        </a>
			        <a href="javascript:void(0)" onclick="shareTwitter()" class="share-icon twitter">
			            <i class="fab fa-twitter"></i>
			        </a>
			        <a href="javascript:void(0)" onclick="shareKakao()" class="share-icon kakao">
			            <img src="${pageContext.request.contextPath}/resource/img/KakaoTalk_logo.png" alt="카카오톡 공유">
			        </a>
			        <a href="javascript:void(0)" onclick="shareNaver()" class="share-icon naver">
			            <img src="${pageContext.request.contextPath}/resource/img/naverblog.PNG" alt="네이버 공유">
			        </a>
			        <a href="javascript:void(0)" onclick="copyLink()" class="share-icon link">
			            <i class="fas fa-link"></i>
			        </a>
			    </div>
			</div>
        </div>
    </div>
    
    <!-- 이미지와 같은 D-day 카운트다운 스타일 -->
	<div class="dday-countdown fade-up-element" data-aos="fade-up" data-aos-duration="800" data-aos-once="true">
	    <div class="dday-left">
	        <h2 class="dday-title">D-day.</h2>
	        <p class="dday-hashtag"># 행사시작까지는?</p>
	    </div>
	    <div class="dday-right">
	        <div class="countdown-container">
	            <div class="countdown-unit">
	                <div class="unit-label">Days</div>
	                <div class="unit-value" id="countdown-days">6</div>
	            </div>
	            <div class="countdown-separator">
	                <span>•</span>
	                <span>•</span>
	            </div>
	            <div class="countdown-unit">
	                <div class="unit-label">Hours</div>
	                <div class="unit-value" id="countdown-hours">4</div>
	            </div>
	            <div class="countdown-separator">
	                <span>•</span>
	                <span>•</span>
	            </div>
	            <div class="countdown-unit">
	                <div class="unit-label">Mins</div>
	                <div class="unit-value" id="countdown-minutes">15</div>
	            </div>
	        </div>
	    </div>
	</div>
    
    <!-- 탭 메뉴 -->
    <div class="event-tabs">
        <ul class="tabs-list">
            <li class="tab-item active" data-tab="tab-details">상세 정보</li>
            <li class="tab-item" data-tab="tab-location">오시는 길</li>
            <li class="tab-item" data-tab="tab-notes">참가 안내</li>
        </ul>
        
        <!-- 탭 콘텐츠 -->
        <div class="tabs-content">
            <!-- 상세 정보 탭 -->
			<div id="tab-details" class="tab-content active">
			    <div class="edu-showcase fade-up-element" data-aos="fade-up" data-aos-duration="800" data-aos-delay="100" data-aos-once="true">
			        <div class="event-title-section">
			            <h2 class="event-main-title">${event.EV_TITLE}</h2>
			        </div>
			        
			        <div class="event-introduction-section">
			            <!-- DESCRIPTION에서 "1." 이전 부분 추출하여 표시 -->
			            <p class="event-introduction">
			                <c:set var="introText" value="${fn:substringBefore(event.DESCRIPTION, '1.')}" />
			                ${introText}
			            </p>
			        </div>
			        
			        <div class="event-info-section">
			            <div class="info-item">
			                <div class="info-label">기간</div>
			                <div class="info-value">
			                    <fmt:formatDate value="${event.EV_DATE}" pattern="yyyy. MM. dd(E)" />
			                </div>
			            </div>
			            <div class="info-item">
			                <div class="info-label">장소</div>
			                <div class="info-value">${event.LOCATION}</div>
			            </div>
			        </div>
			        
			        <div class="event-program-section">
			            <h3 class="section-title">프로그램 구성</h3>
			            
			            <div class="program-content">
						    <!-- DESCRIPTION에서 "1." 이후 부분만 추출하여 표시 -->
						    <div class="formatted-description">
						        <!-- 1.부터 시작하는 부분만 추출하여 표시 -->
						        <c:set var="programText" value="${fn:substringAfter(event.DESCRIPTION, '1.')}" />
						        <c:set var="formattedText" value="<div class='program-item'><span class='program-number'>1</span>${programText}" />
						        
						        <!-- 번호 스타일링 -->
						        <c:set var="formattedText" value="${fn:replace(formattedText, '2.', '</div><div class=\"program-item\"><span class=\"program-number\">2</span>')}" />
						        <c:set var="formattedText" value="${fn:replace(formattedText, '3.', '</div><div class=\"program-item\"><span class=\"program-number\">3</span>')}" />
						        <c:set var="formattedText" value="${fn:replace(formattedText, '4.', '</div><div class=\"program-item\"><span class=\"program-number\">4</span>')}" />
						        
						        <!-- 태그 스타일링 -->
						        <c:set var="formattedText" value="${fn:replace(formattedText, '[필수 참여]', '<span class=\"program-tag required\">필수 참여</span>')}" />
						        <c:set var="formattedText" value="${fn:replace(formattedText, '[선택 참여 가능]', '<span class=\"program-tag optional\">선택 참여 가능</span>')}" />
						        
						        <!-- 닫는 div 태그 -->
						        <c:set var="formattedText" value="${formattedText}</div>" />
						        
						        ${formattedText}
						    </div>
						</div>
			        </div>
			    </div>
			    
			    <!-- 영상 섹션 -->
				<c:if test="${not empty videoList}">
				    <div class="seoul-video-section fade-up-element" data-aos="fade-up" data-aos-duration="800" data-aos-delay="200" data-aos-once="true">
				        <h2 class="video-title">프로그램 소개 영상</h2>
				        <p class="video-subtitle">교육 프로그램의 생생한 현장을 영상으로 만나보세요</p>
				        
				        <div class="video-wrapper">
				            <c:choose>
				                <c:when test="${videoList[0].FILE_TYPE eq 'youtube'}">
				                    <!-- YouTube 영상 -->
				                    <iframe width="100%" height="100%" 
				                            src="https://www.youtube.com/embed/${videoList[0].SAVE_NAME}" 
				                            title="${event.EV_TITLE} 소개 영상" frameborder="0"
				                            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" 
				                            allowfullscreen></iframe>
				                </c:when>
				                <c:when test="${videoList[0].FILE_TYPE eq 'mp4'}">
				                    <!-- 일반 영상 파일 -->
				                    <video width="100%" height="100%" controls>
				                        <source src="${pageContext.request.contextPath}/uploads/${videoList[0].FILE_PATH}/${videoList[0].SAVE_NAME}" type="video/mp4">
				                        브라우저가 HTML5 비디오를 지원하지 않습니다.
				                    </video>
				                </c:when>
				                <c:when test="${videoList[0].FILE_TYPE eq 'vimeo'}">
				                    <!-- Vimeo 영상 -->
				                    <iframe src="https://player.vimeo.com/video/${videoList[0].SAVE_NAME}" 
				                            width="100%" height="100%" frameborder="0" 
				                            allow="autoplay; fullscreen; picture-in-picture" allowfullscreen></iframe>
				                </c:when>
				                <c:otherwise>
				                    <!-- 지원하지 않는 형식 -->
				                    <div class="no-video-message">
				                        지원하지 않는 영상 형식입니다.
				                    </div>
				                </c:otherwise>
				            </c:choose>
				        </div>
				        
				        <div class="video-caption">
				            <strong>${event.EV_TITLE} - 프로그램 안내</strong>
				            <span class="video-date"><fmt:formatDate value="${event.EV_DATE}" pattern="yyyy.MM.dd"/></span>
				        </div>
				    </div>
				</c:if>
			</div>
            
            <!-- 오시는 길 탭 -->
            <div id="tab-location" class="tab-content">
                <div class="location-info fade-up-element" data-aos="fade-up" data-aos-duration="800" data-aos-once="false">
                    <h3>행사 장소</h3>
                    <p class="location-address">${event.LOCATION}</p>
                    <div class="location-map">
                        <!-- 카카오맵 표시 영역 -->
                        <div id="kakao-map" style="width:100%;height:300px;"></div>
                    </div>
                    <div class="directions">
                        <h4>찾아오시는 방법</h4>
                        <div class="transport-methods">
                            <div class="transport-item">
                                <h5><i class="fas fa-bus"></i> 버스 이용</h5>
                                <ul>
                                    <li>종합청사(국무조정실) 정류장: 1009, 2100, 2001, 2003, 990</li>
                                    <li>세종정부청사 정류장: 601, 801, 990</li>
                                    <li>세종시청 정류장: 550, 551, 552</li>
                                </ul>
                            </div>
                            <div class="transport-item">
                                <h5><i class="fas fa-car"></i> 자가용 이용</h5>
                                <p>도서관 주차장 이용 가능 (2시간 무료)</p>
                            </div>
                            <div class="transport-item">
                                <h5><i class="fas fa-train"></i> 철도 이용</h5>
                                <p>오송역, 조치원역에서 대중교통 환승</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
            <!-- 참가 안내 탭 -->
            <div id="tab-notes" class="tab-content">
                <div class="participation-guide fade-up-element" data-aos="fade-up" data-aos-duration="800" data-aos-once="false">  
                    <h3>참가자 안내사항</h3>
                    <ul class="guide-list">
                        <li>행사 당일 신분증을 지참해주시기 바랍니다.</li>
                        <li>행사 시작 10분 전까지 입장해 주시기 바랍니다.</li>
                        <li>주차는 도서관 주차장을 이용하실 수 있습니다.</li>
                        <li>궁금하신 사항은 도서관 행사 담당자에게 문의해 주세요.</li>
                        <li>신청 취소는 행사 3일 전까지 가능합니다.</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    
    <!-- 관련 프로그램 -->
	<div class="related-events fade-up-element" data-aos="fade-up" data-aos-duration="800" data-aos-delay="300" data-aos-once="true">
	    <h2>관련 프로그램</h2>
	    <div class="related-events-list">
	        <c:choose>
	            <c:when test="${empty relatedEvents}">
	                <p class="no-related-events">현재 관련 프로그램이 없습니다.</p>
	            </c:when>
	            <c:otherwise>
	                <div class="related-event-cards">
	                    <c:forEach items="${relatedEvents}" var="related">
	                        <a href="${pageContext.request.contextPath}/support/events/api/detail/${related.EV_NO}" class="related-event-card">
	                            <div class="related-event-image">
	                                <c:choose>
	                                    <c:when test="${related.IMAGE_PATH eq 'test'}">
	                                        <img src="${pageContext.request.contextPath}/resource/img/${related.SAVE_NAME}" alt="${related.EV_TITLE}">
	                                    </c:when>
	                                    <c:when test="${empty related.IMAGE_PATH}">
	                                        <img src="${pageContext.request.contextPath}/resource/img/no-image.jpg" alt="이미지 없음">
	                                    </c:when>
	                                    <c:otherwise>
	                                        <img src="${pageContext.request.contextPath}/uploads/${related.IMAGE_PATH}" alt="${related.EV_TITLE}">
	                                    </c:otherwise>
	                                </c:choose>
	                                <div class="related-event-date">
	                                    <fmt:formatDate value="${related.EV_DATE}" pattern="yyyy.MM.dd" />
	                                </div>
	                            </div>
	                        </a>
	                    </c:forEach>
	                </div>
	            </c:otherwise>
	        </c:choose>
	    </div>
	</div>
</div>

<!-- 신청 모달 -->
<div id="applyEventModal" class="modal-overlay">
    <div class="modal-content">
        <div class="modal-header">
            <h3>프로그램 신청</h3>
            <button type="button" class="btn-close-modal">&times;</button>
        </div>
        <div class="modal-body">
            <form id="eventApplyForm" method="post" action="${pageContext.request.contextPath}/support/events/api/apply">
                <input type="hidden" name="eventId" value="${event.EV_NO}">
                
                <div class="form-group">
                    <label for="activityType">참여 유형</label>
                    <select id="activityType" name="activityType" required>
                        <option value="">선택하세요</option>
                        <option value="개인">개인</option>
                        <option value="가족">가족</option>
                        <option value="단체">단체</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <div class="privacy-policy-box">
                        <h4>개인정보 수집 및 이용 동의</h4>
                        <div class="policy-content">
                            <p>도서관은 프로그램 신청을 위해 아래와 같이 개인정보를 수집하고 있습니다.</p>
                            <ul>
                                <li>수집항목: 이름, 전화번호, 이메일</li>
                                <li>수집목적: 프로그램 신청 및 관리</li>
                                <li>보유기간: 프로그램 종료 후 3개월</li>
                            </ul>
                            <p>귀하는 개인정보 수집·이용에 동의하지 않을 권리가 있으며, 동의를 거부할 경우 프로그램 신청이 제한됩니다.</p>
                        </div>
                    </div>
                    <div class="checkbox-group">
                        <input type="checkbox" id="agreePrivacy" required>
                        <label for="agreePrivacy">개인정보 수집 및 이용에 동의합니다.</label>
                    </div>
                </div>
                
                <div class="form-actions">
                    <button type="submit" class="btn-submit-application">신청하기</button>
                    <button type="button" class="btn-cancel-application">취소</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- 취소 확인 모달 -->
<div id="cancelConfirmModal" class="modal-overlay">
    <div class="modal-content">
        <div class="modal-header">
            <h3>신청 취소 확인</h3>
            <button type="button" class="btn-close-modal">&times;</button>
        </div>
        <div class="modal-body">
            <p>정말 프로그램 신청을 취소하시겠습니까?</p>
            <p>취소 후에는 다시 신청할 수 있습니다.</p>
            
            <div class="form-actions">
                <button type="button" class="btn-confirm-cancel">확인</button>
                <button type="button" class="btn-cancel-action">취소</button>
            </div>
        </div>
    </div>
</div>

<%-- 푸터 인클루드 --%>
<jsp:include page="/WEB-INF/view/footer.jsp" />

<!-- CSS 파일 로드 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/users/event_detail.css">

<!-- JavaScript 코드 -->
<script>

//공유 기능 구현
function shareFacebook() {
    const url = encodeURIComponent(window.location.href);
    const title = encodeURIComponent("${event.EV_TITLE}");
    window.open(`https://www.facebook.com/sharer/sharer.php?u=${url}&t=${title}`, 
                '_blank', 'width=600,height=400');
}

function shareTwitter() {
    const url = encodeURIComponent(window.location.href);
    const text = encodeURIComponent("${event.EV_TITLE} - 도서관 교육/행사 프로그램");
    window.open(`https://twitter.com/intent/tweet?url=${url}&text=${text}`, 
                '_blank', 'width=600,height=400');
}

function shareKakao() {
    // 카카오 SDK가 로드되어 있어야 합니다.
    if (typeof Kakao !== 'undefined') {
        Kakao.Link.sendDefault({
            objectType: 'feed',
            content: {
                title: "${event.EV_TITLE}",
                description: "도서관 교육/행사 프로그램",
                imageUrl: document.querySelector('.main-event-image').src,
                link: {
                    mobileWebUrl: window.location.href,
                    webUrl: window.location.href
                }
            },
            buttons: [
                {
                    title: '자세히 보기',
                    link: {
                        mobileWebUrl: window.location.href,
                        webUrl: window.location.href
                    }
                }
            ]
        });
    } else {
        alert('카카오톡 공유 기능을 사용할 수 없습니다.');
    }
}

function shareNaver() {
    const url = encodeURIComponent(window.location.href);
    const title = encodeURIComponent("${event.EV_TITLE}");
    window.open(`https://blog.naver.com/openapi/share?url=${url}&title=${title}`, 
                '_blank', 'width=600,height=400');
}

function copyLink() {
    // 새로운 임시 요소 생성
    const dummy = document.createElement("textarea");
    document.body.appendChild(dummy);
    
    // 임시 요소에 URL 복사
    dummy.value = window.location.href;
    dummy.select();
    document.execCommand("copy");
    
    // 임시 요소 삭제
    document.body.removeChild(dummy);
    
    // 사용자에게 알림
    alert("링크가 클립보드에 복사되었습니다!");
}

$(document).ready(function() {
	
	// AOS 초기화
    AOS.init({
        duration: 800,    // 기본 애니메이션 지속 시간
        easing: 'ease-out-cubic',  // 애니메이션 타이밍 함수
        once: true,       // 애니메이션을 한 번만 실행할지 여부
        offset: 50        // 요소가 화면에 얼마나 들어왔을 때 애니메이션을 시작할지 (픽셀 단위)
    });
	
 	// 탭 전환 시 AOS 새로고침
    $('.tab-item').on('click', function() {
        const tabId = $(this).data('tab');
        
        // 활성 탭 표시 변경
        $('.tab-item').removeClass('active');
        $(this).addClass('active');
        
        // 활성 콘텐츠 변경
        $('.tab-content').removeClass('active');
        $('#' + tabId).addClass('active');
        
        // 현재 보이는 탭에 대해 AOS 새로고침 (100ms 지연 적용)
        setTimeout(function() {
            AOS.refresh();
        }, 100);
        
        // 오시는 길 탭 선택 시 지도 초기화
        if (tabId === 'tab-location') {
            initKakaoMap();
        }
        
        return false; // 이벤트 전파 방지
    });
	
	// D-day 카운트다운 업데이트 함수
	function updateDdayCountdown() {
	    // 행사 시작일 설정
	    const eventDate = new Date("${event.EV_DATE}").getTime();
	    const now = new Date().getTime();
	    
	    // 남은 시간 계산
	    const distance = eventDate - now;
	    
	    // 이미 지난 경우
	    if (distance < 0) {
	        document.getElementById("countdown-days").textContent = "0";
	        document.getElementById("countdown-hours").textContent = "0";
	        document.getElementById("countdown-minutes").textContent = "0";
	        return;
	    }
	    
	    // 일, 시간, 분 계산
	    const days = Math.floor(distance / (1000 * 60 * 60 * 24));
	    const hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
	    const minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
	    
	    // 화면 업데이트
	    document.getElementById("countdown-days").textContent = days;
	    document.getElementById("countdown-hours").textContent = hours;
	    document.getElementById("countdown-minutes").textContent = minutes;
	}

	// 초기 카운트다운 설정 및 1분마다 업데이트 (초 단위는 표시하지 않으므로 1분 간격으로 충분)
	updateDdayCountdown();
	setInterval(updateDdayCountdown, 60000);

	// 이미 마감된 경우 또는 마감일이 없는 경우 카운트다운 숨김
	const deadlineExists = "${event.REGISTRATION_DEADLINE}" !== "";
	const isAlreadyClosed = "${event.EV_STATUS}" === "접수마감";

	if (!deadlineExists || isAlreadyClosed) {
	    $(".event-countdown").hide();
	}
	
	
    // 탭 기능 구현
    $('.tab-item').on('click', function() {
        const tabId = $(this).data('tab');
        
        // 활성 탭 표시 변경
        $('.tab-item').removeClass('active');
        $(this).addClass('active');
        
        // 활성 콘텐츠 변경
        $('.tab-content').removeClass('active');
        $('#' + tabId).addClass('active');
        
        // 오시는 길 탭 선택 시 지도 초기화
        if (tabId === 'tab-location') {
            initKakaoMap();
        }
    });
    
    // 이미지 갤러리 기능
    $('.thumbnail-item').on('click', function() {
        // 활성화 클래스 변경
        $('.thumbnail-item').removeClass('active');
        $(this).addClass('active');
        
        // 메인 이미지 변경
        const imgSrc = $(this).find('img').data('full-img');
        $('.main-event-image').attr('src', imgSrc);
    });
    
    // 신청 모달 관련 변수
    const applyModal = document.getElementById('applyEventModal');
    const cancelConfirmModal = document.getElementById('cancelConfirmModal');
    const btnApply = document.getElementById('btnApplyEvent');
    const btnCancel = document.getElementById('btnCancelEvent');
    const btnCloseModals = document.querySelectorAll('.btn-close-modal');
    const btnCancelAction = document.querySelector('.btn-cancel-action');
    const btnConfirmCancel = document.querySelector('.btn-confirm-cancel');
    
    // 신청버튼 클릭 시 모달 표시
    if (btnApply) {
        btnApply.addEventListener('click', function() {
            applyModal.style.display = 'flex';
        });
    }
    
    // 신청 취소 버튼 클릭 시 확인 모달 표시
    if (btnCancel) {
        btnCancel.addEventListener('click', function() {
            cancelConfirmModal.style.display = 'flex';
        });
    }
    
    // 모달 닫기 버튼들에 이벤트 연결
    btnCloseModals.forEach(function(btn) {
        btn.addEventListener('click', function() {
            applyModal.style.display = 'none';
            cancelConfirmModal.style.display = 'none';
        });
    });
    
    // 취소 모달의 '취소' 버튼 클릭 시 모달 닫기
    if (btnCancelAction) {
        btnCancelAction.addEventListener('click', function() {
            cancelConfirmModal.style.display = 'none';
        });
    }
    
    // 신청 모달의 '취소' 버튼 클릭 시 모달 닫기
    document.querySelector('.btn-cancel-application').addEventListener('click', function() {
        applyModal.style.display = 'none';
    });
    
    // 취소 확인 버튼 클릭 시 신청 취소 처리
    if (btnConfirmCancel) {
        btnConfirmCancel.addEventListener('click', function() {
            $.ajax({
                type: 'POST',
                url: '${pageContext.request.contextPath}/support/events/api/cancel',
                data: { eventId: ${event.EV_NO} },
                dataType: 'json',
                success: function(response) {
                    cancelConfirmModal.style.display = 'none';
                    
                    if (response.success) {
                        alert(response.message);
                        location.reload(); // 페이지 새로고침
                    } else {
                        alert(response.message);
                    }
                },
                error: function(xhr, status, error) {
                    cancelConfirmModal.style.display = 'none';
                    console.error('Error:', error);
                    alert('서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
                }
            });
        });
    }
    
    // 모달 외부 클릭 시 모달 닫기
    window.addEventListener('click', function(event) {
        if (event.target === applyModal) {
            applyModal.style.display = 'none';
        }
        if (event.target === cancelConfirmModal) {
            cancelConfirmModal.style.display = 'none';
        }
    });
    
    // 신청 폼 제출 처리
    $('#eventApplyForm').on('submit', function(e) {
        e.preventDefault();
        
        // 개인정보 동의 체크 확인
        if (!$('#agreePrivacy').is(':checked')) {
            alert('개인정보 수집 및 이용에 동의해주세요.');
            return;
        }
        
        // AJAX 폼 제출
        $.ajax({
            type: 'POST',
            url: $(this).attr('action'),
            data: $(this).serialize(),
            dataType: 'json',
            success: function(response) {
                if (response.success) {
                    alert(response.message);
                    applyModal.style.display = 'none';
                    location.reload(); // 페이지 새로고침하여 상태 업데이트
                } else {
                    alert(response.message);
                }
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
                alert('서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
            }
        });
    });
    
    // 카카오맵 API 초기화 함수
    function initKakaoMap() {
        // 세종도서관 좌표 (실제 좌표로 변경 필요)
        const latitude = 36.49863563276369;
        const longitude = 127.26810757514575;
        
        // 지도 생성
        const container = document.getElementById('kakao-map');
        const options = {
            center: new kakao.maps.LatLng(latitude, longitude),
            level: 3
        };
        
        const map = new kakao.maps.Map(container, options);
        
        // 마커 생성
        const markerPosition = new kakao.maps.LatLng(latitude, longitude);
        const marker = new kakao.maps.Marker({
            position: markerPosition
        });
        
        // 마커 지도에 표시
        marker.setMap(map);
        
        // 인포윈도우 생성
        const iwContent = '<div style="padding:5px;">세종도서관</div>';
        const infowindow = new kakao.maps.InfoWindow({
            content: iwContent
        });
        
        // 인포윈도우 표시
        infowindow.open(map, marker);
        
        // 지도 크기 변경 시 다시 중심 맞추기
        window.addEventListener('resize', function() {
            map.setCenter(markerPosition);
        });
    }
    
    // 페이지 로드 시 오시는 길 탭이 활성화된 경우 지도 초기화
    if ($('#tab-location').hasClass('active')) {
        initKakaoMap();
    }
});
</script>