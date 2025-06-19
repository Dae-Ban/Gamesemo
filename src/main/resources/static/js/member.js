

/*회원가입 아이디 유효성 + 중복 검사 */

function checkId() {
	const id = document.getElementById("id").value.trim();
	const resultBox = document.getElementById("idCheckResult");

	const regex = /^[a-zA-Z0-9]+$/; // 영문 + 숫자만 허용

	if (id.length < 4) {
		resultBox.textContent = "아이디는 4자 이상 입력해주세요.";
		resultBox.style.color = "gray";
		return;
	}

	if (!regex.test(id)) {
		resultBox.textContent = "아이디는 영문과 숫자만 사용할 수 있습니다.";
		resultBox.style.color = "gray";
		return;
	}

	fetch("/member/check-id?id=" + encodeURIComponent(id))
		.then(response => response.text())
		.then(data => {
			if (data === "duplicate") {
				resultBox.textContent = "❌ 이미 사용 중인 아이디입니다.";
				resultBox.style.color = "red";
			} else {
				resultBox.textContent = "✅ 사용 가능한 아이디입니다.";
				resultBox.style.color = "green";
			}
		});
}

document.addEventListener("DOMContentLoaded", () => {
	const idInput = document.getElementById("id");
	if (idInput) {
		idInput.addEventListener("input", checkId);
	}
});

// 이름:한글 + 영문만 입력

function validateName() {
	const nameInput = document.getElementById("name").value.trim();
	const nameResult = document.getElementById("nameCheckResult"); // ✅ 여기만 맞춰주면 돼!

	const nameRegex = /^[가-힣a-zA-Z]+$/;

	if (nameInput === "") {
		nameResult.innerText = "";
		return;
	}
	
	if (nameInput.length < 2) {
			nameResult.textContent = "이름은 2글자 이상 입력해주세요.";
			nameResult.style.color = "gray";
			return;
		}

	if (!nameRegex.test(nameInput)) {
		nameResult.innerText = "❌ 이름은 한글 또는 영문만 입력 가능합니다.";
		nameResult.style.color = "red";
	} else {
		nameResult.innerText = "";
	}
}

document.addEventListener("DOMContentLoaded", () => {
	const nameInput = document.getElementById("name");
	if (nameInput) {
		nameInput.addEventListener("input", validateName);
	}
});

/* 회원가입 닉네임 유효성 + 중복 검사 */

function checkNickname() {
	const nickname = document.getElementById("nickname").value.trim();
	const resultBox = document.getElementById("nicknameCheckResult");

	const regex = /^[가-힣]+$/; // 한글만 허용

	if (nickname.length < 2) {
		resultBox.textContent = "닉네임은 2자 이상 입력해주세요.";
		resultBox.style.color = "gray";
		return;
	}

	if (!regex.test(nickname)) {
		resultBox.textContent = "닉네임은 한글만 입력 가능합니다.";
		resultBox.style.color = "gray";
		return;
	}

	fetch("/member/check-nickname?nickname=" + encodeURIComponent(nickname))
		.then(response => response.text())
		.then(data => {
			if (data === "duplicate") {
				resultBox.textContent = "❌ 이미 사용 중인 닉네임입니다.";
				resultBox.style.color = "red";
			} else {
				resultBox.textContent = "✅ 사용 가능한 닉네임입니다.";
				resultBox.style.color = "green";
			}
		});
}


// 페이지 로딩 시 이벤트 바인딩
document.addEventListener("DOMContentLoaded", () => {
	const nicknameInput = document.getElementById("nickname");
	if (nicknameInput) {
		nicknameInput.addEventListener("input", checkNickname);
	}
});


// 비밀번호 유효성 + 비번 확인

function checkPassword() {
	const pw = document.getElementById("pw").value;
	const resultBox = document.getElementById("pwCheckResult");

	const regex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*()\-_=+\\|[\]{};:'",.<>/?`~])[A-Za-z\d!@#$%^&*()\-_=+\\|[\]{};:'",.<>/?`~]{8,}$/

	if (!regex.test(pw)) {
		resultBox.textContent = "비밀번호는 8자 이상, 영문과 숫자, 특수문자를 포함해야 합니다.";
		resultBox.style.color = "gray";
	} else {
		resultBox.textContent = "✅ 사용 가능한 비밀번호입니다.";
		resultBox.style.color = "green";
	}
}

function checkPasswordMatch() {
	const pw = document.getElementById("pw").value;
	const pwConfirm = document.getElementById("pwConfirm").value;
	const matchResult = document.getElementById("pwMatchResult");

	if (pwConfirm === "") {
		matchResult.textContent = "";
		return;
	}

	if (pw === pwConfirm) {
		matchResult.textContent = "✅ 비밀번호가 일치합니다.";
		matchResult.style.color = "green";
	} else {
		matchResult.textContent = "❌ 비밀번호가 일치하지 않습니다.";
		matchResult.style.color = "red";
	}
}

document.addEventListener("DOMContentLoaded", () => {
	const pwInput = document.getElementById("pw");
	const pwConfirmInput = document.getElementById("pwConfirm");

	if (pwInput) pwInput.addEventListener("input", () => {
		checkPassword();
		checkPasswordMatch(); // 비밀번호 바꾸면 확인 일치도 다시 체크
	});

	if (pwConfirmInput) pwConfirmInput.addEventListener("input", checkPasswordMatch);
});

// 휴대폰 번호 인증
function checkPhone() {
	const phoneInput = document.getElementById("phone");
	const resultBox = document.getElementById("phoneCheckResult");

	// 숫자 외 문자 제거
	phoneInput.value = phoneInput.value.replace(/[^0-9]/g, '');
	const phone = phoneInput.value;

	//  먼저 010으로 시작하지 않는 경우 검사
	if (phone && !phone.startsWith("010")) {
		resultBox.textContent = "❌ 010으로 시작하는 번호만 입력 가능합니다.";
		resultBox.style.color = "red";
		return;
	}

	//  11자리가 되기 전엔 안내문구만
	if (phone.length < 11) {
		resultBox.textContent = "휴대폰 번호는 11자리여야 합니다.";
		resultBox.style.color = "gray";
		return;
	}

	//  010으로 시작 + 11자리 정확히 입력된 경우만 통과
	const regex = /^010\d{8}$/;
	if (!regex.test(phone)) {
		resultBox.textContent = "❌ 올바른 휴대폰번호를 입력해주세요.";
		resultBox.style.color = "red";
	} else {
		resultBox.textContent = "✅ 올바른 휴대폰 번호입니다.";
		resultBox.style.color = "green";
	}
}

document.addEventListener("DOMContentLoaded", () => {
	const phoneInput = document.getElementById("phone");
	if (phoneInput) {
		phoneInput.addEventListener("input", checkPhone);
	}
});


// 이메일 도메인 선택 핸들링 (직접입력일 때 input창 보여주고 name 제어)
function handleDomainChange() {
	const select = document.getElementById("emailDomainSelect");
	const customInput = document.getElementById("customEmailDomain"); // customEmailDomain만 쓰기로

	if (select.value === "custom") {
		select.removeAttribute("name");
		customInput.setAttribute("name", "emailDomain");
		customInput.style.display = "inline-block";
		customInput.disabled = false;
		customInput.value = "";
		customInput.focus();
	} else {
		select.setAttribute("name", "emailDomain");
		customInput.removeAttribute("name");
		customInput.style.display = "none";
		customInput.disabled = true;
		customInput.value = "";
	}

	checkEmailDuplicate(); // 도메인 바뀌면 중복검사 다시
}


// 이메일 아이디 유효성 검사 (영문자+숫자만 허용)
function validateEmailId(input) {
	const value = input.value;
	const msg = document.getElementById("emailIdMessage");
	const regex = /^[a-zA-Z0-9._-]+$/;

	if (!regex.test(value)) {
		msg.textContent = "❌ 올바른 이메일아이디 형식이 아닙니다. (영문자+숫자만 허용)";
		msg.style.color = "red";
		input.value = value.replace(/[^a-zA-Z0-9]/g, "");
	} else {
		msg.textContent = "";
	}

	checkEmailDuplicate(); // 유효성 통과 후 중복검사 실행
}

// ✅ 이메일 중복검사 함수 (실제 fetch 요청 실행)
function checkEmailDuplicate() {
	const emailId = document.getElementById("emailId").value.trim();
	let domain = document.getElementById("emailDomainSelect").value;
	const customInput = document.getElementById("customEmailDomain");

	if (domain === "custom") {
		domain = customInput.value.trim();
	}

	const result = document.getElementById("emailCheckResult");

	// 기본 입력값 없으면 메시지 초기화
	if (!emailId || !domain) {
		result.innerText = "";
		result.style.color = "";
		return;
	}

	const fullEmail = `${emailId}@${domain}`;
	const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

	// 이메일 형식이 유효하지 않으면 메시지 숨김
	if (!emailRegex.test(fullEmail)) {
		result.innerText = "";
		result.style.color = "";
		return;
	}

	// ✅ 유효한 이메일일 경우 중복검사 fetch 요청
	fetch("/member/checkEmailDuplicate", {
		method: "POST",
		headers: { "Content-Type": "application/json" },
		body: JSON.stringify({ email: fullEmail })
	})
		.then(res => res.json())
		.then(isDuplicate => {
			if (isDuplicate) {
				result.innerText = "❌ 이미 사용 중인 이메일입니다.";
				result.style.color = "red";
			} else {
				result.innerText = "✅ 사용 가능한 이메일입니다.";
				result.style.color = "green";
			}
		})
		.catch(() => {
			result.innerText = "서버 오류가 발생했습니다.";
			result.style.color = "red";
		});
}

// ✅ 입력 중 실시간 중복검사 함수 (입력 도중에도 문구 반영되게)
function checkEmailDuplicateLive() {
	// 실시간 검사용으로 checkEmailDuplicate() 그대로 사용
	checkEmailDuplicate();
}

// ✅ 이벤트 연결
document.addEventListener("DOMContentLoaded", function() {
	const emailCheckResult = document.getElementById("emailCheckResult");

	// 이메일 아이디 입력 중 실시간 중복검사 실행
	document.getElementById("emailId").addEventListener("input", checkEmailDuplicateLive);

	// 이메일 아이디 입력 후 blur 시 유효성 + 중복검사
	document.getElementById("emailId").addEventListener("blur", function() {
		validateEmailId(this);
	});

	// 도메인 선택 시 중복검사
	document.getElementById("emailDomainSelect").addEventListener("change", handleDomainChange);

	// 직접입력 도메인 입력 중 실시간 중복검사 실행
	document.getElementById("customEmailDomain").addEventListener("input", checkEmailDuplicateLive);

	// 직접입력 도메인 입력 완료 후 blur 시 중복검사
	document.getElementById("customEmailDomain").addEventListener("blur", checkEmailDuplicate);
});

//성별 
function getGender() {
	let gender = document.querySelector('input[name="gender"]:checked');
	if (gender) {
		return gender.value;
	} else {
		alert("성별을 선택해주세요!");
		return null;
	}
}

document.getElementById("signupBtn").addEventListener("click", function() {
	let gender = getGender();
	if (gender) {
		console.log("선택된 성별:", gender);
		// 이후 회원가입 데이터를 서버로 전송하는 로직 추가
	}
});

// 생년월일
document.addEventListener("DOMContentLoaded", function() {
	var birthInput = document.getElementById("birth");

	// 날짜가 선택되지 않으면 제출 방지
	birthInput.addEventListener("blur", function() {
		if (!birthInput.value) {
			alert("생년월일을 반드시 선택해야 합니다!");
			birthInput.focus();
		}
	});
});


// 📌 필수 약관 동의 체크 여부 
function validateForm() {
	const emailAd = document.querySelector('input[name="emailAd"]');
	if (!emailAd.checked) {
		alert("이메일 광고 수신 동의는 필수입니다.");
		emailAd.focus();
		return false;
	}

	// ✅ 장르 선택은 필수가 아니므로 검사 생략
	return true; // 통과 시 제출 허용
}


// ** 비밀번호 변경

// ✅ 현재 비밀번호 확인
function checkCurrentPassword() {
	const currentPw = document.getElementById("currentPassword").value;
	const msg = document.getElementById("currentPwResult");

	// 입력 없을 경우 메시지 제거
	if (!currentPw) {
		msg.textContent = "";
		document.getElementById("newPw").disabled = true;
		document.getElementById("confirmPw").disabled = true;
		return;
	}

	fetch("/member/checkPassword", {
		method: "POST",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify({ currentPassword: currentPw })
	})
		.then(response => response.json())
		.then(data => {
			if (data.match) {
				msg.textContent = "✅ 현재 비밀번호와 일치합니다.";
				msg.style.color = "green";

				document.getElementById("newPw").disabled = false;
				document.getElementById("confirmPw").disabled = false;
			} else {
				msg.textContent = "❌ 비밀번호가 일치하지 않습니다.";
				msg.style.color = "red";

				document.getElementById("newPw").disabled = true;
				document.getElementById("confirmPw").disabled = true;
			}
		});
	// 현재비번 노 일치시 새비밀번호 입력 막아져있음..이거 푸는 법!
	if (data.match) {
		msg.textContent = "✅ 현재 비밀번호와 일치합니다.";
		msg.style.color = "green";

		// ✅ 입력 허용
		document.getElementById("newPw").disabled = false;
		document.getElementById("confirmPw").disabled = false;
	} else {
		msg.textContent = "❌ 비밀번호가 일치하지 않습니다.";
		msg.style.color = "red";

		// ✅ 다시 입력 막기
		document.getElementById("newPw").disabled = true;
		document.getElementById("confirmPw").disabled = true;

		// ✅ 기존 값도 초기화하면 깔끔
		document.getElementById("newPw").value = "";
		document.getElementById("confirmPw").value = "";
		document.getElementById("newPwMsg").textContent = "";
		document.getElementById("pwMatchMsg").textContent = "";
	}

}

// ✅ 새 비밀번호 유효성만 검사
function checkNewPwValid() {
	const newPw = document.getElementById("newPw").value;
	const newPwMsg = document.getElementById("newPwMsg");

	const regex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*()\-_=+\\|[\]{};:'",.<>/?`~]).{8,}$/;

	if (!newPw) {
		newPwMsg.textContent = "";
		return;
	}

	if (!regex.test(newPw)) {
		newPwMsg.textContent = "비밀번호는 8자 이상, 영문+숫자+특수문자를 포함해야 합니다.";
		newPwMsg.style.color = "gray";
	} else {
		newPwMsg.textContent = "✅ 사용 가능한 비밀번호입니다.";
		newPwMsg.style.color = "green";
	}
}

// ✅ 비밀번호 일치 여부만 검사
function checkPwMatch() {
	const newPw = document.getElementById("newPw").value;
	const confirmPw = document.getElementById("confirmPw").value;
	const matchMsg = document.getElementById("pwMatchMsg");

	if (!confirmPw) {
		matchMsg.textContent = "";
		return;
	}

	if (newPw === confirmPw) {
		matchMsg.textContent = "✅ 비밀번호가 일치합니다.";
		matchMsg.style.color = "green";
	} else {
		matchMsg.textContent = "❌ 비밀번호가 일치하지 않습니다.";
		matchMsg.style.color = "red";
	}
}


// 회원탈퇴
function confirmDelete() {
	const pw = document.getElementsByName("pw")[0].value;
	const pwConfirm = document.getElementsByName("pwConfirm")[0].value;

	const msg = document.getElementById("pwMatchMsg");
	msg.innerText = ""; // 초기화

	// 빈칸 검사
	if (!pw || !pwConfirm) {
		msg.innerText = "비밀번호를 정확히 입력해주세요.";
		msg.style.color = "red";
		return false;
	}

	// 비밀번호 불일치
	if (pw !== pwConfirm) {
		msg.innerText = "비밀번호가 일치하지 않습니다.";
		msg.style.color = "red";
		return false;
	}

	// 정말 탈퇴 확인
	const confirmResult = confirm("정말 탈퇴하시겠습니까?");
	if (!confirmResult) {
		return false;
	}

	// 성공이면 통과
	return true;
}


// 아이디/ 비번찾기
function sendIdCode() {
	const email = document.getElementById("findIdEmail").value.trim();
	const message = document.getElementById("idMessage");

	if (!email) {
		message.innerText = "⚠ 이메일을 입력하세요.";
		message.style.color = "red";
		return;
	}

	message.innerText = "⏳ 인증번호를 전송 중입니다...";
	message.style.color = "gray";

	fetch("/member/sendCodeForId", {
		method: "POST",
		headers: { "Content-Type": "application/json" },
		body: JSON.stringify({ email })
	})
		.then(res => res.json())
		.then(data => {

			console.log("✅ data.id 값:", data.id);


			if (data.success) {
				idAuthCode = data.code;
				idFound = data.id;
				message.innerText = "✅ 인증번호가 이메일로 전송되었습니다.";
				message.style.color = "green";
			} else {
				message.innerText = "❌ " + data.message;
				message.style.color = "red";
			}
		})
		.catch(err => {
			console.error("전송 오류:", err);
			message.innerText = "❌ 서버 오류가 발생했습니다.";
			message.style.color = "red";
		});
}

//아이디 찾기
function checkIdCode() {
	const code = document.getElementById("findIdCode").value.trim();
	const result = document.getElementById("idResult");


	if (code === String(idAuthCode) && idFound) {

		result.innerHTML = `✅ 회원님의 아이디는 <strong>${idFound}</strong> 입니다.
			<br><br>
			<a href="/member/login" style="color: black; text-decoration: none;">로그인 페이지로 이동하기</a>`;
		result.style.color = "green";


	} else {
		result.innerText = "❌ 인증번호가 일치하지 않습니다.";
		result.style.color = "red";
	}
}
// 비번찾기
function sendPwCode() {
	const id = document.getElementById("findPwId").value.trim();
	const email = document.getElementById("findPwEmail").value.trim();
	const message = document.getElementById("pwMessage");


	if (!id || !email) {
		message.innerText = "⚠ 아이디와 이메일을 모두 입력하세요.";
		message.style.color = "red";
		return;
	}


	message.innerText = "⏳ 인증번호를 전송 중입니다...";
	message.style.color = "gray";


	fetch("/member/sendCodeForPw", {
		method: "POST",
		headers: { "Content-Type": "application/json" },
		body: JSON.stringify({ id, email })
	})
		.then(res => res.json())
		.then(data => {
			if (data.success) {
				message.innerText = "✅ 인증번호가 이메일로 전송되었습니다.";
				message.style.color = "green";
				pwAuthCode = data.code;
			} else {
				message.innerText = "❌ " + data.message;
				message.style.color = "red";
			}
		})
		.catch(err => {
			console.error("비밀번호 인증번호 전송 오류:", err);
			message.innerText = "❌ 서버 오류가 발생했습니다.";
			message.style.color = "red";
		});
}


function verifyCode() {
	const code = document.getElementById("findPwCode").value.trim();
	const result = document.getElementById("pwResult");

	if (code === pwAuthCode) {
		result.innerText = "✅ 인증 성공! 비밀번호를 재설정하세요.";
		result.style.color = "green";
		document.getElementById("resetPwSection").style.display = "block";

		currentUserId = document.getElementById("findPwId").value.trim();

	} else {
		result.innerText = "❌ 인증번호가 일치하지 않습니다.";
		result.style.color = "red";
	}
}

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

	fetch("/member/resetPassword", {
		method: "POST",
		headers: { "Content-Type": "application/json" },
		body: JSON.stringify({ id: userId, newPassword: newPw })
	})
		.then(res => res.json())
		.then(data => {
			if (data.success) {
				resetResult.innerText = "✅ 비밀번호가 변경되었습니다. 로그인 페이지로 이동합니다.";
				resetResult.style.color = "green";
				setTimeout(() => location.href = "/member/login", 2500);
			} else {
				resetResult.innerText = "❌ 비밀번호 변경에 실패했습니다.";
				resetResult.style.color = "red";
			}
		})
		.catch(err => {
			console.error("비밀번호 변경 오류:", err);
			resetResult.innerText = "❌ 서버 오류가 발생했습니다.";
			resetResult.style.color = "red";
		});
}

document.addEventListener("DOMContentLoaded", () => {
	// 탭 버튼 연결
	document.getElementById("tabIdBtn")?.addEventListener("click", () => showTab("id"));
	document.getElementById("tabPwBtn")?.addEventListener("click", () => showTab("pw"));

	// 아이디 인증번호 확인 버튼 연결 
	const confirmBtn = document.querySelector("button[onclick='checkIdCode()']");
	if (confirmBtn) {
		confirmBtn.addEventListener("click", checkIdCode);
		console.log("✅ 확인 버튼에 checkIdCode() 연결 완료!");
	} else {
		console.warn("⚠ 확인 버튼을 찾지 못했습니다.");
	}
});

//회원정보수정 적용 안 돼서 추가..

function validateUpdateForm() {
	const name = document.getElementById("name")?.value.trim();
	const nickname = document.getElementById("nickname")?.value.trim();
	const phone = document.getElementById("phone")?.value.trim();
	const emailId = document.getElementById("emailId")?.value.trim();
	const emailDomain = document.getElementById("emailDomainSelect")?.value === "custom"
		? document.getElementById("customEmailDomain")?.value.trim()
		: document.getElementById("emailDomainSelect")?.value;
	const birth = document.getElementById("birth")?.value.trim();
	const agree = document.querySelector("input[name='agreeUpdate']");

	if (!name || !nickname || !phone || !emailId || !emailDomain || !birth) {
		alert("모든 항목을 빠짐없이 입력해주세요.");
		return false;
	}

	if (!agree.checked) {
		alert("정보 수정 동의는 필수입니다.");
		return false;
	}

	// 전화번호 010 시작 + 11자리 검사
	if (!/^010\d{8}$/.test(phone)) {
		alert("올바른 휴대폰 번호를 입력해주세요. (예: 01012345678)");
		return false;
	}

	return true;
}


