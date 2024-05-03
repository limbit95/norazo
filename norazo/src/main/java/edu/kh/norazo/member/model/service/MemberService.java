package edu.kh.norazo.member.model.service;

import java.util.Map;

import edu.kh.norazo.member.model.dto.Member;

public interface MemberService {

	/** 로그인 서비스 
	 * @param inputMember
	 * @return
	 */
	Member login(Member inputMember);

	/** 이메일 중복 검사
	 * @param memberEmail
	 * @return
	 */
	int checkEmail(String memberEmail);

	/** 닉네임 중복검사
	 * @param memberNickname
	 * @return
	 */
	int checkNickname(String memberNickname);

	/** 회원 가입 서비스 
	 * @param inputMember
	 * @param memberAddress
	 * @return 
	 */
	int signUp(Member inputMember, String[] memberAddress);

}
