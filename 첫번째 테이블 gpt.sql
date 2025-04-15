CREATE TABLE users (  -- ����� �⺻ ����
    user_id        NUMBER PRIMARY KEY,                         -- ����� ���� ID
    username       VARCHAR2(50) UNIQUE NOT NULL,               -- ����� ���̵�
    password       VARCHAR2(100) NOT NULL,                     -- ��й�ȣ (��ȣȭ ����)
    name           VARCHAR2(100) NOT NULL,                     -- �̸�
    birth_date     DATE,                                       -- �������
    email          VARCHAR2(100) UNIQUE,                       -- �̸���
    phone_number   VARCHAR2(20),                               -- �޴��� ��ȣ
    address        VARCHAR2(200),                              -- �ּ�
    role           VARCHAR2(20) DEFAULT 'USER',                -- ����� ����(USER/ADMIN)
    created_at     DATE DEFAULT SYSDATE                        -- ��������
);

CREATE TABLE social_accounts (  -- �Ҽ� �α��� ���� ����
    social_id      NUMBER PRIMARY KEY,
    user_id        NUMBER REFERENCES users(user_id),           -- ����� �����
    provider       VARCHAR2(20),                               -- SNS ������ (kakao/naver ��)
    social_uid     VARCHAR2(100)                               -- SNS ���� �ĺ���
);

CREATE TABLE email_verification (  -- �̸��� ���� ����
    verification_id  NUMBER PRIMARY KEY,
    user_id          NUMBER REFERENCES users(user_id),         -- ���� ��� �����
    email            VARCHAR2(100),                            -- �̸���
    token            VARCHAR2(100),                            -- ���� ��ū
    verified         CHAR(1) DEFAULT 'N',                      -- ���� ����(Y/N)
    requested_at     DATE DEFAULT SYSDATE                      -- ��û �ð�
);

-------------------------------------------


CREATE TABLE book_categories (  -- ���� �з�
    category_id    NUMBER PRIMARY KEY,
    category_name  VARCHAR2(100) NOT NULL
);

CREATE TABLE book_publishers (  -- ���ǻ� ����
    publisher_id   NUMBER PRIMARY KEY,
    name           VARCHAR2(100) NOT NULL
);

CREATE TABLE book_authors (  -- ���� ����
    author_id      NUMBER PRIMARY KEY,
    name           VARCHAR2(100) NOT NULL
);

CREATE TABLE books (  -- ���� ����
    book_id        NUMBER PRIMARY KEY,
    title          VARCHAR2(200) NOT NULL,                     -- å ����
    category_id    NUMBER REFERENCES book_categories(category_id),
    author_id      NUMBER REFERENCES book_authors(author_id),
    publisher_id   NUMBER REFERENCES book_publishers(publisher_id),
    isbn           VARCHAR2(20) UNIQUE,                        -- ISBN
    publish_year   NUMBER,                                     -- ����⵵
    image_url      VARCHAR2(300),                              -- �̹��� URL
    total_copies   NUMBER DEFAULT 1,                           -- �� ���� ����
    available_copies NUMBER DEFAULT 1                          -- ���� ���� ����
);

---------------------------------------------
CREATE TABLE book_reservations (  -- ���� ���� ����
    reservation_id  NUMBER PRIMARY KEY,
    user_id         NUMBER REFERENCES users(user_id),
    book_id         NUMBER REFERENCES books(book_id),
    reservation_date DATE DEFAULT SYSDATE,
    status          VARCHAR2(20) DEFAULT '���'                -- ���, �Ϸ�, ��� ��
);

CREATE TABLE book_loans (  -- ���� ���� ����
    loan_id         NUMBER PRIMARY KEY,
    user_id         NUMBER REFERENCES users(user_id),
    book_id         NUMBER REFERENCES books(book_id),
    loan_date       DATE DEFAULT SYSDATE,                      -- ������
    due_date        DATE,                                      -- �ݳ�������
    return_date     DATE                                       -- ���� �ݳ���
);

CREATE TABLE loan_history (  -- ���� �̷�
    history_id      NUMBER PRIMARY KEY,
    loan_id         NUMBER REFERENCES book_loans(loan_id),
    status          VARCHAR2(20),                              -- �ݳ��Ϸ�, ��ü ��
    modified_date   DATE DEFAULT SYSDATE
);

CREATE TABLE overdue_users (  -- ��ü�� ����
    user_id         NUMBER REFERENCES users(user_id),
    overdue_days    NUMBER,                                    -- ��ü�ϼ�
    penalty_until   DATE                                       -- ���� ���� ������
);
------------------------------------------------

CREATE TABLE book_favorites (  -- ���� ���� ���
    favorite_id    NUMBER PRIMARY KEY,
    user_id        NUMBER REFERENCES users(user_id),
    book_id        NUMBER REFERENCES books(book_id),
    added_date     DATE DEFAULT SYSDATE                         -- �߰��� ��¥
);

CREATE TABLE book_recommendations (  -- ��õ ���� ���
    recommendation_id  NUMBER PRIMARY KEY,
    user_id            NUMBER REFERENCES users(user_id),
    book_id            NUMBER REFERENCES books(book_id),
    reason             VARCHAR2(300),                           -- ��õ ����
    recommended_date   DATE DEFAULT SYSDATE
);

CREATE TABLE book_requests (  -- ��� ���� ��û
    request_id     NUMBER PRIMARY KEY,
    user_id        NUMBER REFERENCES users(user_id),
    title          VARCHAR2(200) NOT NULL,                      -- ��û�� ���� ����
    author         VARCHAR2(100),                               -- ��û�� ���� ����
    publisher      VARCHAR2(100),                               -- ��û�� ���� ���ǻ�
    reason         VARCHAR2(300),                               -- ��û ����
    request_date   DATE DEFAULT SYSDATE,
    status         VARCHAR2(20) DEFAULT '����'                  -- ����, ó����, �Ϸ�
);
----------------------------------------------------


CREATE TABLE reading_rooms (  -- ������ ����
    room_id        NUMBER PRIMARY KEY,
    name           VARCHAR2(100) NOT NULL,                      -- ������ �̸�
    total_seats    NUMBER                                       -- �¼� ��
);

CREATE TABLE reading_seats (  -- �¼� ����
    seat_id        NUMBER PRIMARY KEY,
    room_id        NUMBER REFERENCES reading_rooms(room_id),
    seat_number    VARCHAR2(10),                                -- �¼� ��ȣ (��: A1)
    is_available   CHAR(1) DEFAULT 'Y'                          -- ��� ���� ����(Y/N)
);

CREATE TABLE reading_reservations (  -- ������ ���� ����
    reservation_id  NUMBER PRIMARY KEY,
    user_id         NUMBER REFERENCES users(user_id),
    seat_id         NUMBER REFERENCES reading_seats(seat_id),
    start_time      DATE,                                       -- ���� ���۽ð�
    end_time        DATE,                                       -- ���� ����ð�
    status          VARCHAR2(20) DEFAULT '����'                 -- ����, ���, ����
);
-------------------------------------------

CREATE TABLE reviews (  -- ���� �ı�
    review_id      NUMBER PRIMARY KEY,
    user_id        NUMBER REFERENCES users(user_id),
    book_id        NUMBER REFERENCES books(book_id),
    rating         NUMBER CHECK (rating BETWEEN 1 AND 5),       -- ���� (1~5)
    content        VARCHAR2(1000),                              -- �ı� ����
    review_date    DATE DEFAULT SYSDATE
);

CREATE TABLE book_reports (  -- ���İ�
    report_id      NUMBER PRIMARY KEY,
    user_id        NUMBER REFERENCES users(user_id),
    book_id        NUMBER REFERENCES books(book_id),
    title          VARCHAR2(200),
    content        CLOB,
    written_date   DATE DEFAULT SYSDATE
);

CREATE TABLE posts (  -- ���� �Խ��� ��
    post_id        NUMBER PRIMARY KEY,
    user_id        NUMBER REFERENCES users(user_id),
    title          VARCHAR2(200),
    content        CLOB,
    created_at     DATE DEFAULT SYSDATE
);

CREATE TABLE comments (  -- �Խñ� ���
    comment_id     NUMBER PRIMARY KEY,
    post_id        NUMBER REFERENCES posts(post_id),
    user_id        NUMBER REFERENCES users(user_id),
    content        VARCHAR2(500),
    created_at     DATE DEFAULT SYSDATE
);

CREATE TABLE faq (  -- ���� ���� ����
    faq_id         NUMBER PRIMARY KEY,
    question       VARCHAR2(300),
    answer         VARCHAR2(1000)
);
----------------------------------------

CREATE TABLE notifications (  -- �˸� ����
    notification_id  NUMBER PRIMARY KEY,
    user_id          NUMBER REFERENCES users(user_id),
    type             VARCHAR2(20),                              -- �˸� ���� (�̸���/SMS)
    message          VARCHAR2(500),
    sent_at          DATE DEFAULT SYSDATE
);

CREATE TABLE statistics (  -- ��� ������
    stat_id         NUMBER PRIMARY KEY,
    stat_date       DATE,                                       -- ��� ��¥
    total_users     NUMBER,
    total_loans     NUMBER,
    total_visits    NUMBER
);

-------------------------------------------

CREATE TABLE library_info (  -- ������ ����
    library_id      NUMBER PRIMARY KEY,
    name            VARCHAR2(100),                              -- ������ �̸�
    description     VARCHAR2(1000),                             -- �Ұ���
    contact_email   VARCHAR2(100),
    contact_phone   VARCHAR2(20)
);

CREATE TABLE locations (  -- ��ġ ����
    location_id     NUMBER PRIMARY KEY,
    name            VARCHAR2(100),                              -- ���� �̸�
    address         VARCHAR2(200),
    open_time       VARCHAR2(20),                               -- � �ð�
    close_time      VARCHAR2(20)
);

CREATE TABLE system_logs (  -- �ý��� �α�
    log_id          NUMBER PRIMARY KEY,
    user_id         NUMBER REFERENCES users(user_id),
    action          VARCHAR2(200),                              -- ������ �۾�
    log_time        DATE DEFAULT SYSDATE,
    ip_address      VARCHAR2(50)
);
