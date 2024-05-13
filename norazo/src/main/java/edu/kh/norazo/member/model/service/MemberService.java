package edu.kh.norazo.member.model.service;

import java.util.List;
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

	/** 비밀번호 찾기 
	 * @param inputMember
	 * @return
	 */
	int findPw(Member inputMember);

	/** 탈퇴한 회원 목록 조회
	 * @return
	 */
	List<Member> secessionMemberList();


	/** 탈퇴한 회원의 게시글 삭제 및 탈퇴 회원 관리 테이블로 이동
	 * @param memberList
	 * @return
	 */
	int secessionMemberManager(List<Member> memberList);
	
}
