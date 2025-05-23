// 메뉴 구조 데이터
const menuStructure = {
  // 회원 메뉴
  member: [
    {
      title: "마이페이지",
      url: "/user/mypage",
      subMenus: [
        {
          title: "회원정보 수정",
          url: "/user/mypage/updateInfo.do",
          subMenus: [
            { title: "회원정보 수정", url: "/user/mypage/updateInfo.do" },
            { title: "비밀번호 변경", url: "/user/mypage/changePassword.do" },
            { title: "회원탈퇴", url: "/user/mypage/quitUser.do" },
          ],
        },
        {
          title: "예약 현황",
          url: "/user/mypage/bookResList.do",
          subMenus: [
            { title: "도서 예약현황", url: "/user/mypage/bookResList.do" },
            { title: "열람실 예약현황", url: "/user/mypage/roomResList.do" },
          ],
        },
        { title: "도서 대출현황", url: "/user/mypage/bookLoansList.do" },
        { title: "도서 대출이력", url: "/user/mypage/loansList.do" },
        { title: "관심도서", url: "/user/mypage/bookFavoritesList.do" },
        { title: "신규도서 신청현황", url: "/user/mypage/bookReqList.do" },
        { title: "열람실 이용내역", url: "/user/mypage/resList.do" },
        { title: "교육/행사 신청내역", url: "/user/mypage/eventReqList.do" },
        { title: "게시글 작성내역", url: "/user/mypage/bookReportList.do" },
      ],
    },

    {
      title: "자료검색",
      url: "/books",
      subMenus: [
        {
          title: "도서 검색",
          url: "/books/search",
          subMenus: [{ title: "카테고리 검색", url: "/books/search/searchCategory" }],
        },
        { title: "신착 도서", url: "/books/new" },
        { title: "관심 도서", url: "/user/mypage/bookFavoritesList.do" },
        { title: "추천 도서", url: "/books/recommend" },
      ],
    },
    {
      title: "열람실",
      url: "/reading/",
      subMenus: [
        { title: "열람실 현황", url: "/readingMain.do" },
        { title: "열람실 예약", url: "/readingMain.do" },
      ],
    },
    {
      title: "도서관 소개",
      url: "/about/",
      subMenus: [
        { title: "인사말", url: "/about/greetings" },
        { title: "시설 소개", url: "/about/intro" },
        { title: "오시는 길", url: "/about/location" },
        { title: "편의시설", url: "/about/facilities" },
      ],
    },
    {
      title: "커뮤니티",
      url: "/community/",
      subMenus: [
        {
          title: "독후감 게시판",
          url: "/community/reviews",
        },
        {
          title: "토론 게시판",
          url: "/community/discussions",
        },
        { title: "회원 도서 추천 게시판", url: "/community/recommendations" },
      ],
    },
    {
      title: "이용안내",
      url: "/support/",
      subMenus: [
        { title: "공지사항", url: "/support/notices" },
        { title: "교육/행사", url: "/support/events" },
        { title: "자주 묻는 질문", url: "/support/faq" },
        { title: "1:1 문의", url: "/support/qa" },
      ],
    },
  ],

  // 관리자 메뉴
  admin: [
    {
      title: "도서/자료 관리",
      url: "/admin/books",
      subMenus: [
        { title: "도서 목록", url: "/admin/books/list" },
        {
          title: "신청 도서", url: "/admin/books/requests"
        },
        {
          title: "분실/파손 도서",
          url: "/admin/books/damaged",
          subMenus: [
            { title: "분실/파손 도서 목록", url: "/admin/books/damaged/list" },
          ],
        },
        { title: "도서 통계", url: "/admin/books/stats" },
      ],
    },
    {
      title: "대출/반납 관리",
      url: "/admin/loans",
      subMenus: [
        {
          title: "대출 관리",
          url: "/admin/loans/management",
          subMenus: [
            { title: "대출 도서 목록", url: "/admin/loans/management/list" },
            {
              title: "대출 연장 관리",
              url: "/admin/loans/management/extension",
            },
          ],
        },
        { title: "반납 도서 목록", url: "/admin/loans/returns" },
        {
          title: "연체/정지",
          url: "/admin/loans/overdue",
          subMenus: [
            { title: "연체 목록", url: "/admin/loans/overdue/list" },
            { title: "연체 기준 설정", url: "/admin/loans/overdue/settings" },
            {
              title: "대출 정지 대상자 목록",
              url: "/admin/loans/overdue/suspended",
            },
          ],
        },
        {
          title: "알림 관리",
          url: "/admin/loans/notifications",
          subMenus: [
            {
              title: "반납일/ 연체자 알림 설정",
              url: "/admin/loans/notifications/settings",
            },
          ],
        },
        {
          title: "대출/반납 통계",
          url: "/admin/loans/stats",
          subMenus: [
            {
              title: "대출/반납/연체 월별 통계",
              url: "/admin/loans/stats/chart",
            },
            { title: "예정 반납일 캘린더", url: "/admin/loans/stats/calendar" },
          ],
        },
      ],
    },
    {
      title: "열람실 관리",
      url: "/admin/reading",
      subMenus: [{ title: "열람실 이용 내역", url: "/admin/reading" }],
    },
    {
      title: "게시판 관리",
      url: "/admin/board",
      subMenus: [{ title: "게시판 현황", url: "/admin/board/list"}],
    },
  ],
};

/**
 * 현재 URL에 맞는 최상위 메뉴 찾기
 * @param {Array} menuData - 메뉴 데이터 배열
 * @param {String} url - 현재 URL
 * @returns {Object|null} - 찾은 최상위 메뉴 객체와 인덱스
 */
function findTopLevelMenu(menuData, url) {
  // Context Path를 제거하는 정확한 방법
  // URL이 "/_Library_Management/board/info"라면,
  // contextPath가 "/_Library_Management"이므로
  // 실제 비교할 URL은 "/board/info"가 되어야 함

  // 디버깅을 위한 출력
  console.log("Finding top level menu:");
  console.log("Current URL:", url);

  // Context Path 제거하지 않고, 각 메뉴 URL이 현재 URL에 포함되는지 확인
  for (let i = 0; i < menuData.length; i++) {
    const menuUrl = menuData[i].url;
    console.log(`Checking menu "${menuData[i].title}" with URL: ${menuUrl}`);

    // 단순히 menuUrl이 현재 URL에 포함되는지 확인 (startsWith 대신)
    if (url.indexOf(menuUrl) !== -1) {
      console.log(`Match found: ${menuData[i].title}`);
      return { menu: menuData[i], index: i };
    }
  }

  console.log("No matching menu found");
  return null;
}

/**
 * 현재 활성화된 메뉴 경로 찾기
 * @param {Array} menuData - 메뉴 데이터 배열 (최상위 메뉴의 하위 메뉴)
 * @param {String} url - 현재 URL
 * @returns {Array} - 활성 메뉴의 인덱스 경로
 */
function findActiveSubMenuPath(menuData, url) {
  const activeMenus = [];

  function traverse(items, parentIndices = [], level = 0) {
    for (let i = 0; i < items.length; i++) {
      const item = items[i];
      const currentIndices = [...parentIndices, i];

      // URL이 메뉴 URL과 일치하거나 시작하면 해당 메뉴는 활성화
      if (url.includes(item.url)) {
        activeMenus.push({
          indices: currentIndices,
          urlLength: item.url.length,
          level: level,
        });
      }

      // 하위 메뉴가 있으면 재귀적으로 탐색
      if (item.subMenus && item.subMenus.length > 0) {
        traverse(item.subMenus, currentIndices, level + 1);
      }
    }
  }

  traverse(menuData);

  // URL 길이가 가장 긴 메뉴를 선택 (가장 구체적인 메뉴)
  if (activeMenus.length > 0) {
    activeMenus.sort((a, b) => {
      // 1. URL 길이로 정렬 (더 긴 것이 더 구체적)
      if (b.urlLength !== a.urlLength) {
        return b.urlLength - a.urlLength;
      }
      // 2. 같은 URL 길이일 경우 레벨이 높은 것 선택
      return b.level - a.level;
    });
    return activeMenus[0].indices;
  }

  return [];
}

/**
 * 메뉴가 활성 경로에 있는지 확인
 * @param {Array} activePath - 활성 메뉴 경로
 * @param {Number} level - 현재 메뉴 레벨
 * @param {Number} index - 현재 메뉴 인덱스
 * @returns {Boolean} - 활성 여부
 */
function isMenuActive(activePath, level, index) {
  return activePath.length > level && activePath[level] === index;
}

/**
 * 현재 경로가 활성 경로의 자식인지 확인
 * @param {Array} currentPath - 현재 경로
 * @param {Array} activePath - 활성 경로
 * @returns {Boolean} - 자식 여부
 */
function isChildOfActivePath(currentPath, activePath) {
  if (currentPath.length > activePath.length) return false;

  for (let i = 0; i < currentPath.length; i++) {
    if (currentPath[i] !== activePath[i]) return false;
  }

  return true;
}

/**
 * Context Path 가져오기
 * @returns {String} - 컨텍스트 경로
 */
function getContextPath() {
  return typeof contextPath !== "undefined" && contextPath ? contextPath : "";
}

/**
 * 사이드바 메뉴 생성
 */
function createSidebarMenu() {
  const menuData = isAdmin ? menuStructure.admin : menuStructure.member;
  const sidebarNav = document.getElementById("sidebarNav");

  if (!sidebarNav) return; // 사이드바 요소가 없으면 중단

  // 현재 URL에 해당하는 최상위 메뉴 찾기
  const topLevelMenuInfo = findTopLevelMenu(menuData, currentURL);

  // 최상위 메뉴가 없으면 기본 메뉴 표시
  if (!topLevelMenuInfo) {
    const defaultTitle = document.createElement("h2");
    defaultTitle.className = "sidebar-title";
    defaultTitle.textContent = isAdmin ? "관리자 메뉴" : "메뉴";
    sidebarNav.appendChild(defaultTitle);
    return;
  }

  const topLevelMenu = topLevelMenuInfo.menu;

  // 현재 메뉴의 하위 메뉴에서 활성 경로 찾기
  const activeSubMenuPath = findActiveSubMenuPath(
    topLevelMenu.subMenus || [],
    currentURL
  );

  // 타이틀 추가
  const title = document.createElement("h2");
  title.className = "sidebar-title";
  title.textContent = topLevelMenu.title;
  sidebarNav.appendChild(title);

  // 하위 메뉴가 없으면 종료
  if (!topLevelMenu.subMenus || topLevelMenu.subMenus.length === 0) {
    return;
  }

  // 메인 메뉴 ul 생성
  const mainMenu = document.createElement("ul");
  mainMenu.className = "sidebar-menu";
  sidebarNav.appendChild(mainMenu);

  // 메뉴 생성 함수 - 아코디언 효과 추가(2025.04.25)
  function createMenuItems(
    parentElement,
    items,
    level = 0,
    parentIndices = []
  ) {
    items.forEach((item, index) => {
      const currentIndices = [...parentIndices, index];
      const isActive = isMenuActive(activeSubMenuPath, level, index);
      const hasSubMenu = item.subMenus && item.subMenus.length > 0;

      // 메뉴 아이템 생성
      const li = document.createElement("li");
      li.className = level === 0 ? "sidebar-menu-item" : "sidebar-submenu-item";
      if (isActive) li.classList.add("active");
      parentElement.appendChild(li);

      // 링크 생성
      const a = document.createElement("a");
      a.href = getContextPath() + item.url;

      // 하위 메뉴가 있는 경우 클래스 추가
      if (hasSubMenu) {
        a.className =
          level === 0
            ? "sidebar-link sidebar-link-with-submenu"
            : "sidebar-sublink sidebar-sublink-with-submenu";
      } else {
        a.className = level === 0 ? "sidebar-link" : "sidebar-sublink";
      }

      a.textContent = item.title;
      li.appendChild(a);

      // 서브메뉴가 있는 경우
      if (hasSubMenu) {
        const subMenu = document.createElement("ul");
        subMenu.className = "sidebar-submenu";

        // 활성화된 경로에 있는 서브메뉴는 열어둠
        if (
          isActive ||
          isChildOfActivePath(currentIndices, activeSubMenuPath)
        ) {
          subMenu.classList.add("open");
        }

        li.appendChild(subMenu);

        // 서브메뉴 아이템 생성 (재귀)
        createMenuItems(subMenu, item.subMenus, level + 1, currentIndices);

        // 클릭 이벤트 - 아코디언 효과
        a.addEventListener("click", function (e) {
          e.preventDefault();

          if (level === 0) {
            // 최상위 메뉴일 경우
            const siblingMenuItems =
              parentElement.querySelectorAll(`.sidebar-menu-item`);
            siblingMenuItems.forEach((menuItem) => {
              if (menuItem !== li) {
                menuItem.classList.remove("active"); // 다른 메뉴 비활성화
                const siblingSubmenu =
                  menuItem.querySelector(".sidebar-submenu");
                if (siblingSubmenu) {
                  siblingSubmenu.classList.remove("open"); // 하위 서브메뉴 닫기
                }
              }
            });
          } else {
            // 하위 메뉴일 경우
            const siblingMenuItems = parentElement.querySelectorAll(
              `.sidebar-submenu-item`
            );
            siblingMenuItems.forEach((menuItem) => {
              if (menuItem !== li) {
                menuItem.classList.remove("active");
                const siblingSubmenu =
                  menuItem.querySelector(".sidebar-submenu");
                if (siblingSubmenu) {
                  siblingSubmenu.classList.remove("open");
                }
              }
            });
          }

          // 현재 메뉴 토글
          li.classList.toggle("active");

          // 서브메뉴 표시/숨김
          const submenu = this.nextElementSibling; // 현재 요소 바로 다음에 위치한 형제 요소 선택
          submenu.classList.toggle("open");
        });
      }
    });
  }

  // 현재 최상위 메뉴의 하위 메뉴만 생성
  createMenuItems(mainMenu, topLevelMenu.subMenus);

  // 활성화된 메뉴에 따라 서브메뉴 표시
  if (activeSubMenuPath.length > 0) {
    // 현재 활성화된 경로의 모든 서브메뉴 표시
    let currentElement = mainMenu;

    for (let i = 0; i < activeSubMenuPath.length; i++) {
      if (i >= currentElement.children.length) break;

      const menuItems = currentElement.children;
      const activeItem = menuItems[activeSubMenuPath[i]];

      if (activeItem) {
        // 활성화 클래스 추가
        activeItem.classList.add("active");

        // 다음 레벨의 서브메뉴 찾기
        const subMenu = activeItem.querySelector(".sidebar-submenu");
        if (subMenu) {
          subMenu.classList.add("open");
          currentElement = subMenu;
        }
      }
    }
  }
}

// DOM이 로드되면 사이드바 메뉴 생성
document.addEventListener("DOMContentLoaded", function () {
  try {
    createSidebarMenu();
  } catch (e) {
    console.log("사이드바 메뉴 초기화 중 오류 발생: ", e);
  }
});