package edu.kh.norazo.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
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
	
	
	/** 로그인 : 로그인시 메인페이지 이동 
	 * @return : /
	 */
	@PostMapping("login")
	public String login(Member inputMember,
						RedirectAttributes ra,
						Model model,
						@RequestParam(value = "saveId", required = false)String saveId,
						HttpServletResponse resp) {
		
		log.debug("확인" + inputMember);
		
		Member loginMember = service.login(inputMember);
		
		log.debug("loginMember : "+loginMember);
		
		String path = null;
		
		// 로그인 실패 시 
		if(loginMember == null) {
			ra.addFlashAttribute("message","아이디 또는 비밀번호가 일치하지 않습니다.");
			path = "/login";
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
	public String signUp() {
		
		return "member/signUp";
	}
}