<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>공지사항 상세</title>
<link rel="stylesheet" href="<c:url value='/css/adminMember.css' />" />
</head>
<body>
	
		<h2 style="text-align: center">공지사항 상세</h2>
		<table class="form-table">
			<tr>
				<th>제목</th>
				<td >${anno.anTitle}</td>
			</tr>
			<tr>
				<th>내용</th>
				<td><pre>${anno.anContent}</pre></td>
			</tr>
			<tr>
				<th>작성일</th>
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

		<div class="form-buttons">
		<input type="button" value="수정" class="btn"
			onclick="location.href='<c:url value='/admin/adminAnno/update/${anno.anNum}' />'">
		<input type="button" value="삭제" class="btn"
			onclick="if(confirm('정말 삭제하시겠습니까?')) location.href='<c:url value='/admin/adminAnno/delete/${anno.anNum}' />'">
		<input type="button" value="목록" class="btn"
			onclick="location.href='<c:url value='/admin/adminAnno?pageNum=1' />'">
		</div>
</body>
</html>