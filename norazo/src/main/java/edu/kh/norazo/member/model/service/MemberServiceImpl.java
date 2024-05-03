package edu.kh.norazo.member.model.service;

import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		if(inputMember.getMemberAddres().equals(",,")) {
			
			String address = String.join("^^^", memberAddress);
			inputMember.setMemberAddres(address);
		} else {
			
			inputMember.setMemberAddres(null);
		}
		
		String encPw = bcrypt.encode(inputMember.getMemberPw());
		inputMember.setMemberPw(encPw);
		
		return mapper.signUp(inputMember);
	}




}
