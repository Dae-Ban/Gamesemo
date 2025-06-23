// Swiper 초기화.
$(function () {
  new Swiper('#review-swiper', {
    slidesPerView: 4,
    slidesPerGroup: 4,
    spaceBetween: 20,
    navigation: {
      nextEl: '#review-next',
      prevEl: '#review-prev',
    },
    loop: false
  });
});


// 로그인 모달 보여주기.
function showLoginModal() {
  const redirectUrl = window.location.pathname + window.location.search;
  sessionStorage.setItem("redirectAfterLogin", redirectUrl);
  $("#loginModal").css("display", "flex");
}

// 로그인 모달 숨기기
function hideLoginModal() {
  $('#loginModal').hide();
}

// 비로그인 상태에서 위시리스트 버튼 클릭 시
function handleWishlistClick(gameNum) {
	$.ajax({
	   url: "/wishlist/gameAdd",
	   type: "POST",
	   data: { gnum },
	   success: function (response) {
	     if (response === "success") {
	       showWishlistToast("위시리스트에 추가되었습니다!");
	       location.reload();
	     } else if (response === "already_exists") {
	       showWishlistToast("이미 위시리스트에 추가되어 있습니다.");
	     } else if (response === "not_logged_in") {
	       // ✅ 백엔드에서 로그인 안 된 상태 응답시 모달 띄우기
	       sessionStorage.setItem("redirectAfterLogin", window.location.pathname + window.location.search);
	       document.getElementById("loginModal").style.display = "flex";
	     }
	   }
	 });
}

// 로그인 폼 AJAX 처리
$(document).on("submit", "#ajaxLoginForm", function (e) {
  e.preventDefault();

  const id = $(this).find("input[name='id']").val();
  const pw = $(this).find("input[name='pw']").val();

  $.post("/wishlist/ajaxLogin", { id, pw }, function (res) {
    if (res === "success") {
      const path = sessionStorage.getItem("redirectAfterLogin") || "/";
      window.location.href = window.location.origin + path;
    } else {
      alert("로그인 실패. 아이디와 비밀번호를 확인하세요.");
    }
  });
});



// 위시리스트 추가 알림 토스트
function showWishlistToast(message) {
  const toast = document.getElementById('wishlist-toast');
  toast.textContent = message || "위시리스트에 추가되었습니다!";
  toast.style.opacity = '1';

  clearTimeout(toast.hideTimeout);
  toast.hideTimeout = setTimeout(() => {
    toast.style.opacity = '0';
  }, 2000);
}

// 위시리스트에 추가 AJAX 처리
function addToWishlist(gnum) {
  $.ajax({
    url: "/wishlist/gameAdd",
    type: "POST",
    data: { gnum },
    success: function (response) {
      if (response === "success") {
        showWishlistToast("위시리스트에 추가되었습니다!");
        location.reload();
      } else if (response === "already_exists") {
        showWishlistToast("이미 위시리스트에 추가되어 있습니다.");
      } else if (response === "not_logged_in") {
        showLoginModal();  // 🔁 로그인 안 돼 있으면 모달 띄우기
      }
    },
    error: function () {
      console.error("⚠️ 서버 요청 실패");
    }
  });
}
