CREATE TABLE users (  -- 사용자 기본 정보
    user_id        NUMBER PRIMARY KEY,                         -- 사용자 고유 ID
    username       VARCHAR2(50) UNIQUE NOT NULL,               -- 사용자 아이디
    password       VARCHAR2(100) NOT NULL,                     -- 비밀번호 (암호화 저장)
    name           VARCHAR2(100) NOT NULL,                     -- 이름
    birth_date     DATE,                                       -- 생년월일
    email          VARCHAR2(100) UNIQUE,                       -- 이메일
    phone_number   VARCHAR2(20),                               -- 휴대폰 번호
    address        VARCHAR2(200),                              -- 주소
    role           VARCHAR2(20) DEFAULT 'USER',                -- 사용자 역할(USER/ADMIN)
    created_at     DATE DEFAULT SYSDATE                        -- 가입일자
);

CREATE TABLE social_accounts (  -- 소셜 로그인 연결 정보
    social_id      NUMBER PRIMARY KEY,
    user_id        NUMBER REFERENCES users(user_id),           -- 연결된 사용자
    provider       VARCHAR2(20),                               -- SNS 제공자 (kakao/naver 등)
    social_uid     VARCHAR2(100)                               -- SNS 고유 식별자
);

CREATE TABLE email_verification (  -- 이메일 인증 내역
    verification_id  NUMBER PRIMARY KEY,
    user_id          NUMBER REFERENCES users(user_id),         -- 인증 대상 사용자
    email            VARCHAR2(100),                            -- 이메일
    token            VARCHAR2(100),                            -- 인증 토큰
    verified         CHAR(1) DEFAULT 'N',                      -- 인증 여부(Y/N)
    requested_at     DATE DEFAULT SYSDATE                      -- 요청 시간
);

-------------------------------------------


CREATE TABLE book_categories (  -- 도서 분류
    category_id    NUMBER PRIMARY KEY,
    category_name  VARCHAR2(100) NOT NULL
);

CREATE TABLE book_publishers (  -- 출판사 정보
    publisher_id   NUMBER PRIMARY KEY,
    name           VARCHAR2(100) NOT NULL
);

CREATE TABLE book_authors (  -- 저자 정보
    author_id      NUMBER PRIMARY KEY,
    name           VARCHAR2(100) NOT NULL
);

CREATE TABLE books (  -- 도서 정보
    book_id        NUMBER PRIMARY KEY,
    title          VARCHAR2(200) NOT NULL,                     -- 책 제목
    category_id    NUMBER REFERENCES book_categories(category_id),
    author_id      NUMBER REFERENCES book_authors(author_id),
    publisher_id   NUMBER REFERENCES book_publishers(publisher_id),
    isbn           VARCHAR2(20) UNIQUE,                        -- ISBN
    publish_year   NUMBER,                                     -- 발행년도
    image_url      VARCHAR2(300),                              -- 이미지 URL
    total_copies   NUMBER DEFAULT 1,                           -- 총 보유 수량
    available_copies NUMBER DEFAULT 1                          -- 대출 가능 수량
);

---------------------------------------------
CREATE TABLE book_reservations (  -- 도서 예약 내역
    reservation_id  NUMBER PRIMARY KEY,
    user_id         NUMBER REFERENCES users(user_id),
    book_id         NUMBER REFERENCES books(book_id),
    reservation_date DATE DEFAULT SYSDATE,
    status          VARCHAR2(20) DEFAULT '대기'                -- 대기, 완료, 취소 등
);

CREATE TABLE book_loans (  -- 도서 대출 내역
    loan_id         NUMBER PRIMARY KEY,
    user_id         NUMBER REFERENCES users(user_id),
    book_id         NUMBER REFERENCES books(book_id),
    loan_date       DATE DEFAULT SYSDATE,                      -- 대출일
    due_date        DATE,                                      -- 반납예정일
    return_date     DATE                                       -- 실제 반납일
);

CREATE TABLE loan_history (  -- 대출 이력
    history_id      NUMBER PRIMARY KEY,
    loan_id         NUMBER REFERENCES book_loans(loan_id),
    status          VARCHAR2(20),                              -- 반납완료, 연체 등
    modified_date   DATE DEFAULT SYSDATE
);

CREATE TABLE overdue_users (  -- 연체자 관리
    user_id         NUMBER REFERENCES users(user_id),
    overdue_days    NUMBER,                                    -- 연체일수
    penalty_until   DATE                                       -- 대출 정지 종료일
);
------------------------------------------------

CREATE TABLE book_favorites (  -- 관심 도서 목록
    favorite_id    NUMBER PRIMARY KEY,
    user_id        NUMBER REFERENCES users(user_id),
    book_id        NUMBER REFERENCES books(book_id),
    added_date     DATE DEFAULT SYSDATE                         -- 추가한 날짜
);

CREATE TABLE book_recommendations (  -- 추천 도서 목록
    recommendation_id  NUMBER PRIMARY KEY,
    user_id            NUMBER REFERENCES users(user_id),
    book_id            NUMBER REFERENCES books(book_id),
    reason             VARCHAR2(300),                           -- 추천 사유
    recommended_date   DATE DEFAULT SYSDATE
);

CREATE TABLE book_requests (  -- 희망 도서 신청
    request_id     NUMBER PRIMARY KEY,
    user_id        NUMBER REFERENCES users(user_id),
    title          VARCHAR2(200) NOT NULL,                      -- 신청한 도서 제목
    author         VARCHAR2(100),                               -- 신청한 도서 저자
    publisher      VARCHAR2(100),                               -- 신청한 도서 출판사
    reason         VARCHAR2(300),                               -- 신청 이유
    request_date   DATE DEFAULT SYSDATE,
    status         VARCHAR2(20) DEFAULT '접수'                  -- 접수, 처리중, 완료
);
----------------------------------------------------


CREATE TABLE reading_rooms (  -- 열람실 정보
    room_id        NUMBER PRIMARY KEY,
    name           VARCHAR2(100) NOT NULL,                      -- 열람실 이름
    total_seats    NUMBER                                       -- 좌석 수
);

CREATE TABLE reading_seats (  -- 좌석 정보
    seat_id        NUMBER PRIMARY KEY,
    room_id        NUMBER REFERENCES reading_rooms(room_id),
    seat_number    VARCHAR2(10),                                -- 좌석 번호 (예: A1)
    is_available   CHAR(1) DEFAULT 'Y'                          -- 사용 가능 여부(Y/N)
);

CREATE TABLE reading_reservations (  -- 열람실 예약 정보
    reservation_id  NUMBER PRIMARY KEY,
    user_id         NUMBER REFERENCES users(user_id),
    seat_id         NUMBER REFERENCES reading_seats(seat_id),
    start_time      DATE,                                       -- 예약 시작시간
    end_time        DATE,                                       -- 예약 종료시간
    status          VARCHAR2(20) DEFAULT '예약'                 -- 예약, 취소, 종료
);
-------------------------------------------

CREATE TABLE reviews (  -- 도서 후기
    review_id      NUMBER PRIMARY KEY,
    user_id        NUMBER REFERENCES users(user_id),
    book_id        NUMBER REFERENCES books(book_id),
    rating         NUMBER CHECK (rating BETWEEN 1 AND 5),       -- 평점 (1~5)
    content        VARCHAR2(1000),                              -- 후기 내용
    review_date    DATE DEFAULT SYSDATE
);

CREATE TABLE book_reports (  -- 독후감
    report_id      NUMBER PRIMARY KEY,
    user_id        NUMBER REFERENCES users(user_id),
    book_id        NUMBER REFERENCES books(book_id),
    title          VARCHAR2(200),
    content        CLOB,
    written_date   DATE DEFAULT SYSDATE
);

CREATE TABLE posts (  -- 자유 게시판 글
    post_id        NUMBER PRIMARY KEY,
    user_id        NUMBER REFERENCES users(user_id),
    title          VARCHAR2(200),
    content        CLOB,
    created_at     DATE DEFAULT SYSDATE
);

CREATE TABLE comments (  -- 게시글 댓글
    comment_id     NUMBER PRIMARY KEY,
    post_id        NUMBER REFERENCES posts(post_id),
    user_id        NUMBER REFERENCES users(user_id),
    content        VARCHAR2(500),
    created_at     DATE DEFAULT SYSDATE
);

CREATE TABLE faq (  -- 자주 묻는 질문
    faq_id         NUMBER PRIMARY KEY,
    question       VARCHAR2(300),
    answer         VARCHAR2(1000)
);
----------------------------------------

CREATE TABLE notifications (  -- 알림 내역
    notification_id  NUMBER PRIMARY KEY,
    user_id          NUMBER REFERENCES users(user_id),
    type             VARCHAR2(20),                              -- 알림 종류 (이메일/SMS)
    message          VARCHAR2(500),
    sent_at          DATE DEFAULT SYSDATE
);

CREATE TABLE statistics (  -- 통계 데이터
    stat_id         NUMBER PRIMARY KEY,
    stat_date       DATE,                                       -- 통계 날짜
    total_users     NUMBER,
    total_loans     NUMBER,
    total_visits    NUMBER
);

-------------------------------------------

CREATE TABLE library_info (  -- 도서관 정보
    library_id      NUMBER PRIMARY KEY,
    name            VARCHAR2(100),                              -- 도서관 이름
    description     VARCHAR2(1000),                             -- 소개글
    contact_email   VARCHAR2(100),
    contact_phone   VARCHAR2(20)
);

CREATE TABLE locations (  -- 위치 정보
    location_id     NUMBER PRIMARY KEY,
    name            VARCHAR2(100),                              -- 지점 이름
    address         VARCHAR2(200),
    open_time       VARCHAR2(20),                               -- 운영 시간
    close_time      VARCHAR2(20)
);

CREATE TABLE system_logs (  -- 시스템 로그
    log_id          NUMBER PRIMARY KEY,
    user_id         NUMBER REFERENCES users(user_id),
    action          VARCHAR2(200),                              -- 수행한 작업
    log_time        DATE DEFAULT SYSDATE,
    ip_address      VARCHAR2(50)
);
