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
  <div class="logo">
    <img src="<c:url value='/images/logo1.png' />" alt="겜세모 로고" />
    <strong>겜세모</strong>
  </div>

  <nav class="simple-nav">
    <a href="#">홈</a>
    <a href="#">게임 목록</a>

    <!-- 커뮤니티 드롭다운 -->
    <div class="dropdown">
      <a href="#" class="dropdown-toggle">커뮤니티</a>
      <div class="dropdown-menu">
        <a href="#">자유게시판</a>
        <a href="#">리뷰게시판</a>
      </div>
    </div>

    <a href="#">공지사항</a>
  </nav>

  <div class="search-login">
    <div class="search-box">
      <input type="text" placeholder="검색어를 입력하세요" />
      <select>
        <option>전체</option>
        <option>게임</option>
        <option>게시판</option>
      </select>
      <button class="search-btn">
        <img src="<c:url value='/images/icons/search-icon.png'/>" alt="검색" />
      </button>
    </div>

    <button class="login-btn">로그인</button>

    <!-- 마이페이지 드롭다운 -->
    <div class="profile-dropdown" id="profileDropdown">
      <img src="<c:url value='/images/icons/profile-icon.png'/>" alt="프로필" class="profile-icon" />
      <div class="profile-menu">
        <a href="/mypage">마이페이지</a>
        <a href="/wishlist">위시리스트</a>
      </div>
    </div>
  </div>
</header>
