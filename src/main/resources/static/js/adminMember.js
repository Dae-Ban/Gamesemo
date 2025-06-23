$(document).on('click', '.blacklist-btn', function () {
  const id = $(this).data('id');
  const currentState = $(this).data('state');
  const targetState = currentState === 2 ? 0 : 2;

  const confirmText = targetState === 2 ? "블랙리스트로 등록할까요?" : "블랙리스트를 해제할까요?";

  if (!confirm(confirmText)) return;

  $.post("/admin/updateState", { id, state: targetState }, function () {
    alert("상태가 변경되었습니다.");
    location.reload();
  }).fail(function () {
    alert("변경 실패. 다시 시도해주세요.");
  });
});