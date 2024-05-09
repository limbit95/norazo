package edu.kh.norazo.board.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.kh.norazo.board.model.dto.Board;
import edu.kh.norazo.board.model.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("board")
public class BoardController {
	
	private final BoardService service;
	
	@GetMapping("{boardCode:[a-z]+}")
	public String selectBoardList(@PathVariable("boardCode") String boardCode,
								  @RequestParam(value = "cp", required = false,defaultValue = "1")int cp,
								  Model model) {
		
		
		log.debug("boardCode : " + boardCode);
		
		Map<String, Object> map = null;
		
		if(boardCode.equals("free")) {
			
			map = service.selectFreeBoardList(boardCode,cp);
		}	
		
		if(boardCode.equals("faq")) {
			
			map = service.selectFaqBoardList(boardCode,cp);
		}
		
		
		model.addAttribute("pagination",map.get("pagination"));
		
		model.addAttribute("boardList",map.get("boardList"));
		
		List<Board> boardList = (List<Board>) map.get("boardList");
		
		model.addAttribute("boardName", boardList.get(0).getBoardName());
		

		
		return "board/boardList";
	}
	
	// 화면 보기용
	@GetMapping("boardDetail")
	public String boardDetail() {
		
		return "board/boardDetail";
	}
	// 화면 보기용 
	@GetMapping("comment")
	public String boardComment() {
		
		return "board/comment";
	}
	
	
}