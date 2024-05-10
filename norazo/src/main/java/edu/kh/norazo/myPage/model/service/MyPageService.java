package edu.kh.norazo.myPage.model.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.norazo.member.model.dto.Member;

public interface MyPageService {

	int profile(MultipartFile profileImg, Member loginMember) throws Exception;

	int updateInfo(Member inputMember, String[] memberAddress);

	int checkNickname(String memberNickname, Member loginMember) throws Exception;

	int changePw(Map<String, Object> paramMap, int memberNo);

	Map<String, Object> selectmyCreateBoardList(String boardCode, int cp, int memberNo);

	Map<String, Object> selectmyBelongBoardList(String boardCode, int cp, int memberNo);

	Map<String, Object> selectmyHeartBoardList(String boardCode, int cp, int memberNo);
}
