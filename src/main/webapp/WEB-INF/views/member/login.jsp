<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>로그인</title>
    <style>
        .container {
            max-width: 1300px;
            margin: 0 auto;
            padding: 0 20px;
        }
        .login-box {
            width: 100%;
            max-width: 400px;
            margin: 100px auto;
            padding: 30px;
            border: 1px solid #ccc;
            border-radius: 10px;
            background-color: #f9f9f9;
        }
        .login-box h2 {
            text-align: center;
            margin-bottom: 20px;
        }
        .login-box input[type="text"],
        .login-box input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
        }
        .login-box input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: royalblue;
            color: white;
            border: none;
            cursor: pointer;
        }
        .login-box .sns-buttons {
            margin-top: 20px;
            display: flex;
            justify-content: space-between;
        }
        .login-box .sns-buttons img {
            width: 32px;
            height: 32px;
            cursor: pointer;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="login-box">
        <h2>로그인</h2>
        <form action="/login" method="post">
            <input type="text" name="id" placeholder="아이디">
            <input type="password" name="pw" placeholder="비밀번호">
            <label><input type="checkbox" name="rememberMe"> 로그인 상태 유지</label>
            <input type="submit" value="Log In">
        </form>

        <div style="text-align: right;">
            <a href="/member/find">아이디/비밀번호를 잊으셨나요?</a>
        </div>

        <div class="sns-buttons">
            <a href="/oauth2/authorization/google">
            <img src="/icons/google.png" alt="Google"></a>
            <a href="/oauth2/authorization/naver">
            <img src="/icons/naver.png" alt="Naver"></a>
            <a href="/oauth2/authorization/kakao">
            <img src="/icons/kakao.png" alt="Kakao"></a>
        </div>

        <div style="text-align: center; margin-top: 20px;">
            아직 계정이 없다면 <a href="/member/register">회원가입</a>
        </div>
    </div>
</div>

</body>
</html>