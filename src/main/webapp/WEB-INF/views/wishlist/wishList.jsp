<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>위시리스트</title>
    <style>
        .wishlist-container { width: 900px; margin: auto; font-family: sans-serif; }
        .wishlist-title { font-size: 28px; font-weight: bold; margin-bottom: 20px; }
        .search-box { margin-bottom: 20px; }
        .game-card { display: flex; align-items: center; padding: 12px 0; border-bottom: 1px solid #ddd; }
        .game-image img { width: 120px; height: 70px; object-fit: cover; margin-right: 20px; }
        .game-details { flex: 1; }
        .title-row { font-size: 16px; font-weight: bold; margin-bottom: 4px; }
        .genre { font-size: 13px; color: #777; margin-left: 10px; }
        .meta-row { font-size: 12px; color: #666; }
        .price-box { text-align: right; margin-right: 20px; font-size: 14px; white-space: nowrap; }
        .price-box .origin { text-decoration: line-through; color: #aaa; }
        .price-box .arrow { margin: 0 5px; }
        .price-box .sale { font-weight: bold; color: #333; }
        .price-box .rate { margin-left: 8px; color: #e74c3c; font-weight: bold; }
        .button-box { display: flex; flex-direction: column; align-items: flex-end; gap: 6px; }
        .buy-btn { background: #3498db; color: white; font-size: 12px; padding: 3px 10px; border: none; border-radius: 4px; cursor: pointer; }
        .delete-btn { background: transparent; border: none; font-size: 12px; color: red; cursor: pointer; }
        .pagination { margin-top: 20px; text-align: center; }
        .pagination a, .pagination span {
            display: inline-block; margin: 0 5px; padding: 5px 10px;
            border-radius: 3px; text-decoration: none; color: #333;
        }
        .pagination .current { background-color: #3498db; color: white; }
    </style>
</head>
<body>
<div class="wishlist-container">
    <div class="wishlist-title">위시 리스트</div>

    <div class="search-box">
        <form method="get" action="/wishlist">
            <input type="text" name="keyword" placeholder="Search" value="${keyword}">
            <select name="order">
                <option value="recent" ${order == 'recent' ? 'selected' : ''}>최신순</option>
                <option value="title" ${order == 'name' ? 'selected' : ''}>이름순</option>
                <option value="price" ${order == 'price' ? 'selected' : ''}>가격순</option>
            </select>
            <button type="submit">검색</button>
        </form>
    </div>

    <c:forEach var="game" items="${wishlist}">
        <div class="game-card">
            <div class="game-image">
                <img src="${game.giThumb}" alt="${game.giTitle}" />
            </div>
            <div class="game-details">
                <div class="title-row">
                    <strong class="title">${game.giTitle}</strong>
                </div>
                <div class="meta-row">
                    플랫폼 : ${game.giPlatform}
                </div>
            </div>
            <div class="price-box">
                <span class="origin">₩${game.giPrice}</span>
                <span class="arrow">→</span>
                <span class="sale">₩${game.giFprice}</span>
                <span class="rate">${game.giRate}%</span>
            </div>
            <div class="button-box">
                <form action="${game.giLink}" method="get" target="_blank">
                    <button type="submit" class="buy-btn">구매</button>
                </form>
			<button class="delete-btn del-btn" data-gi-num="${game.giNum}">삭제</button>
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

      const giNum = this.dataset.giNum;
      const card = this.closest(".game-card");

      fetch("/wishlist/delete", {
        method: "POST",
        credentials: "same-origin",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: "giNum=" + giNum
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
</body>
</html>