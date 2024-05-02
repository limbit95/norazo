package edu.kh.norazo.main.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	// 스포츠 카테고리 목록 페이지로 이동
	@GetMapping("category")
	public String categoryPage(Model model) {
		// DB에 존재하는 스포츠 카테고리 목록 얻어오기
		List<Map<String, Object>> sportsTypeList = service.selectSportsTypeList();
		
		model.addAttribute("sportsTypeList", sportsTypeList);
		
		return "common/fragments/main-category";
	}
	
	@RequestMapping("loginError")
	public String loginError(RedirectAttributes ra) {
		ra.addFlashAttribute("message", "로그인 후 이용해주세요");
		return "redirect:/";
	}
	
	
	
	
	
	
	
	
	
	@GetMapping("modal")
	public String modal() {
		return "common/modal";
	}
	
	@GetMapping("boardWrite")
	public String bordWrite() {
		return "board/boardWrite";
	}

}