package edu.kh.norazo.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.norazo.board.model.dto.Board;
import edu.kh.norazo.board.model.service.SportsBoardService;
import edu.kh.norazo.member.model.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("sportsBoard")

public class SportsBoardController {

	private final SportsBoardService service;
	
	// 모임 게시판 게시글 목록 조회
	@GetMapping("{sportsCode:[a-z]+}")
	public String sportsBoardList(@PathVariable("sportsCode") String sportsCode,
					   			  @RequestParam(value="cp", required=false, defaultValue="1") int cp,
					   			  Model model) {
		
		Map<String, Object> map = service.selectBoardList(sportsCode, cp);
		
		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("boardList", map.get("boardList"));
		model.addAttribute("sportsKrName", map.get("sportsKrName"));
		model.addAttribute("sportsCode", sportsCode);
		
		log.debug("test : " + map.get("pagination"));
		
		return "board/sportsBoard";
	}
	
	// 모임 게시글 모달창 조회
	@ResponseBody
	@GetMapping("modal")
	public Board modalView(@RequestParam("boardNo") int boardNo,
						   @SessionAttribute(value="loginMember", required=false) Member loginMember) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boardNo", boardNo);
		if(loginMember != null) {
			map.put("memberNo", loginMember.getMemberNo());
		}
		
		Board board = service.modalView(map);
		
		return board;
	}
	
	
	// 모임 게시글 상세 정보 조회
	@GetMapping("{sportsCode:[a-z]+}/{boardNo:[0-9]+}")
	public String sportsBoardDetail(@PathVariable("sportsCode") String sportsCode,
									@PathVariable("boardNo") int boardNo,
									@SessionAttribute("loginMember") Member loginMember, 
									Model model) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boardNo", boardNo);
		map.put("memberNo", loginMember.getMemberNo());
		map.put("sportsCode", sportsCode);
		
		
		// 모임글 상세조회 페이지 필요한 서비스
		Board sportsBoardDetail = service.selectSportsBoard(map);

		
		
		model.addAttribute("thumbnail", sportsBoardDetail.getThumbnail());
		model.addAttribute("boardTitle", sportsBoardDetail.getBoardTitle());
		model.addAttribute("sportsKrName", sportsBoardDetail.getSportsKrName());
		model.addAttribute("mettingDate", sportsBoardDetail.getMeetingDate());
		
		log.debug("sportsKrName : " + sportsBoardDetail.getSportsKrName());
		
//		List<Member> memberList = sportsBoardDetail.getMemberList();
				
		// 모임 참석 여부 확인
		int attendFl = service.attendFl(map);
		
		if(attendFl > 0) {
			return "board/sportsBoardDetail";
		}
		
		// 미참석인 모임 참석 클릭시 참석 기능
		int join = service.join(map);
		
		String path = null;
		String message = null;
		
		if(join > 0) {
			path = "board/sportsBoardDetail";
			message = "모임에 참석되셨습니다. 상세조회 페이지로 이동합니다.";
		} else {
			path = "sportsBoard/" + sportsCode;
			message = "참석 실패";
		}
		
		model.addAttribute("message", message);
		
		return path;
	}
	
	// 모임글 좋아요 체크/해제
	@ResponseBody
	@PostMapping("like")
	public int boardLike(@RequestBody Map<String, Object> map) {
		return service.boardLike(map);
	}
	
	
}
