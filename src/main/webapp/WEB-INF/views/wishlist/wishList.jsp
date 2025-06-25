<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>위시리스트</title>
<link
	href="https://fonts.googleapis.com/css2?family=Pretendard&display=swap"
	rel="stylesheet">
<link rel="stylesheet" href="<c:url value='/css/header.css' />" />
<link rel="stylesheet" href="<c:url value='/css/footer.css' />" />
<style>
body {
	font-family: 'Pretendard', sans-serif;
	margin: 0;
	padding:0px;
	background: #f9f9f9;
	color: #333;
}

.container {
	max-width: 960px;
	margin: 0 auto;
}

.title {
	margin-top: 60px;  /* ✅ 추가: 헤더와의 간격 확보 */
	font-size: 32px;
	font-weight: bold;
	margin-bottom: 24px;
}

.search-bar {
	display: flex;
	gap: 10px;
	margin-bottom: 30px;
}

.search-bar input, .search-bar select, .search-bar button {
	padding: 10px 14px;
	border: 1px solid #ccc;
	border-radius: 6px;
	font-size: 14px;
}

.search-bar button {
	background-color: #e50914;
	color: white;
	border: none;
	cursor: pointer;
}

.wishlist-item {
	background: white;
	display: flex;
	align-items: center;
	padding: 16px;
	margin-bottom: 16px;
	border-radius: 12px;
	box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
}

.game-thumb {
	width: 120px;
	height: 70px;
	display: flex;
	align-items: center;
	justify-content: center;
	border-radius: 6px;
}

.game-thumb img {
	max-width: 100%;
	max-height: 100%;
	object-fit: contain;
	border-radius: 6px;
}

.game-info {
	flex: 1;
	margin-left: 16px;
}

.game-title {
	font-size: 18px;
	font-weight: 600;
	margin-bottom: 6px;
}

.game-meta {
	font-size: 13px;
	color: #777;
}

.price-info {
	text-align: right;
	margin-left: 20px;
	white-space: nowrap;
}

.original-price {
	text-decoration: line-through;
	color: #bbb;
}

.discount-price {
	font-weight: bold;
	color: #111;
	display: inline-block;
	margin-left: 8px;
}

.discount-rate {
	color: #e50914;
	font-weight: bold;
	margin-left: 8px;
}

.action-buttons {
	display: flex;
	flex-direction: column;
	align-items: flex-end;
	margin-left: 20px;
	gap: 8px;
}

.btn-buy, .btn-delete {
	padding: 6px 14px;
	border-radius: 6px;
	font-size: 13px;
	cursor: pointer;
}

.btn-buy {
	background-color: #222;
	color: white;
	border: none;
}

.btn-delete {
	background: none;
	color: #e50914;
	border: none;
}

.pagination {
	text-align: center;
	margin-top: 30px;
}

.pagination a, .pagination span {
	display: inline-block;
	margin: 0 5px;
	padding: 6px 12px;
	font-size: 14px;
	border-radius: 6px;
	text-decoration: none;
	color: #333;
}

.pagination .current {
	background-color: #e50914;
	color: white;
}
</style>
</head>
<body>
<!-- 헤더 영역 -->
<jsp:include page="/WEB-INF/views/common/header.jsp" flush="true"/>

<div class="wrapper">
	<div class="container">
		<div class="title">위시 리스트</div>
		<form class="search-bar" method="get" action="/wishlist">
			<input type="text" name="keyword" placeholder="게임 제목 검색"
				value="${keyword}"> <select name="order">
				<option value="title" ${order == 'title' ? 'selected' : ''}>이름순</option>
				<option value="price" ${order == 'price' ? 'selected' : ''}>가격순</option>
			</select>
			<button type="submit">검색</button>
		</form>

		<c:forEach var="game" items="${wishlist}">
			<div class="wishlist-item">
				<div class="game-thumb">
					<img src="${game.giThumb}" alt="${game.giTitle}">
				</div>
				<div class="game-info">
					<div class="game-title">${game.giTitle}</div>
					<div class="game-meta">플랫폼: ${game.giPlatform}</div>
				</div>
				<div class="price-info">
					<span class="original-price"> ₩<fmt:formatNumber
							value="${game.giPrice}" type="number" groupingUsed="true"
							maxFractionDigits="0" />
					</span> <span class="discount-price"> ₩<fmt:formatNumber
							value="${game.giFprice}" type="number" groupingUsed="true"
							maxFractionDigits="0" />
					</span> <span class="discount-rate">${game.giRate}%</span>
				</div>
				<div class="action-buttons">
					<form action="${game.giLink}" method="get" target="_blank">
						<button type="submit" class="btn-buy">구매</button>
					</form>
					<button class="btn-delete del-btn" data-g-num="${game.GNum}">삭제</button>
				</div>
			</div>
		</c:forEach>

		<div class="pagination">
			<c:forEach var="i" begin="1" end="${totalPages}">
				<c:choose>
					<c:when test="${i == currentPage}">
						<span class="current">${i}</span>
					</c:when>
					<c:otherwise>
						<a href="/wishlist?page=${i}&order=${order}&keyword=${keyword}">${i}</a>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</div>
	</div>

	<script>
  document.querySelectorAll(".del-btn").forEach(btn => {
    btn.addEventListener("click", function () {
      if (!confirm("정말 삭제하시겠습니까?")) return;
      const gNum = this.dataset.gNum;
      const card = this.closest(".wishlist-item");

      fetch("/wishlist/delete", {
        method: "delete",
        credentials: "same-origin",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: "gNum=" + gNum
      })
      .then(res => res.text())
      .then(msg => {
        if (msg === "삭제 성공") {
          card.remove();
          alert("삭제되었습니다.");
        } else {
          alert("로그인 필요");
        }
      })
      .catch(() => alert("삭제 중 오류가 발생했습니다."));
    });
  });
</script>

</div>
	<!-- 프로필 아이콘 드롭박스 -->
	<script src="<c:url value='/js/proFile.js'/>"></script>
<!-- 푸터 영역 -->
<jsp:include page="/WEB-INF/views/common/footer.jsp" flush="true"/>
</body>
</html>
