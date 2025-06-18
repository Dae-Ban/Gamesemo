package com.example.demo.controller;

import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.AccountVerification;
import com.example.demo.model.Member;
import com.example.demo.service.EmailService;
import com.example.demo.service.MemberService;
import com.example.demo.service.VerifyService;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private EmailService emailService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private VerifyService verifyService;

	private final PasswordEncoder passwordEncoder;

	MemberController(PasswordEncoder passwordEncoder, EmailService emailService) {
		this.passwordEncoder = passwordEncoder;
		this.emailService = emailService;
	}

	// 회원가입 화면
	@GetMapping("/register")
	public String register() {
		return "member/register";
	}

//	@PostMapping("/registerMember")
//	public String register(@ModelAttribute Member member, @RequestParam("emailId") String emailId,
//			@RequestParam("emailDomain") String emailDomain, @RequestParam("verify_type") String type,
//			RedirectAttributes redirectAttributes) {
//
//		memberService.insertMember(member);
//
//		String email = emailId + "@" + emailDomain;
//
//		redirectAttributes.addAttribute("email", email);
//		redirectAttributes.addAttribute("type", type);
//
//		return "redirect:/sendNewMemberVerify";
//	}

	@GetMapping("/find")
	public String find() {
		return "member/find";
	}

	@GetMapping("/login")
	public String login() {
		return "member/login";
	}

	@GetMapping("/modalLogin")
	public String modalLogin() {
		return "member/modalLogin";
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
			return "redirect:/"; // 로그인 성공 시 메인 페이지로 이동
		} else {
			model.addAttribute("error", "아이디 또는 비밀번호가 틀렸습니다.");
			return "member/login"; // 로그인 실패 시 로그인 화면으로
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session, HttpServletResponse response) {
		session.invalidate();
		Cookie cookie = new Cookie("rememberId", null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		return "redirect:/login";
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

	// 회원가입 폼
	@PostMapping("/register")
	public String register(Member member, @RequestParam("pwConfirm") String pwConfirm, Model model,
			RedirectAttributes redirectAttributes) {

		// ===== 1. 입력값 출력 =====
		System.out.println("===== 회원가입 입력값 확인 =====");
		System.out.println("ID: " + member.getId());
		System.out.println("이름: " + member.getName());
		System.out.println("닉네임: " + member.getNickname());
		System.out.println("비밀번호: " + member.getPw());
		System.out.println("생일: " + member.getBirthDate());
		System.out.println("이메일: " + member.getEmailId() + "@" + member.getEmailDomain());
		System.out.println("성별: " + member.getGender());
		System.out.println("전화번호: " + member.getPhone());
		System.out.println("광고 수신: " + member.getEmailAd());
		System.out.println("이메일 인증: " + member.getEmailVerified());

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

}
