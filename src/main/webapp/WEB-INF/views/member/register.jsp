<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>회원 가입</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jiseon.css">
</head>
<body>
    <div class="container">
        <h2 class="title">회원 가입</h2>
        <div class="divider"></div>

        <form action="/member/insert" method="post">
            <div class="form-group">
                <label>ID</label>
                <input type="text" name="id" required>
            </div>

            <div class="form-group">
                <label>닉네임</label>
                <input type="text" name="nickname" required>
            </div>

            <div class="form-group">
                <label>비밀번호</label>
                <input type="password" name="pw" required>
            </div>

            <div class="form-group">
                <label>비밀번호 확인</label>
                <input type="password" name="pwConfirm" required>
            </div>

            <div class="form-group input-group">
                <input type="email" name="email" placeholder="이메일" required>
                <button type="button" class="btn"> 이메일 인증 </button>
            </div>

            <div class="form-group">
                <label>생년월일</label>
                <input type="date" name="birth" required>
            </div>

            <div class="form-group checkbox-group">
                <label><input type="radio" name="gender" value="M" required> 남자</label>
                <label><input type="radio" name="gender" value="F" required> 여자</label>
            </div>

            <div class="form-group">
                <label>휴대폰</label>
                <input type="tel" name="phone" required>
            </div>

            <div class="form-group checkbox-group">
                <label><input type="checkbox" name="genre" value="rpg"> RPG</label>
                <label><input type="checkbox" name="genre" value="fps"> FPS</label>
                <label><input type="checkbox" name="genre" value="sports"> 스포츠</label>
                <label><input type="checkbox" name="genre" value="strategy"> 전력</label>
                <label><input type="checkbox" name="genre" value="adventure"> 어드베우처</label>
            </div>

            <div class="form-group checkbox-group">
                <label><input type="checkbox" name="agreeTerms" required> 약관 동의</label>
                <label><input type="checkbox" name="agreeEmail"> 이메일 동의</label>
            </div>

            <button type="submit" class="btn"> 가입하기 </button>
        </form>
    </div>
</body>
</html>