package edu.kh.norazo.myPage.controller;

import java.io.Console;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.norazo.myPage.model.service.MyPageService;
import edu.kh.norazo.member.model.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("myPage")
public class MyPageController {

	private final MyPageService service;
	
	@GetMapping("main")
	public String myPage() {
		return "myPage/myPage";
	}
	
	@PostMapping("main")
	public String myPagePost() {
		return "myPage/myPage";
	}
	
	@GetMapping("edit")
	public String edit(@SessionAttribute("loginMember") Member loginMember,
			Model model) {
		// 주소만 꺼내옴
				String memberAddress = loginMember.getMemberAddress();
				
				// 주소가 있을 경우에만 동작
				if(memberAddress != null) {
					
					// 구분자 "^^^"를 기준으로
					// memberAddress 값을 쪼개어 String[]로 반환
					String[] arr = memberAddress.split("\\^\\^\\^");
					
					// "04540^^^서울 중구 남대문로 120^^^3층 E 강의장"
					// --> ["04540", "서울 중구 남대문로 120", "3층 E 강의장"]
					//			0			1						2
					model.addAttribute("postcode",      arr[0]);
					model.addAttribute("address" ,      arr[1]);
					model.addAttribute("detailAddress", arr[2]);
					
				}
		return "myPage/edit";
	}
	
	@PostMapping("edit")
	public String updateInfo(Member inputMember, 
					@SessionAttribute("loginMember") Member loginMember,
					@RequestParam("memberAddress") String[] memberAddress,
					RedirectAttributes ra
			) {
		
		// inputMember에 로그인한 회원번호 추가
		int memberNo = loginMember.getMemberNo();
		inputMember.setMemberNo(memberNo);
		System.out.println(inputMember);
		
		
		// 회원 정보 수정 서비스 호출
		int result = service.updateInfo(inputMember, memberAddress);
		
		String message = null;
		
		if(result > 0) {
			message = "회원 정보 수정 성공!!";
			
			// loginMember는
			// 세션에 저장된 로그인한 회원 정보가 저장된 객체를 참조하고 있다!!
			
			// -> loginMember를 수정하면
			//    세션에 저장된 로그인한 회원 정보가 수정된다!
			
			// == 세션 데이터와 DB 데이터를 맞춤
			
			loginMember.setMemberNickname( inputMember.getMemberNickname() );
			
			loginMember.setGender( inputMember.getGender() );
			System.out.println(inputMember.getGender());
			System.out.println(loginMember.getGender());
			
			loginMember.setMemberAddress( inputMember.getMemberAddress() );
			
			loginMember.setMemberIntroduce( inputMember.getMemberIntroduce() );
			
		} else {
			message = "회원 정보 수정 실패..";
		}
		
		ra.addFlashAttribute("message", message);
				
		
		return "redirect:edit";
	}

	@GetMapping("changePw")
	public String changePw() {
		return "myPage/changePw";
	}
	
	@PostMapping("changePw")
	public String changePw(@RequestParam Map<String, Object> paramMap,
			@SessionAttribute("loginMember") Member loginMember,
			RedirectAttributes ra) {
				// 로그인한 회원 번호
				int memberNo = loginMember.getMemberNo();
				
				// 현재 + 새 + 회원번호를 서비스로 전달
				int result = service.changePw(paramMap, memberNo);
				
				String path = null;
				String message = null;
				
				if(result > 0) {
					path = "/myPage/main";
					message = "비밀번호가 변경 되었습니다";
				} else {
					path = "/myPage/changePw";
					message = "현재 비밀번호가 일치하지 않습니다";
				}
				
				ra.addFlashAttribute("message", message);
				
				return "redirect:" + path;
				
	}
	
	@GetMapping("profile")
	public String profileImagedit() {
		return "myPage/myPage";
	}
	
	@PostMapping("profile")
	public String profileImagedit(@RequestParam("profileImg") MultipartFile profileImg,
			@SessionAttribute("loginMember") Member loginMember,
			RedirectAttributes ra) throws Exception {
		log.debug("test : " + profileImg);
		// 서비스 호출
		// /myPage/profile/변경된 파일명 형태의 문자열
		// 현재 로그인한 회원의 PROFILE_IMG 컬럼값으로 수정 UPDATE
		int result = service.profile(profileImg, loginMember);
		String message = null;
		if(result>0) message = "변경 성공" ;
		else message="변경실패";
		ra.addFlashAttribute("message", message);
		return "redirect:profile";
	}
	
	
	@ResponseBody
	@GetMapping("checkNickname")
	public int checkNickname(Member inputMember, @SessionAttribute("loginMember") Member loginMember, @RequestParam("memberNickname")String memberNickname) throws Exception {
		int memberNo = loginMember.getMemberNo();
		inputMember.setMemberNo(memberNo);
		return service.checkNickname(memberNickname, inputMember);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
