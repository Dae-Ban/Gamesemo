<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>리뷰게시판 작성</title>

    <!-- CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<div class="review-form" style="max-width: 1000px; margin: 30px auto; padding: 20px; background: #fff; border-radius: 10px;">
    <h2 class="review-title">🎮 리뷰게시판 작성</h2>

    <form action="insert" method="post" enctype="multipart/form-data" onsubmit="return submitContents();">
        <!-- 제목 + 추천 선택 -->
        <div class="form-row" style="display: flex; align-items: center; gap: 10px; margin-bottom: 15px;">
            <label for="rb_title" style="font-weight: bold; width: 30px; margin-right: 5px;">제목</label>
            <input type="text" name="rb_title" id="rb_title" required style="flex: 1 1 0; min-width: 0; padding: 5px;">
            <select name="rb_like" style="width: 100px; padding: 5px;" required>
                <option value="">--선택--</option>
                <option value="추천">👍 추천</option>
                <option value="비추천">👎 비추천</option>
            </select>
        </div>

        <!-- 스마트에디터 -->
        <div class="form-row">
            <textarea name="rb_content" id="smarteditor" rows="10" cols="100" style="width: 100%;"></textarea>
        </div>

        <!-- 스마트에디터 JS -->
        <script src="${pageContext.request.contextPath}/smarteditor2/js/HuskyEZCreator.js"></script>
        <script>
            var oEditors = [];

            nhn.husky.EZCreator.createInIFrame({
                oAppRef: oEditors,
                elPlaceHolder: "smarteditor",
                sSkinURI: "${pageContext.request.contextPath}/smarteditor2/SmartEditor2Skin.html",
                fCreator: "createSEditor2"
            });

            function submitContents() {
                oEditors.getById["smarteditor"].exec("UPDATE_CONTENTS_FIELD", []);
                return true;
            }
        </script>

        <!-- 버튼 -->
        <div class="form-buttons" style="text-align: right; margin-top: 20px;">
            <button type="submit" class="btn-submit">글작성</button>
            <button type="button" onclick="location.href='list'" class="btn-cancel">취소</button>
        </div>
    </form>
</div>

</body>
</html>