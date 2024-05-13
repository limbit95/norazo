package edu.kh.norazo.myPage.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.norazo.member.model.dto.Member;

public interface MyPageService {

	int profile(MultipartFile profileImg, Member loginMember) throws Exception;

	int updateInfo(Member inputMember, String[] memberAddress);

	int checkNickname(String memberNickname, Member loginMember) throws Exception;

	int changePw(Map<String, Object> paramMap, int memberNo);

	Map<String, Object> selectmyCreateBoardList(Map<String, Object> map);

	Map<String, Object> selectmyBelongBoardList(Map<String, Object> map);

	Map<String, Object> selectmyHeartBoardList(Map<String, Object> map);

	int secession(String memberPw, int memberNo);

}
