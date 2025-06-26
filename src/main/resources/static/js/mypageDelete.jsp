<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<title>회원탈퇴</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypage.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/member.css">
</head>
<body>

	<%@ include file="/WEB-INF/views/common/header.jsp"%>

	<!-- 에러 메시지 알림 -->
	<c:if test="${not empty error}">
		<script>alert("${error}");</script>
	</c:if>

	<div class="mypage-wrapper">
		<%
			String uri = request.getRequestURI();
		%>
		<!-- 사이드바 -->
		<div class="mypage-sidebar">
			<h1 class="mypage-title">
				<a href="${pageContext.request.contextPath}/member/mypage"
				   style="text-decoration: none; color: inherit;">마이페이지</a>
			</h1>

			<div class="mypage-divider"></div>

			<div class="menu-item <%= uri.contains("mypageProfile") ? "active" : "" %>"
				onclick="location.href='${pageContext.request.contextPath}/member/mypageProfile'">프로필</div>

			<div class="menu-item <%= uri.contains("mypageUpdate") ? "active" : "" %>"
				onclick="location.href='${pageContext.request.contextPath}/member/mypageUpdate'">정보수정</div>

			<div class="menu-item <%= uri.contains("mypagePassword") ? "active" : "" %>"
				onclick="location.href='${pageContext.request.contextPath}/member/mypagePassword'">비밀번호 변경</div>

			<div class="menu-item <%= uri.contains("mypageDelete") ? "active" : "" %>"
				onclick="location.href='${pageContext.request.contextPath}/member/mypageDelete'">회원탈퇴</div>
		</div>

		<!-- 탈퇴 폼 본문 -->
		<div class="mypage-content">
			<c:choose>
				<%-- 일반 회원 --%>
				<c:when test="${empty loginMember.socialPlatform}">
					<form action="/member/delete" method="post" onsubmit="return validateDeleteForm();">
						<div class="form-group">
							<label>ID</label>
							<input type="text" name="id" value="${loginMember.id}" readonly>
						</div>

						<div class="form-group">
							<label>비밀번호</label>
							<input type="password" name="pw" required>
						</div>

						<div class="form-group">
							<label>비밀번호 확인</label>
							<input type="password" name="pwConfirm" oninput="checkDeletePwMatch()">
						</div>

						<p id="pwMatchMsg" class="result-text"></p>

						<div class="form-group button-row">
							<button type="submit" class="btn btn-half">탈퇴하기</button>
							<button type="button" class="btn btn-half" onclick="location.href='/member/mypage'">취소</button>
						</div>
					</form>
				</c:when>

				<%-- 소셜 로그인 회원 --%>
				<c:otherwise>
					<p style="margin-bottom: 20px;">소셜 로그인 사용자는 비밀번호 없이 연동 해제 및 탈퇴가 가능합니다.</p>
					<form action="/member/delete" method="post">
						<input type="hidden" name="social" value="true" />
						<div class="form-group button-row">
							<button type="submit" class="btn btn-half">소셜로그인 연동 해제</button>
							<button type="button" class="btn btn-half" onclick="location.href='/member/mypage'">취소</button>
						</div>
					</form>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

	<!-- JS 연결 -->
	<script src="${pageContext.request.contextPath}/js/member.js"></script>

</body>
</html>
