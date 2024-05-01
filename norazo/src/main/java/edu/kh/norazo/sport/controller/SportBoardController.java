package edu.kh.norazo.sport.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.norazo.sport.model.service.SportBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("sport")
public class SportBoardController {

	private final SportBoardService service;
	
	@GetMapping("board/detail")
	public String sportBoardDetail(){
		return "board/sportsDetailBoard";
	}
	
}
