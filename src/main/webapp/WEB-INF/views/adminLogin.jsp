<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 로그인</title>

<link rel="stylesheet" href="<c:url value='/css/adminlogin.css' />" />
</head>
<body>
  <div class="login-container">
    <h2>관리자 로그인</h2>
    <form action="<c:url value='/admin/login' />" method="post">
      <div class="form-group">
        <label for="adminId">아이디</label>
        <input type="text" name="adminId" id="adminId" placeholder="ID" />
      </div>
      <div class="form-group">
        <label for="adminPw">비밀번호</label>
        <input type="password" name="adminPw" id="adminPw" placeholder="PASSWORD" />
      </div>
      <button type="submit" class="login-btn">Log In</button>
    </form>
  </div>
</body>
</html>