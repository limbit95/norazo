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
	
	@GetMapping("{boardCode:[a-z,A-Z]+}")
	public String selectBoardList(@PathVariable("boardCode") String boardCode,
								  @SessionAttribute("loginMember") Member loginMember,
								  @RequestParam(value = "cp", required = false,defaultValue = "1")int cp,
								  Model model) {
		
		
		log.debug("boardCode : " + boardCode);
		
		int memberNo = loginMember.getMemberNo();
		
		Map<String, Object> map = null;
		
		if(boardCode.equals("free")) {
			
			map = service.selectFreeBoardList(boardCode,cp);
		}	
		
		if(boardCode.equals("faq")) {
			
			map = service.selectFaqBoardList(boardCode,cp);
		}
		
		if(boardCode.equals("myCreate")) {
			
			map = service.selectmyCreateBoardList(boardCode,cp,memberNo);
		}
		
		if(boardCode.equals("myBelong")) {
			
			map = service.selectmyBelongBoardList(boardCode,cp,memberNo);
		}
		
		if(boardCode.equals("myHeart")) {
			
			map = service.selectmyHeartBoardList(boardCode,cp,memberNo);
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
	public String boadWrite(@PathVariable("boardCode")String boardCode,
							Model model) {
		
		if(boardCode.equals("free")) {
			model.addAttribute("boardName", "자유 게시판");
		} else if (boardCode.equals("faq")) {
			model.addAttribute("boardName", "문의 게시판");
		}
		
		return "board/boardWrite";
	}
	
	/** 00 게시판 작성
	 * @param boardCode
	 * @param inputBoard
	 * @return
	 */
	@PostMapping("{boardName:[a-z]+}/insert")
	public String boadWrite(@PathVariable("boardName")String boardName,
							Board inputBoard,
							@SessionAttribute("loginMember") Member member,
							RedirectAttributes ra) {
		
			inputBoard.setMemberNo(member.getMemberNo());
		
		
			if(boardName.equals("free")) {
				inputBoard.setBoardCode(2);
			}
			
			if(boardName.equals("faq")) {
				inputBoard.setBoardCode(3);
			}
			
			int boardNo = service.insertBoard(inputBoard);
			
			String message = null;
			
			String path = null;
			
			if(boardNo > 0) {
				message = "게시글이 등록 되었습니다.";
				
				path = "redirect:/board/"+boardName;
			
			} else {
				message = "게시글이 등록 되지 않았습니다.";
				
				path = "board/boardWrite";
			}
		
			ra.addFlashAttribute("message", message);
			
		return path;
	}

	/** 게시글 수정 
	 * @param boardName
	 * @return
	 */
	@GetMapping("{boardName:[a-z]+}/update/{boardNo:[0-9]+}")
	public String boardUpdate(@PathVariable("boardName")String boardCode,
							  @PathVariable("boardNo") int boardNo,
							  @SessionAttribute("loginMember")Member loginMember,
							  Board inputBoard,
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
		log.debug("inputBoard : "+inputBoard);
		Board board = service.selectOne(map);
		
		String message = null; 
		String path = null; 
		
		if(board == null) {
			message = "해당 게시글이 존재하지 않습니다.";
			
			path = "redirect:/";
			
			ra.addFlashAttribute("message",message);
			
		} else if(board.getMemberNo() != loginMember.getMemberNo()) {
			message = "자신이 작성한 글만 수정할 수 있습니다.";
			
			path = "redirect:/board/"+boardCode;
			
			ra.addFlashAttribute("message",message);
			
		} else {
			
			
			}
			
		
		
		return "";
	}
	
}