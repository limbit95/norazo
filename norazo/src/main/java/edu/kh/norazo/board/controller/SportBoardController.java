package edu.kh.norazo.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.norazo.board.model.service.SportBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("sportsBoard")
public class SportBoardController {

	private final SportBoardService service;
	
	// 모임 게시판  종합 페이지
	@GetMapping("/main")
	public String sportsBoardPage() {
		return "board/sportsBoard";
	}
	
	@GetMapping("{sportsName:[a-z]+}")
	public String test(@PathVariable("sportsName") String sportsName) {
		
		log.debug("종목 : " + sportsName);
		
		return "redirect:/";
	}
	
	// "sportsBoard/{sportsType:[0-9]+}/{boardNo:[0-9]+}/detail" = ex : 축구 게시판 1번 게시글
	
}
