package edu.kh.norazo.myPage.model.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.norazo.member.model.dto.Member;

@Mapper
public interface MyPageMapper {

	int profile(Member mem);

	int updateInfo(Member inputMember);

	int checkNickname(String nickname, Member inputMember);

	String selectPw(int memberNo);

	int changePw(Map<String, Object> paramMap);
}
