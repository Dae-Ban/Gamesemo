<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>공지사항 글수정</title>
<link rel="stylesheet" href="<c:url value='/css/adminMember.css' />" />

 <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="<c:url value='/js/adminAnnoCheck.js' />"></script>
</head>
<body>
		<h2 style="text-align: center">글 수정</h2>

		<div class="form-container">
		<form method="post" action="<c:url value='/admin/adminAnno/update' />" onsubmit="return admin_check();">
		<input type="hidden" name="anNum" value="${anno.anNum}" />
		<table class="form-table">
			<tr>
				<th>제목</th>
				<td><input type="text" name="anTitle" value="${anno.anTitle }" class="form-input" id="anTitle"></td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea name="anContent" rows="6" class="form-textarea" id="anContent">${anno.anContent}</textarea></td>
			</tr>
			<tr>
				<th>상태</th>
				<td><select name="anState" required class="form-select">
						<option value="1">공개</option>
						<option value="0">비공개</option>
				</select></td>
			</tr>
		</table>
		
		<div class="form-buttons">
		<input type="submit" value="수정" class="btn"/>
		<input type="reset" value="취소" class="btn" />
		
		<input type="button" value="목록" class="btn"
			onclick="location.href='<c:url value='/admin/adminAnno' />'" />
		</div>
	</form>
	</div>
	
</body>
</html>