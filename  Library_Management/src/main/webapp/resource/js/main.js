// 메인 페이지 JavaScript 기능
document.addEventListener("DOMContentLoaded", function () {
  // 배너 슬라이더 기능
  const setupBannerSlider = () => {
    const slides = document.querySelectorAll(".slide");
    const dotsContainer = document.getElementById("dotsContainer");
    const prevBtn = document.getElementById("prevSlide");
    const nextBtn = document.getElementById("nextSlide");
    let currentSlide = 0;
    let slideInterval;

    // 점 생성
    slides.forEach((_, index) => {
      const dot = document.createElement("span");
      dot.className = "dot";
      if (index === 0) dot.classList.add("active");
      dot.addEventListener("click", () => goToSlide(index));
      dotsContainer.appendChild(dot);
    });

    const dots = document.querySelectorAll(".dot");

    // 슬라이드 전환 함수
    function goToSlide(n) {
      slides[currentSlide].classList.remove("active");
      dots[currentSlide].classList.remove("active");

      currentSlide = (n + slides.length) % slides.length;

      slides[currentSlide].classList.add("active");
      dots[currentSlide].classList.add("active");
    }

    // 다음 슬라이드로 이동
    function nextSlide() {
      goToSlide(currentSlide + 1);
    }

    // 이전 슬라이드로 이동
    function prevSlide() {
      goToSlide(currentSlide - 1);
    }

    // 자동 슬라이드 시작
    function startSlideShow() {
      slideInterval = setInterval(nextSlide, 5000);
    }

    // 자동 슬라이드 정지
    function stopSlideShow() {
      clearInterval(slideInterval);
    }

    // 이벤트 리스너 등록
    prevBtn.addEventListener("click", function () {
      prevSlide();
      stopSlideShow();
      startSlideShow();
    });

    nextBtn.addEventListener("click", function () {
      nextSlide();
      stopSlideShow();
      startSlideShow();
    });

    // 슬라이더에 마우스 오버 시 자동 슬라이드 정지
    const bannerSlider = document.getElementById("bannerSlider");
    bannerSlider.addEventListener("mouseenter", stopSlideShow);
    bannerSlider.addEventListener("mouseleave", startSlideShow);

    // 초기 자동 슬라이드 시작
    startSlideShow();
  };

  // 메인 검색 기능
  const setupMainSearch = () => {
    const mainSearchButton = document.getElementById("mainSearchButton");
    const mainSearchInput = document.getElementById("mainSearchInput");

    if (mainSearchButton && mainSearchInput) {
      mainSearchButton.addEventListener("click", function () {
        if (mainSearchInput.value.trim() !== "") {
          window.location.href = `${getContextPath()}/books/search/result?query=${encodeURIComponent(
            mainSearchInput.value.trim()
          )}`;
        }
      });

      mainSearchInput.addEventListener("keypress", function (e) {
        if (e.key === "Enter" && mainSearchInput.value.trim() !== "") {
          window.location.href = `${getContextPath()}/books/search/result?query=${encodeURIComponent(
            mainSearchInput.value.trim()
          )}`;
        }
      });
    }
  };

  // 신착 도서 데이터 가져오기(페이징)
  let allBooks = [];
  let currentIndex = 0;
  const booksPerPage = 5;

  function renderBooks() {
    const container = document.getElementById("newBooksContainer");
    container.innerHTML = "";

    const booksToShow = allBooks.slice(
      currentIndex,
      currentIndex + booksPerPage
    );
    booksToShow.forEach((book) => {
      const bookItem = document.createElement("div");
      bookItem.className = "book-item";

      bookItem.innerHTML = `
	              <div class="book-cover">
	                  <img src="${book.cover}" alt="${book.title}">
	              </div>
	              <div class="book-title">${book.title}</div>
	              <div class="book-author">${book.author}</div>
	          `;

      bookItem.addEventListener("click", function () {
        window.location.href = `${getContextPath()}/books/detail?bookNo=${
          book.bookNo
        }`;
      });

      container.appendChild(bookItem);
    });
  }
  //신착도서 불러오기
  function fetchNewBooks() {
    fetchData(`${getContextPath()}/api/books/new`, function (err, data) {
      const container = document.getElementById("newBooksContainer");

      if (err || !data || data.length === 0) {
        container.innerHTML = '<div class="error">신착 도서가 없습니다.</div>';
        return;
      }

      allBooks = data;
      currentIndex = 0;
      renderBooks();
    });
  }

  //신착도서 페이징
  document.getElementById("prevBtn").addEventListener("click", () => {
    if (currentIndex >= booksPerPage) {
      currentIndex -= booksPerPage;
      renderBooks();
    }
  });

  //신착도서 페이징
  document.getElementById("nextBtn").addEventListener("click", () => {
    if (currentIndex + booksPerPage < allBooks.length) {
      currentIndex += booksPerPage;
      renderBooks();
    }
  });

  // 추천 도서 데이터 가져오기
  function fetchRecommendedBooks() {
    const container = document.getElementById("recommendedBooksContainer");

    fetchData(
      `${getContextPath()}/api/books/recommended`,
      function (err, data) {
        if (err || !data || data.length === 0) {
          container.innerHTML =
            '<div class="error">추천 도서가 없습니다.</div>';
          return;
        }

        container.innerHTML = "";

        data.forEach((book) => {
          const bookItem = document.createElement("div");
          bookItem.className = "book-item";

          bookItem.innerHTML = `
                  <div class="book-cover">
                      <img src="${book.cover}" alt="${book.title}">
                  </div>
                  <div class="book-title">${book.title}</div>
                  <div class="book-author">${book.author}</div>
              `;

          bookItem.addEventListener("click", function () {
            window.location.href = `${getContextPath()}/books/detail?bookNo=${
              book.bookNo
            }`;
          });

          container.appendChild(bookItem);
        });
      }
    );
  }

  // 공지사항 데이터 가져오기 (최신순)
  const fetchNotices = () => {
    const container = document.getElementById("noticeList");

    // AJAX 요청 설정
    const params = {
      codeNo: 4, // 공지사항 게시판 코드
      sortType: "latest", // 최신순 정렬
      limit: 5, // 상위 5개만 가져오기
    };

    $.ajax({
      url: `${getContextPath()}/popularBoardAjax`,
      type: "GET",
      data: params,
      dataType: "json",
      success: function (data) {
        if (!data || data.length === 0) {
          container.innerHTML =
            '<li class="no-data">등록된 공지사항이 없습니다.</li>';
          return;
        }

        container.innerHTML = "";

        data.forEach((notice) => {
          const li = document.createElement("li");

          li.innerHTML = `
          <a href="${getContextPath()}/support/notices/Detail?boardNo=${
            notice.boardNo
          }">
            <span class="notice-title">${notice.title}</span>
            <span class="notice-date">${notice.writtenDate}</span>
          </a>
        `;

          container.appendChild(li);
        });
      },
      error: function (xhr, status, error) {
        console.error("공지사항을 불러오는데 실패했습니다:", error);
        container.innerHTML =
          '<li class="error">공지사항을 불러오는데 실패했습니다.</li>';
      },
    });
  };

  // 인기 독후감 데이터 가져오기 (조회수 순)
  const fetchPopularReviews = () => {
    const container = document.getElementById("eventList");

    // AJAX 요청 설정
    const params = {
      codeNo: 1, // 독후감 게시판 코드
      sortType: "views", // 조회수 순 정렬
      limit: 5, // 상위 5개만 가져오기
    };

    $.ajax({
      url: `${getContextPath()}/popularBoardAjax`,
      type: "GET",
      data: params,
      dataType: "json",
      success: function (data) {
        if (!data || data.length === 0) {
          container.innerHTML =
            '<li class="no-data">등록된 독후감이 없습니다.</li>';
          return;
        }

        container.innerHTML = "";

        data.forEach((post) => {
          const li = document.createElement("li");

          li.innerHTML = `
          <a href="${getContextPath()}/community/reviews/Detail?boardNo=${
            post.boardNo
          }">
            <span class="event-title">${post.title}</span>
            <span class="event-date">${post.writtenDate}</span>
          </a>
        `;

          container.appendChild(li);
        });
      },
      error: function (xhr, status, error) {
        console.error("독후감을 불러오는데 실패했습니다:", error);
        container.innerHTML =
          '<li class="error">독후감을 불러오는데 실패했습니다.</li>';
      },
    });
  };

  // 열람실 현황 데이터 가져오기
  const fetchReadingRoomStatus = () => {
    const container = document.getElementById("roomStatus");

    fetchData(
      `${getContextPath()}/api/reading-rooms/status`,
      function (err, data) {
        if (err) {
          container.innerHTML =
            '<div class="error">열람실 정보를 불러오는데 실패했습니다.</div>';
          return;
        }

        if (data.length === 0) {
          container.innerHTML =
            '<div class="no-data">등록된 열람실이 없습니다.</div>';
          return;
        }

        container.innerHTML = "";

        data.forEach((room) => {
          const availableSeats = room.totalSeats - room.occupiedSeats;
          const availabilityPercentage =
            (availableSeats / room.totalSeats) * 100;

          const roomCard = document.createElement("div");
          roomCard.className = "room-card";

          roomCard.innerHTML = `
                    <div class="room-name">${room.name}</div>
                    <div class="seat-info">${availableSeats} / ${
            room.totalSeats
          }</div>
                    <div class="availability-percentage">
                        <div class="availability-bar" style="width: ${availabilityPercentage}%"></div>
                    </div>
                    <a href="${getContextPath()}/reading-room/reserve?id=${
            room.roomId
          }" class="btn">예약하기</a>
                `;

          container.appendChild(roomCard);
        });
      }
    );
  };

  // 모든 함수 실행
  setupBannerSlider();
  setupMainSearch();
  fetchNewBooks();
  fetchRecommendedBooks();
  fetchNotices();
  fetchPopularReviews();
  /*fetchReadingRoomStatus();*/
});
