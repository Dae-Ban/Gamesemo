<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>비밀번호 변경</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jiseon.css">
</head>
<body>
    <div class="container">
        <h2 class="title">비밀번호 변경</h2>
        <div class="divider"></div>

        <form action="/member/change-password" method="post">
            <div class="form-group">
                <label>ID</label>
                <input type="text" name="id" value="${sessionScope.loginId}" readonly>
            </div>

            <div class="form-group">
                <label>현재 비밀번호</label>
                <input type="password" name="currentPw" required>
            </div>

            <div class="form-group">
                <label>새 비밀번호</label>
                <input type="password" name="newPw" required>
            </div>

            <div class="form-group">
                <label>비밀번호 확인</label>
                <input type="password" name="confirmPw" required>
            </div>

            <button type="submit" class="btn">변경하기</button>
        </form>
    </div>
</body>
</html>
