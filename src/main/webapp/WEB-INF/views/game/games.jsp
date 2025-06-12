<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게임 검색 결과</title>
<style>
.container {
	max-width: 1300px;
	margin: 0 auto;
	padding: 0 16px;
}

.content-wrap {
	display: flex;
	justify-content: space-between;
	align-items: flex-start;
}

main {
	flex: 1;
	margin-right: 20px;
}

.sidebar {
	position: sticky;
	top: 80px;
	width: 250px;
	padding: 20px;
	height: 550px;
	overflow-y: auto;
	border: 1px solid black;
	background: black;
	color: white;
}

.search-box {
	margin-bottom: 20px;
}

.search-box input {
	width: 70%;
	padding: 6px;
}

.search-box button {
	padding: 6px 10px;
}

.filter-group {
	margin-bottom: 10px;
	display: flex;
	flex-direction: column;
	padding: 10px;
}

input[type="radio"] {
	appearance: none;
	-webkit-appearance: none;
	background-color: black;
	border: 3px solid #e50914;
	border-radius: 50%;
	width: 25px;
	height: 25px;
	cursor: pointer;
	position: relative;
	outline: none;
}

input[type="radio"]:checked::after {
	content: '';
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	background-color: #e50914;
	width: 14px;
	height: 14px;
	border-radius: 50%;
}

label {
	display: flex;
	align-items: center;
	gap: 10px;
	font-size: 18px;
}

button {
	color: white;
	background: #e50914;
}

input[type="text"] {
	bgcolor: black;
	color: white;
	border: 3px solid #e50914;
}

.loading-wrap {
	display: flex;
	justify-content: center;
	flex-direction: column;
	align-items: center;
	width: 100%;
}
.loading-spinner {
    width: 40px;
    height: 40px;
    border: 5px solid #e50914;
    border-top: 5px solid transparent;
    border-radius: 50%;
    animation: rotate 1s linear infinite;
}

@keyframes rotate {
    from { transform: rotate(0deg); }
    to { transform: rotate(360deg); }
}

</style>
</head>
<body>
	<div class="container">
		<header>
			<h2>목록</h2>
			<hr>
		</header>
		<div class="content-wrap">
		
			<main>
			<div class="loading-wrap">
				<div class="loading-spinner loading-spinner--js"></div>
				<p>불러 오는 중...</p>
			</div>
			</main>
			
			
			<aside class="sidebar">
				<form id="filterForm">
					<!-- 검색창 -->
					<div class="search-box">
						<input type="text" id="keyword" placeholder="게임 검색" />
						<button type="submit">검색</button>
					</div>
	
					<!-- 카테고리 -->
					<div class="filter-group">
						<label><input type="radio" name="state" value="dc" checked>
							할인 중</label><br> <label><input type="radio" name="state"
							value="new"> 신규 출시</label><br> <label><input
							type="radio" name="state" value="free"> 무료</label>
					</div>
	
					<!-- 사이트 필터 -->
					<div class="filter-group">
						<h3>사이트 별</h3>
						<label><input type="radio" name="platform" value="" checked>
							모두</label><br> <label><input type="radio" name="platform"
							value="steam"> 스팀</label><br> <label><input
							type="radio" name="platform" value="directgames"> 다이렉트 게임즈</label><br>
						<label><input type="radio" name="platform"
							value="gamesplanet"> 게임스플래닛</label><br> <label><input
							type="radio" name="platform" value="nintendo"> 닌텐도</label>
					</div>
				</form>
			</aside>
			
		</div>
	</div>
</body>
</html>