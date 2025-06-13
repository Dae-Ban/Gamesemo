

/* 회원가입 아이디 유효성 + 중복 검사 */

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

// 이름 :한글 + 영문만 입력

function validateName() {
	const nameInput = document.getElementById("name").value.trim();
	const nameResult = document.getElementById("nameCheckResult"); // ✅ 여기만 맞춰주면 돼!

	const nameRegex = /^[가-힣a-zA-Z]+$/;

	if (nameInput === "") {
		nameResult.innerText = "";
		return;
	}

	if (!nameRegex.test(nameInput)) {
		nameResult.innerText = "❌ 이름은 한글 또는 영문만 입력 가능합니다.";
		nameResult.style.color = "red";
	} else {
		nameResult.innerText = "";
	}
}

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

document.addEventListener("DOMContentLoaded", () => {
	const nicknameInput = document.getElementById("nickname");
	if (nicknameInput) {
		nicknameInput.addEventListener("input", checkNickname);
	}
});


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

	const regex = /^010\d{8}$/; // 정확히 010으로 시작 + 숫자 8개 → 총 11자리

	// 11자리가 되기 전엔 검사하지 않음
	if (phone.length < 11) {
		resultBox.textContent = ""; // 메시지 숨김
		return;
	}

	if (!regex.test(phone)) {
		if (/[^0-9]/.test(phone)) {
			resultBox.textContent = "❌ 숫자만 입력하세요. (예: 01012345678)";
		} else {
			resultBox.textContent = "❌ 올바른 휴대폰번호를 입력해주세요.";
		}
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

function handleDomainChange() {
	const domainSelect = document.getElementById("emailDomainSelect");
	const customInput = document.getElementById("emailDomain");

	if (domainSelect.value === "custom") {
		customInput.style.display = "inline-block";
		customInput.disabled = false;
		customInput.placeholder = "직접입력";
	} else {
		customInput.style.display = "none";
		customInput.disabled = true;
		customInput.value = domainSelect.value;
	}
}

// 이메일 도메인 선택 핸들링 (직접입력일 때 input창 보여주고 name 제어)
function handleDomainChange() {
	const select = document.getElementById("emailDomainSelect");
	const customInput = document.getElementById("customEmailDomain");

	if (select.value === "custom") {
		// 직접입력 선택 시
		select.removeAttribute("name"); // select는 서버 전송 제외
		customInput.setAttribute("name", "emailDomain"); // input은 서버 전송 대상
		customInput.style.display = "inline-block";
		customInput.disabled = false;
		customInput.focus();

		customInput.value = ""; // 기존 값 지우기
		customInput.focus();    // 커서 자동 포커스

	} else {
		// 일반 도메인 선택 시
		select.setAttribute("name", "emailDomain");
		customInput.removeAttribute("name");
		customInput.style.display = "none";
		customInput.disabled = true;
		customInput.value = "";
	}
}

// 이메일 아이디 유효성 검사 (영문자+숫자만 허용)
function validateEmailId(input) {
	const value = input.value;
	const msg = document.getElementById("emailIdMessage");

	const regex = /^[a-zA-Z0-9._-]+$/;

	if (!regex.test(value)) {
		msg.textContent = "❌ 올바른 이메일아이디 형식이 아닙니다. (영문자+숫자만 허용)";
		msg.style.color = "red";
		// 특수문자 등 제거
		input.value = value.replace(/[^a-zA-Z0-9]/g, "");
	} else {
		msg.textContent = "";
	}
}


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
