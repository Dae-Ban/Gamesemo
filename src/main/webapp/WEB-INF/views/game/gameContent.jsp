<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${game.giTitle}–상세정보</title>

<!-- Swiper CSS -->
<link rel="stylesheet"
	href="https://unpkg.com/swiper@8/swiper-bundle.min.css" />
<script src="http://code.jquery.com/jquery-latest.js"></script>
<style>
/* === 게임 정보 영역 복구 === */
.detail-header {
	display: flex;
	align-items: flex-start;
	gap: 32px;
	margin: 40px;
}

.detail-header .thumb img {
	width: 300px;
	border-radius: 8px;
	box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
}

.detail-header .info {
	flex: 1;
}

.detail-header .info h1 {
	margin: 0 0 16px;
	font-size: 2rem;
	border-bottom: 2px solid #ddd;
	padding-bottom: 8px;
}

.price-table {
	width: 100%;
	border-collapse: collapse;
	margin-bottom: 16px;
}

.price-table th, .price-table td {
	padding: 8px 12px;
	text-align: left;
}

.price-table th {
	border-bottom: 2px solid #ccc;
}

.price-table .discounted {
	font-weight: bold;
	color: #000;
}

.price-table .original {
	text-decoration: line-through;
	color: #888;
	margin-left: 8px;
}

.btn-group .btn {
	display: inline-block;
	margin-right: 12px;
	padding: 8px 16px;
	border-radius: 4px;
	text-decoration: none;
	font-size: 0.9rem;
}

.btn.primary {
	background: #555;
	color: #fff;
}

.btn.secondary {
	border: 1px solid #555;
	color: #555;
}

/* === 5개짜리 동영상 슬라이더 === */
.video-slider-wrapper {
	position: relative;
	width: 100%;
	max-width: 1000px; /* 5*200px 영상 너비 + 여백 */
	margin: 40px auto;
	padding: 0 40px; /* 버튼을 위해 여백 확보 */
}

.video-nav {
	position: absolute;
	top: 50%;
	transform: translateY(-50%);
	font-size: 2rem;
	width: 24px;
	height: 24px;
	line-height: 24px;
	text-align: center;
	cursor: pointer;
	user-select: none;
	z-index: 10;
	color: #333;
}

.video-nav.prev {
	left: 8px;
}

.video-nav.next {
	right: 8px;
}
//
/* gameContent.css 에 추가 또는 수정 */
.video-slider-wrapper {
	position: relative;
	width: 100%;
	max-width: 100%; /* 페이지 레이아웃에 맞게 */
	margin: 40px auto;
	padding: 0 40px; /* 버튼 공간 확보 */
	box-sizing: border-box;
}
//
.video-slider-wrapper .swiper {
	/* 슬라이더 전체 너비 차지 */
	width: 100%;
}

.video-slider-wrapper .swiper-slide {
	/* 슬라이드 하나당 flex 설정 없이 Swiper에 맡김 */
	display: flex;
	justify-content: center;
	align-items: center;
}

/* iframe 은 슬라이드 너비 100% */
.video-slider-wrapper .swiper-slide iframe {
	width: 100%;
	height: auto;
	aspect-ratio: 16/9; /* 비율 유지 */
}

.video-nav {
	position: absolute;
	top: 50%;
	transform: translateY(-50%);
	font-size: 2rem;
	width: 24px;
	height: 24px;
	line-height: 24px;
	text-align: center;
	cursor: pointer;
	user-select: none;
	z-index: 10;
	color: #333;
}

.video-nav.prev {
	left: 8px;
}

.video-nav.next {
	right: 8px;
}

#login-toast {
	position: fixed;
	bottom: 40px;
	left: 50%;
	transform: translateX(-50%);
	background: rgba(0, 0, 0, 0.75);
	color: #fff;
	padding: 10px 16px;
	border-radius: 4px;
	font-size: 0.9rem;
	opacity: 0;
	pointer-events: none;
	transition: opacity 0.4s ease-in-out;
	z-index: 9999;
}

.login-container {
	display: none;
	justify-content: center;
	align-items: center;
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background: rgba(0, 0, 0, 0.5);
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
	background-color: royalblue;
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

#close-button {
	width: 14px;
	height: 14px;
	cursor: pointer
}

.close {
	position: absolute;
	top: 12px;
	right: 12px;
	z-index: 10;
}

#wishlist-btn {
	background-color: #E50914;
}

.wishlist-btn-container {
	display: flex;
	justify-content: center;
	align-items: center;
}

.wishlist-btn {
	background-color: #E50914;
	color: white;
	font-weight: bold;
	padding: 6px 10px;
	border: 2px solid black;
	border-radius: 4px;
	cursor: pointer;
	transition: all 0.2s ease-in-out;
}

.wishlist-btn:hover {
	background-color: black;
	color: #E50914;
	border-color: #E50914;
}

.discounted {
	font-weight: bold;
	color: #000;
	font-size: 1rem;
}

.original {
	text-decoration: line-through;
	color: #888;
	font-size: 0.9rem;
}
</style>
</head>
<body>
	<!-- 상단 게임 정보 -->
	<!-- … 상단 게임 정보 영역 … -->
	<div class="detail-header">
		<div class="thumb">
			<img src="${game.giThumb}" alt="${game.giTitle} 썸네일" />
		</div>
		<div class="info">
			<h1>${game.giTitle}</h1>
			<table class="price-table">
				<thead>
					<tr>
						<th>플랫폼</th>
						<th>할인가</th>
						<th>정가</th>
						<th>링크</th>
						<th>위시리스트</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="p" items="${platformList}">
						<tr>
							<td>${p.giPlatform}</td>
							<td class="discounted">${p.giFprice}원</td>
							<td class="original">${p.giPrice}원</td>
							<!-- 링크 컬럼 -->
							<td><a href="${p.giLink}" target="_blank"
								style="color: #007bff; text-decoration: none;"> 바로가기 </a></td>
							<!-- 위시리스트 컬럼 -->
							<td>
								<div class="price-box">
									<div class="wishlist-btn-container">
										<c:choose>
											<c:when test="${not empty loginUser}">
												<button class="wishlist-btn"
													onclick="addToWishlist(${p.giNum})">+위시리스트</button>
											</c:when>
											<c:otherwise>
												<button class="wishlist-btn"
													onclick="handleWishlistClick(${p.giNum})">+위시리스트</button>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

		</div>
	</div>

	<!-- 리뷰 영상 6개 슬라이더 -->
	<h2 style="margin-left: 40px;">리뷰 영상</h2>
	<div class="video-slider-wrapper">
		<div class="video-nav prev" id="review-prev">‹</div>
		<div class="swiper" id="review-swiper">
			<div class="swiper-wrapper">
				<c:forEach var="v" items="${reviewVideos}">
					<div class="swiper-slide">
						<iframe src="https://www.youtube.com/embed/${v.videoId}"
							frameborder="0" allowfullscreen> </iframe>
					</div>
				</c:forEach>
			</div>
		</div>
		<div class="video-nav next" id="review-next">›</div>
	</div>

	<!-- 모달 로그인 영역 -->
	<div class="login-container">

		<div class="login-box">
			<div class="close">
				<img src="/images/icons/close-button.png" id="close-button">
			</div>
			<h2>로그인</h2>
			<c:if test="${not empty error}">
				<div style="color: red;">${error}</div>
			</c:if>
			<form action="/member/login" method="post">
				<input type="text" name="id" placeholder="아이디"> <input
					type="password" name="pw" placeholder="비밀번호"> <label><input
					type="checkbox" name="rememberMe"> 로그인 상태 유지</label> <input
					type="submit" value="Log In">
			</form>

			<div style="text-align: right;">
				<a href="/member/find">아이디/비밀번호를 잊으셨나요?</a>
			</div>

			<div class="sns-buttons">
				<a href="/oauth2/authorization/google"> <img
					src="/images/icons/google.png" alt="Google"></a> <a
					href="/oauth2/authorization/naver"> <img
					src="/images/icons/naver.png" alt="Naver"></a> <a
					href="/oauth2/authorization/kakao"> <img
					src="/images/icons/kakao.png" alt="Kakao"></a>
			</div>

			<div style="text-align: center; margin-top: 20px;">
				아직 계정이 없다면 <a href="/member/register">회원가입</a>
			</div>
		</div>
	</div>
	<!-- Swiper JS -->
	<script src="https://unpkg.com/swiper@8/swiper-bundle.min.js"></script>


	<div id="login-toast">로그인을 먼저 해주세요</div>
	<script>
		window.isLoggedIn = ${not empty loginUser ? 'true' : 'false'};
	</script>
	<script src="${pageContext.request.contextPath}/js/gameContent.js"
		defer></script>
</body>
</html>