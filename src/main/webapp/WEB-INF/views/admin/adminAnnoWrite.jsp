<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>공지사항 상세</title>
<link rel="stylesheet" href="<c:url value='/css/admin.css' />" />
</head>
<body>
	<div style="text-align: center">
		<h2>글 작성</h2>

		<table border=1 align="center" width="500">
			<tr>
				<th>제목</th>
				<td><input type="text" name="anTitle"></td>
			</tr>
			<tr>
				<th>내용</th>
				<td><pre>${anno.anContent}</pre></td>
			</tr>
			<tr>
				<th>상태</th>
				<td>${anno.anDate}</td>
			</tr>
			<tr>
				<th>상태</th>
				<td><c:choose>
						<c:when test="${anno.anState == 1}">공개</c:when>
						<c:otherwise>비공개</c:otherwise>
					</c:choose></td>
			</tr>
		</table>

		<br> <input type="button" value="수정"
			onclick="location.href='<c:url value='/admin/adminAnnoUpdate/${anno.anNum}' />'">
		<input type="button" value="삭제"
			onclick="if(confirm('정말 삭제하시겠습니까?')) location.href='<c:url value='/admin/adminAnnoDelete/${anno.anNum}' />'">
		<input type="button" value="목록"
			onclick="location.href='<c:url value='/admin/adminAnno?pageNum=1' />'">
	</div>
</body>
</html>