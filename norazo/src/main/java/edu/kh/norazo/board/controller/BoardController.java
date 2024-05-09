package edu.kh.norazo.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.norazo.board.model.dto.Board;
import edu.kh.norazo.board.model.service.BoardService;
import edu.kh.norazo.member.model.dto.Member;
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
	
	/** 게시글 상세 조회
	 * @param boardCode
	 * @param boardNo
	 * @param model
	 * @param ra
	 * @return
	 */
	@GetMapping("{boardCode:[a-z]+}/{boardNo:[0-9]+}")
	public String boardDetail(@PathVariable("boardCode") String boardCode,
							  @PathVariable("boardNo") int boardNo,
							  Model model,
							  RedirectAttributes ra) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("boardCode", boardCode);
		map.put("boardNo", boardNo);
					
			if(boardCode.equals("free")) {
				map.put("boardCode", 2);
			}
			
			if(boardCode.equals("faq")) {
				map.put("boardCode", 3);
			}
			

		Board board = service.selectOne(map);
		
		String path = null;
		
		if(board == null) {
			path = "redirect:/board" + boardCode;
			ra.addFlashAttribute("message","게시글이 존재하지 않습니다.");
			
		} else {
			
			path = "board/boardDetail";
			
			model.addAttribute("board",board);
		}
		return path;
	}
	
	/** 00게시판 작성 이동
	 * @param boardCode
	 * @return
	 */
	@GetMapping("{boardCode:[a-z]+}/insert")
	public String boadWrite(@PathVariable("boardCode")String boardCode) {
		
		return "board/boardWrite";
	}
	
	/** 00 게시판 작성
	 * @param boardCode
	 * @param inputBoard
	 * @return
	 */
	@PostMapping("{boardCode:[a-z]+}/insert")
	public String boadWrite(@PathVariable("boardCode")String boardCode,
							Board inputBoard,
							@SessionAttribute("loginMember") Member member,
							RedirectAttributes ra) {
		
		
			if(boardCode.equals("free")) {

			}
			
			if(boardCode.equals("faq")) {

			}
			

		
		return "board/boardWrite";
	}
	
}