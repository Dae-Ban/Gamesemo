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

<div class="community-form" style="max-width: 1000px; margin: 30px auto;">
    <h2>✏ 리뷰 수정</h2>

    <form id="communityForm" action="${pageContext.request.contextPath}/community/update" method="post">
        <input type="hidden" name="cb_num" value="${community.cb_num}" />

        <!-- 제목 + 추천 -->
        <div style="margin-bottom: 10px;">
            제목:
            <input type="text" name="cb_title" value="${community.cb_title}" required />
<!--             <select name="cb_like" required> -->
<!--                 <option value="">--선택--</option> -->
<%--                 <option value="추천" ${community.cb_like == '추천' ? 'selected' : ''}>👍 추천</option> --%>
<%--                 <option value="비추천" ${community.cb_like == '비추천' ? 'selected' : ''}>👎 비추천</option> --%>
<!--             </select> -->
        </div>

        <!-- 내용 -->
        <textarea name="cb_content" id="cb_content" rows="10" cols="100" style="width: 100%;">
            <c:out value="${community.cb_content}" escapeXml="false"/>
        </textarea>

        <div style="text-align: right; margin-top: 10px;">
            <button type="submit">수정 완료</button>
            <button type="button" onclick="location.href='${pageContext.request.contextPath}/community/view?cb_num=${community.cb_num}'">취소</button>
        </div>
    </form>
</div>

<script>
    var oEditors = [];
    nhn.husky.EZCreator.createInIFrame({
        oAppRef: oEditors,
        elPlaceHolder: "cb_content",
        sSkinURI: "${pageContext.request.contextPath}/smarteditor2/SmartEditor2Skin.html",
        fCreator: "createSEditor2"
    });

    document.getElementById("communityForm").addEventListener("submit", function(e) {
        oEditors.getById["cb_content"].exec("UPDATE_CONTENTS_FIELD", []);
    });
</script>

</body>
</html>
