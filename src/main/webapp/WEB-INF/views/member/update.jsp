<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>회원 정보 수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jiseon.css">

</head>
<body>
    <div class="container">
        <h2>회원 정보 수정</h2>
        <form action="/member/{id}/update" method="post">
            <label>아이디</label>
            <input type="text" name="id" required readonly>

            <label>닉네임</label>
            <input type="text" name="nickname" required>

            <label>이메일</label>
            <input type="email" name="email" required>

            <label>생년월일</label>
            <input type="date" name="birthdate" required>

            <label>성별</label>
            <div class="checkbox-group">
                <input type="radio" name="gender" value="male" required> 남자
                <input type="radio" name="gender" value="female" required> 여자
            </div>

            <label>휴대폰 번호</label>
            <input type="tel" name="phone" required>

            <input type="checkbox" name="agreeUpdate" required>
            <label>정보 수정 동의 (필수)</label>

            <button type="submit" class="btn">수정하기</button>
        </form>
    </div>
</body>
</html>
