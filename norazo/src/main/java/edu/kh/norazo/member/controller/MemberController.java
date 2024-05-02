package edu.kh.norazo.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.kh.norazo.board.controller.BoardController;
import edu.kh.norazo.board.model.service.BoardService;
import edu.kh.norazo.main.model.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SessionAttributes({"loginMember"})
@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("member")
public class MemberController {

	@GetMapping("login")
	public String login() {
		return "member/login";
	}
	
	@GetMapping("signUp")
	public String signUp() {
		return "common/signUp";
	}
	
}