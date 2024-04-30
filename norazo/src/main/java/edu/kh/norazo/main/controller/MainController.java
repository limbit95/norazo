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
		return "common/main-category";
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
	
}