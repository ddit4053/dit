// header, nav, footer관련 javaScript

// 메가 드롭다운 메뉴 기능
document.addEventListener("DOMContentLoaded", function () {
  // 메가 드롭다운 메뉴 기능
  const setupMegaDropdown = () => {
	
//	이미 dropdown-spacer가 있는지 확인
//    if (document.querySelector(".dropdown-spacer")) {
//      console.log("Dropdown spacer already exists. Skipping initialization.");
//      return;
//    }
		
    // 드롭다운 spacer 추가
    const mainContent = document.querySelector(".main-content");
    if (mainContent) {
      const dropdownSpacer = document.createElement("div");
      dropdownSpacer.className = "dropdown-spacer";
      mainContent.parentNode.insertBefore(dropdownSpacer, mainContent);
    }

    // 네비게이션 컨테이너
    const navContainer = document.querySelector(".main-nav");
    if (navContainer) {
      // 메가 드롭다운 컨테이너 생성
      const megaDropdownContainer = document.createElement("div");
      megaDropdownContainer.className = "mega-dropdown-container";

      // 사용자 권한 확인 - body 태그에 data-user-no 속성을 사용
      const userRole = document.body.getAttribute("data-role") || 'USER';

      // 메뉴 데이터 - 권한별로 분리
      let menuData;

      if (userRole === 'ADMIN') {
        // 관리자인 경우
        menuData = [
          {
            title: "도서/자료 관리",
            items: [
              { name: "도서 목록", url: "/admin/books/list" },
              { name: "신청 도서", url: "/admin/books/requests" },
              { name: "분실/파손 도서", url: "/admin/books/damaged" },
              { name: "도서 통계", url: "/admin/books/stats" },
            ],
          },
          {
            title: "대출/반납 관리",
            items: [
              { name: "대출 관리", url: "/admin/loans/management" },
              { name: "반납 도서 목록", url: "/admin/loans/returns" },
              { name: "연체/정지", url: "/admin/loans/overdue" },
              { name: "알림 관리", url: "/admin/loans/notifications" },
              { name: "대출/반납 통계", url: "/admin/loans/stats" },
            ],
          },
          {
            title: "열람실 관리",
            items: [
              { name: "열람실 이용 내역", url: "/admin/reading/room-history" },
            ],
          },
          {
            title: "게시판 관리",
            items: [
              { name: "커뮤니티 게시판", url: "/admin/board/community" },
              { name: "이용안내 게시판", url: "/admin/board/info" },
              { name: "게시판 통합 관리", url: "/admin/board/settings" },
            ],
          },
        ];
      } else {
        // 일반 사용자인 경우
        menuData = [
          {
            title: "자료 검색",
            items: [
              { name: "도서 검색", url: "/books/search" },
              { name: "신착 도서", url: "/books/new" },
              { name: "관심 도서", url: "/books/favor" },
              { name: "추천 도서", url: "/books/recommend" },
            ],
          },
          {
            title: "열람실",
            items: [
              { name: "열람실 현황", url: "/reading/room-status" },
              { name: "열람실 예약", url: "/reading/room-booking" },
            ],
          },
          {
            title: "도서관 소개",
            items: [
              { name: "인사말", url: "/board/guide/greetings" },
              { name: "도서관 소개", url: "/board/guide/intro" },
              { name: "편의 시설", url: "/board/guide/facillities" },
              { name: "오시는 길", url: "/board/guide/path" },
            ],
          },
          {
            title: "커뮤니티",
            items: [
              { name: "독후감 게시판", url: "/board/community/review" },
              { name: "토론게시판", url: "/board/community/discussion" },
              { name: "회원 도서추천게시판", url: "/board/community/recommend" },
            ],
          },
          {
            title: "이용안내",
            items: [
              { name: "공지사항", url: "/board/info/notice" },
              { name: "교육/행사", url: "/board/info/edu-event" },
              { name: "시설 이용 안내", url: "/board/info/facility" },
              { name: "자주 묻는 질문", url: "/board/info/faq" },
              { name: "1:1 문의", url: "/board/info/qa" },
            ],
          },
        ];
      }

      // 메가 드롭다운 메뉴 생성
      const megaDropdownMenu = document.createElement("div");
      megaDropdownMenu.className = "mega-dropdown-menu";

      // 메뉴 데이터로 컬럼 생성
      menuData.forEach((section) => {
        const column = document.createElement("div");
        column.className = "mega-menu-column";

        const title = document.createElement("h3");
        title.textContent = section.title;
        column.appendChild(title);

        const itemList = document.createElement("ul");

        section.items.forEach((item) => {
          const listItem = document.createElement("li");
          const link = document.createElement("a");
          link.href = `${getContextPath()}${item.url}`;
          link.textContent = item.name;
          listItem.appendChild(link);
          itemList.appendChild(listItem);
        });

        column.appendChild(itemList);
        megaDropdownMenu.appendChild(column);
      });

      megaDropdownContainer.appendChild(megaDropdownMenu);
      navContainer.appendChild(megaDropdownContainer);

      // 네비게이션 이벤트 리스너
      navContainer.addEventListener("mouseenter", function () {
        document.body.classList.add("dropdown-active");
      });

      navContainer.addEventListener("mouseleave", function () {
        document.body.classList.remove("dropdown-active");
      });
    }
  };

  // 기존 함수 호출과 함께 호출
  setupMegaDropdown();
});

// context path 가져오기
function getContextPath() {
  const path = window.location.pathname;
  const webappName = path.split("/")[1];
  return "/" + webappName;
}

// AJAX 요청 함수
function fetchData(url, callback) {
  const xhr = new XMLHttpRequest();
  xhr.open("GET", url, true);

  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4) {
      if (xhr.status === 200) {
        callback(null, JSON.parse(xhr.responseText));
      } else {
        callback(new Error("데이터를 불러오는데 실패했습니다."), null);
      }
    }
  };

  xhr.onerror = function () {
    callback(new Error("네트워크 오류가 발생했습니다."), null);
  };

  xhr.send();
}
