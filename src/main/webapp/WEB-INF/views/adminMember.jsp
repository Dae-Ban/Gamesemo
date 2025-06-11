<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 관리</title>
<link rel="stylesheet" href="<c:url value='/css/adminHome.css' />">
</head>
<body>

	 <div align="right">
	 <a href="/admin/adminHome">홈</a>
	 </div><br>
     
   
    
    <form action="list.do" align="right">
			<input type="hidden" name="pageNum" value="1"> 
			<select	name="search">
				<option value="">회원명</option>
				<option value="">이메일</option>
				<option value="">상태</option>
			</select> 
			<input type="text" name="keyword"> 
			<input type="submit" value="검색">
		</form>

	<table border=1 align="center" width="800">
	<caption><h2>회원 관리</h2></caption>
	<tr>
		<th>회원명</th>
		<th>이메일</th>
		<th>상태</th>
		<th>블랙리스트</th>
	</tr>
	
	</table>
</body>
</html>