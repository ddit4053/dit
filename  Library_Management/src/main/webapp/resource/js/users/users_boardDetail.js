document.addEventListener("DOMContentLoaded", function () {
  // 컨텍스트 경로 가져오기
  const contextPath = document.getElementById("contextPath")?.value || "";

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
  const commentEditForm = document.getElementById("commentEditForm");
  if (commentEditForm) {
    commentEditForm.addEventListener("submit", updateComment);
  }
});

// 첨부 파일 목록 로드
async function loadAttachedFiles() {
  const fileList = document.getElementById("fileList");
  if (!fileList) return;

  const fileGroupNum = fileList.dataset.fileGroup;
  if (!fileGroupNum || fileGroupNum <= 0) return; // 첨부된 파일이 없는 경우 AJAX 요청하지 않음

  try {
    // 첨부파일 목록 AJAX
    const response = await fetch(
      `${contextPath}/file/list?fileGroupNum=${fileGroupNum}`
    );

    if (!response.ok) {
      throw new Error("서버 응답 오류");
    }
    const data = await response.json();
    renderFileList(data);
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
function renderFileList(files) {
  const fileList = document.getElementById("fileList");

  if (!files || files.length === 0) {
    fileList.innerHTML = '<div class="no-files">첨부된 파일이 없습니다.</div>';
    return;
  }

  let fileItems = "";
  files.forEach((file) => {
    // 파일 아이콘 결정
    const fileIcon = getFileIcon(file.fileType);

    fileItems += `
        <div class="file-item">
            <span class="file-icon">${fileIcon}</span>
            <span class="file-name">${file.orgName}</span>
            <span class="file-size">${formatFileSize(file.fileSize)}</span>
            <a href="${contextPath}/file/download?fileNo=${
      file.fileNo
    }" class="file-download">다운로드</a>
        </div>
    `;
  });
  fileList.innerHTML = fileItems;
}

/**
 * 파일 타입에 따른 아이콘 반환
 * @param {String} fileType - 파일 타입 (확장자)
 * @returns {String} - 파일 타입에 맞는 아이콘 HTML
 */
function getFileIcon(fileType) {
  if (!fileType) return "📄";

  fileType = fileType.toLowerCase();

  // 이미지 파일
  if (["jpg", "jpeg", "png", "gif", "bmp", "webp"].includes(fileType)) {
    return "🖼️";
  }

  // 문서 파일
  if (["doc", "docx", "pdf", "txt", "rtf", "odt"].includes(fileType)) {
    return "📝";
  }

  // 압축 파일
  if (["zip", "rar", "7z", "tar", "gz"].includes(fileType)) {
    return "🗜️";
  }

  // 오디오 파일
  if (["mp3", "wav", "ogg", "flac", "aac"].includes(fileType)) {
    return "🎵";
  }

  // 비디오 파일
  if (["mp4", "avi", "mov", "wmv", "mkv"].includes(fileType)) {
    return "🎞️";
  }

  // 스프레드시트 파일
  if (["xls", "xlsx", "csv"].includes(fileType)) {
    return "📊";
  }

  // 프레젠테이션 파일
  if (["ppt", "pptx"].includes(fileType)) {
    return "📺";
  }

  // 기본 아이콘
  return "📄";
}

/**
 * 파일 크기 포맷팅
 * @param {Number} size - 바이트 단위 파일 크기
 * @returns {String} - 포맷팅된 파일 크기
 */
function formatFileSize(size) {
  if (size < 1024) {
    return size + " B";
  } else if (size < 1024 * 1024) {
    return (size / 1024).toFixed(1) + " KB";
  } else if (size < 1024 * 1024 * 1024) {
    return (size / (1024 * 1024)).toFixed(1) + " MB";
  } else {
    return (size / (1024 * 1024 * 1024)).toFixed(1) + " GB";
  }
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

/**
 * 댓글 수정 모달 표시
 * @param {Number} cmNo - 댓글 번호
 * @param {String} cmContent - 댓글 내용
 */
function showEditForm(cmNo, cmContent) {
  const modal = document.getElementById("commentEditModal");
  const cmNoInput = document.getElementById("editCommentId");
  const cmContentInput = document.getElementById("editCommentContent");

  cmNoInput.value = cmNo;
  cmContentInput.value = cmContent;

  modal.style.display = "block";
  cmContentInput.focus();
}

/**
 * 댓글 수정 처리
 * @param {Event} e - 이벤트 객체
 */
async function updateComment(e) {
  e.preventDefault();

  const formData = new FormData(e.target);
  const commentData = {
    cmNo: formData.get("cmNo"),
    cmContent: formData.get("cmContent"),
  };

  if (!commentData.cmContent.trim()) {
    alert("댓글 내용을 입력해주세요.");
    return;
  }

  try {
    const response = await fetch(`${contextPath}/comment/update`, {
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
      // 모달 닫기
      document.getElementById("commentEditModal").style.display = "none";

      // 성공 메시지 표시
      alert("댓글이 수정되었습니다.");

      // 페이지 새로고침하여 댓글 목록 갱신
      window.location.reload();
    } else {
      alert("댓글 수정에 실패했습니다: " + (data.message || "알 수 없는 오류"));
    }
  } catch (error) {
    console.error("댓글 수정 중 오류 발생:", error);
    alert("댓글 수정 중 오류가 발생했습니다.");
  }
}

/**
 * 댓글 삭제 처리
 * @param {Number} cmNo - 댓글 번호
 */
async function deleteComment(cmNo) {
  if (!confirm("정말로 이 댓글을 삭제하시겠습니까?")) {
    return;
  }

  try {
    const response = await fetch(`${contextPath}/comment/delete`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ cmNo }),
    });

    if (!response.ok) {
      throw new Error("서버 응답 오류");
    }

    const data = await response.json();

    if (data.success) {
      alert("댓글이 삭제되었습니다.");
      window.location.reload();
    } else {
      alert("댓글 삭제에 실패했습니다: " + (data.message || "알 수 없는 오류"));
    }
  } catch (error) {
    console.error("댓글 삭제 중 오류 발생:", error);
    alert("댓글 삭제 중 오류가 발생했습니다.");
  }
}

/**
 * 답글 폼 표시/숨김 토글
 * @param {Number} cmNo - 댓글 번호
 */
function toggleReplyForm(cmNo) {
  const replyForm = document.getElementById(`reply-form-${cmNo}`);

  if (replyForm.style.display === "none" || replyForm.style.display === "") {
    replyForm.style.display = "block";
    // 폼 내의 텍스트 영역에 포커스
    replyForm.querySelector("textarea").focus();
  } else {
    replyForm.style.display = "none";
  }
}

/**
 * 답글 등록 처리
 * @param {Number} cmNo2 - 부모 댓글 번호
 * @param {Number} boardNo - 게시글 번호
 */
async function submitReply(cmNo2, boardNo) {
  const replyForm = document.getElementById(`reply-form-${cmNo2}`);
  const replyContent = replyForm.querySelector("textarea").value;

  if (!replyContent.trim()) {
    alert("답글 내용을 입력해주세요.");
    return;
  }

  try {
    const response = await fetch(`${contextPath}/comment/reply`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        boardNo,
        cmNo2,
        cmContent: replyContent,
      }),
    });

    if (!response.ok) {
      throw new Error("서버 응답 오류");
    }

    const data = await response.json();

    if (data.success) {
      alert("답글이 등록되었습니다.");
      window.location.reload();
    } else {
      alert("답글 등록에 실패했습니다: " + (data.message || "알 수 없는 오류"));
    }
  } catch (error) {
    console.error("답글 등록 중 오류 발생:", error);
    alert("답글 등록 중 오류가 발생했습니다.");
  }
}

/**
 * 게시글 삭제 처리
 * @param {Number} boardNo - 게시글 번호
 */
async function deleteBoard(boardNo) {
  if (!confirm("정말로 이 게시글을 삭제하시겠습니까?")) {
    return;
  }

  try {
    const response = await fetch(`${contextPath}/board/delete`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ boardNo }),
    });

    if (!response.ok) {
      throw new Error("서버 응답 오류");
    }

    const data = await response.json();

    if (data.success) {
      alert("게시글이 삭제되었습니다.");
      // 게시글 목록 페이지로 이동
      window.location.href = `${contextPath}/board/list`;
    } else {
      alert(
        "게시글 삭제에 실패했습니다: " + (data.message || "알 수 없는 오류")
      );
    }
  } catch (error) {
    console.error("게시글 삭제 중 오류 발생:", error);
    alert("게시글 삭제 중 오류가 발생했습니다.");
  }
}
