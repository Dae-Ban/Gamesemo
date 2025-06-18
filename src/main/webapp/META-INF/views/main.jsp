<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
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

  <header>  
  
    <div class="logo">
      <img src="<c:url value='/images/logo1.png' />" alt="겜세모 로고" />
      <strong>겜세모</strong>
    </div>
    
    
 <nav class="simple-nav">
  <a href="#">홈</a>
  <a href="#">게임 목록</a>

  <!-- 드롭다운 -->
  <div class="dropdown">
    <a href="#" class="dropdown-toggle">커뮤니티</a>
    <div class="dropdown-menu">
      <a href="/board/free">자유게시판</a>
      <a href="/board/review">리뷰게시판</a>
    </div>
  </div>

  <a href="#">공지사항</a>
</nav>

  <div class="search-login">
  <div class="search-box">
    <input type="text" placeholder="검색어를 입력하세요" />
    <select>
      <option>전체</option>
      <option>게임</option>
      <option>게시판</option>
    </select>
    <button class="search-btn">
      <img src="<c:url value='/images/search-icon.png'/>" alt="검색" />
    </button>
  </div>

  <button class="login-btn">로그인</button>

<!-- 드롭다운 시작 -->
<div class="dropdown">
  <img src="<c:url value='/images/profile-icon.png'/>" alt="프로필" class="profile-icon" />
  <div class="dropdown-menu">
    <a href="/mypage">마이페이지</a>
    <a href="/wishlist">위시리스트</a>
  </div>
</div>
</div>
  </header>

  <!-- Carousel Section -->
  <div id="myCarousel" class="carousel slide" data-ride="carousel" style="width: 80%; margin: 30px auto;">
    <!-- Indicators -->
    <ol class="carousel-indicators">
      <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
      <li data-target="#myCarousel" data-slide-to="1"></li>
      <li data-target="#myCarousel" data-slide-to="2"></li>
    </ol>

    <!-- Slides -->
    <div class="carousel-inner">
      <div class="item active">
        <img src="<c:url value='/images/mario.jpg' />" alt="mario"  style="width:1000px; height:500px; object-fit:cover; display:block; margin:0 auto;">
        <div class="carousel-caption">
          <h3>Mario</h3>
        </div>
      </div>

      <div class="item">
        <img src="<c:url value='/images/zelda.jpg' />" alt="zelda"  style="width:1000px; height:500px; object-fit:cover; display:block; margin:0 auto;">
        <div class="carousel-caption">
          <h3>Zelda</h3>
        </div>
      </div>

      <div class="item">
        <img src="<c:url value='/images/FANTASY.jpg' />" alt="FANTASY" style="width:1000px; height:500px; object-fit:cover; display:block; margin:0 auto;">
        <div class="carousel-caption">
          <h3>FANTASY</h3>
        </div>
      </div>
    </div>

    <!-- Controls -->
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
  <button class="platform-btn" data-platform="switch">Switch</button>
</div>

<!-- 플랫폼별 게임 목록들 -->
<div class="sections">
  <div class="section platform-list" data-platform="steam">
    <h3>Steam 게임</h3>
    <div class="game-list">Half-Life - ₩15,000</div>
    <div class="game-list">Portal 2 - ₩10,000</div>
    <div class="game-list">Half-Life - ₩15,000</div>
    <div class="game-list">Portal 2 - ₩10,000</div>
    <div class="game-list">Half-Life - ₩15,000</div>
    <div class="game-list">Portal 2 - ₩10,000</div>
  </div>

  <div class="section platform-list" data-platform="xbox" style="display: none;">
    <h3>Xbox 게임</h3>
    <div class="game-list">Halo Infinite - ₩25,000</div>
    <div class="game-list">Forza Horizon - ₩20,000</div>
  </div>

  <div class="section platform-list" data-platform="ps" style="display: none;">
    <h3>PlayStation 게임</h3>
    <div class="game-list">The Last of Us - ₩30,000</div>
    <div class="game-list">Spider-Man - ₩28,000</div>
  </div>

  <div class="section platform-list" data-platform="switch" style="display: none;">
    <h3>Switch 게임</h3>
    <div class="game-list">Zelda: BOTW - ₩35,000</div>
    <div class="game-list">Mario Kart 8 - ₩29,000</div>
  </div>
</div>

  <footer>
     겜세모팀 ⓒ 2025. All rights reserved.
  </footer>

  <script src="<c:url value='/js/platform.js'/>"></script>

</body>
</html>