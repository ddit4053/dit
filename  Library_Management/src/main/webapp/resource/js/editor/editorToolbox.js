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

  // 이미지를 본문에 삽입하는 함수
  const insertImageToContent = (imageUrl, width, height, fileName) => {
    const contentArea = document.getElementById("contentInput");
    if (!contentArea) return;

    // figure 태그로 이미지 감싸기
    const imageHtml = `
<figure class="imageblock aligncenter" data-ke-mobilestyle="widthOrigin" data-filename="${fileName}" data-origin-width="${width}" data-origin-height="${height}">
  <img src="${imageUrl}" alt="${fileName}" width="${width}" height="${height}" />
  <figcaption></figcaption>
</figure>
`;
    
    // 현재 커서 위치에 이미지 HTML 삽입
    const cursorPos = contentArea.selectionStart;
    const textBefore = contentArea.value.substring(0, cursorPos);
    const textAfter = contentArea.value.substring(cursorPos);
    contentArea.value = textBefore + imageHtml + textAfter;
    
    // 커서 위치 이동
    const newCursorPos = cursorPos + imageHtml.length;
    contentArea.selectionStart = newCursorPos;
    contentArea.selectionEnd = newCursorPos;
    contentArea.focus();
  };
  
  
  

  // 이미지 툴 기능 처리 (수정됨)
  const handleImageTool = () => {
    const fileInput = createHiddenFileInput();
    fileInput.accept = "image/*"; // 이미지 파일만 허용
    document.body.appendChild(fileInput);

    // 파일 선택 시 이벤트
    fileInput.addEventListener("change", function () {
      if (this.files && this.files[0]) {
        const file = this.files[0];
        
        // 첨부 파일 목록에 추가
        addAttachmentToList(file.name, fileInput);
        
		
		// 서버에 파일 업로드
      const formData = new FormData();
      formData.append('file', file);
	  
	  
	  // 현재 게시글 번호 또는 임시 번호 설정
	  const urlParams = new URLSearchParams(window.location.search);
	  const boardNo = urlParams.get("boardNo");

	  // 필수 파라미터 추가
	  formData.append('referenceType', 'BOARD');
	  formData.append('referenceId', boardNo ? boardNo : -1); // 없으면 임시값으로 -1 사용
	  formData.append('isTemp', boardNo ? 'N' : 'Y'); // 임시 파일 여부 표시
	  
      
    fetch(`${contextPath}/file/upload`, {
          method: 'POST',
          body: formData
        })
        .then(response => response.json())
        .then(data => {
          if (data.success) {
            // 성공적으로 업로드된 파일 정보 추출
            const fileInfo = data.files[0];
            const fileUrl = `${contextPath}/file/download?fileNo=${fileInfo.fileNo}`;
            
            // 본문에 이미지 삽입
            insertImageToContent(fileUrl, 800, 600, file.name); // 기본 크기 지정 (또는 서버에서 제공하는 크기 사용)
          } else {
            alert('이미지 업로드에 실패했습니다: ' + (data.message || '알 수 없는 오류'));
          }
        })
        .catch(error => {
          console.error('이미지 업로드 중 오류 발생:', error);
          alert('이미지 업로드 중 오류가 발생했습니다.');
          
          // 업로드 실패 시 임시 URL로 이미지 표시 (개발 중에만 사용)
          const reader = new FileReader();
          reader.onload = function(e) {
        const imageUrl = e.target.result;
            
            // 이미지 크기 가져오기
            const img = new Image();
            img.onload = function() {
              // 본문에 이미지 삽입
              insertImageToContent(imageUrl, this.width, this.height, file.name);
            };
            img.src = imageUrl;
          };
          reader.readAsDataURL(file);
        });
      }
    });

    fileInput.click(); // 파일 선택 다이얼로그 표시
  };

  // 유튜브 URL인지 확인하고 비디오 ID 추출하는 함수
  const getYoutubeVideoId = (url) => {
    const regExp =
      /^.*((youtu.be\/)|(v\/)|(\/u\/\w\/)|(embed\/)|(watch\?))\??v?=?([^#&?]*).*/;
    const match = url.match(regExp);
    return match && match[7].length === 11 ? match[7] : false;
  };

  // 주어진 텍스트가 이미 HTML 태그를 포함하는지 확인
  const containsHtmlTags = (text) => {
    return /<[a-z][\s\S]*>/i.test(text);
  };

  // 링크 툴 기능 처리
  const handleUrlTool = () => {
    // 사용자에게 URL 입력 받기
    const url = prompt("추가할 링크 URL을 입력하세요:", "http://");
    if (!url) return;

    // 이미 HTML 태그를 포함하는 경우
    if (containsHtmlTags(url)) {
      alert("URL에 HTML 태그가 포함되어 있습니다. 순수 URL만 입력해주세요.");
      return;
    }

    // 유튜브 URL인지 확인
    const youtubeId = getYoutubeVideoId(url);

    if (youtubeId) {
      // 유튜브 영상인 경우
      const options = ["임베드", "URL"];
      const selectedOption = prompt(
        "유튜브 링크를 추가하는 방식을 선택하세요:\n1. 임베드\n2. URL",
        "1"
      );

      if (selectedOption === "1") {
        // 임베드 코드 생성
        const embedCode = `
<figure class="imageblock aligncenter" data-ke-mobilestyle="widthOrigin">
  <div class="youtube-embed">
    <iframe width="560" height="315" src="https://www.youtube.com/embed/${youtubeId}" 
    frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; 
    gyroscope; picture-in-picture" allowfullscreen></iframe>
  </div>
  <figcaption></figcaption>
</figure>
`;

        // 에디터에 삽입
        const contentArea = document.getElementById("contentInput");
        const cursorPos = contentArea.selectionStart;
        const textBefore = contentArea.value.substring(0, cursorPos);
        const textAfter = contentArea.value.substring(cursorPos);
        contentArea.value = textBefore + embedCode + textAfter;

        return;
      } else if (selectedOption === "2") {
        // URL만 추가 (a 태그로 변환)
        const aTag = `<a href="${url}" target="_blank">${url}</a>`;
        const contentArea = document.getElementById("contentInput");
        const cursorPos = contentArea.selectionStart;
        const textBefore = contentArea.value.substring(0, cursorPos);
        const textAfter = contentArea.value.substring(cursorPos);
        contentArea.value = textBefore + aTag + textAfter;

        return;
      }
    }

    // 일반 링크인 경우 a 태그로 변환
    const aTag = `<a href="${url}" target="_blank">${url}</a>`;
    
    // 현재 커서 위치에 링크 삽입
    const contentArea = document.getElementById("contentInput");
    const cursorPos = contentArea.selectionStart;
    const textBefore = contentArea.value.substring(0, cursorPos);
    const textAfter = contentArea.value.substring(cursorPos);
    contentArea.value = textBefore + aTag + textAfter;
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

  // 본문 입력 시 자동으로 유튜브 URL 감지하여 임베드하는 기능
  const handleContentInput = (e) => {
    const contentArea = e.target;
    const content = contentArea.value;
    const cursorPos = contentArea.selectionStart;

    // 현재 줄 추출
    const textBeforeCursor = content.substring(0, cursorPos);
    const lastNewlineIndex = textBeforeCursor.lastIndexOf("\n");
    const currentLine = textBeforeCursor.substring(lastNewlineIndex + 1);

    // 현재 줄에 HTML 태그가 이미 포함되어 있으면 처리하지 않음
    if (containsHtmlTags(currentLine)) {
      return;
    }

    // URL 패턴 체크 - 일반 URL과 유튜브 URL 모두 확인
    const urlRegex = /(https?:\/\/[^\s<>"]+)/g;
    const match = currentLine.match(urlRegex);

    if (match && match.length > 0) {
      const url = match[0];
      const youtubeId = getYoutubeVideoId(url);

      // 이미 HTML 태그를 포함하는 경우 패스
      if (containsHtmlTags(url)) {
        return;
      }

      if (youtubeId) {
        // 유튜브 URL이 감지되면 간소화된 팝업 옵션 표시
        const optionsDiv = document.createElement("div");
        optionsDiv.className = "youtube-options";
        optionsDiv.style.position = "absolute";
        optionsDiv.style.zIndex = "1000";
        optionsDiv.style.backgroundColor = "#333";
        optionsDiv.style.color = "#fff";
        optionsDiv.style.padding = "10px";
        optionsDiv.style.borderRadius = "5px";
        optionsDiv.style.boxShadow = "0 2px 5px rgba(0,0,0,0.3)";

        // 에디터 위치 기준으로 옵션 표시 위치 계산
        const editorRect = contentArea.getBoundingClientRect();
        const lineHeight = parseInt(getComputedStyle(contentArea).lineHeight);
        const lines = textBeforeCursor.split("\n").length;

        // 옵션 아이템 생성 (임베드와 URL만 표시)
        const options = ["임베드", "URL"];
        options.forEach((option) => {
          const optionItem = document.createElement("div");
          optionItem.textContent = option;
          optionItem.style.padding = "5px 10px";
          optionItem.style.cursor = "pointer";
          optionItem.style.marginBottom = "5px";

          optionItem.addEventListener("mouseover", () => {
            optionItem.style.backgroundColor = "#555";
          });

          optionItem.addEventListener("mouseout", () => {
            optionItem.style.backgroundColor = "transparent";
          });

          optionItem.addEventListener("click", () => {
            // 기존 URL 제거
            const textBefore = content.substring(0, cursorPos - url.length);
            const textAfter = content.substring(cursorPos);

            // 선택된 옵션에 따라 처리
            if (option === "임베드") {
              // 임베드 코드 생성
              const embedCode = `
<figure class="imageblock aligncenter" data-ke-mobilestyle="widthOrigin">
  <div class="youtube-embed">
    <iframe width="560" height="315" src="https://www.youtube.com/embed/${youtubeId}" 
    frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; 
    gyroscope; picture-in-picture" allowfullscreen></iframe>
  </div>
  <figcaption></figcaption>
</figure>
`;

              contentArea.value = textBefore + embedCode + textAfter;
            } else {
              // URL을 a 태그로 변환
              const aTag = `<a href="${url}" target="_blank">${url}</a>`;
              contentArea.value = textBefore + aTag + textAfter;
            }

            // 커서 위치 조정
            contentArea.focus();
            contentArea.selectionStart = textBefore.length;
            contentArea.selectionEnd = contentArea.selectionStart;

            // 옵션 팝업 제거
            document.body.removeChild(optionsDiv);
          });

          optionsDiv.appendChild(optionItem);
        });

        // 옵션 팝업 위치 설정 및 화면에 추가
        document.body.appendChild(optionsDiv);
        const optionsRect = optionsDiv.getBoundingClientRect();

        optionsDiv.style.left = `${editorRect.left + 20}px`;
        optionsDiv.style.top = `${
          editorRect.top + lineHeight * lines - optionsRect.height - 10
        }px`;

        // 외부 클릭 시 옵션 팝업 제거
        const clickOutsideHandler = (event) => {
          if (
            !optionsDiv.contains(event.target) &&
            event.target !== contentArea
          ) {
            document.body.removeChild(optionsDiv);
            document.removeEventListener("click", clickOutsideHandler);
          }
        };

        // 조금 지연 후 이벤트 리스너 추가 (바로 추가하면 즉시 닫힐 수 있음)
        setTimeout(() => {
          document.addEventListener("click", clickOutsideHandler);
        }, 100);
      } else {
        // 일반 URL인 경우 자동으로 a 태그로 변환
        const aTag = `<a href="${url}" target="_blank">${url}</a>`;
        
        // URL을 a 태그로 대체
        const textBefore = content.substring(0, cursorPos - url.length);
        const textAfter = content.substring(cursorPos);
        contentArea.value = textBefore + aTag + textAfter;
        
        // 커서 위치 조정
        contentArea.focus();
        contentArea.selectionStart = textBefore.length + aTag.length;
        contentArea.selectionEnd = contentArea.selectionStart;
      }
    }
  };

  // 수정 모드인 경우 기존 첨부 파일 목록 가져오기
  const loadExistingFiles = () => {
    // 에디터가 있는 페이지인지 확인
    const contentInput = document.getElementById("contentInput");
    const editorMode = document.getElementById("editorMode");

    if (!contentInput || !editorMode) {
      return; // 에디터 페이지가 아니면 함수 종료
    }

    const urlParams = new URLSearchParams(window.location.search);
    const boardNo = urlParams.get("boardNo");
    const mode = editorMode.value;

    // 수정 모드이고 게시글 번호가 있는 경우에만 첨부 파일 로드
    if (mode === "update" && boardNo) {
      const originalCodeNo = document.getElementById("originalCodeNo");

      if (!originalCodeNo) {
        return; // originalCodeNo 요소가 없으면 함수 종료
      }

      const codeNo = originalCodeNo.value;

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
              }
            });
          }
        })
        .catch((error) => {
          console.error("Error loading files:", error);
        });
    }
  };
  
  // 세션 ID를 기반으로 임시 파일 그룹 번호 계산하는 함수
  const getTempFileGroupNum = () => {
    // JSESSIONID 쿠키에서 세션 ID 가져오기
    const cookies = document.cookie.split(';');
    let sessionId = '';
    
    for (let i = 0; i < cookies.length; i++) {
      const cookie = cookies[i].trim();
      if (cookie.indexOf('JSESSIONID=') === 0) {
        sessionId = cookie.substring('JSESSIONID='.length, cookie.length);
        break;
      }
    }
    
    // 세션 ID 해시코드 계산
    let hash = 0;
    for (let i = 0; i < sessionId.length; i++) {
      const char = sessionId.charCodeAt(i);
      hash = ((hash << 5) - hash) + char;
      hash = hash & hash; // 32비트 정수로 변환
    }
    
    return Math.abs(hash);
  };

  // 임시 파일을 영구 파일로 업데이트하는 함수
  const updateTempFilesToPermanent = (boardNo) => {
    const tempGroupNum = getTempFileGroupNum();
    
    // 디버그용 로그
    console.log('임시 파일 업데이트 요청:', { tempGroupNum, boardNo });
    
    fetch(`${contextPath}/file/updateTempFiles`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: `tempGroupNum=${tempGroupNum}&boardNo=${boardNo}`
    })
    .then(response => response.json())
    .then(data => {
      console.log('임시 파일 업데이트 결과:', data);
      if (!data.success) {
        console.error('임시 파일 업데이트 실패:', data.message);
      }
    })
    .catch(error => {
      console.error('임시 파일 업데이트 중 오류 발생:', error);
    });
  };

  // 폼 제출 이벤트 리스너 등록
  const boardForm = document.getElementById('boardForm');
  if (boardForm) {
    // 기존 제출 이벤트 보존
    const originalSubmit = boardForm.onsubmit;
    
    boardForm.onsubmit = function(event) {
      // 기존 제출 이벤트가 있으면 실행
      let result = true;
      if (typeof originalSubmit === 'function') {
        result = originalSubmit.call(this, event);
      }
      
      // 기존 제출 이벤트가 false를 반환하면 중단
      if (result === false) {
        return false;
      }
      
      // 폼 데이터에서 boardNo 필드 찾기 (게시글 저장 후 생성된 번호)
      const boardNoField = document.querySelector('input[name="boardNo"]');
      
      // 폼이 제출된 후 처리 (게시글 저장 후)
      if (boardNoField && boardNoField.value) {
        // 약간의 지연 후 임시 파일 업데이트 요청 (게시글 저장이 완료된 후)
        setTimeout(() => {
          updateTempFilesToPermanent(boardNoField.value);
        }, 500);
      }
      
      return true;
    };
  }

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

  // 콘텐츠 입력 이벤트 리스너 등록
  const contentArea = document.getElementById("contentInput");
  if (contentArea) {
    contentArea.addEventListener("input", handleContentInput);

    // 붙여넣기 이벤트 리스너도 추가
    contentArea.addEventListener("paste", (e) => {
      setTimeout(() => {
        handleContentInput({ target: contentArea });
      }, 100);
    });
  }

  // 페이지 로드 시 기존 첨부 파일 목록 로드
  loadExistingFiles();

  // 이전에 입력된 내용에서 URL 확인하고 변환
  if (contentArea && contentArea.value) {
    // URL 패턴 체크
    const urlRegex = /(https?:\/\/[^\s<>"]+)/g;
    let matches;
    const content = contentArea.value;
    let newContent = content;
    let offset = 0;

    // 먼저 HTML 태그가 있는 부분을 찾아 해당 범위를 피하기 위한 정규식
    const htmlTagsRegex = /<[^>]*>[^<]*<\/[^>]*>|<[^>]*\/>/g;
    const htmlMatches = [];
    let htmlMatch;
    
    while ((htmlMatch = htmlTagsRegex.exec(content)) !== null) {
      htmlMatches.push({
        start: htmlMatch.index,
        end: htmlMatch.index + htmlMatch[0].length
      });
    }

    // 해당 위치가 HTML 태그 내부에 있는지 확인하는 함수
    const isInsideHtmlTag = (position) => {
      return htmlMatches.some(match => position >= match.start && position <= match.end);
    };

    while ((matches = urlRegex.exec(content)) !== null) {
      const url = matches[0];
      const youtubeId = getYoutubeVideoId(url);

      // URL의 시작 위치와 끝 위치
      const startPos = matches.index + offset;
      const endPos = startPos + url.length;

      // HTML 태그 내부에 있으면 건너뛰기
      if (isInsideHtmlTag(matches.index)) {
        continue;
      }

      // 이미 a 태그로 감싸진 URL인지 확인
      const before = newContent.substring(Math.max(0, startPos - 15), startPos);
      const after = newContent.substring(endPos, Math.min(newContent.length, endPos + 15));
      
      if (before.includes('<a href="') && after.includes('</a>')) {
        continue; // 이미 a 태그로 감싸진 경우 건너뛰기
      }

      if (youtubeId) {
        // URL을 임베드 코드로 변환할지 사용자에게 확인
        if (confirm(`유튜브 영상 링크를 임베드로 변환하시겠습니까? (${url})`)) {
          const embedCode = `
<figure class="imageblock aligncenter" data-ke-mobilestyle="widthOrigin">
  <div class="youtube-embed">
    <iframe width="560" height="315" src="https://www.youtube.com/embed/${youtubeId}" 
    frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; 
    gyroscope; picture-in-picture" allowfullscreen></iframe>
  </div>
  <figcaption></figcaption>
</figure>
`;

          // URL을 임베드 코드로 대체
          newContent = newContent.substring(0, startPos) + embedCode + newContent.substring(endPos);
          offset += embedCode.length - url.length;
        } else {
          // a 태그로 변환
          const aTag = `<a href="${url}" target="_blank">${url}</a>`;
          newContent = newContent.substring(0, startPos) + aTag + newContent.substring(endPos);
          offset += aTag.length - url.length;
        }
      } else {
        // 일반 URL을 a 태그로 변환
        const aTag = `<a href="${url}" target="_blank">${url}</a>`;
        newContent = newContent.substring(0, startPos) + aTag + newContent.substring(endPos);
        offset += aTag.length - url.length;
      }
      
      // 변경된 내용에 맞춰 HTML 태그 위치 업데이트
      htmlMatches.forEach((match, index) => {
        if (match.start >= endPos) {
          htmlMatches[index].start += offset;
          htmlMatches[index].end += offset;
        }
      });
    }

    // 변경된 내용 적용
    if (newContent !== content) {
      contentArea.value = newContent;
    }
  }
});