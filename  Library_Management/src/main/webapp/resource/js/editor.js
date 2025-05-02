document.addEventListener("DOMContentLoaded", function () {
  // 등록하기 버튼 클릭 이벤트
  const saveButton = document.querySelector(".save-button");
  if (saveButton) {
    saveButton.addEventListener("click", savePost);
  }

  // 취소 버튼 클릭 이벤트
  const cancelButton = document.querySelector(".cancel-button");
  if (cancelButton) {
    cancelButton.addEventListener("click", function () {
      if (confirm("작성 중인 내용이 저장되지 않습니다. 취소하시겠습니까?")) {
        window.history.back();
      }
    });
  }
});

// 게시글 저장 함수
function savePost() {
  // 입력값 가져오기
  const title = document.getElementById("titleInput").value.trim();
  const content = document.getElementById("contentInput").value.trim();

  // 입력값 검증
  if (title === "") {
    alert("제목을 입력해주세요.");
    document.getElementById("titleInput").focus();
    return;
  }

  if (content === "") {
    alert("내용을 입력해주세요.");
    document.getElementById("contentInput").focus();
    return;
  }

  // 현재 URL에서 codeNo 파라미터 가져오기
  const urlParams = new URLSearchParams(window.location.search);
  const codeNo = urlParams.get("codeNo") || 1;

  // 파일 그룹 번호 생성 또는 가져오기
  let fileGroupNum = null;

  // 파일 업로드가 있는 경우 파일 그룹 번호 생성
  if (document.querySelectorAll(".file-item").length > 0) {
    // 파일 그룹 생성 API 호출
    fetch(contextPath + "/file/createGroup", {
      method: "POST",
    })
      .then((response) => response.json())
      .then((data) => {
        if (data.success) {
          // 파일 업로드 및 게시글 등록
          uploadFilesAndSavePost(title, content, codeNo, data.fileGroupNum);
        } else {
          alert("파일 그룹 생성에 실패했습니다.");
        }
      })
      .catch((error) => {
        console.error("Error:", error);
        alert("파일 그룹 생성 중 오류가 발생했습니다.");
      });
  } else {
    // 파일이 없는 경우 바로 게시글 등록
    sendPostData(title, content, codeNo, null);
  }
}

// 파일 업로드 및 게시글 등록
function uploadFilesAndSavePost(title, content, codeNo, fileGroupNum) {
  // 파일 업로드 폼데이터 생성
  const uploadFormData = new FormData();
  uploadFormData.append("referenceType", "BOARD");
  uploadFormData.append("referenceId", "0"); // 임시값, 나중에 업데이트될 수 있음
  uploadFormData.append("fileGroupNum", fileGroupNum);

  // 파일 입력 필드에서 파일 추가
  const fileInputs = document.querySelectorAll('input[type="file"]');
  fileInputs.forEach((input) => {
    if (input.files.length > 0) {
      uploadFormData.append("file", input.files[0]);
    }
  });

  // 파일 업로드 요청
  fetch(contextPath + "/file/upload", {
    method: "POST",
    body: uploadFormData,
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.success) {
        // 파일 업로드 성공 후 게시글 등록
        sendPostData(title, content, codeNo, fileGroupNum);
      } else {
        alert("파일 업로드에 실패했습니다.");
      }
    })
    .catch((error) => {
      console.error("Error:", error);
      alert("파일 업로드 중 오류가 발생했습니다.");
    });
}

// 게시글 데이터 전송
function sendPostData(title, content, codeNo, fileGroupNum) {
  // 폼 데이터 생성
  const formData = new FormData();
  formData.append("title", title);
  formData.append("content", content);
  formData.append("codeNo", codeNo);

  if (fileGroupNum) {
    formData.append("fileGroupNum", fileGroupNum);
  }

  // 서버에 데이터 전송
  fetch(contextPath + "/insert", {
    method: "POST",
    body: formData,
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("서버 응답 오류");
      }
      return response.json();
    })
    .then((data) => {
      if (data.success) {
        alert("게시글이 등록되었습니다.");

        // 게시판 유형에 따라 목록 페이지로 이동
        let redirectUrl = "";
        switch (parseInt(codeNo)) {
          case 1:
            redirectUrl = contextPath + "/community/reviews";
            break;
          case 2:
            redirectUrl = contextPath + "/community/discussions";
            break;
          case 3:
            redirectUrl = contextPath + "/community/recommendations";
            break;
          case 4:
            redirectUrl = contextPath + "/support/notices";
            break;
          case 5:
            redirectUrl = contextPath + "/support/events";
            break;
          case 6:
            redirectUrl = contextPath + "/support/qa";
            break;
          default:
            redirectUrl = contextPath + "/community/reviews";
        }
        window.location.href = redirectUrl;
      } else {
        alert(data.message || "게시글 등록에 실패했습니다.");
      }
    })
    .catch((error) => {
      console.error("Error:", error);
      alert("게시글 등록 중 오류가 발생했습니다.");
    });
}
