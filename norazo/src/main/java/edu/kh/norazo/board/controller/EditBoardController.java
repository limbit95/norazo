package edu.kh.norazo.board.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.norazo.board.model.dto.Board;
import edu.kh.norazo.board.model.service.EditBoardService;
import edu.kh.norazo.board.model.service.SportsBoardService;
import edu.kh.norazo.main.model.service.MainService;
import edu.kh.norazo.member.model.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("editBoard")
public class EditBoardController {

	private final EditBoardService service;

	private final MainService mainService;
	
	private final SportsBoardService sportsBoardService;
	
	/** 게시글 작성 페이지 이동
	 * @param sportsCode
	 * @return
	 */
	@GetMapping("insert/{boardCode:[0-9]+}")
	public String boardWrite(Model model,
							 @PathVariable("boardCode") int boardCode) {
		
		// 스포츠 종류 리스트를 모임글 작성하는 페이지에 request scope으로 올리기 위한 코드
		List<Map<String, Object>> sportsTypeList = mainService.selectSportsTypeList();
		model.addAttribute("sportsTypeList", sportsTypeList);
		
		String path = null;
		
		if(boardCode == 1) {
			path = "board/sportsBoardWrite";
		} else if(boardCode == 2 || boardCode == 3) {
			path = "board/boardWrite";
		}
		
		return path;
	}
	
	/** 게시글 작성
	 * @param inputBoard
	 * @param inputThumbnail
	 * @param boardCode
	 * @param loginMember
	 * @param ra
	 * @return
	 * @throws Exception
	 */
	@PostMapping("insert/{boardCode:[0-9]+}")
	public String boardWrite(Board inputBoard,
							 @RequestParam(value = "inputThumbnail", required=false) List<MultipartFile> inputThumbnail,
							 @PathVariable("boardCode") int boardCode,
							 @SessionAttribute("loginMember") Member loginMember,
							 RedirectAttributes ra) throws Exception {
		
		// 날짜 변환 및 inputBoard에 로그인 회원 번호, 게시판 코드 삽입
		String date = inputBoard.getMeetingDate().replace("T", " ");
		inputBoard.setMeetingDate(date);
		inputBoard.setMemberNo(loginMember.getMemberNo());
		inputBoard.setBoardCode(boardCode);
		
		int boardNo = service.boardInsert(inputBoard, inputThumbnail);
		
		String path = null;
		String message = null;
		
		if(boardNo > 0) {
			if(boardCode == 1) {
				path = "/sportsBoard/detail/" + inputBoard.getSportsCode() + "/" + inputBoard.getBoardNo();
			} else if(boardCode == 2 || boardCode == 3) {
				path = "";
			}
			message = "게시글이 작성 되었습니다!";
		} else {
			path = "insert/" + boardCode;
			message = "게시글 작성 실패";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
	
	/** 게시글 수정 화면 전환
	 * @param sportsCode
	 * @param boardNo
	 * @param loginMember
	 * @param model
	 * @param ra
	 * @return
	 */
	@GetMapping("update/{sportsCode:[a-z]+}/{boardNo:[0-9]+}")
	public String boardUpdate(@PathVariable("sportsCode") String sportsCode,
							  @PathVariable("boardNo") int boardNo,
							  Model model,
							  RedirectAttributes ra) {
		
		// 수정 화면에 출력한 기존의 제목/내용/이미지 다시 조회 후 삽입해야 함
		// -> 게시글 상세 조회 서비스를 호출해야 함
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sportsCode", sportsCode);
		map.put("boardNo", boardNo);
		
		Board board = sportsBoardService.selectSportsBoard(map);
		
		String message = null;
		String path = null;
		
		if(board == null) {
			message = "해당 게시글이 존재하지 않습니다";
			path = "redirect:/";
			
			ra.addFlashAttribute("message", message);
		} else {
			path = "sportsBoard/sportsBoardUpdate";
			model.addAttribute("board", board);
		}
		
		return path;
	}
	
	
	/** 게시글 수정
	 * @param sportsCode
	 * @param boardNo
	 * @param inputBoard
	 * @param inputThumbnail
	 * @param ra
	 * @param deleteOrder
	 * @param queryString
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@PostMapping("update/{sportsCode:[a-z]+}/{boardNo:[0-9]+}")
	public String boardUpdate(@PathVariable("sportsCode") String sportsCode,
			  				  @PathVariable("boardNo") int boardNo,
			  				  @ModelAttribute Board inputBoard,
			  				  @RequestParam("inputThumbnail") List<MultipartFile> inputThumbnail,
			  				  RedirectAttributes ra,
			  				  @RequestParam(value="deleteOrder", required=false) String deleteOrder,
			  				  @RequestParam(value="queryString", required=false, defaultValue="") String queryString) 
			  						throws IllegalStateException, IOException{
		
		// 1. 커맨드 객체(inputBoard)에 boardCode, boardNo, memberNo 세팅(추가 삽입)
		inputBoard.setSportsCode(sportsCode);
		inputBoard.setBoardNo(boardNo);
		// -> inputBoard 에 제목/내용/boardCode/boardNo/memberNo 세팅되어 있음
		
		// 2. 게시글 수정 서비스 호출 후 결과 반환 받기
		int result = service.sportsBoardUpdate(inputBoard, inputThumbnail, deleteOrder);
		
		// 3. 서비스 결과에 따라 응답 제어
		String message = null;
		String path = null;
		
		if(result > 0) {
			message = "게시글이 수정 되었습니다";
			path = String.format("/sportsBoard/detail/%d/%d%s", sportsCode, boardNo, queryString);
		} else {
			message = "수정 실패";
			path = "update"; // 수정 화면으로 전환 redirect
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
	
}