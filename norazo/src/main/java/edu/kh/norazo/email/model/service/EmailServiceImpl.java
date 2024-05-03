package edu.kh.norazo.email.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import edu.kh.norazo.email.model.mapper.EmailMapper;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(rollbackFor=Exception.class)
@Service
public class EmailServiceImpl implements EmailService{
	
	private final EmailMapper mapper;
	private final SpringTemplateEngine templateEngine;
	private final JavaMailSender mailSender;

	@Override
	public String sendEmail(String htmlName, String email) {

		String authKey = createAuthKey();
		
		try {
			// 메일 제목 
			String subject = null;
			
			switch (htmlName) {
			case "signUp":
				subject = "[norazo] 회원 가입 인증번호 입니다."; break;
				
			}	
			// 인증 메일 보내기 
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			
			helper.setTo(email);
			helper.setSubject(subject);
			
			helper.setText( loadHtml(authKey, htmlName) );
			
			helper.addInline("logo",new ClassPathResource("static/images/logo.png"));
			mailSender.send(mimeMessage);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		 // 이메일 + 인증 번호 테이블 저장 
		Map<String, String> map = new HashMap<>();
		map.put("authKey", authKey);
		map.put("email", email);
		
		int result = mapper.updateAuthKey(map);
		
		if(result == 0) {
			result = mapper.insertAuthKey(map);
		}
		
		if(result == 0) return null;
		
		
		
		return authKey;
	}
	
	private String loadHtml(String authKey, String htmlName) {
			
			Context context = new Context();
			
			context.setVariable("authKey", authKey);
			
			
			return templateEngine.process("email/"+ htmlName, context);
		}
	
	/** 인증번호 생성 (영어 대문자 + 소문자 + 숫자 6자리)
	    * @return authKey
	    */
	   public String createAuthKey() {
	   	String key = "";
	       for(int i=0 ; i< 6 ; i++) {
	          
	           int sel1 = (int)(Math.random() * 3); // 0:숫자 / 1,2:영어
	          
	           if(sel1 == 0) {
	              
	               int num = (int)(Math.random() * 10); // 0~9
	               key += num;
	              
	           }else {
	              
	               char ch = (char)(Math.random() * 26 + 65); // A~Z
	              
	               int sel2 = (int)(Math.random() * 2); // 0:소문자 / 1:대문자
	              
	               if(sel2 == 0) {
	                   ch = (char)(ch + ('a' - 'A')); // 대문자로 변경
	               }
	              
	               key += ch;
	           }
	          
	       }
	       return key;
	   }

	   
	@Override
	public int checkAuthKey(Map<String, Object> map) {

		return mapper.checkAuthKey(map);
	}

}
