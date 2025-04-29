/**
 * 게시판 에디터 JavaScript
 * 작성 및 수정 모드 통합 구현
 */
document.addEventListener("DOMContentLoaded", function () {
  // 에디터 모드 확인 (작성 또는 수정)
  const editorMode = document.getElementById("editorMode").value;
  const isEditMode = editorMode === "edit";

  // 필요한 요소들 선택
  const boardSelect = document.getElementById("boardCode");
  const titleInput = document.getElementById("titleInput");
  const contentInput = document.getElementById("contentInput");
  const saveButton = document.querySelector(".save-button");
  const cancelButton = document.querySelector(".cancel-button");
  const previewToggleButton = document.querySelector(".preview-toggle-button");
  const previewContent = document.querySelector(".preview-content");

  // 툴바 버튼들
  const imageBtn = document.querySelector(".image-tool");
  const urlBtn = document.querySelector(".url-tool");
  const fileBtn = document.querySelector(".file-tool");
  const codeBtn = document.querySelector(".code-tool");

  // 텍스트 서식 버튼들
  const h1Btn = document.querySelector(".h1Font");
  const h2Btn = document.querySelector(".h2Font");
  const h3Btn = document.querySelector(".h3Font");
  const olBtn = document.querySelector(".olFont");
  const ulBtn = document.querySelector(".ulFont");
  const boldBtn = document.querySelector(".boldFont");
  const italicBtn = document.querySelector(".italicFont");
  const strikeBtn = document.querySelector(".strikeFont");

  // 제목 입력 자동 높이 조절
  if (titleInput) {
    // 초기 로드 시 높이 조절 (수정 모드에서 제목이 이미 있을 때)
    if (isEditMode && titleInput.value) {
      titleInput.style.height = "auto";
      titleInput.style.height = titleInput.scrollHeight + "px";
    }

    titleInput.addEventListener("input", function () {
      this.style.height = "auto";
      this.style.height = this.scrollHeight + "px";
    });
  }

  // 미리보기 토글 기능
  if (previewToggleButton && previewContent) {
    let previewMode = false;

    previewToggleButton.addEventListener("click", function () {
      previewMode = !previewMode;

      if (previewMode) {
        // 미리보기 활성화
        const markdown = contentInput.value;
        const html = marked.parse(markdown);
        previewContent.innerHTML = html;

        contentInput.style.display = "none";
        previewContent.style.display = "block";
        previewToggleButton.textContent = "에디터";
      } else {
        // 에디터 활성화
        contentInput.style.display = "block";
        previewContent.style.display = "none";
        previewToggleButton.textContent = "미리보기";
      }
    });
  }

  // 취소 버튼 이벤트
  if (cancelButton) {
    cancelButton.addEventListener("click", function () {
      if (
        confirm("작성 중인 내용이 저장되지 않을 수 있습니다. 취소하시겠습니까?")
      ) {
        // 게시판 목록 페이지로 이동
        window.location.href = "/board/list.do";
      }
    });
  }

  // 이미지 업로드 버튼 이벤트
  if (imageBtn) {
    imageBtn.addEventListener("click", function () {
      const fileInput = document.createElement("input");
      fileInput.type = "file";
      fileInput.accept = "image/*";
      fileInput.click();

      fileInput.addEventListener("change", function () {
        if (this.files && this.files[0]) {
          uploadImage(this.files[0]);
        }
      });
    });
  }

  // 이미지 업로드 함수
  function uploadImage(file) {
    const formData = new FormData();
    formData.append("image", file);

    // 수정 모드일 경우 게시글 번호도 함께 전송
    if (isEditMode) {
      const postNo = document.getElementById("postNo").value;
      formData.append("postNo", postNo);
    }

    fetch("/api/upload-image", {
      method: "POST",
      body: formData,
    })
      .then((response) => response.json())
      .then((data) => {
        if (data.url) {
          // 이미지 마크다운 형식으로 삽입
          insertToEditor(`![${file.name}](${data.url})\n`);
        }
      })
      .catch((error) => {
        console.error("이미지 업로드 오류:", error);
        alert("이미지 업로드에 실패했습니다.");
      });
  }

  // URL 삽입 버튼 이벤트
  if (urlBtn) {
    urlBtn.addEventListener("click", function () {
      const url = prompt("링크 URL을 입력하세요:");
      if (!url) return;

      const text = prompt("링크 텍스트를 입력하세요:", url);
      insertToEditor(`[${text || url}](${url})`);
    });
  }

  // 파일 업로드 버튼 이벤트
  if (fileBtn) {
    fileBtn.addEventListener("click", function () {
      const fileInput = document.createElement("input");
      fileInput.type = "file";
      fileInput.click();

      fileInput.addEventListener("change", function () {
        if (this.files && this.files[0]) {
          uploadFile(this.files[0]);
        }
      });
    });
  }

  // 파일 업로드 함수
  function uploadFile(file) {
    const formData = new FormData();
    formData.append("file", file);

    // 수정 모드일 경우 게시글 번호도 함께 전송
    if (isEditMode) {
      const postNo = document.getElementById("postNo").value;
      formData.append("postNo", postNo);
    }

    fetch("/api/upload-file", {
      method: "POST",
      body: formData,
    })
      .then((response) => response.json())
      .then((data) => {
        if (data.url) {
          // 파일 링크 삽입
          insertToEditor(`[${file.name} 다운로드](${data.url})`);
        }
      })
      .catch((error) => {
        console.error("파일 업로드 오류:", error);
        alert("파일 업로드에 실패했습니다.");
      });
  }

  // 코드 블록 삽입 버튼 이벤트
  if (codeBtn) {
    codeBtn.addEventListener("click", function () {
      const language = prompt(
        "코드 언어를 입력하세요 (예: javascript, python, java 등):",
        "javascript"
      );
      insertToEditor(`\`\`\`${language}\n\n\`\`\`\n`);

      // 커서를 코드 블록 안에 위치시키기
      const cursorPos = contentInput.value.lastIndexOf("\n```\n") - 1;
      contentInput.setSelectionRange(cursorPos, cursorPos);
      contentInput.focus();
    });
  }

  // H1 텍스트 포맷 버튼 이벤트
  if (h1Btn) {
    h1Btn.addEventListener("click", function () {
      insertHeadingFormat(1);
    });
  }

  // H2 텍스트 포맷 버튼 이벤트
  if (h2Btn) {
    h2Btn.addEventListener("click", function () {
      insertHeadingFormat(2);
    });
  }

  // H3 텍스트 포맷 버튼 이벤트
  if (h3Btn) {
    h3Btn.addEventListener("click", function () {
      insertHeadingFormat(3);
    });
  }

  // 번호 목록 버튼 이벤트
  if (olBtn) {
    olBtn.addEventListener("click", function () {
      const selectedText = getSelectedText();
      if (selectedText) {
        const lines = selectedText.split("\n");
        let formattedText = "";

        lines.forEach((line, index) => {
          formattedText += `${index + 1}. ${line}\n`;
        });

        replaceSelectedText(formattedText);
      } else {
        insertToEditor("1. ");
      }
    });
  }

  // 글머리 기호 목록 버튼 이벤트
  if (ulBtn) {
    ulBtn.addEventListener("click", function () {
      const selectedText = getSelectedText();
      if (selectedText) {
        const lines = selectedText.split("\n");
        let formattedText = "";

        lines.forEach((line) => {
          formattedText += `- ${line}\n`;
        });

        replaceSelectedText(formattedText);
      } else {
        insertToEditor("- ");
      }
    });
  }

  // 굵게 버튼 이벤트
  if (boldBtn) {
    boldBtn.addEventListener("click", function () {
      formatText("**", "**");
    });
  }

  // 기울임 버튼 이벤트
  if (italicBtn) {
    italicBtn.addEventListener("click", function () {
      formatText("*", "*");
    });
  }

  // 취소선 버튼 이벤트
  if (strikeBtn) {
    strikeBtn.addEventListener("click", function () {
      formatText("~~", "~~");
    });
  }

  // 제목 포맷 삽입 함수
  function insertHeadingFormat(level) {
    const prefix = "#".repeat(level) + " ";
    const selectedText = getSelectedText();

    if (selectedText) {
      // 선택된 텍스트가 있으면 제목 형식으로 변환
      replaceSelectedText(`${prefix}${selectedText}`);
    } else {
      // 선택된 텍스트가 없으면 제목 형식만 삽입
      insertToEditor(`${prefix}`);
    }
  }

  // 텍스트 포맷 함수
  function formatText(prefix, suffix) {
    const selectedText = getSelectedText();

    if (selectedText) {
      replaceSelectedText(`${prefix}${selectedText}${suffix}`);
    } else {
      const cursorPos = contentInput.selectionStart;
      insertToEditor(`${prefix}텍스트${suffix}`);

      // 포맷팅된 텍스트 중앙에 커서 위치시키기
      const newCursorPos = cursorPos + prefix.length;
      const endTextPos = newCursorPos + 3; // '텍스트'의 길이
      contentInput.setSelectionRange(newCursorPos, endTextPos);
      contentInput.focus();
    }
  }

  // 선택된 텍스트 가져오기
  function getSelectedText() {
    if (!contentInput) return "";
    return contentInput.value.substring(
      contentInput.selectionStart,
      contentInput.selectionEnd
    );
  }

  // 선택된 텍스트 교체하기
  function replaceSelectedText(newText) {
    if (!contentInput) return;

    const start = contentInput.selectionStart;
    const end = contentInput.selectionEnd;
    const text = contentInput.value;

    contentInput.value =
      text.substring(0, start) + newText + text.substring(end);
    contentInput.focus();
  }

  // 에디터에 텍스트 삽입하기
  function insertToEditor(text) {
    if (!contentInput) return;

    const cursorPos = contentInput.selectionStart;
    const textBeforeCursor = contentInput.value.substring(0, cursorPos);
    const textAfterCursor = contentInput.value.substring(cursorPos);

    contentInput.value = textBeforeCursor + text + textAfterCursor;
    contentInput.focus();

    // 커서 위치 조정
    const newCursorPos = cursorPos + text.length;
    contentInput.setSelectionRange(newCursorPos, newCursorPos);
  }

  // 글 저장 및 제출 함수
  function savePost() {
    const boardCode = boardSelect ? boardSelect.value : "";
    const title = titleInput ? titleInput.value.trim() : "";
    const content = contentInput ? contentInput.value.trim() : "";

    // 유효성 검사
    if (!title) {
      alert("제목을 입력해주세요.");
      titleInput.focus();
      return;
    }

    if (!content) {
      alert("내용을 입력해주세요.");
      contentInput.focus();
      return;
    }

    // 저장할 데이터 준비
    const postData = {
      boardCode: boardCode,
      title: title,
      content: content,
    };

    // 수정 모드일 경우 게시글 번호 추가
    if (isEditMode) {
      const postNo = document.getElementById("postNo").value;
      postData.postNo = postNo;
    }

    // 서버에 데이터 전송
    fetch(isEditMode ? "/api/update-post" : "/api/save-post", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(postData),
    })
      .then((response) => response.json())
      .then((data) => {
        if (data.success) {
          alert(
            isEditMode
              ? "글이 성공적으로 수정되었습니다."
              : "글이 성공적으로 등록되었습니다."
          );
          // 성공 후 리디렉션 처리
          window.location.href =
            data.redirectUrl || "/board/view?postNo=" + data.postNo;
        } else {
          alert(
            data.message ||
              (isEditMode
                ? "글 수정에 실패했습니다."
                : "글 등록에 실패했습니다.")
          );
        }
      })
      .catch((error) => {
        console.error("오류 발생:", error);
        alert(
          isEditMode
            ? "글 수정 중 오류가 발생했습니다."
            : "글 등록 중 오류가 발생했습니다."
        );
      });
  }

  // 저장 버튼 클릭 이벤트
  if (saveButton) {
    saveButton.addEventListener("click", savePost);
  }

  // 자동 저장 기능 (선택 사항)
  function setupAutoSave() {
    const AUTOSAVE_INTERVAL = 30000; // 30초마다 자동 저장
    let autoSaveTimer;
    let lastSavedContent = contentInput.value;

    function autoSave() {
      const currentContent = contentInput.value;

      // 내용이 변경된 경우에만 자동 저장
      if (currentContent !== lastSavedContent) {
        console.log("자동 저장 중...");

        // 로컬 스토리지에 저장
        const autoSaveData = {
          boardCode: boardSelect.value,
          title: titleInput.value,
          content: currentContent,
          timestamp: new Date().getTime(),
        };

        // 수정 모드일 경우 게시글 번호도 저장
        if (isEditMode) {
          const postNo = document.getElementById("postNo").value;
          autoSaveData.postNo = postNo;
        }

        localStorage.setItem("editorAutoSave", JSON.stringify(autoSaveData));
        lastSavedContent = currentContent;
      }
    }

    // 자동 저장 시작
    autoSaveTimer = setInterval(autoSave, AUTOSAVE_INTERVAL);

    // 페이지 언로드 시 자동 저장
    window.addEventListener("beforeunload", autoSave);

    // 자동 저장된 데이터 복원 (페이지 로드 시)
    const savedData = localStorage.getItem("editorAutoSave");
    if (savedData && !isEditMode) {
      try {
        const parsedData = JSON.parse(savedData);

        // 1시간 이내에 저장된 데이터만 복원
        const ONE_HOUR = 60 * 60 * 1000;
        if (new Date().getTime() - parsedData.timestamp < ONE_HOUR) {
          if (
            confirm("이전에 작성 중이던 내용이 있습니다. 복원하시겠습니까?")
          ) {
            boardSelect.value = parsedData.boardCode;
            titleInput.value = parsedData.title;
            contentInput.value = parsedData.content;

            // 제목 높이 조절
            titleInput.style.height = "auto";
            titleInput.style.height = titleInput.scrollHeight + "px";
          } else {
            // 복원하지 않을 경우 저장된 데이터 삭제
            localStorage.removeItem("editorAutoSave");
          }
        } else {
          // 오래된 데이터는 삭제
          localStorage.removeItem("editorAutoSave");
        }
      } catch (e) {
        console.error("자동 저장 데이터 복원 오류:", e);
        localStorage.removeItem("editorAutoSave");
      }
    }
  }

  // 자동 저장 기능 활성화 (선택 사항)
  // setupAutoSave();
});
