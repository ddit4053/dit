document.addEventListener("DOMContentLoaded", function () {
  // 모드와 게시글 번호 확인 (수정 모드인지 확인)
  const urlParams = new URLSearchParams(window.location.search);
  const mode = urlParams.get("mode");
  const boardNo = urlParams.get("boardNo") || "";
  // 원래 게시판 코드 저장 변수
  let originalCodeNo = null;

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

    // 현재 선택한 게시판 코드 가져오기
    const codeNoSelect = document.getElementById("codeNoSelect");
    const codeNo = codeNoSelect.value;
    console.log("선택된 게시판 코드:", codeNo, typeof codeNo);

    // 원래 게시판 코드 가져오기
    if (mode === "update" && !originalCodeNo) {
      originalCodeNo = document.getElementById("originalCodeNo")?.value;
    }

    // 폼 데이터 생성 및 기본 정보 추가
    const formData = new FormData();
    formData.append("title", title);
    formData.append("content", content);
    formData.append("codeNo", codeNo);
    formData.append("mode", mode);

    // 수정 모드인 경우 게시글 번호 추가
    if (mode === "update" && boardNo) {
      formData.append("boardNo", boardNo);
    }

    // 첨부 파일 확인 및 추가
    const fileInputs = document.querySelectorAll("input[name='file[]']");
    const hasFiles = fileInputs.length > 0;

    if (hasFiles) {
      // 각 파일 입력에서 파일 추가
      fileInputs.forEach((input, index) => {
        if (input.files.length > 0) {
          formData.append("file", input.files[0]);
        }
      });
    }

    // 서버에 데이터 전송 (모드에 따라 다른 엔드포인트 사용)
    const endpoint =
      mode === "update" ? contextPath + "/update" : contextPath + "/insert";

    // 서버 엔드포인트와 요청 방식 확인
    console.log("요청 모드:", mode);
    console.log("게시글 번호:", boardNo);
    console.log("사용 엔드포인트:", endpoint);
    console.log("원래 게시판 코드:", originalCodeNo);
    console.log("선택된 게시판 코드(formData):", formData.get("codeNo"));
    console.log(
      "codeNoSelect 값:",
      document.getElementById("codeNoSelect").value
    );

    // formData 내용 확인
    console.log("formData boardNo:", formData.get("boardNo"));

    fetch(endpoint, {
      method: "POST",
      body: formData,
    })
      .then((response) => {
        console.log("서버 응답 상태:", response.status);
        if (!response.ok) {
          throw new Error("서버 응답 오류");
        }
        return response.json();
      })
      .then((data) => {
        console.log("서버 응답 데이터:", data);
        if (data.success) {
          // 성공 메시지 표시
          alert(
            mode === "update"
              ? "게시글이 수정되었습니다."
              : "게시글이 등록되었습니다."
          );

          // 게시판 유형에 따라 목록 또는 상세 페이지로 이동
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
          // 수정 모드인 경우 상세 페이지로 이동, 아니면 목록 페이지로 이동
          if (mode === "update") {
            if (
              originalCodeNo &&
              parseInt(codeNo) !== parseInt(originalCodeNo)
            ) {
              // 게시판이 변경된 경우 변경된 게시판 목록으로 리다이렉트
              console.log("게시판 변경됨:", originalCodeNo, "->", codeNo);
            } else {
              // 게시판이 변경되지 않은 경우 상세 페이지로 이동
              redirectUrl += "/Detail?boardNo=" + (data.boardNo || boardNo);
            }
          }

          window.location.href = redirectUrl;
        } else {
          alert(
            data.message ||
              (mode === "update"
                ? "게시글 수정에 실패했습니다."
                : "게시글 등록에 실패했습니다.")
          );
        }
      })
      .catch((error) => {
        console.error("Error:", error);
        alert(
          mode === "update"
            ? "게시글 수정 중 오류가 발생했습니다."
            : "게시글 등록 중 오류가 발생했습니다."
        );
      });
  }

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
