<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<body>
<!-- 헤더 영역 -->
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<!-- Carousel Section -->
<div id="myCarousel" class="carousel slide" data-ride="carousel" style="width: 80%; margin: 30px auto;">
  <ol class="carousel-indicators">
    <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
    <li data-target="#myCarousel" data-slide-to="1"></li>
    <li data-target="#myCarousel" data-slide-to="2"></li>
  </ol>
  <div class="carousel-inner">
    <div class="item active">
      <img src="<c:url value='/images/thumbnails/mario.jpg' />" alt="mario" style="width:1000px; height:500px; object-fit:cover; display:block; margin:0 auto;">
      <div class="carousel-caption"><h3>Mario</h3></div>
    </div>
    <div class="item">
      <img src="<c:url value='/images/thumbnails/zelda.jpg' />" alt="zelda" style="width:1000px; height:500px; object-fit:cover; display:block; margin:0 auto;">
      <div class="carousel-caption"><h3>Zelda</h3></div>
    </div>
    <div class="item">
      <img src="<c:url value='/images/thumbnails/FANTASY.jpg' />" alt="FANTASY" style="width:1000px; height:500px; object-fit:cover; display:block; margin:0 auto;">
      <div class="carousel-caption"><h3>FANTASY</h3></div>
    </div>
  </div>
  <a class="left carousel-control" href="#myCarousel" data-slide="prev">
    <span class="glyphicon glyphicon-chevron-left"></span>
    <span class="sr-only">Previous</span>
  </a>
  <a class="right carousel-control" href="#myCarousel" data-slide="next">
    <span class="glyphicon glyphicon-chevron-right"></span>
    <span class="sr-only">Next</span>
  </a>
</div>

<div class="platform-buttons">
  <button class="platform-btn active" data-platform="steam">Steam</button>
  <button class="platform-btn" data-platform="xbox">Xbox</button>
  <button class="platform-btn" data-platform="ps">PlayStation</button>
  <button class="platform-btn" data-platform="nintendo">Nintendo</button>
  <button class="platform-btn" data-platform="direct">Direct</button> <%-- ✅ 추가된 버튼 --%>
</div>

<div class="game-section">
  <div class="game-category">
    <div class="game-header">
      <h2>💸 할인 게임 Top 10</h2>
      <a href="/game/list?state=dc" class="inline-more-link">더보기 &gt;</a>
    </div>
    <div id="discounted-games" class="game-list-container"></div>
  </div>

  <div class="game-category">
    <div class="game-header">
      <h2>🆕 신상 게임 Top 10</h2>
      <a href="/game/list?state=new" class="inline-more-link">더보기 &gt;</a>
    </div>
    <div id="new-games" class="game-list-container"></div>
  </div>
</div>

<!-- 푸터 영역 -->
<%@ include file="/WEB-INF/views/common/footer.jsp" %>

<script src="<c:url value='/js/proFile.js'/>"></script>
<script src="<c:url value='/js/gameList.js'/>" ></script>
<script src="<c:url value='/js/mainButton.js'/>"></script>
</body>
</html>