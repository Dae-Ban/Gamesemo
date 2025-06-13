<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>아이디 / 비밀번호 찾기</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/jiseon.css">
</head>
<body>
	<div class="container">
		<h2 class="title">아이디 / 비밀번호 찾기</h2>
		<div class="divider"></div>

		<!-- 탭 버튼 -->
		<div class="tab-menu">
			<button id="tabIdBtn" class="tab-button active">아이디 찾기</button>
			<button id="tabPwBtn" class="tab-button">비밀번호 찾기</button>
		</div>

		<!-- 아이디 찾기 탭 -->
		<div id="tabId" class="tab-content active">
			<div class="input-inline">
				<input type="email" id="findIdEmail" placeholder="example@gmail.com">
				<button class="btn-small" onclick="sendIdCode()">인증번호 전송</button>
			</div>
			<div class="input-inline">
				<input type="text" id="findIdCode" placeholder="인증번호 입력">
				<button class="btn-small" onclick="checkIdCode()">확인</button>
			</div>
			<p id="idResult" class="result-text"></p>
		</div>

		<!-- 비밀번호 찾기 탭 -->
		<div id="tabPw" class="tab-content">
			<div class="input-inline">
				<input type="text" id="findPwId" placeholder="아이디 입력">
			</div>
			<div class="input-inline">
				<input type="email" id="findPwEmail" placeholder="example@gmail.com">
				<button class="btn-small" onclick="sendPwCode()">인증번호 전송</button>
			</div>
			<div class="input-inline">
				<input type="text" id="findPwCode" placeholder="인증번호 입력">
				<button class="btn-small" onclick="verifyCode()">확인</button>
			</div>
			<p id="pwResult" class="result-text"></p>
		</div>
	</div>

	<!-- 비밀번호 재설정 영역 (초기엔 숨김) -->
	<div id="resetPwSection" class="tab-content"
		style="display: none; margin-top: 20px;">
		<div class="input-inline">
			<input type="password" id="newPw" placeholder="새 비밀번호 입력">
		</div>
		<div class="input-inline">
			<input type="password" id="confirmPw" placeholder="비밀번호 확인">
		</div>
		<div class="form-group button-row">
			<button class="btn-small" onclick="resetPassword()">비밀번호 변경</button>
		</div>
		<p id="resetResult" class="result-text"></p>
	</div>


	<script>
//비밀번호 탭 안넘어가서 파일 하나로 함. 	
//아이디 / 비밀번호 찾기 탭 + 인증번호 처리 자바스크립트

//인증코드 (가상)
let idAuthCode = "123456";
let pwAuthCode = "654321";

//탭 전환 함수
function showTab(tab) {
	const idTab = document.getElementById("tabId");
	const pwTab = document.getElementById("tabPw");
	const idBtn = document.getElementById("tabIdBtn");
	const pwBtn = document.getElementById("tabPwBtn");

	idTab.classList.remove("active");
	pwTab.classList.remove("active");
	idBtn.classList.remove("active");
	pwBtn.classList.remove("active");

	if (tab === "id") {
		idTab.classList.add("active");
		idBtn.classList.add("active");
		idTab.querySelector("input")?.focus(); // 탭 전환 후 첫 input 자동 포커스
	} else {
		pwTab.classList.add("active");
		pwBtn.classList.add("active");
		pwTab.querySelector("input")?.focus(); // 탭 전환 후 첫 input 자동 포커스
	}
}


//이벤트 바인딩
document.addEventListener("DOMContentLoaded", () => {
	document.getElementById("tabIdBtn")?.addEventListener("click", () => showTab("id"));
	document.getElementById("tabPwBtn")?.addEventListener("click", () => showTab("pw"));
});

//아이디 인증번호
function sendIdCode() {
	const email = document.getElementById("findIdEmail").value.trim();
	if (!email) return alert("이메일을 입력하세요.");
	alert("인증번호가 이메일로 전송되었습니다.");
}

function checkIdCode() {
	const code = document.getElementById("findIdCode").value.trim();
	const result = document.getElementById("idResult");
	if (code === idAuthCode) {
		result.innerText = "회원님의 아이디는 user123입니다.";
		result.style.color = "black";
	} else {
		result.innerText = "인증번호가 일치하지 않습니다.";
		result.style.color = "red";
	}
}

//비밀번호 인증번호

function sendPwCode() {
	const id = document.getElementById("findPwId").value.trim();
	const email = document.getElementById("findPwEmail").value.trim();
	if (!id || !email) return alert("아이디와 이메일을 모두 입력하세요.");
	alert("비밀번호 재설정 인증번호가 이메일로 전송되었습니다.");
}

function verifyCode() {
	const code = document.getElementById("findPwCode").value.trim();
	const result = document.getElementById("pwResult");

	if (code === pwAuthCode) {
		result.innerText = "인증 성공! 비밀번호를 재설정하세요.";
		result.style.color = "green";

		document.getElementById("resetPwSection").style.display = "block"; // 재설정 폼 보여줌
	} else {
		result.innerText = "인증번호가 일치하지 않습니다.";
		result.style.color = "red";
	}
}

// 비밀번호 재설정 버튼 처리
function resetPassword() {
	const userId = document.getElementById("findPwId").value.trim();
	const newPw = document.getElementById("newPw").value.trim();
	const confirmPw = document.getElementById("confirmPw").value.trim();
	const resetResult = document.getElementById("resetResult");

	const regex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*()\-_=+\\|[\]{};:'",.<>/?`~]).{8,}$/;

			
	if (!newPw || !confirmPw) {
		resetResult.innerText = "비밀번호를 모두 입력해주세요.";
		resetResult.style.color = "red";
		return;
	}
	
	if (!regex.test(newPw)) {
				resetResult.innerText = "비밀번호는 8자 이상, 영문+숫자+특수문자를 포함해야 합니다.";
				resetResult.style.color = "gray";
				return;
			}

	if (newPw !== confirmPw) {
		resetResult.innerText = "비밀번호가 일치하지 않습니다.";
		resetResult.style.color = "red";
		return;
	}
	
	
	// ✅ 서버에 비밀번호 변경 요청 (AJAX)
	fetch("/member/resetPassword", {
		method: "POST",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify({
			id: userId,
			newPassword: newPw
		})
	})
		.then(response => response.json())
		.then(data => {
			if (data.success) {
				resetResult.innerText = "✅ 비밀번호가 변경되었습니다. 로그인 페이지로 이동합니다.";
				resetResult.style.color = "green";
				setTimeout(() => {
					location.href = "/member/login";
				}, 2000);
			} else {
				resetResult.innerText = "❌ 비밀번호 변경에 실패했습니다.";
				resetResult.style.color = "red";
			}
		})
		.catch(error => {
			console.error("비밀번호 변경 오류:", error);
			resetResult.innerText = "❌ 서버 오류가 발생했습니다.";
			resetResult.style.color = "red";
		});
}


</script>
	<script src="${pageContext.request.contextPath}/js/find.js"></script>
</body>
</html>
