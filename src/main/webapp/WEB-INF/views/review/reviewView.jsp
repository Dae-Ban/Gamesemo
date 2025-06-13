<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>리뷰 상세보기</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<div class="review-view-wrapper">

    <!-- 제목 + 추천/비추천 -->
    <div class="review-view-title">
        ${review.rb_title}
        <span class="recommend-icon">
            <c:choose>
                <c:when test="${review.rb_like == '추천'}">👍 추천</c:when>
                <c:when test="${review.rb_like == '비추천'}">👎 비추천</c:when>
                <c:otherwise>-</c:otherwise>
            </c:choose>
        </span>
    </div>

    <!-- 작성자/날짜/조회수 -->
    <div class="review-meta">
        <span>작성자: ${review.id}</span>
        <span>작성일: <fmt:formatDate value="${review.rb_date}" pattern="yyyy-MM-dd" /></span>
        <span>조회수: ${review.rb_readcount}</span>
    </div>

    <!-- 본문 -->
    <div class="review-content">
        <c:out value="${review.rb_content}" escapeXml="false" />
    </div>

    <!-- 신고 버튼 -->
    <form action="${pageContext.request.contextPath}/report/insert" method="post" style="margin-top: 20px;">
        <input type="hidden" name="rb_num" value="${review.rb_num}">
        <button type="submit" class="report-button">🚨 신고하기</button>
    </form>

    <!-- 댓글 영역 -->
    <div class="comment-section">
        <div class="comment-title">💬 댓글</div>

        <!-- 댓글 목록 영역 (AJAX 로드 대상) -->
        <div id="comment-list"></div>

        <!-- 댓글 작성 폼 -->
        <div class="comment-form">
            <textarea id="comment-content" placeholder="댓글을 입력하세요 (최대 200자)" maxlength="200"></textarea>
            <button id="submit-comment">등록</button>
        </div>
    </div>

</div>

<!-- 댓글 AJAX 스크립트는 추후 추가 예정 -->

</body>
</html>