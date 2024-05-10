package edu.kh.norazo.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.norazo.board.model.dto.Board;
import edu.kh.norazo.board.model.dto.Pagination;
import edu.kh.norazo.board.model.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
	
	private final BoardMapper mapper;

	@Override
	public Map<String, Object> selectBoardList(int boardCode, int cp) {

		int listCount = mapper.getFreeListCount(boardCode);
		
		Pagination pagination = new Pagination(cp,listCount); 
		
		int limit = pagination.getLimit();
		
		int offset = (cp - 1) * limit;
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Board> boardList = mapper.selectBoardList(boardCode, rowBounds);
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("pagination", pagination);
		map.put("boardList", boardList);
		
		return map;
	}


	// 게시글 상세 조회
	@Override
	public Board selectOne(Map<String, Object> map) {
		
		return mapper.selectOne(map);
	}

	@Override
	public int insertBoard(Board inputBoard) {
		
		
		
		return mapper.insertBoard(inputBoard);
	}
	
	// 게시글 수정
	@Override
	public int boardUpdate(Board inputBoard) {
		
		
		
		return mapper.boardUpdate(inputBoard);
	}
}