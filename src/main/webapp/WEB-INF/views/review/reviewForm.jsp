<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    response.setHeader("X-Frame-Options", "SAMEORIGIN");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>리뷰게시판 작성</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script>
        document.domain = 'localhost';
    </script>
    <script src="${pageContext.request.contextPath}/smarteditor2/js/HuskyEZCreator.js"></script>
</head>
<body>

<c:if test="${empty sessionScope.loginMember}">
    <script>
        alert("로그인 후 이용 가능합니다.");
        location.href = "${pageContext.request.contextPath}/member/login";
    </script>
</c:if>

<div class="review-form" style="max-width: 1000px; margin: 30px auto; padding: 20px; background: #fff; border-radius: 10px;">
    <h2 class="review-title">🎮 리뷰게시판 작성</h2>

    <form id="reviewForm" action="${pageContext.request.contextPath}/review/insert" method="post" enctype="multipart/form-data">
        <div class="form-row" style="display: flex; align-items: center; gap: 10px; margin-bottom: 15px;">
            <label for="rb_title" style="font-weight: bold; width: 30px;">제목</label>
            <input type="text" name="rb_title" id="rb_title" required maxlength="50"     style="flex: 1; padding: 5px;" autocomplete="off">
            <select name="rb_like" style="width: 100px; padding: 5px;" required>
                <option value="">--선택--</option>
                <option value="추천">👍 추천</option>
                <option value="비추천">👎 비추천</option>
            </select>
        </div>

        <div class="form-row">
            <textarea name="rb_content" id="rb_content" rows="10" cols="100" style="width: 100%;"></textarea>
        </div>

        <div class="form-buttons" style="text-align: right; margin-top: 20px;">
            <button type="submit" class="btn-submit">글작성</button>
            <button type="button" onclick="location.href='${pageContext.request.contextPath}/review/list'" class="btn-cancel">취소</button>
        </div>
    </form>
</div>

<script>
    var oEditors = [];
    let submitted = false;

    nhn.husky.EZCreator.createInIFrame({
        oAppRef: oEditors,
        elPlaceHolder: "rb_content",
        sSkinURI: "${pageContext.request.contextPath}/smarteditor2/SmartEditor2Skin.html",
        fCreator: "createSEditor2",
        fOnAppLoad: function () {
            console.log("에디터 로드 완료");
        },
        htParams : {
            sUploadURL : "${pageContext.request.contextPath}/smarteditorMultiImageUpload"
        }
    });

    document.getElementById("reviewForm").addEventListener("submit", function (e) {
        e.preventDefault();

        if (submitted) {
            alert("이미 전송 중입니다.");
            return false;
        }

        if (oEditors.getById["rb_content"]) {
            oEditors.getById["rb_content"].exec("UPDATE_CONTENTS_FIELD", []);

            setTimeout(() => {
                const form = document.getElementById("reviewForm");
                const title = document.getElementById("rb_title").value.trim();
                const content = document.getElementById("rb_content").value.trim();

                if (!title) {
                    alert("제목을 입력하세요.");
                    return;
                }

                if (!content) {
                    alert("내용을 입력하세요.");
                    return;
                }

                submitted = true;
                form.querySelector(".btn-submit").disabled = true;

                console.log("🎯 form submit 실행됨");
                form.submit();
            }, 100);
        } else {
            alert("에디터가 아직 로드되지 않았습니다.");
        }
    });
</script>


</body>
</html>
