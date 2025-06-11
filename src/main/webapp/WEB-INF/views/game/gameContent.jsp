<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>게임 상세 페이지</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/gameContent.css">
</head>
<body>
  <div class="container">
    <div class="top-section">
      <div class="thumbnail">
        <img src="썸네일경로.jpg" alt="게임 타이틀 썸네일">
      </div>
      <div class="game-info">
        <h2>게임 타이틀</h2>
        <p class="original-price"><del>${price}</del></p>
        <p class="sale-price">${fPrice}</p>
        <p class="description">${shortDesc}</p>
        <div class="buttons">
          <button class="go-btn">바로가기</button>
          <button class="wishlist-btn">위시리스트</button>
        </div>
      </div>
    </div>

    <div class="reviews">
      <div class="review-item">유튜브 리뷰영상 1</div>
      <div class="review-item">유튜브 리뷰영상 2</div>
      <div class="review-item">유튜브 리뷰영상 3</div>
    </div>
  </div>
</body>
</html>
