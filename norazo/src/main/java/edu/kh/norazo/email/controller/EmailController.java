package edu.kh.norazo.email.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.norazo.email.model.service.EmailService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("email")
public class EmailController {
	
	
	private final EmailService service;

	/** 이메일 전송 
	 * @param email
	 * @return
	 */
	@ResponseBody
	@PostMapping("signUp")
	public int signUp(@RequestBody String email) {
		
		String authKey = service.sendEmail("signUp",email);
		
		
		if(authKey != null) {
			
			return 1;
		}
		
		return 0;
	}
	
	@ResponseBody
	@PostMapping("checkAuthKey")
	public int checkAuthKey(@RequestBody Map<String, Object> map) {
		
		return service.checkAuthKey(map);
	}
}
