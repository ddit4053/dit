/**
 * 
 */

document.addEventListener('DOMContentLoaded', () => {
  const ctx = '${ctx}';

  // 모달, 폼 컨테이너, 버튼들
  const layoutModal = document.getElementById('layoutModal');
  const showLayoutBtn = document.getElementById('showLayoutBtn');
  const closeModalBtn = layoutModal.querySelector('.close-btn');
  const layoutSeats = layoutModal.querySelectorAll('.layout-seat');
  const formContainer = document.getElementById('formContainer');
  const seatNoInput = document.getElementById('seatNo');
  const cancelFormBtn = document.getElementById('cancelForm');

  // 1) “좌석/실 배치 보기” 버튼 → 모달 열기
  showLayoutBtn.addEventListener('click', () => {
    layoutModal.style.display = 'block';
  });

  // 2) 모달 닫기 (× 버튼 / 외부 클릭)
  closeModalBtn.addEventListener('click', () => layoutModal.style.display = 'none');
  layoutModal.addEventListener('click', e => {
    if (e.target === layoutModal) layoutModal.style.display = 'none';
  });

  // 3) 레이아웃 내 좌석 클릭 → 선택 & 폼 열기
  layoutSeats.forEach(el => {
    el.addEventListener('click', () => {
      if (el.classList.contains('unavailable')) return;
      // 이전 선택 해제
      layoutSeats.forEach(s => s.classList.remove('selected'));
      // 새로 선택
      el.classList.add('selected');
      // hide modal
      layoutModal.style.display = 'none';
      // 폼에 좌석 번호 채우고 열기
      seatNoInput.value = el.dataset.seatNo;
      formContainer.style.display = 'block';
    });
  });

  // 4) 폼 취소 버튼 → 폼 닫고 선택 해제
  cancelFormBtn.addEventListener('click', () => {
    formContainer.style.display = 'none';
    layoutSeats.forEach(s => s.classList.remove('selected'));
  });
});
