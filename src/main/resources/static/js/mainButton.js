console.log("🟢 mainButton.js 실행 시작됨");

if (typeof GameList === "undefined") {
  console.error("❌ GameList가 정의되지 않았습니다. gameList.js를 먼저 로드하세요.");
} else {
  console.log("✅ GameList 로드 확인됨");
}

// 게임 목록을 HTML로 렌더링하는 함수
function renderGames(data, containerId) {
  const container = document.getElementById(containerId);
  container.innerHTML = "";

  data.forEach(game => {
    container.innerHTML += `
      <div class="game-row">
        <a href="/game/${game.giNum}" class="game-item">
          <img src="${game.giThumb}" alt="${game.giTitle}" class="game-thumb"
		  onerror="this.onerror=null; this.src='/images/icons/noThumb.png';" />
          <div class="game-info">
            <div class="game-title">${game.giTitle}</div>
            <div class="game-price">
              ${
                game.giRate > 0
                  ? `<del>₩${game.giPrice.toLocaleString()}</del> → ₩${game.giFprice.toLocaleString()}
                     <span class="game-discount">${game.giRate}% 할인</span>`
                  : `₩${game.giFprice.toLocaleString()}`
              }
            </div>
          </div>
        </a>
      </div>
    `;
  });
}

// DOM이 로드된 후 실행
window.addEventListener("DOMContentLoaded", () => {
  console.log("✅ DOMContentLoaded 실행됨");

  const loadByPlatform = (platform) => {
    console.log("🔘 플랫폼 선택:", platform);

    GameList.loadGames({
      amount: 10,
      state: "dc",
      platform,
      onSuccess: data => {
        console.log("📥 할인 게임 데이터 도착:", data);
        renderGames(data, "discounted-games");
      }
    });

    GameList.loadGames({
      amount: 10,
      state: "new",
      platform,
      onSuccess: data => {
        console.log("📥 신상 게임 데이터 도착:", data);
        renderGames(data, "new-games");
      }
    });
  };

  // 초기 Steam 플랫폼 기준 로딩
  loadByPlatform("steam");

  // 플랫폼 버튼 클릭 시 다시 로딩
  document.querySelectorAll(".platform-btn").forEach(button => {
    button.addEventListener("click", () => {
      // 버튼 활성화 UI
      document.querySelectorAll(".platform-btn").forEach(btn => btn.classList.remove("active"));
      button.classList.add("active");

      const platform = button.dataset.platform;
      loadByPlatform(platform);
    });
  });
});