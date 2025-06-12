<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>공지사항 관리</title>

  <!-- CSS -->
  <link rel="stylesheet" href="<c:url value='/css/adminMember.css' />" />

  <!-- jQuery -->
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>

  <h1>공지사항 관리</h1>
  
     <div class="menu-container">
    <div class="menu-box">
      <a href="/admin/adminHome">홈</a>
    </div>
    <div class="menu-box">
      <a href="/admin/adminReport">신고글관리</a>
    </div>
    <div class="menu-box">
      <a href="/admin/adminMember">회원관리</a>
    </div>
     <div class="menu-box">
    <a href="/admin/logout">로그아웃</a>
    </div>
  </div>

  <!-- 검색 영역 -->
  <form method="get" action="<c:url value='/admin/adminAnno' />" class="search-form">
    <select name="type">
      <option value="title" ${param.type == 'title' ? 'selected' : ''}>제목</option>
      <option value="content" ${param.type == 'content' ? 'selected' : ''}>내용</option>
      <option value="state" ${param.type == 'state' ? 'selected' : ''}>상태</option>
    </select>
    <input type="text" name="keyword" value="${param.keyword}" placeholder="검색어 입력" />
    <button type="submit">검색</button>
  </form>

  <!-- 글작성 버튼 -->
  <div style="text-align: right; margin-bottom: 10px;">
    <a href="<c:url value='/admin/adminAnno/write' />">
      <button>글 작성</button>
    </a>
  </div>

  <!-- 목록 테이블 -->
  <table class="member-table">
    <thead>
      <tr>
        <th>번호</th>
        <th>제목</th>
        <th>작성일</th>
        <th>상태</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="anno" items="${annoList}">
        <tr>
          <td>${anno.anNum}</td>
          <td>
          <a href="<c:url value='/admin/adminAnno/${anno.anNum}' />">${anno.anTitle}</a>
          </td>
          <td>${anno.anDate}</td>
          <td>
            <c:choose>
              <c:when test="${anno.anState == 1}">공개</c:when>
              <c:otherwise>비공개</c:otherwise>
            </c:choose>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>

  <!-- 페이징 영역 -->
<div class="paging">
  <c:if test="${total > 0}">
    <a href="?pageNum=1&type=${type}&keyword=${keyword}">&lt;</a>

    <c:if test="${startPage > 1}">
      <a href="?pageNum=${startPage - 1}&type=${type}&keyword=${keyword}">[이전]</a>
    </c:if>

    <c:forEach var="i" begin="${startPage}" end="${endPage}">
      <c:choose>
        <c:when test="${i == currentPage}">
          <a class="active">${i}</a>
        </c:when>
        <c:otherwise>
          <a href="?pageNum=${i}&type=${type}&keyword=${keyword}">${i}</a>
        </c:otherwise>
      </c:choose>
    </c:forEach>

    <c:if test="${endPage < pageCount}">
      <a href="?pageNum=${endPage + 1}&type=${type}&keyword=${keyword}">[다음]</a>
    </c:if>

    <a href="?pageNum=${pageCount}&type=${type}&keyword=${keyword}">&gt;</a>
  </c:if>
</div>

</body>
</html>