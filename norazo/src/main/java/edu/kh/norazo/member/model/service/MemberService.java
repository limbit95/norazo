package edu.kh.norazo.member.model.service;

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

}
