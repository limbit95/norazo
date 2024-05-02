package edu.kh.norazo.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import edu.kh.norazo.main.model.mapper.MainMapper;
import edu.kh.norazo.main.model.service.MainService;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class MainController {
	
	private final MainService service;

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
		
		return "member/login";
	}
	
	@GetMapping("sportsBoard")
	public String sportsBoardPage() {
		return "board/sportsBoard";
	}
	
	@GetMapping("modal")
	public String modal() {
		return "common/modal";
	}

	@GetMapping("signUp")
	public String signUp() {
		return "common/signUp";
	}
	@GetMapping("boardWrite")
	public String bordWrite() {
		return "board/boardWrite";
	}

}