<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>${game.giTitle}–상세정보</title>
<!-- Swiper CSS -->
<link rel="stylesheet"
	href="https://unpkg.com/swiper@8/swiper-bundle.min.css" />
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<style>
/* === 전체 레이아웃 === */
body {
	margin: 0;
	font-family: 'Noto Sans KR', sans-serif;
	background: #fff;
	color: #333;
}

.container {
	max-width: 1200px;
	margin: 5% auto;
	padding: 40px;
	box-sizing: border-box;
}
/* === 상세 헤더 === */
.detail-header {
	display: flex;
	gap: 40px;
	align-items: center;
}

.thumb img {
	width: 600px;
	border-radius: 8px;
	box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.info {
	flex: 1;
}

.info h1 {
	margin-bottom: 16px;
	font-size: 2.5rem;
	border-bottom: 2px solid #ddd;
	padding-bottom: 8px;
}
/* === 가격 카드 === */
.platform-card {
	display: flex;
	align-items: center;
	padding: 16px;
	border-radius: 8px;
	margin-bottom: 16px;
	background-color: #f9f9f9;
	box-shadow: 0 1px 3px rgba(0, 0, 0, 0.06);
	transition: box-shadow 0.2s;
}

.platform-card:hover {
	box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
}

.discount-tag {
	background: #E50914;
	color: #fff;
	font-size: 0.9rem;
	font-weight: bold;
	padding: 4px 10px;
	border-radius: 6px;
	text-align: center;
}

.price-info {
	display: flex;
	flex-direction: column;
	margin-left: 16px;
	gap: 4px;
}

.original {
	font-size: 0.9rem;
	text-decoration: line-through;
	opacity: 0.7;
}

.discounted {
	font-size: 1.2rem;
	font-weight: bold;
}

.btn {
	display: inline-block;
	margin-left: auto;
	padding: 8px 16px;
	border-radius: 20px;
	font-size: 0.9rem;
	font-weight: 500;
	text-decoration: none;
	color: #333;
	border: 1px solid #ccc;
	transition: background 0.2s, border-color 0.2s;
}

.btn:hover {
	background: #eaeaea;
	border-color: #bbb;
}
/* === 위시리스트 버튼 === */
.wishlist-container {
	text-align: center;
	margin: 24px 0;
}

.wishlist-btn-full {
	width: 100%;
	max-width: 400px;
	padding: 14px 0;
	background: #E50914;
	color: #fff;
	font-size: 1rem;
	font-weight: bold;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	transition: background 0.2s;
}

.wishlist-btn-full:hover {
	background: #b00710;
}
/* === 리뷰 영상 슬라이더 === */
.video-section {
	margin-top: 60px;
}

.video-section h2 {
	margin-bottom: 16px;
	font-size: 1.8rem;
	border-bottom: 2px solid #ddd;
}

.video-slider {
	position: relative;
	padding: 0 40px;
}

.video-slider, #review-swiper {
	overflow: visible !important;
}

.video-nav {
	position: absolute;
	top: 50%;
	transform: translateY(-50%);
	font-size: 2rem;
	cursor: pointer;
	color: #888;
	z-index: 10;
}

.video-nav.prev {
	left: 0;
}

.video-nav.next {
	right: 0;
}

.swiper {
	width: 100%;
}

.swiper-slide iframe {
	width: 100%;
	aspect-ratio: 16/9;
	border-radius: 6px;
	box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.login-container {
	display: none;
	position: fixed;
	inset: 0;
	background: rgba(0, 0, 0, 0.5);
	justify-content: center;
	align-items: center;
	z-index: 1000;
}

.login-box {
	width: 100%;
	max-width: 400px;
	margin: 100px auto;
	padding: 30px;
	border: 1px solid #ccc;
	border-radius: 10px;
	background-color: #f9f9f9;
	position: relative;
}

.login-box h2 {
	text-align: center;
	margin-bottom: 20px;
}

.login-box input[type="text"], .login-box input[type="password"] {
	width: 100%;
	padding: 10px;
	margin-bottom: 15px;
}

.login-box input[type="submit"] {
	width: 100%;
	padding: 10px;
	background: royalblue;
	color: white;
	border: none;
	cursor: pointer;
}

.login-box .sns-buttons {
	margin-top: 20px;
	display: flex;
	justify-content: space-between;
}

.login-box .sns-buttons img {
	width: 32px;
	height: 32px;
	cursor: pointer;
}

.close {
	position: absolute;
	top: 12px;
	right: 12px;
	width: 15px;
	height: 15px;
	cursor: pointer;
}

.close img {
	width: 100%;
	height: 100%;
	display: block;
}

#review-swiper {
	overflow: hidden !important; /* visible 강제 해제 */
}

/* 2) 네비 버튼 위치 조정 */
.video-nav {
	position: absolute;
	top: 50%;
	transform: translateY(-50%);
	font-size: 2rem;
	cursor: pointer;
	color: #888;
	z-index: 10;
	user-select: none;
}

.video-nav.prev {
	left: 0; /* 왼쪽 꼭대기 */
	margin-left: -40px; /* .video-slider 패딩만큼 빼주면 깔끔 */
}

.video-nav.next {
	right: 0;
	margin-right: -40px;
}

.wishlist-btn-full.wishlisted {
  background-color: #1D1D1D;
  color: #fff;
  border: 1px solid #1D1D1D;
}

</style>
</head>
<body>
	<div class="container">
		<div class="detail-header">
			<div class="thumb">
				<img src="${game.giThumb}" alt="${game.giTitle} 썸네일" />
			</div>
			<div class="info">
				<h1>${game.giTitle}</h1>
				<c:forEach var="p" items="${platformList}">
					<div class="platform-card">
						<div class="discount-tag">${p.giRate}%</div>
						<div class="price-info">
							<div class="original">
								<fmt:formatNumber value="${p.giPrice}" type="number"
									groupingUsed="true" maxFractionDigits="0" />
								원
							</div>
							<div class="discounted">
								<fmt:formatNumber value="${p.giFprice}" type="number"
									groupingUsed="true" maxFractionDigits="0" />
								원
							</div>
						</div>
						<c:choose>
							<c:when test="${p.giPlatform == 'steam'}">
								<a href="${p.giLink}" target="_blank" class="btn">Steam</a>
							</c:when>
							<c:when test="${p.giPlatform == 'direct'}">
								<a href="${p.giLink}" target="_blank" class="btn">Direct
									Games</a>
							</c:when>
							<c:when test="${p.giPlatform == 'planet'}">
								<a href="${p.giLink}" target="_blank" class="btn">Games
									Planet</a>
							</c:when>
							<c:when test="${p.giPlatform == 'nintendo'}">
								<a href="${p.giLink}" target="_blank" class="btn">Nintendo</a>
							</c:when>
							<c:otherwise>
								<a href="${p.giLink}" target="_blank" class="btn">구매하러가기</a>
							</c:otherwise>
						</c:choose>
					</div>
				</c:forEach>
				<div class="wishlist-container">
					<c:choose>
						<c:when test="${not empty loginUser}">
							<c:choose>
								<c:when test="${wishlisted}">
									<button class="wishlist-btn-full wishlisted"  onclick="showWishlistToast('이미 위시리스트에 추가되었습니다.')">✔️ 위시리스트에
										추가됨</button>
								</c:when>
								<c:otherwise>
									<button class="wishlist-btn-full"
										onclick="addToWishlist(${game.GNum})">위시리스트 추가</button>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<button class="wishlist-btn-full"
								onclick="handleWishlistClick(${game.GNum})">위시리스트 추가</button>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
		<!-- 리뷰 영상 슬라이더 -->
		<div class="video-section">
			<h2>리뷰 영상</h2>
			<div class="video-slider">
				<!-- 커스텀 네비 버튼 -->
				<div class="video-nav prev" id="review-prev">‹</div>

				<div class="swiper" id="review-swiper">
					<div class="swiper-wrapper">
						<c:forEach var="v" items="${reviewVideos}">
							<div class="swiper-slide">
								<iframe src="https://www.youtube.com/embed/${v.videoId}"
									allowfullscreen></iframe>
							</div>
						</c:forEach>
					</div>
				</div>

				<div class="video-nav next" id="review-next">›</div>
			</div>
		</div>
		<!-- 모달 로그인 영역 -->
		<div class="login-container" id="loginModal">
			<div class="login-box">
				<div class="close">
					<img src="/images/icons/close-button.png" id="close-button"
						onclick="hideLoginModal()" alt="닫기" />
				</div>
				<h2>로그인</h2>
				<c:if test="${not empty error}">
					<div style="color: red;">${error}</div>
				</c:if>
				<form id="ajaxLoginForm">
					<input type="text" name="id" placeholder="아이디" required /> <input
						type="password" name="pw" placeholder="비밀번호" required /> <label><input
						type="checkbox" name="rememberMe" /> 로그인 상태 유지</label> <input
						type="submit" value="Log In" />
				</form>

				<div style="text-align: right;">
					<a href="/member/find">아이디/비밀번호를 잊으셨나요?</a>
				</div>
				<div class="sns-buttons">
					<a href="/oauth2/authorization/google"><img
						src="/images/icons/google.png" alt="Google" /></a> <a
						href="/oauth2/authorization/naver"><img
						src="/images/icons/naver.png" alt="Naver" /></a> <a
						href="/oauth2/authorization/kakao"><img
						src="/images/icons/kakao.png" alt="Kakao" /></a>
				</div>
				<div style="text-align: center; margin-top: 20px;">
					아직 계정이 없다면 <a href="/member/register">회원가입</a>
				</div>
			</div>
		</div>
	</div>
	<div id="wishlist-toast"
		style="position: fixed; bottom: 20px; left: 50%; transform: translateX(-50%); background: #333; color: #fff; padding: 12px 24px; border-radius: 24px; opacity: 0; transition: opacity 0.3s ease; z-index: 9999; font-weight: bold; font-size: 0.95rem;">위시리스트에
		추가되었습니다!</div>
	<!-- Swiper JS -->
	<script src="https://unpkg.com/swiper@8/swiper-bundle.min.js"></script>
	<script>
	
// 유튜브 슬라이더 갯수 및 간격 영역
$(function() {
	<script src="https://unpkg.com/swiper@8/swiper-bundle.min.js"></script>
	<script>
	  $(function() {
	    new Swiper('#review-swiper', {
	      slidesPerView: 4,       // 한 번에 4개 보이기
	      slidesPerGroup: 4,      // 한 번에 4개씩 이동
	      spaceBetween: 20,       // 슬라이드 간격
	      navigation: {
	        nextEl: '#review-next',
	        prevEl: '#review-prev',
	      },
	      loop: false
	    });
	  });
 		function showLoginModal() {
 			const redirectUrl = window.location.pathname
 					+ window.location.search;
 			sessionStorage.setItem("redirectAfterLogin", redirectUrl);  
 			$("#loginModal").css("display", "flex");
 		}

 		function hideLoginModal() {
 			$('#loginModal').hide();
 		}

 		function handleWishlistClick(gameNum) {
 			 sessionStorage.setItem("redirectAfterLogin",
 				    window.location.pathname + window.location.search);
 				  document.getElementById("loginModal").style.display = "flex";
 			}

		//로그인 submit 영역		
 		$(document).on("submit", "#ajaxLoginForm", function (e) {
 			  e.preventDefault();
 			  const id = $(this).find("input[name='id']").val();
 			  const pw = $(this).find("input[name='pw']").val();

 			 $.post("/wishlist/ajaxLogin", { id, pw }, function (res) {
 				  if (res === "success") {
 				    const path = sessionStorage.getItem("redirectAfterLogin") || "/";
 				    // origin 은 프로토콜+호스트+포트를 포함합니다.
 				    window.location.href = window.location.origin + path;
 				   $('.wishlist-btn-full').addClass('logged-in');
 				  } else {
 				    alert("로그인 실패. 아이디와 비밀번호를 확인하세요.");
 				  }
 				});
 			});
 		
 		
 		//위시리스트 버튼 토스트 부분
 		function showWishlistToast(message) {
 		    const toast = document.getElementById('wishlist-toast');
 		    toast.textContent = message || "위시리스트에 추가되었습니다!";
 		    toast.style.opacity = '1';

 		    clearTimeout(toast.hideTimeout);
 		    toast.hideTimeout = setTimeout(() => {
 		      toast.style.opacity = '0';
 		    }, 2000);
 		  }

 		function addToWishlist(gnum) {
 			  $.ajax({
 			    url: "/wishlist/add",
 			    type: "POST",
 			    data: { gnum },
 			    success: function(response) {
 			      if (response === "success") {
 			        showWishlistToast("위시리스트에 추가되었습니다!");
 			        location.reload();
 			      } else if (response === "already_exists") {
 			        showWishlistToast("이미 위시리스트에 추가되어 있습니다.");
 			      } else if (response === "not_logged_in") {
 			        // 로그인 여부 재확인
 			        handleWishlistClick(gnum);
 			      }
 			    }
 			  });
 			}
 		  
</script>

</body>
</html>