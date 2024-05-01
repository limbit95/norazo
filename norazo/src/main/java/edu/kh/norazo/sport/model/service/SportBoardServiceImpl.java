package edu.kh.norazo.sport.model.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.norazo.sport.model.mapper.SportBoardMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class SportBoardServiceImpl implements SportBoardService{

	private final SportBoardMapper mapper;
	
}
