package com.example.demo.controller;

import java.sql.Timestamp;
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

//	토큰 멤버에 저장하지 않고, AccountVerification 테이블에 insert하기로함;;
		// ===== 5. 이메일 인증 토큰 생성 및 기본 상태 설정 =====
//		String token = UUID.randomUUID().toString();
//		member.setVerificationToken(token);
//		member.setEmailVerified("n"); // 이메일 인증 전 상태

	 // ===== 5. 회원가입 처리 =====
	    boolean result = memberService.registerMember(member);

	    // ===== 6. 결과 처리 =====
	    if (result) {
	        // 인증 토큰 생성 및 객체 생성
	        String token = UUID.randomUUID().toString();
	        Timestamp expiresAt = new Timestamp(System.currentTimeMillis() + 10 * 60 * 1000);

	        AccountVerification verification = new AccountVerification();
	        verification.setId(member.getId());
	        verification.setCode(token);
	        verification.setType("MEMBER_JOIN");
	        verification.setExpiresAt(expiresAt);
	        verification.setVerified('N');

	        // 인증 테이블에 저장
	        verifyService.insertVerification(verification);

	        // 이메일 링크 발송
	        String link = "http://localhost/verify?code=" + token;
	        String email = member.getEmailId() + "@" + member.getEmailDomain();
	        emailService.sendVerificationEmail(email, link);

	        System.out.println("Link: " + link);

	        redirectAttributes.addFlashAttribute("message", "회원가입이 완료되었습니다! 이메일 인증을 완료해주세요.");
	        return "redirect:/member/login";
	    } else {
	        model.addAttribute("error", "회원가입 중 오류가 발생했습니다.");
	        return "member/register";
	    }
	}

//-*******************---------------------------------

//// 임시로 만듦!! login페이지는 영교님.!!!!!!!!!!!!!!
//// mypage는 민정!!!!!!!!!!!!!!!!!!!!!!! 
//
//	// 로그인 페이지로 이동
//	@GetMapping("/login")
//	public String loginPage() {
//		return "member/login"; // /WEB-INF/views/member/login.jsp 를 의미
//	}
//
//	@PostMapping("/login")
//	public String login(@RequestParam("id") String id, @RequestParam("pw") String pw, HttpSession session, Model model,
//			RedirectAttributes redirectAttributes) {
//		// 로그인 시 DB에서 사용자 정보 조회
//		Member member = memberService.login(id, pw);
//
//		if (member != null) {
//			// 로그인 성공 시 세션에 저장
//			session.setAttribute("loginMember", member);
//
//			// 로그인된 회원 정보 콘솔에 출력
//			System.out.println("로그인된 회원 정보: " + member);
//
//			// 로그인 성공 후 성공 메시지 추가 (alert 메시지용)
//			redirectAttributes.addFlashAttribute("message", "로그인 성공!");
//
//			// 로그인 성공 후 마이페이지로 이동
//			return "redirect:/member/mypage";
//		} else {
//			// 로그인 실패 시 에러 메시지 표시
//			model.addAttribute("error", "로그인 실패");
//			return "member/login";
//		}
//	}
//
//	// 마이페이지 !! 임시로...!!!!!!!!!!
//	@GetMapping("/mypage")
//	public String myPage(HttpSession session, Model model) {
//		// 로그인된 사용자 정보 가져오기
//		Member loginMember = (Member) session.getAttribute("loginMember");
//
//		if (loginMember == null) {
//			// 로그인 안 한 상태면 로그인 페이지로 리다이렉트
//			return "redirect:/member/login";
//		}
//
//		// 로그인된 사용자 정보 model에 추가
//		model.addAttribute("member", loginMember);
//
//		// 마이페이지 JSP로 이동
//		return "member/mypage";
//	}

//--******************--------------------------------    

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
			return "redirect:/member/mypage";
		} else {
			// 실패 메시지 추가
			redirectAttributes.addFlashAttribute("error", "회원 정보 수정에 실패했습니다.");

			// 수정 페이지로 리다이렉트
			return "redirect:/member/update";
		}
	}

//******************************
//	// 가짜로그인 시키기.. 로그인 없이 폼 확인하기위해..
//	@GetMapping("/fake-login")
//	public String fakeLogin(HttpSession session) {
//		Member fakeUser = new Member();
//		fakeUser.setId("testuser");
//		fakeUser.setName("testuser");
//		fakeUser.setNickname("테스트유저");
//		fakeUser.setEmailId("testuser");
//		fakeUser.setEmailDomain("gmail.com");
//		fakeUser.setPhone("01012345678");
//		fakeUser.setGender("F");
//		fakeUser.setBirthDate("2000-01-01");
//
//		session.setAttribute("loginMember", fakeUser); // 세션에 강제로 로그인 사용자 주입
//
//		return "redirect:/member/update"; // 로그인된 상태로 수정 페이지로 이동
//	}
// *******************************

	// 비밀번호 변경 폼
	@GetMapping("/changePassword")
	public String changePasswordForm(HttpSession session, Model model) {
		Member member = (Member) session.getAttribute("loginMember");

		// null 체크
		if (member == null) {
			return "redirect:/member/login"; // 로그인 안돼있으면 로그인 페이지로 보내기
		}

		model.addAttribute("loginMember", member);
		return "member/changePassword";
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
			return "redirect:/member/changePassword";
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
			return "redirect:/member/changePassword";
		}

		// 비밀번호 업데이트
		member.setPw(passwordEncoder.encode(newPassword));
		memberService.updatePassword(member);

		redirectAttributes.addFlashAttribute("success", "✅ 비밀번호가 성공적으로 변경되었습니다.");
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

	// 아이디 비번찾기
	@GetMapping("/find")
	public String find() {
		return "member/find";
	}

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
			return "redirect:/member/delete";
		}

		// 실제 비밀번호 비교 (암호화된 비밀번호와 비교)
		if (!passwordEncoder.matches(pw, loginMember.getPw())) {
			redirectAttributes.addFlashAttribute("error", "비밀번호가 올바르지 않습니다.");
			return "redirect:/member/delete";
		}

		// 탈퇴 처리
		boolean result = memberService.deleteMember(loginMember.getId());
		if (!result) {
			redirectAttributes.addFlashAttribute("error", "회원 탈퇴에 실패했습니다.");
			return "redirect:/main";
		}

		
		// 탈퇴 성공시 세션 끊고 메인으로 이동
		session.invalidate();
		return "redirect:/main";
	}

}
