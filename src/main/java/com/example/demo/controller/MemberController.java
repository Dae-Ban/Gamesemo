package com.example.demo.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.AccountVerification;
import com.example.demo.model.Member;
import com.example.demo.service.EmailService;
import com.example.demo.service.MemberService;
import com.example.demo.service.VerifyService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class MemberController {

	private final EmailService emailService;

	private final PasswordEncoder passwordEncoder;

	@Autowired
	private MemberService memberService;

	@Autowired
	private VerifyService verifyService;

	MemberController(PasswordEncoder passwordEncoder, EmailService emailService) {
		this.passwordEncoder = passwordEncoder;
		this.emailService = emailService;
	}

	// 회원가입 화면
	@GetMapping("/register")
	public String register() {
		return "member/register";
	}

	// 아이디 중복 확인
	@ResponseBody
	@GetMapping("/check-id")
	public String checkId(@RequestParam("id") String id) {
		boolean exists = memberService.checkIdExists(id);
		return exists ? "duplicate" : "ok";
	}

	// 닉네임 중복 확인
	@ResponseBody
	@GetMapping("/check-nickname")
	public String checkNickname(@RequestParam("nickname") String nickname) {
		boolean exists = memberService.checkNicknameExists(nickname);
		return exists ? "duplicate" : "ok";
	}

	// 이메일 중복 확인
	@PostMapping("/checkEmailDuplicate")
	@ResponseBody
	public boolean checkEmailDuplicate(@RequestBody Map<String, String> data) {
		String email = data.get("email");
		return memberService.findByEmailForRegister(email) != null;
	}


	//회원가입 폼
	@PostMapping("/register")
	public String register(Member member, @RequestParam("pwConfirm") String pwConfirm, Model model,
			RedirectAttributes redirectAttributes) {

		// ===== 2. 비밀번호확인 =====
		if (!member.getPw().equals(pwConfirm)) {
			model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
			return "member/register";
		}

		// ===== 3. emailVerified 기본값 처리 =====
		member.setEmailVerified("N");

		// 정상회원 상태값 명시적 0으로 설정
		member.setState(0); //
		System.out.println("state: " + member.getState());

		// ===== 4. 비밀번호 암호화 =====
		member.setPw(passwordEncoder.encode(member.getPw()));

		// ===== 5. 회원가입 처리 =====
		boolean result = memberService.registerMember(member);

		// ===== 6. 결과 처리 =====
		if (result) {
			verifyAndSendEmail(member);
			redirectAttributes.addFlashAttribute("message", "회원가입이 완료되었습니다! 이메일 인증을 완료해주세요.");
			return "redirect:/member/login";
		} else {
			model.addAttribute("error", "회원가입 중 오류가 발생했습니다.");
			return "member/register";
		}
	}

	private void verifyAndSendEmail(Member member) {
		String token = UUID.randomUUID().toString();
		Timestamp expiresAt = new Timestamp(System.currentTimeMillis() + 10 * 60 * 1000);

		AccountVerification verification = new AccountVerification();
		verification.setId(member.getId());
		verification.setCode(token);
		verification.setType("MEMBER_JOIN");
		verification.setExpiresAt(expiresAt);
		verification.setVerified('N');

		verifyService.insertVerification(verification);

		String fullEmail = member.getEmailId() + "@" + member.getEmailDomain();
		emailService.sendVerificationEmail(fullEmail, token);

	}

//	영교님 controller 함수 - 시작 
	@GetMapping("/login")
	public String login() {
		return "member/login";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute Member login,
			@RequestParam(name = "rememberMe", required = false) String rememberMe, Model model, HttpSession session,
			HttpServletResponse response) {
		System.out.println("입력 ID: " + login.getId());
		System.out.println("입력 PW: " + login.getPw());
		Member member = memberService.login(login);
		System.out.println("DB에서 찾은 Member: " + member);
		if (member != null) {
			session.setAttribute("loginMember", member);
			session.setAttribute("id", member.getId());
			if (rememberMe != null) {
				Cookie cookie = new Cookie("rememberId", member.getId());
				cookie.setMaxAge(60 * 60 * 24 * 3);
				cookie.setPath("/");
				response.addCookie(cookie);
			}
			String redirect = (String) session.getAttribute("redirectAfterLogin");
			if (redirect != null) {
				session.removeAttribute("redirectAfterLogin");
				return "redirect:" + redirect;
			}

			System.out.println("로그인 성공!");
			return "redirect:/"; // 로그인 성공 시 메인 페이지로 이동
		} else {
			model.addAttribute("error", "아이디 또는 비밀번호가 틀렸습니다.");
			return "member/login"; // 로그인 실패 시 로그인 화면으로
		}
	}

//	영교님 controller 함수 - 끝 
	
	// 재원 추가
	@GetMapping("/find")
	public String find() {
		return "member/find";
	}

	// 추가함 25.06.20 (영교님꺼)
	@GetMapping("/logout")
	public String logout(HttpSession session, HttpServletResponse response) {
		session.invalidate();
		Cookie cookie = new Cookie("rememberId", null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		return "redirect:/login";
	}

//******************--------------------------------    

// 마이페이지 추가

	@GetMapping("/mypage")
	public String mypage(HttpSession session, Model model) {
		Member member = (Member) session.getAttribute("loginMember");
		model.addAttribute("member", member);

		if (member != null && member.getJoinDate() != null) {
			LocalDate joinDate = member.getJoinDate().toLocalDateTime().toLocalDate();
			LocalDate today = LocalDate.now(ZoneId.systemDefault());
			long dday = ChronoUnit.DAYS.between(joinDate, today);
			model.addAttribute("dDay", dday);
		}

		return "member/mypage";
	}

	// 마이페이지 프로필 화면
	@GetMapping("/mypageProfile")
	public String mypageProfile(HttpSession session, Model model) {
		Member member = (Member) session.getAttribute("loginMember");
		model.addAttribute("member", member);
		return "member/mypageProfile"; // /WEB-INF/views/member/mypageProfile.jsp
	}

	// 마이페이지 회원 정보 수정 화면
	@GetMapping("/mypageUpdate")
	public String mypageUpdate(HttpSession session, Model model) {
		Member member = (Member) session.getAttribute("loginMember");
		model.addAttribute("member", member);
		return "member/mypageUpdate";
	}

	// 마이페이지 비밀번호 변경 화면
	@GetMapping("/mypagePassword")
	public String mypagePassword(HttpSession session, Model model) {
		Member member = (Member) session.getAttribute("loginMember");
		model.addAttribute("loginMember", member);
		return "member/mypagePassword";
	}

	// 마이페이지 회원 탈퇴 화면
	@GetMapping("/mypageDelete")
	public String mypageDelete(HttpSession session, Model model) {
		Member member = (Member) session.getAttribute("loginMember");
		model.addAttribute("loginMember", member);
		return "member/mypageDelete";
	}

	// 회원 수정 폼
	@GetMapping("/update")
	public String showUpdateForm(HttpSession session, Model model) {
		Member loginMember = (Member) session.getAttribute("loginMember");

		if (loginMember == null) {
			return "redirect:/member/login"; // 로그인 안 했으면 로그인으로
		}

		model.addAttribute("member", loginMember); // JSP에 회원 정보 넘겨줌
		return "member/update"; // 수정 폼 화면 반환
	}

	// 회원 정보 수정 처리
	@PostMapping("/update")
	public String updateMember(@ModelAttribute Member member, HttpSession session,
			RedirectAttributes redirectAttributes) {
		Member loginMember = (Member) session.getAttribute("loginMember");

		if (loginMember == null) {
			return "redirect:/member/login"; // 로그인 안 한 경우 login폼으로 이동
		}

		// 세션의 ID로 고정 (보안상 중요!)
		member.setId(loginMember.getId());

		boolean result = memberService.updateMember(member);

		if (result) {
			// 세션에 최신 회원 정보 다시 저장
			session.setAttribute("loginMember", memberService.getMemberById(member.getId()));

			// 성공 메시지 추가
			redirectAttributes.addFlashAttribute("message", "회원 정보가 성공적으로 수정되었습니다.");

			// 마이페이지로 리다이렉트
			return "redirect:/member/mypageProfile";
		} else {
			// 실패 메시지 추가
			redirectAttributes.addFlashAttribute("error", "회원 정보 수정에 실패했습니다.");

			// 수정 페이지로 리다이렉트
			return "redirect:/member/mypageUpdate";
		}
	}

	// 비밀번호 변경 폼
	@GetMapping("/changePassword")
	public String changePasswordForm(HttpSession session, Model model) {
		Member member = (Member) session.getAttribute("loginMember");

		// null 체크
		if (member == null) {
			return "redirect:/member/login"; // 로그인 안돼있으면 로그인 페이지로 보내기
		}

		model.addAttribute("loginMember", member);
		return "member/mypagePassword";
	}

	// 비밀번호 변경 처리
	@PostMapping("/changePassword")
	public String changePassword(@RequestParam("currentPassword") String currentPassword,
			@RequestParam("newPassword") String newPassword, @RequestParam("confirmPassword") String confirmPassword,
			HttpSession session, RedirectAttributes redirectAttributes) {

		Member member = (Member) session.getAttribute("loginMember");

		// 현재 비밀번호 확인
		if (!passwordEncoder.matches(currentPassword, member.getPw())) {
			redirectAttributes.addFlashAttribute("error", "❌ 현재 비밀번호가 일치하지 않습니다.");
			return "redirect:/member/mypagePassword";
		}

		// 새 비밀번호 유효성 검사
		String pwRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()\\-_=+\\[\\]{};:'\",.<>/?`~]).{8,}$";
		if (!newPassword.matches(pwRegex)) {
			redirectAttributes.addFlashAttribute("error", "❌ 비밀번호는 8자 이상, 영문+숫자+특수문자를 포함해야 합니다.");
			return "redirect:/member/changePassword";
		}

		// 새 비밀번호 일치 여부 확인
		if (!newPassword.equals(confirmPassword)) {
			redirectAttributes.addFlashAttribute("error", "❌ 새 비밀번호가 서로 다릅니다.");
			return "redirect:/member/mypagePassword";
		}

		// 비밀번호 업데이트
		member.setPw(passwordEncoder.encode(newPassword));
		memberService.updatePassword(member);

		redirectAttributes.addFlashAttribute("success", " 비밀번호가 성공적으로 변경되었습니다. ");
		return "redirect:/member/mypage";
	}

	// ✅ 현재 비밀번호 일치 확인용 (AJAX 요청 처리)
	@PostMapping("/checkPassword")
	@ResponseBody
	public Map<String, Boolean> checkPassword(@RequestBody Map<String, String> requestBody,
			@SessionAttribute("loginMember") Member loginMember) {
		String currentPassword = requestBody.get("currentPassword");
		boolean match = passwordEncoder.matches(currentPassword, loginMember.getPw());

		Map<String, Boolean> result = new HashMap<>();
		result.put("match", match);
		return result;
	}
//
////	// 아이디 찾기 - 이메일 인증번호 전송
//	@PostMapping("/sendCodeForId")
//	@ResponseBody
//	public Map<String, Object> sendCodeForId(@RequestBody Map<String, String> data) {
//		String email = data.get("email");
//		Map<String, Object> result = new HashMap<>();
//
//		if (email == null || !email.contains("@")) {
//			result.put("success", false);
//			result.put("message", "올바른 이메일 형식이 아닙니다.");
//			return result;
//		}
//
//		// ✅ 전체 이메일 한 줄로 검사
//		Member member = memberService.findByEmailForFind(email);
//
//		if (member != null) {
//			// ✅ 6자리 숫자 인증코드 생성
//			System.out.println("🔍 찾은 회원 ID: " + member.getId()); // id나오는지 찍어보기
//
//			String code = String.valueOf((int) (Math.random() * 900000) + 100000);
//			// 아이디 찾기
//			memberService.sendIdAuthCode(email, code);
//
//			// ✅ JS에서 받을 인증번호 포함 응답
//			result.put("success", true);
//			result.put("code", code);
//			result.put("id", member.getId());
//
//		} else {
//			result.put("success", false);
//			result.put("message", "가입되지 않은 이메일입니다.");
//		}
//
//		return result;
//	}
////
//	// 비밀번호 찾기
////
//	@PostMapping("/sendCodeForPw")
//	@ResponseBody
//	public Map<String, Object> sendCodeForPw(@RequestBody Map<String, String> data) {
//		String id = data.get("id");
//		String email = data.get("email");
//
//		Map<String, Object> result = new HashMap<>();
//		Member member = memberService.findByIdAndEmail(id, email);
//
//		if (member != null) {
//			String code = String.valueOf((int) (Math.random() * 900000) + 100000);
//
//			// ✅ 이메일 문자열 조합
//			String fullEmail = member.getEmailId() + "@" + member.getEmailDomain();
//
//			// ✅ 기존 이메일 전송 메서드 재원님꺼에서 직접 호출
//			emailService.sendFindPwEmail(fullEmail, code);
//
//			result.put("success", true);
//			result.put("code", code);
//		} else {
//			result.put("success", false);
//			result.put("message", "아이디 또는 이메일이 일치하지 않습니다.");
//		}
//		return result;
//	}
//
//	// 비번찾기 - 새비번설정
//	@PostMapping("/resetPassword")
//	@ResponseBody
//	public Map<String, Object> resetPassword(@RequestBody Map<String, String> data) {
//		String id = data.get("id");
//		String newPassword = data.get("newPassword");
//
//		Map<String, Object> result = new HashMap<>();
//
//		Member member = memberService.findById(id);
//		if (member == null) {
//			result.put("success", false);
//			result.put("message", "회원 정보 없음");
//			return result;
//		}
//
//		// 비밀번호 암호화
//		String encodedPw = passwordEncoder.encode(newPassword);
//
//		// Map으로 전달
//		Map<String, Object> paramMap = new HashMap<>();
//		paramMap.put("id", id);
//		paramMap.put("newPw", encodedPw);
//
//		boolean updated = memberService.updatePasswordForFind(paramMap); // service 메서드도 새로 만들어야 함
//
//		if (updated) {
//			result.put("success", true);
//		} else {
//			result.put("success", false);
//			result.put("message", "DB 업데이트 실패");
//		}
//
//		return result;
//	}

	// 탈퇴 폼 GET
	@GetMapping("/delete")
	public String deleteForm(HttpSession session, Model model) {
		Member member = (Member) session.getAttribute("loginMember");
		model.addAttribute("loginMember", member);
		return "member/delete";
	}

	// ✅ 탈퇴 처리 POST
	@PostMapping("/delete")
	public String deleteMember(@RequestParam("id") String id, @RequestParam("pw") String pw,
			@RequestParam("pwConfirm") String pwConfirm, HttpSession session, RedirectAttributes redirectAttributes) {
		Member loginMember = (Member) session.getAttribute("loginMember");

		// 비밀번호 일치 확인
		if (!pw.equals(pwConfirm)) {
			redirectAttributes.addFlashAttribute("error", "비밀번호가 일치하지 않습니다.");
			return "redirect:/member/mypageDelete";
		}

		// 실제 비밀번호 비교 (암호화된 비밀번호와 비교)
		if (!passwordEncoder.matches(pw, loginMember.getPw())) {
			redirectAttributes.addFlashAttribute("error", "비밀번호가 올바르지 않습니다.");
			return "redirect:/member/mypageDelete";
		}

		// 탈퇴처리
		boolean result = memberService.deleteMember(loginMember.getId());
		if (!result) {
			redirectAttributes.addFlashAttribute("error", "회원 탈퇴에 실패했습니다.");
			return "redirect:/member/mypage";
		}

		// 탈퇴 성공시 세션 끊고 메인으로 이동하기
		session.invalidate();
		return "redirect:/main";
	}

////	// 로그아웃
////	@GetMapping("/member/logout")
////	public String logout(HttpSession session) {
////		session.invalidate(); // 세션 종료
////		return "redirect:/member/main"; // 로그인 페이지로 이동 (또는 홈)
////	}
//
	// 메인페이지
	@GetMapping("/main")
	public String memberMain(HttpSession session, Model model) {
		Member member = (Member) session.getAttribute("loginMember");
		model.addAttribute("member", member);
		return "main";
	}
}
