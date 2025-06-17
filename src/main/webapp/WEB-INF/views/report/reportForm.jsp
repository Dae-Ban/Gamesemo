<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>신고하기</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<!-- ✅ 로그인 안 된 경우 차단 -->
<c:if test="${empty sessionScope.loginMember}">
    <script>
        alert("로그인 후 이용 가능합니다.");
        location.href = "${pageContext.request.contextPath}/member/login";
    </script>
</c:if>

<!-- Flash 메시지(alert) 표시 -->
<c:if test="${not empty msg}">
    <script>
        alert('${msg}');
    </script>
</c:if>

<div style="max-width: 600px; margin: 50px auto; padding: 30px; background: #fff; border-radius: 10px; font-family: 'Noto Sans KR', sans-serif;">
    <h2 style="margin-bottom: 20px;">🚨 게시글 신고</h2>

    <form action="${pageContext.request.contextPath}/review/report/insert" method="post">
        <!-- 히든 필드: 신고 대상 테이블/글번호 -->
        <input type="hidden" name="rpTable" value="${rp_table}" />
        <input type="hidden" name="boardNum" value="${board_num}" />

        <div style="margin-bottom: 15px;">
            <label for="rpReason" style="font-weight: bold;">신고 사유</label><br>
            <textarea name="rpReason" id="rpReason" rows="6" style="width: 100%; padding: 10px;" placeholder="신고 사유를 입력하세요" required></textarea>
        </div>

        <div style="text-align: right;">
            <button type="submit" style="padding: 8px 16px; background-color: crimson; color: white; border: none; border-radius: 5px; cursor: pointer;">신고 접수</button>
            <button type="button" onclick="history.back()" style="padding: 8px 16px; background-color: gray; color: white; border: none; border-radius: 5px; cursor: pointer;">취소</button>
        </div>
    </form>
</div>

</body>
</html>
