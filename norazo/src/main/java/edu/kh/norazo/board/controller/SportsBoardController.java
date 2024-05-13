package edu.kh.norazo.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
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
		
//		log.debug("test : " + map.get("pagination"));
		
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
	@GetMapping("detail/{sportsCode:[a-z]+}/{boardNo:[0-9]+}")
	public String sportsBoardDetail(@PathVariable("sportsCode") String sportsCode,
									@PathVariable("boardNo") int boardNo,
									@SessionAttribute("loginMember") Member loginMember, 
									@RequestParam(value="myGroup", required=false, defaultValue="null") String myGroup,
									Model model,
									RedirectAttributes ra) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boardNo", boardNo);
		map.put("memberNo", loginMember.getMemberNo());
		map.put("sportsCode", sportsCode);
		
		// 모임글 상세조회 페이지 필요한 서비스
		
		Board sportsBoardDetail = service.selectSportsBoard(map);
		
		if(sportsBoardDetail == null) {
			ra.addFlashAttribute("message", "게시물이 존재하지 않습니다.");
			return "redirect:/";
		}
		
		Member createMember = service.boardCreateMember(boardNo);

		model.addAttribute("memberList", sportsBoardDetail.getMemberList());
		model.addAttribute("createMember", createMember);
		model.addAttribute("board", sportsBoardDetail);
		model.addAttribute("myGroup", myGroup);
		
		
		
		// 모임 참석 여부 확인
		int attendFl = service.attendFl(map);
		
		if(attendFl > 0) {
			return "board/sportsBoardDetail";
		} 
		
		// 미참석인 모임 참석 클릭시 참석 기능
		int join = service.join(map);
		
		if(join > 0) {
			map.put("likeCheck", 1);
			int boardLike = service.boardLike(map);
		}
		
		sportsBoardDetail = service.selectSportsBoard(map);
		model.addAttribute("board", sportsBoardDetail);
		model.addAttribute("memberList", sportsBoardDetail.getMemberList());
		
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
	
	
	/** 모임글 참여 취소
	 * @return
	 */
	@PostMapping("deleteJoinMember")
	public String deleteJoinMember(RedirectAttributes ra, 
									@RequestParam("boardNo") int boardNo, 
									@SessionAttribute(value="loginMember", required=false) Member loginMember, 
									@RequestParam("createMemberNo") int createMemberNo,
									@RequestParam(value="myGroup", required=false, defaultValue="null") String myGroup,
									Model model) {
		
		if(loginMember == null) {
			ra.addFlashAttribute("message", "로그아웃 상태입니다. 다시 로그인 해주세요.");
			return "redirect:/member/login";
		}
		
		int memberNo = loginMember.getMemberNo();
		
		int result = service.deleteJoinMember(boardNo, memberNo);
		
		String path = null;
		
		if (result > 0) {
			if(myGroup.equals("내가 만든 모임")) {
				ra.addFlashAttribute("message", "참여가 취소 되었습니다");
				return "redirect:/myPage/myCreate";
			} 
			if(myGroup.equals("내가 속한 모임")) {
				ra.addFlashAttribute("message", "참여가 취소 되었습니다");
				return "redirect:/myPage/myBelong";
			}
			ra.addFlashAttribute("message", "참여가 취소 되었습니다");
			path = "redirect:/";
		} else {
			ra.addFlashAttribute("message", "참여 취소 실패");
			path = "redirect:/boardNo" + boardNo;
		}
		
		
		
		return path;
	}
	
	
	/** 게시글 삭제
	 * @param boardCode
	 * @param boardNo
	 * @param cp
	 * @param ra
	 * @return
	 */
	@GetMapping("delete/{sportsCode:[a-z]+}/{boardNo:[0-9]+}")
	public String boardDelete(@PathVariable("sportsCode") String sportsCode,
						      @PathVariable("boardNo") int boardNo,
						      RedirectAttributes ra) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sportsCode", sportsCode);
		map.put("boardNo", boardNo);
		
		int result = service.sportsBoardDelete(map);
		
		String message = null;
		String path = null;
		
		if(result > 0) {
			message = "모임이 삭제 되었습니다";
			path = "/sportsBoard/" + sportsCode;
		} else {
			message = "모임 삭제 실패";
			path = "/sportsBoard/detail/" + sportsCode + "/" + boardNo;
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
	
}
