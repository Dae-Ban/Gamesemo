<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>회원 관리</title>

  <!-- CSS 적용 -->
  <link rel="stylesheet" href="<c:url value='/css/adminMember.css' />" />

  <!-- jQuery -->
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

  <!-- JS 분리 파일 -->
  <script src="<c:url value='/js/adminMember.js' />"></script>
</head>
<body>

  <h1>회원 관리</h1>
  
  <!-- 검색 영역 -->
<form method="get" action="<c:url value='/admin/adminMember' />" class="search-form">
  <select name="type">
    <option value="all" ${param.type == 'all' ? 'selected' : ''}>전체</option>
    <option value="name" ${param.type == 'name' ? 'selected' : ''}>회원명</option>
    <option value="email" ${param.type == 'email' ? 'selected' : ''}>이메일</option>
    <option value="state" ${param.type == 'state' ? 'selected' : ''}>상태</option>
  </select>

  <input type="text" name="keyword" value="${param.keyword}" placeholder="검색어 입력" />
  <button type="submit">검색</button>
</form>

  <table class="member-table">
    <thead>
      <tr>
        <th>ID</th>
        <th>이름</th>
        <th>이메일</th>
        <th>상태</th>
        <th>블랙리스트</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="member" items="${memberList}">
        <tr>
          <td>${member.id}</td>
          <td>${member.name}</td>
          <td>${member.emailId}@${member.emailDomain}</td>
          <td class="state-${member.state}">
            <c:choose>
              <c:when test="${member.state == 0}">일반</c:when>
              <c:when test="${member.state == 1}">탈퇴</c:when>
              <c:when test="${member.state == 2}">블랙리스트</c:when>
            </c:choose>
          </td>
          <td>
            <c:if test="${member.state != 1}">
              <c:choose>
                <c:when test="${member.state == 2}">
                  <button class="blacklist-btn remove" data-id="${member.id}" data-state="2">해제</button>
                </c:when>
                <c:otherwise>
                  <button class="blacklist-btn add" data-id="${member.id}" data-state="0">등록</button>
                </c:otherwise>
              </c:choose>
            </c:if>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>

<!-- 📄 페이징 -->
<div class="paging">
  <c:forEach var="i" begin="1" end="${total / 10 + (total % 10 > 0 ? 1 : 0)}">
    <a href="<c:url value='/admin/adminMember?pageNum=${i}&type=${type}&keyword=${keyword}' />"
       class="${pageNum == i ? 'active' : ''}">${i}</a>
  </c:forEach>
</div>


</body>
</html>