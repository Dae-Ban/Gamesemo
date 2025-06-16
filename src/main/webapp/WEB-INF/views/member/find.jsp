<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>아이디 / 비밀번호 찾기</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/jiseon.css">
<script src="http://code.jquery.com/jquery-latest.js"></script>
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
		<form id="idCodeForm" action="/verify/testSubmit" method="post">
			<div id="tabId" class="tab-content active">
				<div class="input-inline">
					<input type="email" name="verifyEmail" id="findIdEmail"
						placeholder="example@gmail.com">
					<button class="btn-small" onclick="sendIdCode()">인증번호 전송</button>
				</div>
				<div class="input-inline">
					<input type="text" name="verifyCode" id="findIdCode"
						placeholder="인증번호 입력" required /> <input type="hidden"
						name="verifyType" value="FIND_ID" />
					<!--type값(ACCOUNT_VERIFICATION.TYPE 테이블)controller에 넘겨주는 부분  -->
					<button type="button" class="btn-small" onclick="checkCode()">확인</button>
				</div>
				<p id="idResult" class="result-text"></p>
			</div>
		</form>

		<!-- 비밀번호 찾기 탭 -->
		<form id="pwCodeForm" action="/verify/testSubmit" method="post">
			<div id="tabPw" class="tab-content">
				<div class="input-inline">
					<input type="text" name="verifyId" id="findPwId"
						placeholder="아이디 입력">
				</div>
				<div class="input-inline">
					<input type="email" name="verifyEmail" id="findPwEmail"
						placeholder="example@gmail.com">
					<button class="btn-small" id="sendPwBtn">인증번호 전송</button>
					<div id="emailError" style="color: red; display: none;">존재하지
						않는 이메일 입니다.</div>
					<div id="emailSent" style="color: green; display: none;">인증번호가
						발송되었습니다.</div>
				</div>
				<div class="input-inline">
					<div class="input-inline">
						<input type="text" name="verifyCode" id="findPwCode"
							placeholder="인증번호 입력" required> <input type="hidden"
							name="verifyType" value="FIND_PASSWORD">
						<!--type값(ACCOUNT_VERIFICATION.TYPE 테이블)controller에 넘겨주는 부분  -->
						<button type="button" class="btn-small" id="verifyPwBtn">확인</button>
					</div>
				</div>
				<p id="pwResult" class="result-text"></p>
			</div>
		</form>

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
	const email = $('#findIdEmail').val().trim();
	if (!email) return alert("이메일을 입력하세요.");

	$.ajax({
		url: "/checkEmail",
		type: "POST",
		data: { email: email },
		success: function(result) {
			if (result.exists) {
				// 이메일 존재 → 인증코드 전송
				$.ajax({
					url: "/sendFindIdCode",
					type: "POST",
					data: { email: email },
					success: function() {
						alert("인증번호가 이메일로 전송되었습니다.");
					}
				});
			} else {
				alert("존재하지 않는 이메일입니다.");
			}
		},
		error: function() {
			alert("서버 오류 발생");
		}
	});
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

//인증번호 확인
function checkCode() {
	const inputCode = $('#findIdCode').val().trim();
	const email = $('#findIdEmail').val().trim(); 
	const result = $('#idResult');

	if (!inputCode || !email) {
		result.text("이메일과 인증번호를 입력해주세요.").css("color", "red");
		return;
	}

	$.ajax({
		url: "/findId",  // 경로 수정
		type: "GET",
		data: {
			code: inputCode,
			email: email,
			type: "FIND_ID"
		},
		success: function(res) {
			if (res.success) {
				result.text("회원님의 아이디는 " + res.userId + "입니다.").css("color", "black");
			} else {
				result.text("인증번호가 일치하지 않습니다.").css("color", "red");
			}
		}
	});
}

//비밀번호 인증번호
function sendPwCode() {
	const id = $('#findPwId').val().trim();
	const email = $('#findPwEmail').val().trim();
	if (!id || !email) return alert("아이디와 이메일을 모두 입력하세요.");

	$.ajax({
		url: "/checkEmail",
		type: "POST",
		data: { email: email },
		success: function(result) {
			if (result.exists) {
				// 이메일 존재 → 인증코드 전송
				$.ajax({
					url: "/sendFindPwCode",
					type: "POST",
					data: { email: email },
					success: function() {
						$('#emailError').hide();
						$('#emailSent').show();
					}
				});
			} else {
				$('#emailError').show();
				$('#emailSent').hide();
			}
		},
		error: function() {
			alert("서버 오류 발생");
		}
	});
}

function verifyCode() {
	const code = $('#findPwCode').val().trim();
	const type = 'FIND_PASSWORD'; 
	const resultText = $('#pwResult');

	if (!code) {
		resultText.text("인증번호를 입력해주세요.").css("color", "red");
		return;
	}

	$.ajax({
		url: "/verifyCode",
		type: "GET", 
		data: { code: code, type: type },
		success: function(responseHtml) {
			// 응답 HTML로 전체 페이지가 올 경우, modal이 아니라면 리다이렉트
			document.open();
			document.write(responseHtml);
			document.close();
		},
		error: function() {
			resultText.text("서버 오류가 발생했습니다.").css("color", "red");
		}
	});
}

function checkEmail(){
	const targetEmail = $('#findIdEmail').value();
	
	$.ajax({
		url:"/checkEmail",
		type:"POST",
		data: {email: email},
		sucess: function(result){
			if(result.exists){
				$("#emailError").hide();
				$("#emailSent").show();
				sendVerificationCode(email);
			}else{
				$("#emailError").show();
				$("#emailSent").hide();
			}
		},
		error: function(){
			alert("서버 오류");
		}
	});
	
}

function sendFindIdCode(email){
	$.ajax({
		url:"/sendCode",
		type:"POST",
		data: {email: email},
		success: function(){
			console.log("인증번호 전송 완료");
		}
	});
}
function sendFindPwCode(email){
	$.ajax({
		url:"/sendCode",
		type:"POST",
		data: {email: email},
		success: function(){
			console.log("인증번호 전송 완료");
		}
	});
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
			document.addEventListener("DOMContentLoaded", function() {
			  // 이미 있는 verifyPwBtn 바인딩
			  const pwVerifyBtn = document.getElementById("verifyPwBtn");
			  if (pwVerifyBtn) {
			    pwVerifyBtn.addEventListener("click", verifyCode);
			  }

			  // sendPwBtn 바인딩 추가
			  const sendPwBtn = document.getElementById("sendPwBtn");
			  if (sendPwBtn) {
			    sendPwBtn.addEventListener("click", sendPwCode);
			  }
			});

</script>
	<%-- 	<script src="${pageContext.request.contextPath}/js/find.js"></script> --%>
</body>
</html>