<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 목록</title>
<style>
body {
	font-family: 'Arial', sans-serif;
	background-color: #fff;
	margin: 0;
	padding: 0;
}

h3 {
	text-align: left;
	max-width: 900px;
	margin: 40px auto 10px;
	font-size: 24px;
	border-bottom: 2px solid #444;
	padding-bottom: 5px;
}

h3::after {
	content: "";
	position: absolute;
	left: 0;
	bottom: 0;
	width: 30%;
	height: 2px;
	background-color: #444;
}

.search-form {
	max-width: 900px;
	margin: auto;
	margin-bottom: 20px;
	display: flex;
	justify-content: flex-end;
}

.search-form select, .search-form input[type="text"], .search-form input[type="submit"]
	{
	padding: 5px 10px;
	font-size: 14px;
}

table {
	width: 900px;
	margin: auto;
	border-collapse: collapse;
	margin-bottom: 30px;
}

th, td {
	border: 1px solid #ddd;
	padding: 12px;
	text-align: center;
	font-size: 14px;
}

th {
	background-color: #f5f5f5;
	font-weight: bold;
}

td a {
	text-decoration: none;
	color: #000;
}

td a:hover {
	text-decoration: underline;
}

/* Pagination */
div[style*="text-align: center"] {
	margin-bottom: 40px;
}

div[style*="text-align: center"] a {
	margin: 0 4px;
	text-decoration: none;
	color: #2273e6;
	font-size: 14px;
}

div[style*="text-align: center"] a:hover {
	text-decoration: underline;
}

/* Current page style */
div[style*="text-align: center"] c\\:if {
	font-weight: bold;
	color: black;
}

@charset "UTF-8";
/* 기본 스타일 */
.paging a {
	display: inline-block;
	margin: 0 5px;
	padding: 6px 12px;
	text-decoration: none;
	border: 1px solid #ccc;
	color: #333;
	background-color: #f9f9f9;
	border-radius: 4px;
	transition: all 0.3s;
}

/* 현재 선택된 페이지 */
.paging a.active {
	background-color: #e50914;
	color: #fff;
	border-color: #e50914;
	font-weight: bold;
}

/* Hover 효과 */
.paging a:hover {
	background-color: #b20710;
	color: #fff;
	border-color: #b20710;
}
</style>

</head>
<body>

	<form action="announcement" class="search-form">
		<input type="hidden" name="page" value="1"> <select
			name="search">
			<option value="subject"
				<c:if test="${search=='subject'}">selected="selected" </c:if>>제목</option>
			<option value="content"
				<c:if test="${search=='content'}">selected="selected" </c:if>>내용</option>
			<%--                 <option value="writer"  <c:if test="${search=='writer'}">selected="selected" </c:if>>작성자</option> --%>
			<option value="subcon"
				<c:if test="${search=='subcon'}">selected="selected" </c:if>>제목+내용</option>
		</select> <input type="text" name="keyword"> <input type="submit"
			value="확인">
	</form>
	<table border=1 align="center" width="800" style="text-align: center">
		<caption>
			<h3>공지사항</h3>
		</caption>
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>날짜</th>
		</tr>

		<c:set var="num" value="${listcount-(page-1)*10}" />

		<c:forEach var="b" items="${boardlist}">
			<c:if test="${b.anState == 1}">
				<tr>
					<td>${num} <c:set var="num" value="${num-1}" />
					</td>
					<td><a
						href="announcementContent?no=${b.anNum}&page=${page}&state=${b.anState}">
							${b.anTitle} </a></td>
					<td>관리자</td>
					<td><fmt:formatDate value="${b.anDate}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
				</tr>
			</c:if>
		</c:forEach>

	</table>

	<!-- 페이지 -->
	<div class="paging" style="text-align: center">
		<c:if test="${listcount>0}">

			<!-- 1페이지로 이동 -->
			<a href="announcement?page=1" style="text-decoration: none"> < </a>

			<!-- 이전 블록으로 이동 -->
			<c:if test="${startpage > 10}">
				<a href="announcement?page=${startpage-10}">[이전]</a>
			</c:if>

			<!-- 각 블럭에 10개의 페이지 출력 -->
			<c:forEach var="i" begin="${startpage}" end="${endpage}">
				<c:choose>
					<c:when test="${i == page}">
						<a href="announcement?page=${i}" class="active">${i}</a>
					</c:when>
					<c:otherwise>
						<a href="announcement?page=${i}">${i}</a>
					</c:otherwise>
				</c:choose>
			</c:forEach>

			<!-- 다음 블럭으로 이동 -->
			<c:if test="${endpage < pagecount}">
				<a href="announcement?page=${startpage+10}">[다음]</a>
			</c:if>

			<!-- 마지막 페이지로 이동 -->
			<a href="announcement?page=${pagecount}"
				style="text-decoration: none"> > </a>
		</c:if>
	</div>

</body>
</html>
