<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>검색 결과</title>
<script src="https://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript" src="/js/gameSearch.js"></script>
<link rel="stylesheet" type="text/css" href="/css/games.css">
<script type="text/javascript" src="/js/scrollUp.js"></script>
</head>
<body>
	<div class="container">
		<header>
			<h2>검색 결과 <img src="/images/logo.png"></h2>
			<hr>
		</header>
		<div class="content-wrap">
			<main class="result-wrap">
				<table class="game-contents"></table>
				<h2 style="margin-top: 50px;"><img src="/images/icons/steam.png"> 스팀에서 더 보기</h2>
				<hr>
				<div class="game-grid"></div>
			</main>

			<aside class="sidebar" style="height: 150px;">
				<form id="filterForm" method="get" action="/game/search">
					<!-- 검색창 -->
					<div class="search-box">
						<input type="text" id="keyword" name="keyword" placeholder="게임 검색" />
						<button type="submit">검색</button>
					</div>
				</form>
				<button class="btn-wide" onclick="location.href='/game'">
				<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" 
				viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" 
				stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-undo2-icon lucide-undo-2">
				<path d="M9 14 4 9l5-5"/><path d="M4 9h10.5a5.5 5.5 0 0 1 5.5 5.5a5.5 5.5 0 0 1-5.5 5.5H11"/></svg>
				게임 목록으로
				</button>
				<button id="scrollToTopBtn" class="btn-wide">▲ 맨 위로</button>
			</aside>
		</div>
	</div>
</body>
</html>