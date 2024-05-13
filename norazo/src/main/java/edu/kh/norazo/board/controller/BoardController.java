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
								  @RequestParam(value = "cp", required = false,defaultValue = "1")int cp,
								  Model model) {

		Board board = new Board();
		
		Map<String, Object> map = null;
		
		if(boardCode.equals("free")) {
        model.addAttribute("boardName", "자유 게시판");
        board.setBoardCode(2);
        
    }   
    if(boardCode.equals("faq")) {
        model.addAttribute("boardName", "문의 게시판");
        board.setBoardCode(3);
    }

		
		map = service.selectBoardList(board.getBoardCode(),cp);
		
		model.addAttribute("boardList",map.get("boardList"));
		
		model.addAttribute("pagination",map.get("pagination"));
		
// 		List<Board> boardList = (List<Board>) map.get("boardList");
		
	
		return "board/boardList";
	}
	
	/** 게시글 상세 조회
	 * @param boardCode
	 * @param boardNo
	 * @param model
	 * @param ra
	 * @return
	 */
//	http://localhost/board/free/91
	@GetMapping("{boardCode:[a-z]+}/{boardNo:[0-9]+}")
	public String boardDetail(@PathVariable("boardCode") String boardCode,
							  @PathVariable("boardNo") int boardNo,
							  Model model,
							  RedirectAttributes ra,
							  @RequestParam(value = "cp", required = false,defaultValue = "1")int cp) {
		
		Map<String, Object> map = new HashMap<>();
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
			path = "redirect:/board/" + boardCode;
			ra.addFlashAttribute("message","게시글이 존재하지 않습니다.");
			
		} else {
			//Map<String, Integer> prevAndNextMap = new HashMap<>();
			
			map.put("boardNo", boardNo);
			// 해당 게시글의 이전글 / 다음글 얻어오기
			Map<String, Integer> prevAndNextMap = service.selectPrevAndNextBoard(map);
	
			
			log.info("BoardCode {}", map.get("boardCode"));
			
			model.addAttribute("currentBoardNo", prevAndNextMap.get("currentBoardNo"));
			model.addAttribute("prevBoardNo", prevAndNextMap.get("prevBoardNo"));
			model.addAttribute("nextBoardNo", prevAndNextMap.get("nextBoardNo"));
			model.addAttribute("cp",cp);

			log.info("이전 페이지 {}", model.getAttribute("prevBoardNo"));
			log.info("현재 페이지 {}", model.getAttribute("currentBoardNo"));
			log.info("다음 페이지 {}", model.getAttribute("nextBoardNo"));

			
			
			
			path = "board/boardDetail";
			
			model.addAttribute("board",board);
			
			log.info("보드 코드는 ? : ", board.getBoardCode());
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

	/** 게시글 수정 이동
	 * @param boardName
	 * @return
	 */
	@GetMapping("{boardName:[a-z]+}/{boardNo:[0-9]+}/update")
	public String boardUpdate(@PathVariable("boardName")String boardCode,
							  @PathVariable("boardNo") int boardNo,
							  @SessionAttribute("loginMember")Member loginMember,
							  Board inputBoard,
							  Model model, 
							  RedirectAttributes ra) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boardNo", boardNo);
					
		if(boardCode.equals("free")) {
			map.put("boardCode", 2);
			model.addAttribute("boardCode", boardCode);
		}
		
		if(boardCode.equals("faq")) {
			map.put("boardCode", 3);
			model.addAttribute("boardCode", boardCode);
		}
		

		Board board = service.selectOne(map);
		
		String message = null; 
		String path = null; 
		
		if(board == null) {
			
			message = "해당 게시글이 존재하지 않습니다.";
			
			path = "redirect:/";
			
			ra.addFlashAttribute("message",message);
			
		} else if(board.getMemberNo() != loginMember.getMemberNo()) {
			message = "자신이 작성한 글만 수정할 수 있습니다.";
			
			path = String.format("redirect:/board/%s/%d", boardCode,boardNo);
			
			ra.addFlashAttribute("message",message);
			
		} else {
			
			path ="board/boardUpdate";
			
			model.addAttribute("board",board);
					

		}
		
		return path;
	}	

	/** 게시글 수정
	 * @param boardCode
	 * @param boardNo
	 * @param inputBoard
	 * @param loginMember
	 * @param ra
	 * @param queryString
	 * @return 
	 */
	@PostMapping("{boardName:[a-z]+}/{boardNo:[0-9]+}/update")
	public String boardUpdate(@PathVariable("boardName")String boardCode,
			  				  @PathVariable("boardNo") int boardNo,
			  				  Board inputBoard,
			  				  @SessionAttribute("loginMember") Member loginMember,
			  				  RedirectAttributes ra,
			  				  @RequestParam(value = "queryString", required = false, defaultValue = "")String queryString) {
		
		if(boardCode.equals("free")) {
			inputBoard.setBoardCode(2);
		}
		
		if(boardCode.equals("faq")) {
			inputBoard.setBoardCode(3);
		}
		
		
		inputBoard.setMemberNo(loginMember.getMemberNo());
		
		inputBoard.setBoardNo(boardNo);
		
		int result = service.boardUpdate(inputBoard);
		
		String path = null;
		String message = null;
		
		if(result > 0) {
		
			path = String.format("redirect:/board/%s/%d", boardCode,boardNo);
			
			message = "게시글이 수정 되었습니다.";
	
		} else {
			
			path = String.format("redirect:/board/%s/%d", boardCode,boardNo);
			message = "게시글 수정 실패되었습니다.";
		}
		
		ra.addFlashAttribute("message",message);
		
		
		return path;
	}
	
	/** 게시글 삭제 
	 * @param boardCode
	 * @param boardNo
	 * @param inputBoard
	 * @param loginMember
	 * @param ra
	 * @param queryString
	 * @return
	 */
	@GetMapping("{boardName:[a-z]+}/{boardNo:[0-9]+}/delete")
	public String boardDelete(@PathVariable("boardName")String boardCode,
			  				  @PathVariable("boardNo") int boardNo,
			  				  Board inputBoard,
			  				  @SessionAttribute("loginMember") Member loginMember,
			  				  RedirectAttributes ra,
			  				  @RequestParam(value = "queryString", required = false, defaultValue = "")String queryString) {
		
		Map<String, Object> map = new HashMap<>();
		
		
		if(boardCode.equals("free")) {
			inputBoard.setBoardCode(2);
		}
		
		if(boardCode.equals("faq")) {
			inputBoard.setBoardCode(3);
		}
		
		map.put("boardCode", inputBoard.getBoardCode());
		map.put("boardNo", boardNo);
		map.put("memberNo", loginMember.getMemberNo());
		

		int result = service.boardDelete(map);
		
		String path = null;
		String message = null;
		
		if(result > 0) {
		
			path = "redirect:/board/"+boardCode;
			
			message = "게시글이 삭제 되었습니다.";
	
		} else {
			
			path = String.format("redirect:/board/%s/%d", boardCode,boardNo);
			message = "게시글 삭제 실패";
		}
		
		ra.addFlashAttribute("message",message);
		
		
		return path;
	}
}