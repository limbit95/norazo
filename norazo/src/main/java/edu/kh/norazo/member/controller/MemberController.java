package edu.kh.norazo.member.controller;

import java.util.Map;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.norazo.member.model.dto.Member;
import edu.kh.norazo.member.model.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("member")
@Controller
@RequiredArgsConstructor
@Slf4j
@SessionAttributes({"loginMember"})
public class MemberController {

	private final MemberService service;
	
	@GetMapping("login")
	public String login() {
		return "member/login";
	}
	
	/** 로그 아웃
	 * @param status
	 * @return
	 */
	@GetMapping("logout")
	public String logout(SessionStatus status, 
						 RedirectAttributes ra) {
		status.setComplete();
		
		ra.addFlashAttribute("message","로그아웃 되었습니다.");
		
		return "redirect:/";
	}
	
	/** 로그인 : 로그인시 메인페이지 이동 
	 * @return : /
	 */
	@PostMapping("login")
	public String login(Member inputMember,
						RedirectAttributes ra,
						Model model,
						@RequestParam(value = "saveId", required = false)String saveId,
						HttpServletResponse resp) {
		
		Member loginMember = service.login(inputMember);
		
		String path = null;
		
		// 로그인 실패 시 
		if(loginMember == null) {
			ra.addFlashAttribute("message","아이디 또는 비밀번호가 일치하지 않습니다.");
			path = "login";
		}
		// 로그인 성공 시 
		if(loginMember != null) {
			model.addAttribute("loginMember",loginMember);
			path="/";
			
			// *******************************
			// 아이디 저장(Cookie) 
			Cookie cookie = new Cookie("saveId", loginMember.getMemberEmail());
			
			cookie.setPath("/");
			
			// 쿠키 만료 기간 
			if(saveId != null) {
				cookie.setMaxAge(60 *60 * 24 * 30);
				
			} else { // 미체크시 
				cookie.setMaxAge(0);
			}
			
			// 클라이언트 전달 
			resp.addCookie(cookie);
		}
		
		return "redirect:"+path;
	}
	
	@GetMapping("signUp")
	public String signUpPage() {
		return "member/signUp";
	}
	
	
	/** 이메일 중복 검사
	 * @param memberEmail
	 * @return
	 */
	@ResponseBody
	@GetMapping("checkEmail")
	public int checkEmail(@RequestParam("memberEmail")String memberEmail) {
		
		return service.checkEmail(memberEmail);
	}
	
	/** 닉네임 중복 검사
	 * @return
	 */
	@ResponseBody
	@GetMapping("checkNickname")
	public int checkNickname(@RequestParam("memberNickname")String memberNickname) {
		return service.checkNickname(memberNickname);
	}
	 
	/** 회원 가입 
	 * @param inputMember
	 * @param memberAddress
	 * @param birthMap
	 * @param genderMap
	 * @param ra
	 * @return
	 */
	@PostMapping("signUp")
	public String signUp(Member inputMember,
						 @RequestParam("memberAddress")String[]memberAddress,
						 @RequestParam("birthday")String[]birthday,
						 RedirectAttributes ra) {
		
		String birthdayString = birthday[0] + "-" + birthday[1] + "-" + birthday[2];
		
		log.debug("birthdayString : " + birthdayString);
		inputMember.setBirthDate(birthdayString);

		int result = service.signUp(inputMember, memberAddress);
		
		String path = null;
		String message = null;
		
		if(result > 0) {
			message = inputMember.getMemberNickname() + "님의 가입을 환영 합니다!";
			path = "/member/login";
		
		} else {
			message = "회원 가입 실패";
			path = "/member/signUp";
		}
		
		ra.addFlashAttribute("message", message);
		return "redirect:"+path;
	}
	@GetMapping("findPw")
	public String findPw() {
		
		return "member/findPw";
	}
	
	/** 비밀번호 찾기 
	 * @return
	 */
	@ResponseBody
	@PostMapping("findPw")
	public int findPw(@RequestBody String inputEmail,
						 Member inputMember,
						 RedirectAttributes ra) {
		inputMember.setMemberEmail(inputEmail);
		
		int result = service.findPw(inputMember);
		
		return result;
	}
	

}