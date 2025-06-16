package com.example.demo.controller;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.AccountVerification;
import com.example.demo.model.Member;
import com.example.demo.service.EmailService;
import com.example.demo.service.MemberService;
import com.example.demo.service.VerifyService;

@Controller
public class VerifyController {

	@Autowired
	private VerifyService verifyService;

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private MemberService memberService;
	
//	// 이메일 유효성 검사 // 지선님이 구현한거 사용
//	@RequestMapping("/checkEmail")
//	@ResponseBody
//	public Map<String, Boolean> checkEmail(@RequestParam("email") String email) {
//
//		// 회원 이메일 존재여부 member 테이블에서 찾기
//		boolean exists = verifyService.isEmailExist(email);
//		Map<String, Boolean> result = new HashMap<>();
//		result.put("exists", exists);
//
//		return result;
//	}
//------------------------코드 입력후 작동되는 영역------------------------	
	//(이메일찾기) 유저가 입력한 코드값과 DB에저장된 코드값이 일치하는지 확인 
	@GetMapping("/findId")
	@ResponseBody
	public Map<String, Object> verifyCode(
	    @RequestParam("code") String code,
	    @RequestParam("email") String email,
	    @RequestParam("type") String type) {

		Map<String, Object> response = new HashMap<>();

		AccountVerification verification = verifyService.findByEmailAndType(email, type);

		if (verification != null && verification.getCode().equals(code)) {
			// 일치할 경우 - 아이디도 함께 보내기
			verifyService.updateVerificationTable(code, type);
			Member member = memberService.findByEmail(email);
			response.put("success", true);
			response.put("userId", member.getId());
		} else {
			response.put("success", false);
		}
		System.out.println("검증용 입력 코드: " + code);
		System.out.println("DB에서 가져온 CODE: " + verification.getCode());
		System.out.println("EMAIL: " + email + ", TYPE: " + type);
		return response;
	}
	
	//(회원가입 인증 & 비밀번호 찾기) 사용자가 6자리 코드 입력 또는 인증버튼 눌렀을시 코드
		@RequestMapping("/verifyCode")
		public String verifyCode(@RequestParam("code") String code, @RequestParam("type") String type, Model model) {
			int result;
			AccountVerification verification = verifyService.findByCode(code, type);

			// 해당코드가 없을때 or 이미 확인 되었을때 -> 인증실패
			if (verification == null || "Y".equals(verification.getVerified())) {
				result = 0;
				// 만료시간이 현재시간보다 이전이면 -> 인증시간 초과
			} else if (verification.getExpiresAt().before(new Timestamp(System.currentTimeMillis()))) {
				result = -1;
				// 인증 성공 -> ACCOUNT_VERIFICATION 테이블 인증성공 여부 & 인증시간 UPDATE
			} else {
				result = verifyService.updateVerificationTable(code, type);
			}

			// 결과 값 VIEW 페이지로 넘겨주기
			model.addAttribute("result", result);
			model.addAttribute("type", type);

			// 성공결과 VIEW 페이지로 이동
			return "verify/verifyResult";
		}

		
//---------------------회원가입,아이디찾기,비밀번호찾기 이메일 전송 ------------------------		
	
		// 회원가입시 회원인증 이메일 전송
	@RequestMapping("/sendNewMemberVerify")
	@ResponseBody
	public void sendNewMemberVerify(@RequestParam("email") String email) {
		// 6자리 랜덤 코드 생성
		SecureRandom secureRandom = new SecureRandom();
		int sixDigitRan = secureRandom.nextInt(1_000_000);
		String code = String.format("%06d", sixDigitRan);

		// email로 MEMBER 테이블에서 해당 회원찾기
		AccountVerification verification = new AccountVerification();
		Member member = verifyService.findIdMember(email);
		System.out.println("member: " + member);

		// Account_Verification 테이블에 insert
		verification.setId(member.getId());
		verification.setCode(code);
		verification.setType("MEMBER_JOIN");
		Timestamp expiresAt = Timestamp.valueOf(LocalDateTime.now().plusMinutes(10));
		verification.setExpiresAt(expiresAt);
		verifyService.insertVerification(verification);

		// 이메일 전송 서비스(sendVerificationEmail 함수로 이메일주소값 & 코드값)
		emailService.sendVerificationEmail(member.getEmailId() + "@" + member.getEmailDomain(), code);
	}

	// 아이디 찾기 인증번호 전송
	@RequestMapping("/sendFindIdCode")
	@ResponseBody
	public void sendFindIdCode(@RequestParam("email") String email) {
		// 6자리 랜덤 코드 생성
		SecureRandom secureRandom = new SecureRandom();
		int sixDigitRan = secureRandom.nextInt(1_000_000);
		String code = String.format("%06d", sixDigitRan);

		// email로 MEMBER 테이블에서 해당 회원찾기
		AccountVerification verification = new AccountVerification();
		Member member = verifyService.findIdMember(email);
		System.out.println("member: " + member);

		// Account_Verification 테이블에 insert
		verification.setId(member.getId());
		verification.setCode(code);
		verification.setType("FIND_ID");
		Timestamp expiresAt = Timestamp.valueOf(LocalDateTime.now().plusMinutes(10));
		verification.setExpiresAt(expiresAt);
		verifyService.insertVerification(verification);

		// 이메일 전송 서비스(sendFindIdEmail 함수로 이메일주소값 & 코드값)
		emailService.sendFindIdEmail(member.getEmailId() + "@" + member.getEmailDomain(), code);
	}

	// 비밀번호 찾기 인증번호 전송
	@RequestMapping("/sendFindPwCode")
	@ResponseBody
	public void sendFindPwCode(@RequestParam("email") String email) {
		// 6자리 랜덤 코드 생성
		SecureRandom secureRandom = new SecureRandom();
		int sixDigitRan = secureRandom.nextInt(1_000_000);
		String code = String.format("%06d", sixDigitRan);

		// email로 MEMBER 테이블에서 해당 회원찾기
		AccountVerification verification = new AccountVerification();
		Member member = verifyService.findIdMember(email);
		System.out.println("member: " + member);

		// Account_Verification 테이블에 insert
		verification.setId(member.getId());
		verification.setCode(code);
		verification.setType("FIND_PASSWORD");
		Timestamp expiresAt = Timestamp.valueOf(LocalDateTime.now().plusMinutes(10));
		verification.setExpiresAt(expiresAt);
		verifyService.insertVerification(verification);

		// 이메일 전송 서비스(sendFindPwEmail 함수로 이메일주소값 & 코드값)
		emailService.sendFindPwEmail(member.getEmailId() + "@" + member.getEmailDomain(), code);
	}

}	

//-------------------UI 없었을때 테스트 용도------------------------- 	
	
//	@RequestMapping("/verifyNewMember")
//	public String verifyNewMember(@RequestParam("code") String code, @RequestParam("type") String type, Model model) {
//		int result;
//		AccountVerification verification = verifyService.findByCode(code, type);
//
//		if (verification == null || "Y".equals(verification.getVerified())) {
//			result = 0;
//			model.addAttribute("result", result);
//		} else if (verification.getExpiresAt().before(new Timestamp(System.currentTimeMillis()))) {
//			result = -1;
//		} else {
//			verifyService.updateVerificationTable(code, type);
//			result = verifyService.updateEmailVerified(code);
//		}
//
//		model.addAttribute("result", result);
//		return "verify/verifyResult";
//	}	

//	@RequestMapping("/test")
//	public String testVerify() {
//		//회원가입 인증 코드 생성
//		String code = UUID.randomUUID().toString();
//		String emailVerificationLink = code;
//		
//		//아이디 or 비밀번호 찾기 코드 생성
//		SecureRandom secureRandom = new SecureRandom();
//		int sixDigitRan = secureRandom.nextInt(1_000_000); //두번째 파라미터는 자리수
//		String sixDigitCode = String.format("%06d",sixDigitRan);
//		
//				  			    // "MEMBER_JOIN"   //1 회원가입인증   
//								// "FIND_PASSWORD" //그외 아이디 or 비밀번호 찾기
//								// "FIND_ID"
//		insertTest(sixDigitCode, "FIND_ID", 2);
//		
//		return "verify";
//	}

//	@RequestMapping("member/login")
//	public String login() {
//		return "member/login";
//	}
//	
//	@RequestMapping("verify/testForm")
//	public String testForm() {
//		return "verify/testForm";
//	}

//	@RequestMapping("verify/testSubmit")
//	public String testSubmit(@RequestParam("verifyCode") String code, @RequestParam("verifyType") String type, Model model) {
//		AccountVerification verification = verifyService.findByCode(code, type);
//		int result;
//		if(code.equals(verification.getCode())) {
//			result = verifyService.updateVerificationTable(code, type);
//			System.out.println("인증성공!!!!!!!!");
//			model.addAttribute("result", result);
//			return "verify/verifySucess";
//		}else {
//			result = 0;
//			System.out.println("인증실패");
//			return "verify/verifySucess";
//		}
//	}
//	

//	private void insertTest( String code, String type, int select) {
//		if(select == 1) {
//			Member member = new Member();
//			Timestamp ts = new Timestamp(System.currentTimeMillis());
//			member.setId("tester54");
//			member.setName("테스터54");
//			member.setNickname("TEST");
//			member.setPw("1234");
//			member.setBirthDate("19900101");
//			member.setEmailId("2j1william");
//			member.setEmailDomain("gmail.com");
//			member.setPhone("12345");
//			member.setGender("남");
//			member.setJoinDate(ts);
//			member.setState(2);
//			member.setEmailAd("Y");
//			member.setEmailVerified("N");
//			
//			AccountVerification verification = new AccountVerification();
//			
//			verification.setId(member.getId());
//			verification.setCode(code);
//			verification.setType(type);
//			Timestamp expiresAt = Timestamp.valueOf(LocalDateTime.now().plusMinutes(10));
//			verification.setExpiresAt(expiresAt);
//			
//			verifyService.insertTestMember(member);
//			verifyService.insertVerification(verification);
//			
//			emailService.sendVerificationEmail(member.getEmailId() + "@" + member.getEmailDomain(), code);
//		}else {
//			
//			AccountVerification verification = new AccountVerification();
//			Member member = verifyService.findIdMember("2j1william","gmail.com"); //member email 값이 추후에 들어가야함
//			System.out.println("member: "+member);
//			
//			verification.setId(member.getId());
//			verification.setCode(code);
//			verification.setType(type);
//			
//			Timestamp expiresAt = Timestamp.valueOf(LocalDateTime.now().plusMinutes(10));
//			verification.setExpiresAt(expiresAt);
//			
//			verifyService.insertVerification(verification);
//			
////			emailService.sendFindIdEmail(member.getEmailId() + "@" + member.getEmailDomain(), code);
//			emailService.sendFindPwEmail(member.getEmailId() + "@" + member.getEmailDomain(), code);
//			
//		}
//	}


