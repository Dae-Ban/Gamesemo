<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>${game.giTitle}–상세정보</title>
<!-- Swiper CSS. -->
<link rel="stylesheet"
	href="https://unpkg.com/swiper@8/swiper-bundle.min.css" />
<link rel="stylesheet" href="/css/gameContent.css" />
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

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
						<c:when test="${wishlisted}">
							<button class="wishlist-btn-full wishlisted"
								onclick="showWishlistToast('이미 위시리스트에 추가되어 있습니다.')">✔️
								위시리스트에 추가됨</button>
						</c:when>

						<c:otherwise>
							<button class="wishlist-btn-full"
								onclick="addToWishlist(${game.GNum})">위시리스트 추가</button>
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
		<div class="modal-login-container" id="loginModal">
			<div class="modal-login-box">
				<div class="modal-login-close">
					<img src="/images/icons/close-button.png" alt="닫기"
						onclick="hideLoginModal()" />
				</div>

				<div class="modal-logo-global">
					<img src="https://i.imgur.com/VKxL314.png" alt="로고"
						class="modal-logo-img">
				</div>

				<form id="ajaxLoginForm">
					<input type="text" name="id" placeholder="아이디" required> <input
						type="password" name="pw" placeholder="비밀번호" required>

					<div class="modal-login-options">
						<label> <input type="checkbox" name="rememberMe">
							로그인 상태 유지
						</label> <a href="/member/find" class="modal-find-link">아이디/비밀번호 찾기</a>
					</div>

					<input type="submit" value="로그인" class="modal-login-btn">
				</form>

				<div class="modal-bottom-links">
					<p class="modal-sns-title">SNS 계정으로 간편 로그인하기</p>
					<div class="modal-sns-buttons">
						<a href="/oauth2/authorization/google" class="modal-sns-icon">
							<img src="/images/icons/google.png" alt="Google">
						</a> <a href="/oauth2/authorization/naver" class="modal-sns-icon">
							<img src="/images/icons/naver.png" alt="Naver">
						</a> <a href="/oauth2/authorization/kakao" class="modal-sns-icon">
							<img src="/images/icons/kakao.png" alt="Kakao">
						</a>
					</div>

					<div class="modal-register">
						<a href="/member/register" class="modal-signup-btn">회원가입하기</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="wishlist-toast"
		style="position: fixed; bottom: 20px; left: 50%; transform: translateX(-50%); background: #333; color: #fff; padding: 12px 24px; border-radius: 24px; opacity: 0; transition: opacity 0.3s ease; z-index: 9999; font-weight: bold; font-size: 0.95rem;">위시리스트에
		추가되었습니다!</div>
	<!-- Swiper JS -->
	<script src="https://unpkg.com/swiper@8/swiper-bundle.min.js"></script>
	<script src="/js/gameContent.js"></script>


</body>


</html>
