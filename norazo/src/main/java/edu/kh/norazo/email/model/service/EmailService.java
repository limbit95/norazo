package edu.kh.norazo.email.model.service;

import java.util.Map;

import edu.kh.norazo.member.model.dto.Member;



public interface EmailService {

	/** 이메일 전송
	 * @param string
	 * @param email
	 * @return
	 */
	String sendEmail(String htmlName, String email);

	/** 인증 번호 확인
	 * @param map
	 * @return
	 */
	int checkAuthKey(Map<String, Object> map);

	
	String sendPwEmail(Member inputMember, String findPassword);

		
}
