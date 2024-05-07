package edu.kh.norazo.member.model.mapper;

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
	int findPw(Member inputMember);
	
}
