/**
 * 게시판 에디터 JavaScript - ES6 문법 적용
 * 작성 및 수정 모드 통합 구현
 */
document.addEventListener("DOMContentLoaded", () => {
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
      titleInput.style.height = `${titleInput.scrollHeight}px`;
    }

    titleInput.addEventListener("input", function() {
      this.style.height = "auto";
      this.style.height = `${this.scrollHeight}px`;
    });
  }

  // 미리보기 토글 기능
  if (previewToggleButton && previewContent) {
    let previewMode = false;

    previewToggleButton.addEventListener("click", () => {
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
    cancelButton.addEventListener("click", () => {
      if (
        confirm("작성 중인 내용이 저장되지 않을 수 있습니다. 취소하시겠습니까?")
      ) {
        // 게시판 목록 페이지로 이동 (현재 URL 기반으로 게시판 목록 URL 결정)
        const currentURL = window.location.pathname;
        const contextPath = window.contextPath || "";
        
        // 게시판 URL 패턴 매핑
        const boardPatterns = [
          { pattern: "/community/reviews", listUrl: "/community/reviews" },
          { pattern: "/community/discussions", listUrl: "/community/discussions" },
          { pattern: "/community/recommendations", listUrl: "/community/recommendations" },
          { pattern: "/support/notices", listUrl: "/support/notices" },
          { pattern: "/support/events", listUrl: "/support/events" },
          { pattern: "/support/qa", listUrl: "/support/qa" },
          { pattern: "/admin/community/reviews", listUrl: "/admin/community/reviews" },
          { pattern: "/admin/community/discussions", listUrl: "/admin/community/discussions" },
          { pattern: "/admin/community/recommendations", listUrl: "/admin/community/recommendations" },
          { pattern: "/admin/support/notices", listUrl: "/admin/support/notices" },
          { pattern: "/admin/support/events", listUrl: "/admin/support/events" },
          { pattern: "/admin/support/faq", listUrl: "/admin/support/faq" },
          { pattern: "/admin/support/qa", listUrl: "/admin/support/qa" }
        ];
        
        // 현재 URL과 게시판 패턴 매칭
        let redirectUrl = "/board/list.do"; // 기본값
        
        for (const board of boardPatterns) {
          if (currentURL.includes(board.pattern)) {
            redirectUrl = board.listUrl;
            break;
          }
        }
        
        // 리다이렉트
        window.location.href = contextPath + redirectUrl;
      }
    });
  }

  // 이미지 업로드 버튼 이벤트
  if (imageBtn) {
    imageBtn.addEventListener("click", () => {
      const fileInput = document.createElement("input");
      fileInput.type = "file";
      fileInput.accept = "image/*";
      fileInput.click();

      fileInput.addEventListener("change", function() {
        if (this.files && this.files[0]) {
          uploadImage(this.files[0]);
        }
      });
    });
  }

  // 이미지 업로드 함수
  const uploadImage = (file) => {
    const formData = new FormData();
    formData.append("file", file);  // 컨트롤러가 "file"으로 파일을 기대함
    formData.append("referenceType", "BOARD");
    
    // 게시글 번호(referenceId) 추가
    if (isEditMode) {
      const postNo = document.getElementById("postNo").value;
      formData.append("referenceId", postNo);
    } else {
      // 작성 모드에서는 임시 ID 사용
      formData.append("referenceId", "0");
    }
    
    // Context Path 가져오기
    const contextPath = window.contextPath || "";
    
    fetch(`${contextPath}/file/upload`, {
      method: "POST",
      body: formData,
    })
      .then(response => response.json())
      .then(data => {
        if (data.success && data.files && data.files.length > 0) {
          // 업로드된 이미지 마크다운 형식으로 삽입
          const fileUrl = `${contextPath}/file/download?fileNo=${data.files[0].fileNo}`;
          insertToEditor(`![${file.name}](${fileUrl})\n`);
          
          // 파일 그룹 번호가 있으면 저장
          if (data.fileGroupNum) {
            storeFileGroupNum(data.fileGroupNum);
          }
        }
      })
      .catch(error => {
        console.error("이미지 업로드 오류:", error);
        alert("이미지 업로드에 실패했습니다.");
      });
  };

  // 파일 그룹 번호 저장 함수
  const storeFileGroupNum = (fileGroupNum) => {
    // 숨겨진 input 필드가 없으면 생성
    let fileGroupInput = document.getElementById("fileGroupNum");
    if (!fileGroupInput) {
      fileGroupInput = document.createElement("input");
      fileGroupInput.type = "hidden";
      fileGroupInput.id = "fileGroupNum";
      document.querySelector(".editor-content").appendChild(fileGroupInput);
    }
    fileGroupInput.value = fileGroupNum;
  };

  // URL 삽입 버튼 이벤트
  if (urlBtn) {
    urlBtn.addEventListener("click", () => {
      const url = prompt("링크 URL을 입력하세요:");
      if (!url) return;

      const text = prompt("링크 텍스트를 입력하세요:", url);
      insertToEditor(`[${text || url}](${url})`);
    });
  }

  // 파일 업로드 버튼 이벤트
  if (fileBtn) {
    fileBtn.addEventListener("click", () => {
      const fileInput = document.createElement("input");
      fileInput.type = "file";
      fileInput.click();

      fileInput.addEventListener("change", function() {
        if (this.files && this.files[0]) {
          uploadFile(this.files[0]);
        }
      });
    });
  }

  // 파일 업로드 함수
  const uploadFile = (file) => {
    const formData = new FormData();
    formData.append("file", file);
    formData.append("referenceType", "BOARD");
    
    // 게시글 번호(referenceId) 추가
    if (isEditMode) {
      const postNo = document.getElementById("postNo").value;
      formData.append("referenceId", postNo);
    } else {
      formData.append("referenceId", "0");
    }
    
    // Context Path 가져오기
    const contextPath = window.contextPath || "";

    fetch(`${contextPath}/file/upload`, {
      method: "POST",
      body: formData,
    })
      .then(response => response.json())
      .then(data => {
        if (data.success && data.files && data.files.length > 0) {
          // 파일 링크 삽입
          const fileUrl = `${contextPath}/file/download?fileNo=${data.files[0].fileNo}`;
          insertToEditor(`[${file.name} 다운로드](${fileUrl})`);
          
          // 파일 그룹 번호가 있으면 저장
          if (data.fileGroupNum) {
            storeFileGroupNum(data.fileGroupNum);
          }
        }
      })
      .catch(error => {
        console.error("파일 업로드 오류:", error);
        alert("파일 업로드에 실패했습니다.");
      });
  };

  // 코드 블록 삽입 버튼 이벤트
  if (codeBtn) {
    codeBtn.addEventListener("click", () => {
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

  // 헤딩 포맷 이벤트 등록
  if (h1Btn) h1Btn.addEventListener("click", () => insertHeadingFormat(1));
  if (h2Btn) h2Btn.addEventListener("click", () => insertHeadingFormat(2));
  if (h3Btn) h3Btn.addEventListener("click", () => insertHeadingFormat(3));

  // 번호 목록 버튼 이벤트
  if (olBtn) {
    olBtn.addEventListener("click", () => {
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
    ulBtn.addEventListener("click", () => {
      const selectedText = getSelectedText();
      if (selectedText) {
        const lines = selectedText.split("\n");
        let formattedText = "";

        lines.forEach(line => {
          formattedText += `- ${line}\n`;
        });

        replaceSelectedText(formattedText);
      } else {
        insertToEditor("- ");
      }
    });
  }

  // 텍스트 서식 이벤트 등록
  if (boldBtn) boldBtn.addEventListener("click", () => formatText("**", "**"));
  if (italicBtn) italicBtn.addEventListener("click", () => formatText("*", "*"));
  if (strikeBtn) strikeBtn.addEventListener("click", () => formatText("~~", "~~"));

  // 제목 포맷 삽입 함수
  const insertHeadingFormat = (level) => {
    const prefix = "#".repeat(level) + " ";
    const selectedText = getSelectedText();

    if (selectedText) {
      // 선택된 텍스트가 있으면 제목 형식으로 변환
      replaceSelectedText(`${prefix}${selectedText}`);
    } else {
      // 선택된 텍스트가 없으면 제목 형식만 삽입
      insertToEditor(prefix);
    }
  };

  // 텍스트 포맷 함수
  const formatText = (prefix, suffix) => {
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
  };

  // 선택된 텍스트 가져오기
  const getSelectedText = () => {
    if (!contentInput) return "";
    return contentInput.value.substring(
      contentInput.selectionStart,
      contentInput.selectionEnd
    );
  };

  // 선택된 텍스트 교체하기
  const replaceSelectedText = (newText) => {
    if (!contentInput) return;

    const start = contentInput.selectionStart;
    const end = contentInput.selectionEnd;
    const text = contentInput.value;

    contentInput.value =
      text.substring(0, start) + newText + text.substring(end);
    contentInput.focus();
  };

  // 에디터에 텍스트 삽입하기
  const insertToEditor = (text) => {
    if (!contentInput) return;

    const cursorPos = contentInput.selectionStart;
    const textBeforeCursor = contentInput.value.substring(0, cursorPos);
    const textAfterCursor = contentInput.value.substring(cursorPos);

    contentInput.value = textBeforeCursor + text + textAfterCursor;
    contentInput.focus();

    // 커서 위치 조정
    const newCursorPos = cursorPos + text.length;
    contentInput.setSelectionRange(newCursorPos, newCursorPos);
  };

  // 현재 URL에서 게시판 유형 파악하는 함수
  const getBoardType = () => {
    const currentURL = window.location.pathname;
    const contextPath = window.contextPath || "";
    
    // 게시판 URL 패턴 매핑
    const boardPatterns = [
      { pattern: "/community/reviews", apiPath: "/api/community/reviews" },
      { pattern: "/community/discussions", apiPath: "/api/community/discussions" },
      { pattern: "/community/recommendations", apiPath: "/api/community/recommendations" },
      { pattern: "/support/notices", apiPath: "/api/support/notices" },
      { pattern: "/support/events", apiPath: "/api/support/events" },
      { pattern: "/support/faq", apiPath: "/api/support/faq" },
      { pattern: "/support/qa", apiPath: "/api/support/qa" },
      { pattern: "/admin/community/reviews", apiPath: "/api/admin/community/reviews" },
      { pattern: "/admin/community/discussions", apiPath: "/api/admin/community/discussions" },
      { pattern: "/admin/community/recommendations", apiPath: "/api/admin/community/recommendations" },
      { pattern: "/admin/support/notices", apiPath: "/api/admin/support/notices" },
      { pattern: "/admin/support/events", apiPath: "/api/admin/support/events" },
      { pattern: "/admin/support/faq", apiPath: "/api/admin/support/faq" },
      { pattern: "/admin/support/qa", apiPath: "/api/admin/support/qa" }
    ];
    
    // 현재 URL과 게시판 패턴 매칭
    for (const board of boardPatterns) {
      if (currentURL.includes(board.pattern)) {
        return {
          apiPath: board.apiPath,
          viewPath: board.pattern
        };
      }
    }
    
    // 기본값 반환
    return {
      apiPath: "/board",  // 기본 API 경로
      viewPath: "/board/view"  // 기본 조회 경로
    };
  };

  // 글 저장 및 제출 함수
  const savePost = () => {
    const codeNo = boardSelect ? boardSelect.value : "";
    const title = titleInput ? titleInput.value.trim() : "";
    const content = contentInput ? contentInput.value.trim() : "";
    const fileGroupInput = document.getElementById("fileGroupNum");
    const fileGroupNum = fileGroupInput ? fileGroupInput.value : "0";

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

    // 폼 데이터 준비
    const formData = new FormData();
    formData.append("codeNo", codeNo);
    formData.append("title", title);
    formData.append("content", content);
    
    if (fileGroupNum && fileGroupNum !== "0") {
      formData.append("fileGroupNum", fileGroupNum);
    }

    // 수정 모드일 경우 게시글 번호 추가
    if (isEditMode) {
      const postNo = document.getElementById("postNo").value;
      formData.append("boardNo", postNo);
    }
    
    // 현재 게시판에 맞는 API 경로 가져오기
    const boardInfo = getBoardType();
    const contextPath = window.contextPath || "";
    const apiUrl = contextPath + (isEditMode ? `${boardInfo.apiPath}/update` : `${boardInfo.apiPath}/write`);

    // 서버에 데이터 전송
    fetch(apiUrl, {
      method: "POST",
      body: formData,
    })
      .then(response => response.json())
      .then(data => {
        if (data.success) {
          alert(
            isEditMode
              ? "글이 성공적으로 수정되었습니다."
              : "글이 성공적으로 등록되었습니다."
          );
          
          // 성공 후 리디렉션 처리
          const boardNo = data.boardNo || data.postNo;
          const redirectUrl = data.redirectUrl || 
                             `${contextPath}${boardInfo.viewPath}?boardNo=${boardNo}`;
          window.location.href = redirectUrl;
        } else {
          alert(
            data.message ||
              (isEditMode
                ? "글 수정에 실패했습니다."
                : "글 등록에 실패했습니다.")
          );
        }
      })
      .catch(error => {
        console.error("오류 발생:", error);
        alert(
          isEditMode
            ? "글 수정 중 오류가 발생했습니다."
            : "글 등록 중 오류가 발생했습니다."
        );
      });
  };

  // 저장 버튼 클릭 이벤트
  if (saveButton) {
    saveButton.addEventListener("click", savePost);
  }

  // 자동 저장 기능 (선택 사항)
  const setupAutoSave = () => {
    const AUTOSAVE_INTERVAL = 30000; // 30초마다 자동 저장
    let autoSaveTimer;
    let lastSavedContent = contentInput.value;

    const autoSave = () => {
      const currentContent = contentInput.value;

      // 내용이 변경된 경우에만 자동 저장
      if (currentContent !== lastSavedContent) {
        console.log("자동 저장 중...");

        // 로컬 스토리지에 저장
        const autoSaveData = {
          codeNo: boardSelect.value,
          title: titleInput.value,
          content: currentContent,
          timestamp: new Date().getTime(),
        };

        // 수정 모드일 경우 게시글 번호도 저장
        if (isEditMode) {
          const postNo = document.getElementById("postNo").value;
          autoSaveData.boardNo = postNo;
        }

        localStorage.setItem("editorAutoSave", JSON.stringify(autoSaveData));
        lastSavedContent = currentContent;
      }
    };

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
            boardSelect.value = parsedData.codeNo;
            titleInput.value = parsedData.title;
            contentInput.value = parsedData.content;

            // 제목 높이 조절
            titleInput.style.height = "auto";
            titleInput.style.height = `${titleInput.scrollHeight}px`;
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
    
    return { stop: () => clearInterval(autoSaveTimer) };
  };

  // 자동 저장 기능 활성화 (선택 사항)
  // const autoSave = setupAutoSave();
});