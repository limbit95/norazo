package edu.kh.norazo.myPage.model.service;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.norazo.member.model.dto.Member;

public interface MyPageService {

	int profile(MultipartFile profileImg, Member loginMember) throws Exception;

	int updateInfo(Member inputMember, String[] memberAddress);

	int checkNickname(String memberNickname, Member loginMember) throws Exception;

}
