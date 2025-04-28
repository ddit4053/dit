// 메뉴 구조 데이터
const menuStructure = {
  // 회원 메뉴
  member: [
    {
      title: "자료검색",
      url: "/books",
      subMenus: [
        {
          title: "도서 검색",
          url: "/books/search",
          subMenus: [
            { title: "카테고리 검색", url: "/books/search/searchCategory" },
          ],
        },
        { title: "신착 도서", url: "/books/new" },
        { title: "관심 도서", url: "/books/favor" },
        { title: "추천 도서", url: "/books/recommend" },
      ],
    },
    {
      title: "열람실",
      url: "/reading",
      subMenus: [
        { title: "열람실 현황", url: "/reading/room-status" },
        { title: "열람실 예약", url: "/reading/room-booking" },
      ],
    },
    {
      title: "도서관 소개",
      url: "/board/guide",
      subMenus: [
        { title: "인사말", url: "/board/guide/greetings" },
        { title: "시설 소개", url: "/board/guide/intro" },
        { title: "편의 시설", url: "/board/guide/facilities" },
        { title: "오시는 길", url: "/board/guide/path" },
      ],
    },
    {
      title: "커뮤니티",
      url: "/board/community",
      subMenus: [
        {
          title: "독후감 게시판",
          url: "/board/community/review",
          subMenus: [
            { title: "독후감 게시판", url: "/board/community/review/list" },
            { title: "나의 독후감", url: "/board/community/review/my" },
          ],
        },
        {
          title: "토론 게시판",
          url: "/board/community/discussion",
          subMenus: [
            { title: "토론 게시판", url: "/board/community/discussion/list" },
            { title: "참여중인 토론", url: "/board/community/discussion/my" },
          ],
        },
        { title: "회원 도서 추천 게시판", url: "/board/community/recommend" },
      ],
    },
    {
      title: "이용안내",
      url: "/board/info",
      subMenus: [
        { title: "공지사항", url: "/board/info/notice" },
        { title: "교육/행사", url: "/board/info/event" },
        {
          title: "시설 이용 안내",
          url: "/board/info/facility",
          subMenus: [
            { title: "도서 대출/반납 안내", url: "/board/info/facility/loan" },
            {
              title: "열람실 이용 안내",
              url: "/board/info/facility/reading-room",
            },
          ],
        },
        {
          title: "자주 묻는 질문",
          url: "/board/info/faq",
          subMenus: [
            { title: "도서 FAQ", url: "/board/info/faq/books" },
            { title: "반납/대출 FAQ", url: "/board/info/faq/loans" },
            { title: "열람실 FAQ", url: "/board/info/faq/reading-room" },
            { title: "사이트 이용 FAQ", url: "/board/info/faq/site" },
          ],
        },
        {
          title: "1:1 문의",
          url: "/board/info/qa",
          subMenus: [
            { title: "1:1 문의 작성", url: "/board/info/qa/write" },
            { title: "1:1 문의 내역", url: "/board/info/qa/list" },
          ],
        },
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
          title: "신청 도서",
          url: "/admin/books/requests",
          subMenus: [{ title: "신청 목록", url: "/admin/books/requests/list" }],
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
            {
              title: "대출 정지 기준 설정",
              url: "/admin/loans/overdue/suspended-settings",
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
            { title: "대출 예약 통계", url: "/admin/loans/stats/reservation" },
            { title: "누적 대출 통계", url: "/admin/loans/stats/cumulative" },
            { title: "예정 반납일 캘린더", url: "/admin/loans/stats/calendar" },
          ],
        },
      ],
    },
    {
      title: "열람실 관리",
      url: "/admin/reading",
      subMenus: [
        { title: "열람실 이용 내역", url: "/admin/reading/room-history" },
      ],
    },
    {
      title: "게시판 관리",
      url: "/admin/board",
      subMenus: [
        {
          title: "커뮤니티 게시판",
          url: "/admin/board/community",
          subMenus: [
            {
              title: "독후감 게시판 관리",
              url: "/admin/board/community/review",
              subMenus: [
                {
                  title: "게시글 목록",
                  url: "/admin/board/community/review/list",
                },
                {
                  title: "삭제된 게시글",
                  url: "/admin/board/community/review/deleted",
                },
              ],
            },
            {
              title: "토론 게시판 관리",
              url: "/admin/board/community/discussion",
              subMenus: [
                {
                  title: "게시글 목록",
                  url: "/admin/board/community/discussion/list",
                },
                {
                  title: "삭제된 게시글",
                  url: "/admin/board/community/discussion/deleted",
                },
              ],
            },
            {
              title: "추천도서 게시판 관리",
              url: "/admin/board/community/recommend",
              subMenus: [
                {
                  title: "게시글 목록",
                  url: "/admin/board/community/recommend/list",
                },
                {
                  title: "삭제된 게시글",
                  url: "/admin/board/community/recommend/deleted",
                },
              ],
            },
          ],
        },
        {
          title: "이용안내 게시판",
          url: "/admin/board/info",
          subMenus: [
            {
              title: "공지사항 게시판 관리",
              url: "/admin/board/info/notice",
              subMenus: [
                { title: "게시글 목록", url: "/admin/board/info/notice/list" },
                {
                  title: "삭제된 게시글",
                  url: "/admin/board/info/notice/deleted",
                },
              ],
            },
            {
              title: "교육/행사 관리",
              url: "/admin/board/info/edu-event",
              subMenus: [
                {
                  title: "게시글 목록",
                  url: "/admin/board/info/edu-event/list",
                },
                {
                  title: "삭제된 게시글",
                  url: "/admin/board/info/edu-event/deleted",
                },
              ],
            },
            {
              title: "FAQ 관리",
              url: "/admin/board/info/faq",
              subMenus: [
                { title: "게시글 목록", url: "/admin/board/info/faq/list" },
                {
                  title: "삭제된 게시글",
                  url: "/admin/board/info/faq/deleted",
                },
              ],
            },
            {
              title: "1:1문의 관리",
              url: "/admin/board/info/qa",
              subMenus: [
                { title: "신규 문의", url: "/admin/board/info/qa/new" },
                { title: "완료 문의", url: "/admin/board/info/qa/completed" },
              ],
            },
          ],
        },
        {
          title: "게시판 통합 관리",
          url: "/admin/board/settings",
          subMenus: [
            {
              title: "상단 공지 기준 설정",
              url: "/admin/board/settings/notice-criteria",
            },
          ],
        },
      ],
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
document.addEventListener("DOMContentLoaded", createSidebarMenu);
