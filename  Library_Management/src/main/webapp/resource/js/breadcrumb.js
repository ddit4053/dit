
document.addEventListener("DOMContentLoaded", function () {
  generateBreadcrumb();
});

/**
 * 현재 URL에 맞는 breadcrumb 생성
 */
function generateBreadcrumb() {
  const breadcrumbElement = document.querySelector(".breadcrumb");
  if (!breadcrumbElement) return; // breadcrumb 요소가 없으면 중단

  // 이미 처리된 breadcrumb인 경우 중단 (동적 생성된 항목이 있는 경우)
  if (breadcrumbElement.querySelector(".dynamic-breadcrumb")) return;

  // 기존 breadcrumb 내용 저장
  const originalContent = breadcrumbElement.innerHTML;

  // 현재 URL 가져오기 (sidebar.js와 동일한 방식 사용)
  const url = currentURL;

  // 메뉴 데이터 가져오기 (member 또는 admin)
  const menuData = isAdmin ? menuStructure.admin : menuStructure.member;

  // breadcrumb 경로 찾기
  const pathInfo = findBreadcrumbPath(menuData, url);

  if (!pathInfo || !pathInfo.path || pathInfo.path.length === 0) {
    // 경로를 찾지 못했으면 기존 breadcrumb 유지
    return;
  }

  // 새 breadcrumb HTML 생성
  let newBreadcrumbHTML = `<a href="${contextPath}/main.do">홈</a> > `;

  // pathInfo.path에 있는 각 항목에 대해 링크 생성
  for (let i = 0; i < pathInfo.path.length; i++) {
    const item = pathInfo.path[i];

    if (i === pathInfo.path.length - 1) {
      // 마지막 항목은 링크가 아닌 텍스트로 표시
      newBreadcrumbHTML += `<span class="dynamic-breadcrumb">${item.title}</span>`;
    } else {
      // 중간 항목은 링크로 표시
      newBreadcrumbHTML += `<a href="${contextPath}${item.url}" class="dynamic-breadcrumb">${item.title}</a> > `;
    }
  }

  // breadcrumb 업데이트
  breadcrumbElement.innerHTML = newBreadcrumbHTML;
}

/**
 * 현재 URL에 맞는 breadcrumb 경로 찾기
 * @param {Array} menuData - 메뉴 데이터 배열
 * @param {String} url - 현재 URL
 * @returns {Object} - 찾은 breadcrumb 경로
 */
function findBreadcrumbPath(menuData, url) {
  const paths = [];

  function traverse(items, currentPath = []) {
    for (let i = 0; i < items.length; i++) {
      const item = items[i];
      const newPath = [...currentPath, { title: item.title, url: item.url }];

      // URL이 메뉴 URL과 일치하거나 포함하면 해당 경로 추가
      if (url.includes(item.url)) {
        paths.push({
          path: newPath,
          urlLength: item.url.length,
        });
      }

      // 하위 메뉴가 있으면 재귀적으로 탐색
      if (item.subMenus && item.subMenus.length > 0) {
        traverse(item.subMenus, newPath);
      }
    }
  }

  traverse(menuData);

  // URL 길이가 가장 긴 경로 선택 (가장 구체적인 메뉴 경로)
  if (paths.length > 0) {
    paths.sort((a, b) => b.urlLength - a.urlLength);
    return paths[0];
  }

  return null;
}
