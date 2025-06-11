<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>로그인</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f7f7f7;
        }
        .login-container {
            width: 400px;
            margin: 80px auto;
            padding: 40px;
            background: white;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            text-align: center;
        }
        input[type=text], input[type=password] {
            width: 100%;
            padding: 12px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .login-button {
            width: 100%;
            background-color: #007bff;
            color: white;
            border: none;
            padding: 12px;
            border-radius: 5px;
            margin-top: 10px;
        }
        .sns-buttons a {
            display: inline-block;
            margin: 10px;
            padding: 10px 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            text-decoration: none;
            color: black;
        }
        .sns-buttons img {
            width: 20px;
            vertical-align: middle;
            margin-right: 5px;
        }
        .extra-links {
            margin-top: 15px;
            font-size: 13px;
        }
    </style>
</head>
<body>
<div class="login-container">
    <h2>로그인</h2>

    <form method="post" action="/login">
        <input type="text" name="id" placeholder="아이디" value="${rememberedId}" required>
        <input type="password" name="pw" placeholder="비밀번호" required>
        <div style="text-align:left">
            <label><input type="checkbox" name="rememberMe"> 로그인 상태 유지</label>
            <span style="float:right"><a href="/find-account">아이디/비밀번호를 잊으셨나요?</a></span>
        </div>
        <input type="submit" class="login-button" value="Log In">
    </form>

    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>

    <div style="margin-top:30px">sns로 로그인</div>
    <div class="sns-buttons">
        <a href="/oauth2/authorization/google">
            <img src="/icons/google.png"> Google
        </a>
        <a href="/oauth2/authorization/naver">
            <img src="/icons/naver.png"> Naver
        </a>
        <a href="/oauth2/authorization/kakao">
            <img src="/icons/kakao.png"> Kakao
        </a>
    </div>

    <div class="extra-links">
        아직 계정이 없다면 <a href="/member/form">회원가입</a>
    </div>
</div>
</body>
</html>
