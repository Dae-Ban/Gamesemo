<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>회원 가입</title>
    <style>
        body { font-family: Arial, sans-serif; }
        .container { width: 400px; margin: 0 auto; padding: 20px; border: 1px solid #ccc; border-radius: 5px; }
        input, select { width: 100%; padding: 8px; margin: 5px 0; }
        .checkbox-group { display: flex; gap: 10px; }
        .btn { background-color: #007bff; color: white; padding: 10px; border: none; cursor: pointer; width: 100%; }
    </style>
</head>
<body>
    <div class="container">
        <h2> 회원 가입</h2>
        <form action="/member/insert" method="post">
            <label>아이디</label>
            <input type="text" name="id" required>

            <label>닉네임</label>
            <input type="text" name="nickname" required>

            <label>비밀번호</label>
            <input type="password" name="password" required>

            <label>비밀번호 확인</label>
            <input type="password" name="passwordConfirm" required>

            <label>이메일</label>
            <input type="email" name="email" required>
            <button type="button">이메일 인증하기</button><br>

            <label>생년월일</label>
            <input type="date" name="birthdate" required>

            <label>성별</label>
            <div class="checkbox-group">
                <input type="radio" name="gender" value="male" required> 남자
                <input type="radio" name="gender" value="female" required> 여자
            </div>

            <label>휴대폰 번호</label>
            <input type="tel" name="phone" required>

          <label>선호 게임 장르 선택</label>
<div class="checkbox-group">
    <label><input type="checkbox" name="gameGenre" value="rpg"> RPG</label>
    <label><input type="checkbox" name="gameGenre" value="fps"> FPS</label>
    <label><input type="checkbox" name="gameGenre" value="sports"> 스포츠</label>
    <label><input type="checkbox" name="gameGenre" value="strategy"> 전략</label>
    <label><input type="checkbox" name="gameGenre" value="adventure"> 어드벤처</label>
</div>

            
            <input type="checkbox" name="agreeTerms" required>
			<label>정보수집 및 약관 동의 (필수)</label>
             <input type="checkbox" name="agreeEmail">
             <label>이메일 발송 동의 (필수)</label>
           

            <button type="submit" class="btn">가입하기</button>
        </form>
    </div>
</body>
</html>
