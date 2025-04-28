document.addEventListener("DOMContentLoaded", function () {
  loadBoardList(1);
  setupOptionListeners();
});

function getCurrentBoardType() {
  const currentPath = window.location.pathname;

  if (currentPath.includes("community/reviews")) {
    return "reviews";
  } else if (currentPath.includes("/community/discussions")) {
    return "discussions";
  } else if (currentPath.includes("/community/recommendations")) {
    return "recommendations";
  } else if (currentPath.includes("/support/notices")) {
    return "notices";
  } else if (currentPath.includes("/support/events")) {
    return "events";
  } else if (currentPath.includes("/support/qa")) {
    return "qa";
  }
  return "reviews"; // 기본값
}
// API 엔드포인트 가져오기
function getBoardApiEndpoint() {
  const boardType = getCurrentBoardType();
  return "/" + boardType + "ListAjax";
}

// 상세 페이지 URL 가져오기
function getDetailPageUrl(boardNo) {
  const boardType = getCurrentBoardType();
  return contextPath + "/board/" + boardType + "detail?boardNo=" + boardNo;
}

// 옵션 변경 이벤트 리스너
function setupOptionListeners() {
  // 공지사항 숨기기 체크박스
  document
    .getElementById("isNoticeVisible")
    .addEventListener("change", function () {
      loadBoardListCurrentOptions();
    });

  // 정렬 조건 변경
  document.querySelector(".order-type").addEventListener("change", function () {
    loadBoardListCurrentOptions();
  });

  // 블록 사이즈 변경
  document
    .querySelector(".block-select")
    .addEventListener("change", function () {
      loadBoardListCurrentOptions();
    });
}

// 상단 옵션 설정으로 게시글 목록 로드
function loadBoardListCurrentOptions() {
  const searchForm = document.getElementById("searchForm");
  const searchType = searchForm ? searchForm.searchType.value : "";
  const searchKeyword = searchForm ? searchForm.searchKeyword.value.trim() : "";

  loadBoardList(1, searchType, searchKeyword);
}

// 게시글 목록 불러오기 함수
function loadBoardList(page, searchType = "", searchKeyword = "") {
  const hideNotice = document.getElementById("isNoticeVisible").checked;
  const orderType = document.querySelector(".order-type").value;
  const blockSize = document.querySelector(".block-select").value;

  const params = {
    page: page,
    searchType: searchType,
    searchKeyword: searchKeyword,
    hideNotice: hideNotice,
    orderType: orderType,
    blockSize: parseInt(blockSize.replace(/[^0-9]/g, "")),
  };

  $.ajax({
    url: contextPath + getBoardApiEndpoint(), // 동적 엔드포인트 생성
    type: "GET",
    data: params,
    dataType: "json",
    success: function (res) {
      console.log("성공", res);

      displayBoardList(res.boardList);
      displayPagination(res.paging, searchType, searchKeyword);
    },
    error: function (xhr, status, error) {
      console.log("요청 URL:", getBoardApiEndpoint());
      console.error("Error:", error);
      console.error("상태:", status);
      console.error("응답:", xhr.responseText);
      alert("게시글을 불러오는데 실패했습니다.");
    },
  });
}

// 게시글 목록 표시 함수
function displayBoardList(boardList) {
  const tbody = document.getElementById("boardTableBody");
  tbody.innerHTML = "";
  const boardType = getCurrentBoardType();

  if (boardList && boardList.length > 0) {
    boardList.forEach((board) => {
      const tr = document.createElement("tr");

      // 공지사항인 경우 클래스 추가
      if (board.codeNo === 4) {
        tr.classList.add("notice-row");
      }

      tr.innerHTML = `
            <td>${board.boardNo}</td>
            <td class="title">
            <a href="${getDetailPageUrl(board.boardNo)}">
            ${board.codeNo === 4 ? '<span class="notice-tag">공지</span>' : ""}
            ${board.title}
            ${
              board.commentsCount > 0
                ? `<span class="comment-count">[${board.commentsCount}]</span>`
                : ""
            }</a>
            </td>
            <td>${board.writer}</td>
            <td>${formatDate(board.writtenDate)}</td>
            <td>${board.views}</td>
        `;
      tbody.appendChild(tr);
    });
  } else {
    const tr = document.createElement("tr");
    tr.innerHTML = `<td colspan="5" class="no-data">등록된 게시글이 없습니다.</td> `;
    tbody.appendChild(tr);
  }
}

// 페이징 표시 함수
function displayPagination(paging, searchType, searchKeyword) {
  const paginationArea = document.getElementById("paginationArea");
  paginationArea.innerHTML = "";

  if (paging.startPage > 1) {
    const prevBtn = createPageButton(
      "이전",
      paging.startPage - 1,
      searchType,
      searchKeyword,
      "page-btn prev"
    );
    paginationArea.appendChild(prevBtn);
  }

  for (let i = paging.startPage; i <= paging.endPage; i++) {
    const isActive = i === paging.currentPage;
    const pageBtn = createPageButton(
      i,
      i,
      searchType,
      searchKeyword,
      `page-num ${isActive ? "active" : ""}`
    );
    paginationArea.appendChild(pageBtn);
  }

  if (paging.endPage < paging.totalPages) {
    const nextBtn = createPageButton(
      "다음",
      paging.endPage + 1,
      searchType,
      searchKeyword,
      "page-btn next"
    );
    paginationArea.appendChild(nextBtn);
  }
}

// 페이지 버튼 생성 함수
function createPageButton(text, page, searchType, searchKeyword, className) {
  const btn = document.createElement("a");
  btn.href = "#";
  btn.className = className;
  btn.textContent = text;
  btn.onclick = function (e) {
    e.preventDefault();
    // 현재 옵션을 유지하면서 페이지 이동
    loadBoardList(page, searchType, searchKeyword);
  };
  return btn;
}

// 검색 함수
function searchBoard() {
  const form = document.getElementById("searchForm");
  const searchType = form.searchType.value;
  const searchKeyword = form.searchKeyword.value.trim();

  if (searchKeyword === "") {
    alert("검색어를 입력하세요.");
    return;
  }

  loadBoardList(1, searchType, searchKeyword);
}

// 날짜 포맷 함수
function formatDate(dateStr) {
  const date = new Date(dateStr);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");

  return `${year}-${month}-${day}`;
}

// 글쓰기 버튼 이벤트
document.querySelector(".go-to-editor").addEventListener("click", function () {
  window.location.href = contextPath + "/board/textEditor";
});
