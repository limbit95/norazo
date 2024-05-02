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

	int checkEmail(String memberEmail);
	
}
