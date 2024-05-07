package edu.kh.norazo.member.model.service;

import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.norazo.email.model.service.EmailService;
import edu.kh.norazo.member.model.dto.Member;
import edu.kh.norazo.member.model.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

	private final MemberMapper mapper;
	private final EmailService emailService;
	private final BCryptPasswordEncoder bcrypt;
	
	// 로그인 서비스 
	@Override
	public Member login(Member inputMember) {
								
		Member loginMember = mapper.login(inputMember.getMemberEmail());
			
		// 비밀번호가 일치하지 않는다면 ------------- 
		if(loginMember == null) return null;
		
		if( !bcrypt.matches(inputMember.getMemberPw(), loginMember.getMemberPw())) {
			return null;
		}
		
		loginMember.setMemberPw(null);
		// -------------------------
		return loginMember;
	}

	@Override
	public int checkEmail(String memberEmail) {
		
		return mapper.checkEmail(memberEmail);
	}

	@Override
	public int checkNickname(String memberNickname) {
		// TODO Auto-generated method stub
		return mapper.checkNickname(memberNickname);
	}

	@Override
	public int signUp(Member inputMember, String[] memberAddress) {
		
		// 주소 처리 
		// 주소가 입력된 경우
		if( !inputMember.getMemberAddress().equals(",,")) {
			
			String address = String.join("^^^", memberAddress);
			
			inputMember.setMemberAddress(address);
			
		// 입력 안했을시 null 주입 
		} else {
			
			inputMember.setMemberAddress(null);
		}
		log.debug("address : "+ inputMember.getMemberAddress());
		// 비밀번호 암호화 
		String encPw = bcrypt.encode(inputMember.getMemberPw());
		inputMember.setMemberPw(encPw);
		
		return mapper.signUp(inputMember);
	}

	// 비밀번호 찾기 
	@Override
	public int findPw(Member inputMember) {
		
		String findPassword = createPassword();
		
		// 평문인 findPassword를 메일로 보내기
		String result = emailService.sendPwEmail(inputMember, findPassword);
		
		// 암호화된 비밀번호를 디비에 업데이트
		
		findPassword = bcrypt.encode(result);
		
		inputMember.setMemberPw(findPassword);
		
		log.debug("findPassword : " + findPassword);
		
		return mapper.updatePw(inputMember);
	}


	/** 임시 비밀번호 생성 
	 * @return 8 자리 비밀번호 
	 */
	private String createPassword() {
		String key = "";
	       for(int i=0 ; i< 8 ; i++) {
	          
	           int sel1 = (int)(Math.random() * 3); // 0:숫자 / 1,2:영어
	          
	           if(sel1 == 0) {
	              
	               int num = (int)(Math.random() * 10); // 0~9
	               key += num;
	              
	           }else {
	              
	               char ch = (char)(Math.random() * 26 + 65); // A~Z
	              
	               int sel2 = (int)(Math.random() * 2); // 0:소문자 / 1:대문자
	              
	               if(sel2 == 0) {
	                   ch = (char)(ch + ('a' - 'A')); // 대문자로 변경
	               }
	              
	               key += ch;
	           }
	          
	       }
	       return key;
	}

}
