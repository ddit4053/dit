/*
*
*/

document.addEventListener('DOMContentLoaded', () => {
  // 1) 네비게이션 활성화
  const currentPath = window.location.href;
  document.querySelectorAll('.nav-item').forEach(li => {
    const link = li.querySelector('a');
    if(link && link.href === currentPath) {
      li.classList.add('active');
    }
  });

  // 2) 닫기 버튼
  const closeBtn = document.getElementById('closeBtn');
  if(closeBtn) {
    closeBtn.addEventListener('click', () => {
      window.close();
    });
  }
});
