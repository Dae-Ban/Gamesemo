<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${headline } 게임</title>
<script src="https://code.jquery.com/jquery-latest.js"></script>
<link rel="stylesheet" type="text/css" href="/css/games.css">
<link rel="stylesheet" href="<c:url value='/css/header.css' />" />
<script type="text/javascript" src="/js/gameList.js"></script>
<script type="text/javascript" src="/js/games.js"></script>
<script type="text/javascript" src="/js/scrollUp.js"></script>
</head>
<body>
<!-- 헤더 영역 -->
<jsp:include page="/WEB-INF/views/common/header.jsp" flush="true"/>
	<div class="container">
		<header>
			<h2><em></em><span></span>게임 <img src="/images/logo.png"></h2>
			<hr>
		</header>
		<div class="content-wrap">
			<main>
				<div class="game-contents-wrap">
					<table class="game-contents">
						<!-- 로딩 -->
					</table>
				</div>
				<div class="loading-wrap scroll-anchor">
					<img src="/images/spinner.png" class="loading-spinner">
					<p>불러 오는 중...</p>
				</div>
			</main>
			
			
			<aside class="sidebar">
				<form id="filterForm" method="get" action="/game/search">
					<!-- 검색창 -->
					<div class="search-box">
						<input type="text" id="keyword" name="keyword" placeholder="게임 검색" />
						<button type="submit">검색</button>
					</div>
					
					<div id="sorting">
						<select id="sortOption">
							 <option value="rateDesc">높은 할인율</option>
							 <option value="priceAsc">낮은 가격</option>
							 <option value="priceDesc">높은 가격</option>
						</select>
					</div>
	
					<!-- 카테고리 -->
					<div class="filter-group">
						<label>
							<input type="radio" name="state" value="dc" checked> 할인 중
						</label>
						<br>
						<label>
						<input type="radio" name="state" value="new"> 신규 출시
						</label><br>
						<label>
							<input type="radio" name="state" value="free"> 무료
						</label>
					</div>
	
					<!-- 사이트 필터 -->
					<div class="filter-group">
						<h3>사이트 별</h3>
						<label>
							<input type="radio" name="platform" value="all" checked> 모두
						</label>
						<br>
						<label>
						<input type="radio" name="platform" value="steam"> 스팀
						</label>
						<br>
						<label>
							<input type="radio" name="platform" value="direct"> 다이렉트 게임즈
						</label>
						<br>
						<label>
							<input type="radio" name="platform" value="planet"> 게임스플래닛
						</label>
						<br>
						<label>
							<input type="radio" name="platform" value="nintendo"> 닌텐도
						</label>
					</div>
				</form>
				<button id="scrollToTopBtn" class="btn-wide">▲ 맨 위로</button>
			</aside>
			
		</div>
	</div>
	<script src="<c:url value='/js/proFile.js'/>"></script>
</body>
</html>