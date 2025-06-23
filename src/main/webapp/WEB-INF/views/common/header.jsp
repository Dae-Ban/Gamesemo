<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>겜세모 - 메인</title>
  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <!-- jQuery -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
  <!-- Bootstrap JS -->
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <!-- Custom CSS -->
  <link rel="stylesheet" href="<c:url value='/css/main.css' />" />
</head>
<body>

<header>
  <!-- 왼쪽: 로고 -->
  <div class="logo">
    <img src="<c:url value='/images/logo1.png' />" alt="겜세모 로고" />
  </div>

  <!-- 가운데: 메뉴 -->
  <nav class="simple-nav">
    <a href="<c:url value='/main' />">홈</a>
    <a href="<c:url value='/game' />">게임 목록</a>

    <div class="dropdown">
      <a href="#" class="dropdown-toggle">커뮤니티</a>
      <div class="dropdown-menu">
        <a href="<c:url value='/community/list' />">자유게시판</a>
        <a href="<c:url value='/review/list' />">리뷰게시판</a>
      </div>
    </div>

    <a href="<c:url value='announcement/announcementList' />">공지사항</a>
  </nav>

  <!-- 오른쪽: 검색 + 로그인 + 프로필 묶음 -->
  <div class="search-login">
    <div class="search-box">
      <form action="<c:url value='/game/search' />" method="get" style="display: flex; gap: 5px;">
        <input type="text" name="keyword" placeholder="게임 이름을 입력하세요" required />
        <button type="submit" class="search-btn">
          <img src="<c:url value='/images/icons/search-icon.png'/>" alt="검색" />
        </button>
      </form>
    </div>

      <!-- ✅ 로그인 안 된 경우: 로그인 버튼 -->
  <c:if test="${empty sessionScope.id}">
    <a href="/member/login" class="login-btn">로그인</a>
  </c:if>

  <!-- ✅ 로그인 된 경우: 로그아웃 버튼 -->
  <c:if test="${not empty sessionScope.id}">
    <a href="<c:url value='/member/logout' />" class="login-btn">로그아웃</a>
  </c:if>

  <!-- 마이페이지 드롭다운은 로그인된 경우에만 보여주고 싶다면 여기도 감싸기 -->
  <c:if test="${not empty sessionScope.id}">
    <div class="profile-dropdown" id="profileDropdown">
      <img src="<c:url value='/images/icons/profile-icon.png'/>" alt="프로필" class="profile-icon" />
      <div class="profile-menu">
        <a href="/member/mypage">마이페이지</a>
        <a href="/wishlist">위시리스트</a>
      </div>
    </div>
  </c:if>
</div>
</header>