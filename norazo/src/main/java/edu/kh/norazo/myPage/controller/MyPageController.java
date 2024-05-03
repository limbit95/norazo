package edu.kh.norazo.myPage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.norazo.myPage.model.service.MyPageService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("myPage")
public class MyPageController {

	private final MyPageService service;
	
	@GetMapping("main")
	public String myPage() {
		return "myPage/myPage";
	}
	
	@PostMapping("main")
	public String myPagePost() {
		return "myPage/myPage";
	}
	
	@GetMapping("create")
	public String create() {
		return "myPage/create";
	}
	
	@GetMapping("belong")
	public String belong() {
		return "myPage/belong";
	}
	
	@GetMapping("heart")
	public String heart() {
		return "myPage/heart";
	}
	
	@GetMapping("edit")
	public String edit() {
		return "myPage/edit";
	}

	@GetMapping("changePw")
	public String changePw() {
		return "myPage/changePw";
	}
}
