<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/users/users_faq.css">
<script src="${pageContext.request.contextPath}/resource/js/users/users_faq.js"></script>

<div class="info-content">
    <div class="info-content-header">
        <h2 class="content-title">${pageTitle}</h2>
        <p class="content-description">${pageDescription}</p>
    </div>
    
    <div class="faq-container">
        <div class="faq-category">
            <ul class="category-list">
                <li class="category-item active" data-category="all">전체</li>
                <li class="category-item" data-category="general">일반 문의</li>
                <li class="category-item" data-category="membership">회원 정보</li>
                <li class="category-item" data-category="loan">대출/반납</li>
                <li class="category-item" data-category="facility">시설 이용</li>
                <li class="category-item" data-category="program">프로그램/행사</li>
            </ul>
        </div>
        
        <div class="faq-search">
            <input type="text" id="faqSearch" placeholder="자주 묻는 질문 검색">
            <button type="button" id="faqSearchBtn" class="btn">검색</button>
        </div>
        
        <div class="faq-list">
            <!-- 일반 문의 -->
            <div class="faq-item" data-category="general">
                <div class="faq-question">
                    <span class="question-tag">Q</span>
                    <span class="question-text">도서관 운영 시간은 어떻게 되나요?</span>
                    <span class="toggle-icon">+</span>
                </div>
                <div class="faq-answer">
                    <span class="answer-tag">A</span>
                    <div class="answer-content">
                        <p>도서관 운영 시간은 다음과 같습니다.</p>
                        <ul>
                            <li>평일(월~금): 오전 9시 ~ 오후 9시</li>
                            <li>주말(토~일): 오전 9시 ~ 오후 6시</li>
                            <li>공휴일: 오전 10시 ~ 오후 5시</li>
                        </ul>
                        <p>다만, 매월 첫째 월요일은 도서관 정기 휴관일이니 참고해 주시기 바랍니다.</p>
                    </div>
                </div>
            </div>
            
            <div class="faq-item" data-category="general">
                <div class="faq-question">
                    <span class="question-tag">Q</span>
                    <span class="question-text">도서관 휴관일은 언제인가요?</span>
                    <span class="toggle-icon">+</span>
                </div>
                <div class="faq-answer">
                    <span class="answer-tag">A</span>
                    <div class="answer-content">
                        <p>도서관 정기 휴관일은 매월 첫째 월요일입니다. 이 외에 법정 공휴일과 임시 휴관일이 있을 수 있으니, 방문 전 홈페이지 공지사항을 확인해 주시기 바랍니다.</p>
                    </div>
                </div>
            </div>
            
            <!-- 회원 정보 -->
            <div class="faq-item" data-category="membership">
                <div class="faq-question">
                    <span class="question-tag">Q</span>
                    <span class="question-text">회원 가입은 어떻게 하나요?</span>
                    <span class="toggle-icon">+</span>
                </div>
                <div class="faq-answer">
                    <span class="answer-tag">A</span>
                    <div class="answer-content">
                        <p>회원 가입은 홈페이지 또는 도서관 방문을 통해 가능합니다.</p>
                        <p><strong>온라인 가입</strong>: 홈페이지 상단의 '회원가입' 버튼을 클릭하여 필요한 정보를 입력하시면 됩니다.</p>
                        <p><strong>방문 가입</strong>: 신분증을 지참하여 도서관 1층 안내데스크에서 회원 가입 신청서를 작성하시면 됩니다.</p>
                    </div>
                </div>
            </div>
            
            <div class="faq-item" data-category="membership">
                <div class="faq-question">
                    <span class="question-tag">Q</span>
                    <span class="question-text">비밀번호를 잊어버렸어요. 어떻게 해야 하나요?</span>
                    <span class="toggle-icon">+</span>
                </div>
                <div class="faq-answer">
                    <span class="answer-tag">A</span>
                    <div class="answer-content">
                        <p>비밀번호 찾기는 다음 방법으로 가능합니다.</p>
                        <p>1. 로그인 페이지에서 '비밀번호 찾기' 클릭</p>
                        <p>2. 가입 시 등록한 이메일 또는 휴대폰 번호를 입력</p>
                        <p>3. 인증 후 새로운 비밀번호 설정</p>
                        <p>온라인에서 해결이 어려우신 경우, 신분증을 지참하여 도서관을 방문하시면 안내데스크에서 도움을 드립니다.</p>
                    </div>
                </div>
            </div>
            
            <!-- 대출/반납 -->
            <div class="faq-item" data-category="loan">
                <div class="faq-question">
                    <span class="question-tag">Q</span>
                    <span class="question-text">도서 대출은 몇 권까지 가능한가요?</span>
                    <span class="toggle-icon">+</span>
                </div>
                <div class="faq-answer">
                    <span class="answer-tag">A</span>
                    <div class="answer-content">
                        <p>회원 구분에 따라 대출 가능 권수가 다릅니다.</p>
                        <ul>
                            <li>일반 회원: 5권</li>
                            <li>우수 회원(1년 이상 이용자): 7권</li>
                            <li>어린이 회원(만 14세 미만): 3권</li>
                        </ul>
                        <p>대출 기간은 모든 회원 구분에 관계없이 14일(2주)입니다.</p>
                    </div>
                </div>
            </div>
            
            <div class="faq-item" data-category="loan">
                <div class="faq-question">
                    <span class="question-tag">Q</span>
                    <span class="question-text">대출한 도서 연장은 어떻게 하나요?</span>
                    <span class="toggle-icon">+</span>
                </div>
                <div class="faq-answer">
                    <span class="answer-tag">A</span>
                    <div class="answer-content">
                        <p>대출 연장은 1회에 한해 7일간 가능합니다. 연장 방법은 다음과 같습니다.</p>
                        <p><strong>온라인 연장</strong>: 마이페이지 > 대출현황에서 '연장하기' 버튼 클릭</p>
                        <p><strong>전화 연장</strong>: 도서관 대출데스크(☎ 042-123-4567)로 연락</p>
                        <p><strong>방문 연장</strong>: 도서관 1층 대출데스크 방문</p>
                        <p>단, 해당 도서에 예약이 있거나 반납일이 이미 지난 경우에는 연장이 불가능합니다.</p>
                    </div>
                </div>
            </div>
            
            <!-- 시설 이용 -->
            <div class="faq-item" data-category="facility">
                <div class="faq-question">
                    <span class="question-tag">Q</span>
                    <span class="question-text">스터디룸은 어떻게 예약하나요?</span>
                    <span class="toggle-icon">+</span>
                </div>
                <div class="faq-answer">
                    <span class="answer-tag">A</span>
                    <div class="answer-content">
                        <p>스터디룸 예약은 홈페이지 또는 모바일 앱을 통해 가능합니다.</p>
                        <p>1. 홈페이지 로그인 후 '시설예약' 메뉴 선택</p>
                        <p>2. 원하는 스터디룸과 시간대 선택</p>
                        <p>3. 이용 인원 및 목적 입력 후 예약 완료</p>
                        <p>스터디룸은 최소 2인 이상부터 이용 가능하며, 최대 3시간까지 예약할 수 있습니다. 예약은 7일 전부터 가능합니다.</p>
                    </div>
                </div>
            </div>
            
            <div class="faq-item" data-category="facility">
                <div class="faq-question">
                    <span class="question-tag">Q</span>
                    <span class="question-text">도서관 내 Wi-Fi 이용이 가능한가요?</span>
                    <span class="toggle-icon">+</span>
                </div>
                <div class="faq-answer">
                    <span class="answer-tag">A</span>
                    <div class="answer-content">
                        <p>네, 도서관 전 구역에서 무료 Wi-Fi 이용이 가능합니다.</p>
                        <p>Wi-Fi 이름: Library_Free_WiFi</p>
                        <p>별도의 비밀번호 없이 연결하실 수 있으며, 일일 데이터 사용량 제한은 없습니다.</p>
                    </div>
                </div>
            </div>
            
            <!-- 프로그램/행사 -->
            <div class="faq-item" data-category="program">
                <div class="faq-question">
                    <span class="question-tag">Q</span>
                    <span class="question-text">도서관에서 진행하는 문화 프로그램은 어디서 확인할 수 있나요?</span>
                    <span class="toggle-icon">+</span>
                </div>
                <div class="faq-answer">
                    <span class="answer-tag">A</span>
                    <div class="answer-content">
                        <p>도서관에서 진행하는 다양한 문화 프로그램은 다음 경로에서 확인하실 수 있습니다.</p>
                        <p>1. 홈페이지 > 교육/행사 게시판</p>
                        <p>2. 도서관 모바일 앱 > 프로그램 안내</p>
                        <p>3. 도서관 내 게시판 및 안내 리플릿</p>
                        <p>대부분의 프로그램은 선착순 예약제로 운영되므로, 관심 있는 프로그램이 있으시면 빠른 신청을 권장해 드립니다.</p>
                    </div>
                </div>
            </div>
            
            <div class="faq-item" data-category="program">
                <div class="faq-question">
                    <span class="question-tag">Q</span>
                    <span class="question-text">독서 동아리는 어떻게 가입할 수 있나요?</span>
                    <span class="toggle-icon">+</span>
                </div>
                <div class="faq-answer">
                    <span class="answer-tag">A</span>
                    <div class="answer-content">
                        <p>도서관에서는 다양한 주제의 독서 동아리를 운영하고 있습니다.</p>
                        <p>독서 동아리 가입 방법:</p>
                        <p>1. 홈페이지 > 커뮤니티 > 독서 동아리 메뉴에서 현재 운영 중인 동아리 확인</p>
                        <p>2. 관심 있는 동아리의 '가입 신청' 버튼 클릭</p>
                        <p>3. 간단한 자기소개와 가입 이유 작성 후 제출</p>
                        <p>동아리별로 정원이 있으므로, 모집 기간을 참고하여 신청해 주시기 바랍니다. 새로운 동아리 개설을 원하시는 경우 도서관 2층 문화프로그램팀으로 문의해 주세요.</p>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- 추가 문의 버튼 -->
        <div class="faq-more">
            <p>원하는 답변을 찾지 못하셨나요?</p>
            <a href="${pageContext.request.contextPath}/support/qa" class="inquiry-btn btn">1:1 문의하기</a>
        </div>
    </div>
</div>