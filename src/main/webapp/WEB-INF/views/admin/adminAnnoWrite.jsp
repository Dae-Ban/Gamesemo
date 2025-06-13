<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>공지사항 글작성</title>
<link rel="stylesheet" href="<c:url value='/css/adminMember.css' />" />

 <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="<c:url value='/js/adminAnnoCheck.js' />"></script>
</head>
<body>
		<h2 style="text-align: center;">글 작성</h2>

		<div class="form-container">
		<form method="post" action="<c:url value='/admin/adminAnno/write' />" onsubmit="return admin_check();">
		<table class="form-table">
			<tr>
				<th>제목</th>
				<td><input type="text" name="anTitle" id="anTitle"  class="form-input"></td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea name="anContent" rows="6"  id="anContent"  class="form-textarea"></textarea></td>
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
		<input type="submit" value="등록"  class="btn"/>
		<input type="reset" value="취소" class="btn" />
		
		<input type="button" value="목록"  class="btn"
			onclick="location.href='<c:url value='/admin/adminAnno' />'" />
		</div>
	</form>
	</div>
	
</body>
</html>