<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>커뮤니티 게시판</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<div class="community-wrapper">

    <h2 class="community-title">🎮 커뮤니티 게시판</h2>

    <!-- 검색 폼 -->
    <form method="get" action="${pageContext.request.contextPath}/community/list" class="search-form">
        <select name="search">
            <option value="cb_title" <c:if test="${search == 'cb_title'}">selected</c:if>>제목</option>
            <option value="id" <c:if test="${search == 'id'}">selected</c:if>>작성자</option>
            <option value="cb_state_recommend" <c:if test="${search == 'cb_state_recommend'}">selected</c:if>>글추천</option>
        </select>
        <input type="text" name="keyword" value="${keyword}" placeholder="검색어를 입력하세요">
        <button type="submit" class="btn-search">검색</button>
    </form>

    <!-- 게시글 테이블 -->
    <table class="community-table">
        <thead>
            <tr>
                <th>No</th>
                <th>제목</th>
                <th>글쓴이</th>
                <th>작성일</th>
                <th>조회수</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="community" items="${communityList}">
                <tr>
                    <td>${community.cb_num}</td>
<!--                     <td> -->
<%--                         <c:choose> --%>
<%--                             <c:when test="${community.cb_like eq '추천'}">👍 추천</c:when> --%>
<%--                             <c:when test="${community.cb_like eq '비추천'}">👎 비추천</c:when> --%>
<%--                             <c:otherwise>-</c:otherwise> --%>
<%--                         </c:choose> --%>
<!--                     </td> -->
                    <td class="center">
                        <a href="${pageContext.request.contextPath}/community/view?cb_num=${community.cb_num}">
                            ${community.cb_title}
                        </a>
                    </td>
                    <td>${community.id}</td>
                    <td><fmt:formatDate value="${community.cb_date}" pattern="yyyy-MM-dd" /></td>
                    <td>${community.cb_readcount}</td>
                </tr>
            </c:forEach>

            <c:if test="${empty communityList}">
                <tr><td colspan="6" class="center">등록된 리뷰가 없습니다.</td></tr>
            </c:if>
        </tbody>
    </table>

    <!-- 페이징 -->
    <div class="pagination">
        <c:forEach var="i" begin="${pgn.startPage}" end="${pgn.endPage}">
            <c:choose>
                <c:when test="${i == pgn.currentPage}">
                    <span class="page current">[${i}]</span>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/community/list?page=${i}&search=${search}&keyword=${keyword}" class="page">[${i}]</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </div>

    <!-- 글쓰기 버튼 -->
    <div class="write-button">
        <a href="${pageContext.request.contextPath}/community/form" class="btn-write">글작성</a>
    </div>

</div>

</body>
</html>
