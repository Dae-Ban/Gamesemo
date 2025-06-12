<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>공지사항 글수정</title>
<link rel="stylesheet" href="<c:url value='/css/admin.css' />" />
</head>
<body>
	<div style="text-align: center">
		<h2>글 수정</h2>

		<form method="post" action="<c:url value='/admin/adminAnno/update' />" >
		<input type="hidden" name="anNum" value="${anno.anNum}" />
		<table border=1 align="center" width="500">
			<tr>
				<th>제목</th>
				<td><input type="text" name="anTitle" value="${anno.anTitle }"></td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea name="anContent" rows="6" span="5">${anno.anContent}</textarea></td>
			</tr>
			<tr>
				<th>상태</th>
				<td><select name="anState" required style="padding: 5px;">
						<option value="1">공개</option>
						<option value="0">비공개</option>
				</select></td>
			</tr>
		</table>
		<br>
		<input type="submit" value="수정" />
		<input type="reset" value="취소" />
		
		<input type="button" value="목록"
			onclick="location.href='<c:url value='/admin/adminAnno' />'" />

	</form>
	</div>
	
	
</body>
</html>