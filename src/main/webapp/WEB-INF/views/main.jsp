<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>겜세모 - 메인</title>
  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <!-- jQuery -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
  <!-- Bootstrap JS -->
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <!-- Custom CSS -->
  <link rel="stylesheet" href="<c:url value='/css/main.css' />" />
</head>

<body>
<!-- 헤더 영역 -->
<jsp:include page="/WEB-INF/views/common/header.jsp" flush="true"/>

<div class="container">
<!-- Carousel Section -->
<div id="myCarousel" class="carousel slide" data-ride="carousel" style="width: 80%; margin: 30px auto;">
  <ol class="carousel-indicators">
    <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
    <li data-target="#myCarousel" data-slide-to="1"></li>
    <li data-target="#myCarousel" data-slide-to="2"></li>
  </ol>
  <div class="carousel-inner">
    <div class="item active">
      <a href="<c:url value='/game' />">
        <div class="carousel-frame">
          <img src="https://store.nintendo.co.kr/media/wysiwyg/mario_kart_world_1.jpg" alt="Mario" />
        </div>
      </a>
      <div class="carousel-caption"></div>
    </div>
    <div class="item">
      <a href="<c:url value='/game' />">
        <div class="carousel-frame">
           <img src="https://store.nintendo.co.kr/media/wysiwyg/tears_of_the_kingdom__.jpg" alt="Zelda" />
        </div>
      </a>
      <div class="carousel-caption"></div>
    </div>
    <div class="item">
      <a href="<c:url value='/game' />">
        <div class="carousel-frame">
          <img src="https://store.nintendo.co.kr/media/catalog/product/cache/3be328691086628caca32d01ffcc430a/5/a/5ae3a9bf04ffc3f70e08ac6192aa751951823cbbe4ecd03978531079c4ce1a94.jpg" alt="welcome" />
        </div>
      </a>
      <div class="carousel-caption"></div>
    </div>
  </div>

  <!-- Carousel Controls -->
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
  <button class="platform-btn active" data-platform="steam">
  <img src="<c:url value='/images/icons/steam-black.png' />" alt="steam" class="platform-icon" />
  Steam
   </button>
  <button class="platform-btn" data-platform="nintendo">
  <img src="<c:url value='/images/icons/nintendo-black.png' />" alt="nintendo" class="platform-icon" />
  Nintendo
   </button>
   <button class="platform-btn" data-platform="planet">
  <img src="<c:url value='/images/icons/planet-black.png' />" alt="planet" class="platform-icon" />
  Planet
   </button>
  <button class="platform-btn" data-platform="direct">
  <img src="<c:url value='/images/icons/direct-black.png' />" alt="Direct" class="platform-icon" />
  Direct
</button>
</div>

<div class="game-section">
  <div class="game-category">
    <div class="game-header">
      <h2>💸 할인 게임 Top 10</h2>
      <a href="/game?state=dc" class="inline-more-link">더보기 &gt;</a>
    </div>
    <div id="discounted-games" class="game-list-container"></div>
  </div>

  <div class="game-category">
    <div class="game-header">
      <h2>🆕 신상 게임 Top 10</h2>
      <a href="/game?state=new" class="inline-more-link">더보기 &gt;</a>
    </div>
    <div id="new-games" class="game-list-container"></div>
  </div>
</div>

</div>
<!-- 푸터 영역 -->
<jsp:include page="/WEB-INF/views/common/footer.jsp" flush="true"/>

<script src="<c:url value='/js/proFile.js'/>"></script>
<script src="<c:url value='/js/gameList.js'/>" ></script>
<script src="<c:url value='/js/mainButton.js'/>"></script>
</body>
</html>