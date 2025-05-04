document.addEventListener("DOMContentLoaded", function () {
  // 파일 업로드를 위한 숨겨진 input 요소 생성
  const createHiddenFileInput = () => {
    const fileInput = document.createElement("input");
    fileInput.type = "file";
    fileInput.style.display = "none";
    fileInput.name = "file[]";
    return fileInput;
  };

  // 첨부 파일 목록을 표시하는 함수
  const addAttachmentToList = (fileName, fileInput) => {
    const attachmentArea = document.querySelector(".attachment");
    const attachmentItem = document.createElement("div");
    attachmentItem.className = "attachment-item";

    // 파일 아이콘과 이름 표시
    const fileInfo = document.createElement("span");
    fileInfo.className = "file-info";
    fileInfo.textContent = fileName;

    // 삭제 버튼 추가
    const deleteButton = document.createElement("button");
    deleteButton.className = "delete-attachment";
    deleteButton.textContent = "✕";
    deleteButton.type = "button";
    deleteButton.title = "첨부 파일 삭제";
    deleteButton.setAttribute("aria-label", "첨부 파일 삭제");

    // 삭제 버튼 클릭 이벤트
    deleteButton.addEventListener("click", function () {
      attachmentItem.remove();
      fileInput.remove(); // 숨겨진 file input 요소도 제거
    });

    // 요소들 추가
    attachmentItem.appendChild(fileInfo);
    attachmentItem.appendChild(deleteButton);
    attachmentArea.appendChild(attachmentItem);
  };

  // 이미지 툴 기능 처리
  const handleImageTool = () => {
    const fileInput = createHiddenFileInput();
    fileInput.accept = "image/*"; // 이미지 파일만 허용
    document.body.appendChild(fileInput);

    // 파일 선택 시 이벤트
    fileInput.addEventListener("change", function () {
      if (this.files && this.files[0]) {
        const file = this.files[0];
        // 이미지 미리보기 및 내용에 추가
        const reader = new FileReader();
        reader.onload = function (e) {
          // 이미지를 에디터에 추가
          const contentArea = document.getElementById("contentInput");
          const imgTag = `\n![${file.name}](${e.target.result})\n`;

          // 현재 커서 위치에 이미지 태그 삽입
          const cursorPos = contentArea.selectionStart;
          const textBefore = contentArea.value.substring(0, cursorPos);
          const textAfter = contentArea.value.substring(cursorPos);
          contentArea.value = textBefore + imgTag + textAfter;

          // 첨부 파일 목록에 추가
          addAttachmentToList(file.name, fileInput);
        };
        reader.readAsDataURL(file);
      }
    });

    fileInput.click(); // 파일 선택 다이얼로그 표시
  };

  // 링크 툴 기능 처리
  const handleUrlTool = () => {
    // 사용자에게 URL과 텍스트 입력 받기
    const url = prompt("추가할 링크 URL을 입력하세요:", "http://");
    if (url) {
      const text = prompt("링크 텍스트를 입력하세요:", "링크");
      if (text) {
        // 마크다운 형식의 링크 생성
        const linkMd = `[${text}](${url})`;

        // 현재 커서 위치에 링크 삽입
        const contentArea = document.getElementById("contentInput");
        const cursorPos = contentArea.selectionStart;
        const textBefore = contentArea.value.substring(0, cursorPos);
        const textAfter = contentArea.value.substring(cursorPos);
        contentArea.value = textBefore + linkMd + textAfter;
      }
    }
  };

  // 파일 툴 기능 처리
  const handleFileTool = () => {
    const fileInput = createHiddenFileInput();
    document.body.appendChild(fileInput);

    // 파일 선택 시 이벤트
    fileInput.addEventListener("change", function () {
      if (this.files && this.files[0]) {
        const file = this.files[0];
        // 파일 첨부 표시 추가
        addAttachmentToList(file.name, fileInput);
      }
    });

    fileInput.click(); // 파일 선택 다이얼로그 표시
  };

  // 수정 모드인 경우 기존 첨부 파일 목록 가져오기
  const loadExistingFiles = () => {
    const urlParams = new URLSearchParams(window.location.search);
    const boardNo = urlParams.get("boardNo");
    const mode = document.getElementById("editorMode").value;

    // 수정 모드이고 게시글 번호가 있는 경우에만 첨부 파일 로드
    if (mode === "update" && boardNo) {
      const codeNo = document.getElementById("originalCodeNo").value;

      // 첨부 파일 목록 가져오기
      fetch(`${contextPath}/file/list?fileGroupNum=${boardNo}&codeNo=${codeNo}`)
        .then((response) => response.json())
        .then((files) => {
          if (files && files.length > 0) {
            const attachmentArea = document.querySelector(".attachment");
            attachmentArea.innerHTML = ""; // 기존 목록 초기화

            files.forEach((file) => {
              if (file.delYn !== "Y") {
                // 삭제되지 않은 파일만 표시
                const attachmentItem = document.createElement("div");
                attachmentItem.className = "attachment-item";

                // 파일 아이콘과 이름 표시
                const fileInfo = document.createElement("span");
                fileInfo.className = "file-info";
                fileInfo.textContent = file.orgName;

                // 삭제 버튼 추가
                const deleteButton = document.createElement("button");
                deleteButton.className = "delete-attachment";
                deleteButton.textContent = "✕";
                deleteButton.type = "button";
                deleteButton.title = "첨부 파일 삭제";
                deleteButton.setAttribute("aria-label", "첨부 파일 삭제");

                // 삭제 버튼 클릭 이벤트 - 서버에서 파일 삭제
                deleteButton.addEventListener("click", function () {
                  if (confirm("첨부 파일을 삭제하시겠습니까?")) {
                    fetch(`${contextPath}/file/delete?fileNo=${file.fileNo}`, {
                      method: "POST",
                    })
                      .then((response) => response.json())
                      .then((data) => {
                        if (data.success) {
                          attachmentItem.remove();
                        } else {
                          alert("파일 삭제에 실패했습니다.");
                        }
                      })
                      .catch((error) => {
                        console.error("Error:", error);
                        alert("파일 삭제 중 오류가 발생했습니다.");
                      });
                  }
                });

                // 요소들 추가
                attachmentItem.appendChild(fileInfo);
                attachmentItem.appendChild(deleteButton);
                attachmentArea.appendChild(attachmentItem);

                // 이미지 파일인 경우 내용에 표시
                const fileExt = file.orgName.split(".").pop().toLowerCase();
                const isImage = ["jpg", "jpeg", "png", "gif"].includes(fileExt);

                if (isImage) {
                  const contentArea = document.getElementById("contentInput");
                  const imgTag = `\n![${file.orgName}](${contextPath}/file/download?fileNo=${file.fileNo})\n`;

                  // 내용에 이미지가 이미 포함되어 있는지 확인
                  if (!contentArea.value.includes(imgTag)) {
                    contentArea.value += imgTag;
                  }
                }
              }
            });
          }
        })
        .catch((error) => {
          console.error("Error loading files:", error);
        });
    }
  };

  // 버튼에 이벤트 리스너 등록
  const imageTool = document.querySelector(".image-tool");
  if (imageTool) {
    imageTool.addEventListener("click", handleImageTool);
  }

  const urlTool = document.querySelector(".url-tool");
  if (urlTool) {
    urlTool.addEventListener("click", handleUrlTool);
  }

  const fileTool = document.querySelector(".file-tool");
  if (fileTool) {
    fileTool.addEventListener("click", handleFileTool);
  }

  // 페이지 로드 시 기존 첨부 파일 목록 로드
  loadExistingFiles();
});
