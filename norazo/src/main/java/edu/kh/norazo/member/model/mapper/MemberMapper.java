package edu.kh.norazo.member.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.norazo.member.model.dto.Member;

@Mapper
public interface MemberMapper {

	/** 로그인 SQL
	 * @param memberEmail
	 * @return
	 */
	Member login(String memberEmail);

	/** 이메일 중복 검사 SQL
	 * @param memberEmail
	 * @return
	 */
	int checkEmail(String memberEmail);

	/** 닉네임 중복 검사 SQL
	 * @param memberNickname
	 * @return
	 */
	int checkNickname(String memberNickname);

	int signUp(Member inputMember);

	/** 비밀번호 업데이트 SQL
	 * @param inputMember
	 * @return
	 */
	int updatePw(Member inputMember);
	
	/** 탈퇴한 회원 목록 조회
	 * @return
	 */
	List<Member> secessionMemberList();

	/** 탈퇴한 회원의 게시글 모두 삭제 (N -> Y)
	 * @param memberList
	 * @return
	 */
	int deleteAllBoard(Member member);

	/** 탈퇴한 회원만 관리하는 테이블로 회원 정보 이동
	 * @param member
	 * @return
	 */
	int moveToSecessionGroup(Member member);

	/** 탈퇴한 회원 기존 "Member" 테이블에서 제거
	 * @param member
	 * @return
	 */
	int deleteSecessionMemberList(Member member);
	
}
