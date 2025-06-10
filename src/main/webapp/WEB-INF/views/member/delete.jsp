<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>회원 탈퇴</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jiseon.css">
</head>
<body>
    <div class="container">
        <h2 class="title">회원 탈퇴</h2>
        <div class="divider"></div>

        <form action="/member/delete" method="post" onsubmit="return confirmDelete();">
            <div class="form-group">
                <label>ID</label>
                <input type="text" name="id" value="${sessionScope.loginUser.id}" readonly>
            </div>

            <div class="form-group">
                <label>비밀번호</label>
                <input type="password" name="pw" required>
            </div>

            <div class="form-group">
                <label>비밀번호 확인</label>
                <input type="password" name="pwConfirm" required>
            </div>

            <button type="submit" class="btn">탈퇴하기</button>
        </form>
    </div>

    <script>
        function confirmDelete() {
            const pw = document.getElementsByName("pw")[0].value;
            const pwConfirm = document.getElementsByName("pwConfirm")[0].value;

            if (pw.length < 6) {
                alert("비밀번호는 최소 6자 이상이어야 합니다.");
                return false;
            }
            if (pw === pwConfirm) {
                return confirm("비밀번호가 일치합니다. 정말 탈퇴하시겠습니까?");
            } else {
                alert("비밀번호가 일치하지 않습니다. 다시 확인해주세요.");
                return false;
            }
        }
    </script>
</body>
</html>