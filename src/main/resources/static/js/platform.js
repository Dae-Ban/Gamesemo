document.addEventListener("DOMContentLoaded", function () {
  const buttons = document.querySelectorAll('.platform-btn');
  const gameSections = document.querySelectorAll('.platform-list');

  buttons.forEach(button => {
    button.addEventListener('click', () => {
      const platform = button.getAttribute('data-platform');

      // 버튼 스타일 변경
      buttons.forEach(btn => btn.classList.remove('active'));
      button.classList.add('active');

      // 섹션 보이기/숨기기
      gameSections.forEach(section => {
        section.style.display = section.getAttribute('data-platform') === platform ? 'block' : 'none';
      });
    });
  });
});