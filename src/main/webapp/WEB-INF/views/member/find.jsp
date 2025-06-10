<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>아이디 / 비밀번호 찾기</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jiseon.css">
</head>
<body>
  <div class="container">
    <h2 class="title">아이디 / 비밀번호 찾기</h2>
    <div class="divider"></div>

    <!-- 탭 메뉴 -->
    <div class="tab-menu">
      <button id="tabIdBtn" class="tab-button active" onclick="showTab('id')">아이디 찾기</button>
      <button id="tabPwBtn" class="tab-button" onclick="showTab('pw')">비밀번호 찾기</button>
    </div>

    <!-- 아이디 찾기 탭 -->
    <div id="tabId" class="tab-content active">
      <div class="form-group input-group">
        <input type="email" id="findIdEmail" placeholder="example@gmail.com">
        <button class="btn" onclick="alert('123456 인증번호 전송')">인증번호 전송</button>
      </div>
      <div class="form-group">
        <input type="text" id="findIdCode" placeholder="인증번호 입력">
      </div>
      <button class="btn" onclick="findId()">확인</button>
      <p id="idResult"></p>
    </div>

    <!-- 비밀번호 찾기 탭 -->
    <div id="tabPw" class="tab-content">
      <div class="form-group input-group">
        <input type="email" id="findPwEmail" placeholder="example@gmail.com">
        <button class="btn-send" onclick="alert('654321 인증번호 전송')">인증번호 전송</button>
      </div>
      <div class="form-group">
        <input type="text" id="findPwCode" placeholder="인증번호 입력">
      </div>
      <button class="btn" onclick="verifyCode()">확인</button>
      <p id="pwResult"></p>
    </div>
  </div>

  <script>
    function showTab(tab) {
      document.getElementById("tabId").classList.remove("active");
      document.getElementById("tabPw").classList.remove("active");
      document.getElementById("tabIdBtn").classList.remove("active");
      document.getElementById("tabPwBtn").classList.remove("active");

      if (tab === 'id') {
        document.getElementById("tabId").classList.add("active");
        document.getElementById("tabIdBtn").classList.add("active");
      } else {
        document.getElementById("tabPw").classList.add("active");
        document.getElementById("tabPwBtn").classList.add("active");
      }
    }

    function findId() {
      const email = document.getElementById("findIdEmail").value;
      const code = document.getElementById("findIdCode").value;
      if (email && code === "123456") {
        document.getElementById("idResult").innerText = "아이디: user123";
      } else {
        document.getElementById("idResult").innerText = "인증번호가 잘못되었습니다.";
      }
    }

    function verifyCode() {
      const code = document.getElementById("findPwCode").value;
      if (code === "654321") {
        document.getElementById("pwResult").innerText = "비밀번호 재설정 페이지로 이동합니다.";
      } else {
        document.getElementById("pwResult").innerText = "인증번호가 잘못되었습니다.";
      }
    }
  </script>
</body>
</html>
