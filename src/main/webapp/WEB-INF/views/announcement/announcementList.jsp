<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
  <meta charset="UTF-8">
  <title>공지사항</title>
  <link rel="stylesheet" href="/css/announcement.css" />
</head>
<body>
<div class="container">
  <h3>공지사항</h3>

  <form action="announcementList" class="search-form">
    <input type="hidden" name="page" value="1">
    <select name="search">
      <option value="subject" <c:if test="${search=='subject'}">selected</c:if>>제목</option>
      <option value="content" <c:if test="${search=='content'}">selected</c:if>>내용</option>
      <option value="subcon" <c:if test="${search=='subcon'}">selected</c:if>>제목+내용</option>
    </select>
    <input type="text" name="keyword" value="${keyword}">
    <input type="submit" value="검색">
  </form>

  <table>
    <tr>
      <th>번호</th>
      <th>제목</th>
      <th>작성자</th>
      <th>날짜</th>
    </tr>
    <c:set var="num" value="${listcount - (page - 1) * 10}" />
    <c:forEach var="b" items="${boardlist}">
      <c:if test="${b.anState == 1}">
        <tr>
          <td>${num}<c:set var="num" value="${num - 1}" /></td>
          <td><a href="announcementContent?no=${b.anNum}&page=${page}&state=${b.anState}">${b.anTitle}</a></td>
          <td>관리자</td>
          <td><fmt:formatDate value="${b.anDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
        </tr>
      </c:if>
    </c:forEach>
  </table>

  <div class="paging">
    <c:if test="${listcount > 0}">
      <a href="announcementList?page=1">&lt;</a>
      <c:if test="${startpage > 10}">
        <a href="announcementList?page=${startpage - 10}">[이전]</a>
      </c:if>
      <c:forEach var="i" begin="${startpage}" end="${endpage}">
        <c:choose>
          <c:when test="${i == page}">
            <a class="active">${i}</a>
          </c:when>
          <c:otherwise>
            <a href="announcementList?page=${i}">${i}</a>
          </c:otherwise>
        </c:choose>
      </c:forEach>
      <c:if test="${endpage < pagecount}">
        <a href="announcementList?page=${startpage + 10}">[다음]</a>
      </c:if>
      <a href="announcementList?page=${pagecount}">&gt;</a>
    </c:if>
  </div>
</div>
</body>
</html>
