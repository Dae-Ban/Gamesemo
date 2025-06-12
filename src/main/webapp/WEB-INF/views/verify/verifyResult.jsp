<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원인증 결과</title>
</head>
<body>
	<c:choose>
		<c:when test="${result == 1}">
			<script>
				alert("회원인증 성공!");
				location.href = "/member/login";
				
			</script>
		</c:when>
		<c:otherwise>
			<script>
				alert("회원인증 실패 :(");
				location.href = "/member/login";
			</script>
		</c:otherwise>
	</c:choose>

</body>
</html>