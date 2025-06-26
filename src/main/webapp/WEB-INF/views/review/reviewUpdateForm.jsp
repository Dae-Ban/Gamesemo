<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    response.setHeader("X-Frame-Options", "SAMEORIGIN");
%> 
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>리뷰 수정</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script src="${pageContext.request.contextPath}/smarteditor2/js/HuskyEZCreator.js"></script>
</head>
<body>

<div class="review-form" style="max-width: 1000px; margin: 30px auto;">
    <h2>✏ 리뷰 수정</h2>

    <form id="reviewForm" action="${pageContext.request.contextPath}/review/update" method="post">
        <input type="hidden" name="rb_num" value="${review.rb_num}" />

        <!-- 제목 + 추천 -->
        <div style="margin-bottom: 10px;">
            제목:
            <input type="text" name="rb_title" value="${review.rb_title}" required />
            <select name="rb_like" required>
                <option value="">--선택--</option>
                <option value="추천" ${review.rb_like == '추천' ? 'selected' : ''}>👍 추천</option>
                <option value="비추천" ${review.rb_like == '비추천' ? 'selected' : ''}>👎 비추천</option>
            </select>
        </div>

        <!-- 내용 -->
        <textarea name="rb_content" id="rb_content" rows="10" cols="100" style="width: 100%;">
            <c:out value="${review.rb_content}" escapeXml="false"/>
        </textarea>

        <div style="text-align: right; margin-top: 10px;">
            <button type="submit">수정 완료</button>
            <button type="button" onclick="location.href='${pageContext.request.contextPath}/review/view?rb_num=${review.rb_num}'">취소</button>
        </div>
    </form>
</div>

<script>
    var oEditors = [];
    nhn.husky.EZCreator.createInIFrame({
        oAppRef: oEditors,
        elPlaceHolder: "rb_content",
        sSkinURI: "${pageContext.request.contextPath}/smarteditor2/SmartEditor2Skin.html",
        fCreator: "createSEditor2"
    });

    document.getElementById("reviewForm").addEventListener("submit", function(e) {
        oEditors.getById["rb_content"].exec("UPDATE_CONTENTS_FIELD", []);
    });
</script>

</body>
</html>
