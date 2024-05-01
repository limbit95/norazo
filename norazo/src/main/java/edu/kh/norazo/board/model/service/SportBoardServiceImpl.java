package edu.kh.norazo.board.model.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.norazo.board.model.mapper.SportBoardMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class SportBoardServiceImpl implements SportBoardService{

	private final SportBoardMapper mapper;
	
}
