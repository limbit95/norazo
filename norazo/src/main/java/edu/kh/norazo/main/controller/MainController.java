package edu.kh.norazo.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@RequestMapping("/")
	public String mainPage() {
		return "common/main";
	}
	
	@GetMapping("category")
	public String categoryPage() {
		return "common/fragments/main-category";
	}
	@GetMapping("login")
	public String login() {
		
		return "common/login";
	}
	
	@GetMapping("sportsBoard")
	public String sportsBoardPage() {
		return "board/sportsBoard";
	}
	
	@GetMapping("modal")
	public String modal() {
		return "common/modal";
	}

	@GetMapping("myPage")
	public String myPage() {
		return "common/myPage";
	}
	

	@GetMapping("signUp")
	public String signUp() {
		return "common/signUp";
	}
		

	
	
	
	@GetMapping("test")
	public String test() {
		return "common/main-copy";

	}
	
}