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

<table class="member-table">
    <tr>
      <th>번호</th>
      <th>신고자</th>
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
  <c:if test="${p.startPage > 1}">
    <a href="?page=${p.startPage - 1}">&lt;</a>
  </c:if>

  <c:forEach var="i" begin="${p.startPage}" end="${p.endPage}">
    <c:choose>
      <c:when test="${i == p.currentPage}">
        <button class="page-btn current">${i}</button>
      </c:when>
      <c:otherwise>
        <a href="?page=${i}" class="page-btn">${i}</a>
      </c:otherwise>
    </c:choose>
  </c:forEach>

  <c:if test="${p.endPage < p.pageCount}">
    <a href="?page=${p.endPage + 1}">&gt;</a>
  </c:if>
</div>


</body>
</html>