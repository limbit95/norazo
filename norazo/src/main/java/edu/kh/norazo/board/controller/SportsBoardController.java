package edu.kh.norazo.board.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.kh.norazo.board.model.service.SportsBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("sportsBoard")
public class SportsBoardController {

	private final SportsBoardService service;
	
	// 모임 게시판  종합 페이지
	@GetMapping("/main")
	public String sportsBoardPage() {
		return "board/sportsBoard";
	}
	
	@GetMapping("{sportsCode:[a-z]+}")
	public String test(@PathVariable("sportsCode") String sportsCode,
					   @RequestParam(value="cp", required=false, defaultValue="1") int cp,
					   Model model) {
		Map<String, Object> map = service.selectBoardList(sportsCode, cp);
		
		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("boardList", map.get("boardList"));
		model.addAttribute("sportsKrName", map.get("sportsKrName"));
		
		return "board/sportsBoard";
	}
	
	// "sportsBoard/{sportsType:[0-9]+}/{boardNo:[0-9]+}/detail" = ex : 축구 게시판 1번 게시글
	
}
