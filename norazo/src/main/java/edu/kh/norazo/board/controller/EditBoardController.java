package edu.kh.norazo.board.controller;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.norazo.board.model.dto.Board;
import edu.kh.norazo.board.model.service.EditBoardService;
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
	 * @return
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
	
}