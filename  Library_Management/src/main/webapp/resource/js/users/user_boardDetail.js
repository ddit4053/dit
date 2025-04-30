document.addEventListener("DOMContentLoaded", function () {
  // 첨부 파일 로드
  loadAttachedFiles();

  // 댓글 폼 제출 이벤트 리스너
  const commentForm = document.getElementById("commentForm");
  if (commentForm) {
    commentForm.addEventListener("submit", submitComment);
  }

  // 댓글 수정 모달 관련 이벤트
  const modal = document.getElementById("commentEditModal");
  const closeBtn = document.querySelector(".close");
  if (closeBtn) {
    closeBtn.addEventListener("click", function () {
      modal.style.display = "none"; // 모달창 닫기
    });
  }

  // 모달 외부 클릭 시 닫기
  window.addEventListener("click", function (event) {
    if (event.target === modal) {
      modal.style.display = "none";
    }
  });

  // 댓글 수정 폼 제출 이벤트
  const commentEditForm = this.document.getElementById("commentEditForm");
  if (commentEditForm) {
    commentEditForm.addEventListener("submit", updateComment);
  }
});

// 첨부 파일 목록 로드
async function loadAttachedFiles() {
  const fileList = document.getElementById("fileList");
  if (!fileList) return;

  const fileGroupNum = fileList.dataset.fileGroup;
  if (!fileGroupNum || fileGroupNum <= 0) return;

  try {
    // 첨부파일 목록 AJAX
    const response = await fetch(
      `${contextPath}/file/list?fileGroupNum=${fileGroupNum}`
    );

    if (!response.ok) {
      throw new Error("서버 응답 오류");
    }
    const data = await response.json();
    rederFileList(data);
  } catch (error) {
    console.error("파일 목록 로드 중 오류 발생:", error);
    fileList.innerHTML =
      '<div class="error">파일 목록을 불러오는 중 오류가 발생하였습니다.</div>';
  }
}

/**
 * 파일 목록 렌더링
 * @param {Array} files - 파일 목록 배열
 */
function rederFileList(files) {
  const fileList = document.getElementById("fileList");

  if (!files || files.length === 0) {
    fileList.innerHTML =
      '<div class = "no-files">첨부된 파일이 없습니다.</div>';
    return;
  }

  let fileItems = "";
  files.forEach((file) => {
    fileItems += `
        <div class="file-item">
            <span class="file-name">${file.fileName}</span>
            <a href="${contextPath}/file/download?fileNo=${file.fileNo}" class="file-download">다운로드</a>
        </div>
    `;
  });
  fileList.innerHTML = fileItems;
}

/**
 * 댓글 등록 처리
 * @param {Event} e - 이벤트 객체
 */
async function submitComment(e) {
  e.preventDefault(); // 브라우저 기본동작 제한(새로고침 방지)

  const formData = new FormData(e.target);
  const commentData = {
    boardNo: formData.get("boardNo"),
    userNo: formData.get("userNo"),
    cmContent: formData.get("cmContent"),
  };

  if (!commentData.cmContent.trim()) {
    alert("댓글을 입력해주세요.");
    return;
  }
  try {
    const response = await fetch(`${contextPath}/comment/write`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(commentData),
    });
    if (!response.ok) {
      throw new Error("서버 응답 오류");
    }
    const data = await response.json();

    if (data.success) {
      // 페이지 새로고침하여 댓글 목록 갱신
      window.location.reload();
    } else {
      alert("댓글 등록에 실패했습니다: " + (data.message || "알 수 없는 오류"));
    }
  } catch (error) {
    console.error("댓글 등록 중 오류 발생:", error);
    alert("댓글 등록 중 오류가 발생했습니다.");
  }
}
