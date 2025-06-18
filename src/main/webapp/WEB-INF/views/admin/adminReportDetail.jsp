<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
  <title>신고 상세</title>
  <link rel="stylesheet" href="<c:url value='/css/adminMember.css' />">
</head>
<body>

<h2 style="text-align: center;">신고 상세</h2>
<div class="form-container">
<table class="form-table">
  <tr><th>신고자</th><td>${report.id}</td></tr>
  <tr><th>신고 사유</th><td>${report.rpReason}</td></tr>
  <tr><th>게시판</th><td>${report.rpTable}</td></tr>
  <tr><th>신고일</th><td>${report.rpDate}</td></tr>
  
 <tr><th>상태</th>  
<td>
  <c:choose>
    <c:when test="${report.rpStatus == 'PENDING'}">
      <span class="badge badge-pending">신고 접수</span>
    </c:when>
    <c:when test="${report.rpStatus == 'RESOLVED'}">
      <span class="badge badge-resolved">처리 완료</span>
    </c:when>
    <c:otherwise>
      <span class="badge badge-unknown">알 수 없음</span>
    </c:otherwise>
  </c:choose>
</td></tr>
</table>
</div>

<h3 style="text-align: center;">신고된 게시글 내용</h3>
<div class="form-container">
<table class="form-table">
<tr>
  <th>제목</th>
  <td>
    <c:choose>
  <c:when test="${fn:toLowerCase(report.rpTable) == 'community_board'}">
    <c:out value="${post.cb_title}" />
  </c:when>
  <c:when test="${fn:toLowerCase(report.rpTable) == 'review_board'}">
    <c:out value="${post.rb_title}" />
  </c:when>
  <c:otherwise>
    알 수 없음
  </c:otherwise>
</c:choose>
  </td>
</tr>

<tr>
  <th>작성자</th>
  <td>${post.id}</td>
</tr>

<tr>
  <th>내용</th>
  <td class="smart-editor-content">
<c:choose>
  <c:when test="${fn:toLowerCase(report.rpTable) == 'community_board'}">
    <c:out value="${post.cb_content}" escapeXml="false" />
  </c:when>
  <c:when test="${fn:toLowerCase(report.rpTable) == 'review_board'}">
    <c:out value="${post.rb_content}" escapeXml="false" />
  </c:when>
  <c:otherwise>
    알 수 없음
  </c:otherwise>
</c:choose>
  </td>
</tr>
</table>
</div>

<br>
<!-- 처리 버튼 -->
<c:if test="${report.rpStatus == 'PENDING'}">
  <form method="post" action="/admin/processReport">
    <input type="hidden" name="rpNum" value="${report.rpNum}" />
    <input type="hidden" name="rpTable" value="${report.rpTable}" />
    <input type="hidden" name="boardNum" value="${report.boardNum}" />
    <input type="submit" value="신고 처리" class="btn" />
  </form>
</c:if>

<br/>

<div style="text-align: center;">
<a href="/admin/adminReport">← 목록으로 돌아가기</a>
</div>

<script>
  window.addEventListener('DOMContentLoaded', () => {
    const contextPath = '${pageContext.request.contextPath}';

    document.querySelectorAll('.smart-editor-content img').forEach(img => {
      // 현재 img.src가 절대경로라면 → contextPath를 앞에 붙여줌
      if (img.getAttribute('src') && img.getAttribute('src').startsWith('/upload/')) {
        img.setAttribute('src', contextPath + img.getAttribute('src'));
      }
    });
  });
</script>

</body>
</html>