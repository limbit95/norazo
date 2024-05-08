package edu.kh.norazo.board.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.norazo.board.model.service.EditBoardService;
import edu.kh.norazo.main.model.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("editBoard")
public class EditBoardController {

	private final EditBoardService service;

	private final MainService mainService;
	
	/** 모임글 작성 페이지 이동
	 * @param sportsCode
	 * @return
	 */
	@GetMapping("{sportsCode:[a-z]+}/insert")
	public String boardWrite(@PathVariable("sportsCode") String sportsCode,
							 Model model) {
		
		List<Map<String, Object>> sportsTypeList = mainService.selectSportsTypeList();
		
		model.addAttribute("sportsTypeList", sportsTypeList);
		
		return "board/sportsBoardWrite";
	}
	
}