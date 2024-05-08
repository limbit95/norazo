package edu.kh.norazo.main.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.norazo.board.model.dto.Board;
import edu.kh.norazo.main.model.mapper.MainMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class MainServiceImpl implements MainService{

	private final MainMapper mapper;
	
	// 게시판 종류 조회
	@Override
	public List<Map<String, Object>> selectBoardTypeList() {
		return mapper.selectBoardTypeList();
	}

	// 스포츠 종류 목록 조회
	@Override
	public List<Map<String, Object>> selectSportsTypeList() {
		return mapper.selectSportsTypeList();
	}

	// 모임 게시판 게시글 최신순 4개 조회
	@Override
	public List<Board> selectSportsBoardList() {
		return mapper.selectSportsBoardList();
	}

	// 자유 게시판 게시글 최신순 5개 조회
	@Override
	public List<Board> selectFreeBoardList() {
		return mapper.selectFreeBoardList();
	}
	
}