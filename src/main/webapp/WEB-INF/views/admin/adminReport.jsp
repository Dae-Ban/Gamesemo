<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>신고글 목록</title>
  <link rel="stylesheet" href="<c:url value='/css/adminMember.css' />">
</head>
<body>

<h1 style="text-align: center;">신고글 목록</h1>

<div class="menu-container">
    <div class="menu-box">
      <a href="/admin/adminHome">홈</a>
    </div>
    <div class="menu-box">
      <a href="/admin/adminAnno">공지사항관리</a>
    </div>
    <div class="menu-box">
      <a href="/admin/adminMember">회원관리</a>
    </div>
     <div class="menu-box">
    <a href="/admin/logout">로그아웃</a>
    </div>
  </div>
  
  <!-- 검색 영역 -->
  <div class="search-container">
  <form method="get" action="<c:url value='/admin/adminReport' />" class="search-form">
    <select name="type">
      <option value="reporter" ${param.type == 'reporter' ? 'selected' : ''}>신고자 ID</option>
      <option value="table" ${param.type == 'table' ? 'selected' : ''}>게시판 종류</option>
      <option value="status" ${param.type == 'status' ? 'selected' : ''}>처리상태</option>
    </select>
    <input type="text" name="keyword" value="${param.keyword}" placeholder="검색어 입력" />
    <button type="submit">검색</button>
  </form>
  </div>
  

<table class="member-table">
    <tr>
      <th>번호</th>
      <th>신고자 ID</th>
      <th>게시판</th>
      <th>제목</th>
      <th>신고 사유</th>
      <th>날짜</th>
      <th>상태</th>
      <th>관리</th>
    </tr>
  <tbody>
    <c:forEach var="r" items="${reportList}">
      <tr>
        <td>${r.rpNum}</td>
        <td>${r.id}</td>
        <td>${r.rpTable}</td>
        <td>
          <a href="/admin/reportDetail?rpNum=${r.rpNum}&rpTable=${r.rpTable}&boardNum=${r.boardNum}">
            ${r.postTitle}
          </a>
        </td>
        <td>${r.rpReason}</td>
        <td>${r.rpDate}</td>
        <td>
  <c:choose>
    <c:when test="${r.rpStatus == 'PENDING'}">
      <span class="badge badge-pending">신고 접수</span>
    </c:when>
    <c:when test="${r.rpStatus == 'RESOLVED'}">
      <span class="badge badge-resolved">처리 완료</span>
    </c:when>
    <c:otherwise>
      <span class="badge badge-unknown">알 수 없음</span>
    </c:otherwise>
  </c:choose>
</td>
       <td>
  <c:choose>
    <c:when test="${r.rpStatus == 'PENDING'}">
      <form method="post" action="/admin/processReport">
        <input type="hidden" name="rpNum" value="${r.rpNum}" />
        <input type="hidden" name="rpTable" value="${r.rpTable}" />
        <input type="hidden" name="boardNum" value="${r.boardNum}" />
        <input type="submit" value="처리" class="btn"/>
      </form>
    </c:when>
    <c:when test="${r.rpStatus == 'RESOLVED'}">
      <form method="post" action="/admin/cancelReport">
        <input type="hidden" name="rpNum" value="${r.rpNum}" />
        <input type="submit" value="해제" class="btn"/>
      </form>
    </c:when>
    <c:otherwise>처리 상태 알 수 없음</c:otherwise>
  </c:choose>
</td>
      </tr>
    </c:forEach>
  </tbody>
</table>

<!-- 페이징 -->
<div class="pagination" style="text-align:center; margin-top: 20px;">
  <c:if test="${p.pageCount > 0}">

    <!-- 처음 페이지로 이동 -->
    <a href="?page=1
      <c:if test='${not empty param.type}'> &type=${param.type}</c:if>
      <c:if test='${not empty param.keyword}'> &keyword=${param.keyword}</c:if>
    ">&lt;</a>

    <!-- 이전 블럭으로 이동 -->
    <c:if test="${p.startPage > 1}">
      <a href="?page=${p.startPage - 1}
        <c:if test='${not empty param.type}'> &type=${param.type}</c:if>
        <c:if test='${not empty param.keyword}'> &keyword=${param.keyword}</c:if>
      ">[이전]</a>
    </c:if>

    <!-- 페이지 번호 출력 -->
    <c:forEach var="i" begin="${p.startPage}" end="${p.endPage}">
      <c:choose>
        <c:when test="${i == p.currentPage}">
          <button class="page-btn current">${i}</button>
        </c:when>
        <c:otherwise>
          <a href="?page=${i}
            <c:if test='${not empty param.type}'> &type=${param.type}</c:if>
            <c:if test='${not empty param.keyword}'> &keyword=${param.keyword}</c:if>
          " class="page-btn">${i}</a>
        </c:otherwise>
      </c:choose>
    </c:forEach>

    <!-- 다음 블럭으로 이동 -->
    <c:if test="${p.endPage < p.pageCount}">
      <a href="?page=${p.endPage + 1}
        <c:if test='${not empty param.type}'> &type=${param.type}</c:if>
        <c:if test='${not empty param.keyword}'> &keyword=${param.keyword}</c:if>
      ">[다음]</a>
    </c:if>

    <!-- 마지막 페이지로 이동 -->
    <a href="?page=${p.pageCount}
      <c:if test='${not empty param.type}'> &type=${param.type}</c:if>
      <c:if test='${not empty param.keyword}'> &keyword=${param.keyword}</c:if>
    ">&gt;</a>

  </c:if>
</div>

</body>
</html>