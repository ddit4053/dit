<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="guide-content">
    <div class="guide-content-header">
        <h2 class="content-title">인사말</h2>
        <p class="content-description">
            책GPT도서관에 방문해주신 여러분을 환영합니다.
        </p>
    </div>
    
    <div class="greeting-section">
        <div class="greeting-header">
            <h3 class="greeting-title"><strong>정보</strong>의 <strong>중심</strong>, <strong>책GPT도서관</strong>에 오신 것을 환영합니다.</h3>
        </div>
        
        <div class="greeting-content">
            <div class="greeting-image">
                <img src="${pageContext.request.contextPath}/resource/img/도서관전경.jpg" alt="책GPT도서관 전경" class="library-image">
            </div>
            
            <div class="greeting-text">
                <p>
                    2025년 대전광역시 중심에 개관한 책GPT도서관은 더 창조적인 운영으로 시민이 행복할 수 있고 
                    인문학적 소양 증진을 지원하는 역할을 수행하고 있습니다.
                </p>
                
                <p>
                    "배움은 젊음의 샘이다. 아무리 나이가 많이 들었더라도 배움을 멈추지 마라"란 말에서 볼 수 있듯이
                    배움이란 떄가 정해져 있지 않습니다. 끊임없이 배우고 익힘으로써 급변하는 사회에 우리가 함께 살아갈
                    수 있는 원동력을 만들어 갈 수 있습니다.
                </p>
                
                <p>
                    더불어 열린 도서관을 운영하여 이용고객의 폭넓은 교양을 쌓을 수 있도록 장서를 확충하고, 
                    다양한 독서문화프로그램 개발·운영 등에 최선을 다하며 인문·문화예술 활동을 돕는 
                    복합문화공간의 역할도 수행하고 있습니다.
                </p>
                
                <p>
                   책GPT도서관은 여러분의 성장과 발전을 응원합니다.
                </p>
                
                <div class="signature">
                    <p class="director"><strong>책GPT도서관장</strong></p>
                    
                </div>
            </div>
        </div>
        
        <div class="library-features">
            <h3 class="sub-title">주요 특징</h3>
            
            <div class="features-container">
                <div class="feature-card">
                    <div class="feature-icon">📚</div>
                    <h4>인문학 특화</h4>
                    <p>인문학 관련 자료 집중 수집 및 제공</p>
                </div>
                
                <div class="feature-card">
                    <div class="feature-icon">🏛️</div>
                    <h4>복합문화공간</h4>
                    <p>독서, 문화, 예술이 어우러진 복합 문화 시설</p>
                </div>
                
                <div class="feature-card">
                    <div class="feature-icon">🔍</div>
                    <h4>연구지원</h4>
                    <p>시민과 연구자를 위한 전문 연구 자료 제공</p>
                </div>
                
                <div class="feature-card">
                    <div class="feature-icon">👨‍👩‍👧‍👦</div>
                    <h4>시민 맞춤형</h4>
                    <p>모든 시민을 위한 다양한 독서문화 프로그램 운영</p>
                </div>
            </div>
        </div>
        
        
    </div>
</div>

<style>
.guide-content {
    max-width: 1200px;
    margin: 0 auto;
    padding: 30px 20px;
    font-family: 'Noto Sans KR', sans-serif;
}

.guide-content-header {
    text-align: center;
    margin-bottom: 40px;
}

.content-title {
    font-size: 32px;
    color: #333;
    margin-bottom: 15px;
}

.content-description {
    font-size: 18px;
    color: #555;
    line-height: 1.6;
}

.greeting-section {
    background-color: #fff;
    border-radius: 10px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    padding: 30px;
}

.greeting-header {
    text-align: center;
    margin-bottom: 30px;
    padding-bottom: 20px;
    border-bottom: 1px solid #eee;
}

.greeting-title {
    font-size: 24px;
    color: #8B4513; /* 진한 갈색으로 변경 */
    line-height: 1.5;
}

.greeting-content {
    display: flex;
    flex-direction: column;
    gap: 30px;
}

.greeting-image {
    flex: 1;
    text-align: center;
}

.library-image {
    width: 100%;
    max-width: 600px;
    border-radius: 8px;
    box-shadow: 0 3px 10px rgba(0, 0, 0, 0.2);
}

.greeting-text {
    flex: 1;
}

.greeting-text p {
    font-size: 16px;
    line-height: 1.8;
    margin-bottom: 20px;
    color: #333;
}

.signature {
    text-align: right;
    margin-top: 30px;
}

.director {
    font-size: 18px;
    margin-bottom: 10px;
}

.sign-image {
    width: 150px;
    height: auto;
}

.sub-title {
    font-size: 22px;
    color: #8B4513; /* 진한 갈색으로 변경 */
    margin: 40px 0 20px;
    position: relative;
    padding-left: 15px;
}

.sub-title:before {
    content: "";
    position: absolute;
    left: 0;
    top: 8px;
    height: 60%;
    width: 5px;
    background-color: #8B4513; /* 진한 갈색으로 변경 */
}

.library-features {
    margin-top: 50px;
}

.features-container {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
    justify-content: space-between;
}

.feature-card {
    background-color: #f8f9fa;
    border-radius: 8px;
    padding: 20px;
    flex: 1 1 200px;
    text-align: center;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05);
    transition: transform 0.3s ease;
}

.feature-card:hover {
    transform: translateY(-5px);
}

.feature-icon {
    font-size: 36px;
    margin-bottom: 15px;
}

.feature-card h4 {
    font-size: 18px;
    color: #8B4513; /* 진한 갈색으로 변경 */
    margin-bottom: 10px;
}

.feature-card p {
    font-size: 14px;
    color: #555;
}

.library-navigation {
    margin-top: 50px;
}

.nav-buttons {
    display: flex;
    flex-wrap: wrap;
    gap: 15px;
    justify-content: center;
}

.nav-button {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    width: 150px;
    height: 120px;
    background-color: #8B4513; /* 진한 갈색으로 변경 */
    color: white;
    border-radius: 8px;
    text-decoration: none;
    transition: background-color 0.3s ease;
    text-align: center;
}

.nav-button:hover {
    background-color: #6B3E0D; /* 호버 시 더 어두운 갈색으로 변경 */
}

.nav-icon {
    font-size: 32px;
    margin-bottom: 10px;
}

.nav-button span {
    font-size: 14px;
}

@media (min-width: 768px) {
    .greeting-content {
        flex-direction: row;
    }
    
    .greeting-image, .greeting-text {
        flex: 1;
    }
}
</style>