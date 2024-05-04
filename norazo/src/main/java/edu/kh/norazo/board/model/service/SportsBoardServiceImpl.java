package edu.kh.norazo.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.norazo.board.model.dto.Pagination;
import edu.kh.norazo.board.model.mapper.SportsBoardMapper;
import edu.kh.norazo.board.model.dto.Board;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class SportsBoardServiceImpl implements SportsBoardService{

	private final SportsBoardMapper mapper;

	@Override
	public Map<String, Object> selectBoardList(String sportsCode, int cp) {
		int listCount = 0;
		
		if(sportsCode.equals("main")) {
			// 모임 게시판 모든 게시글 수 조회
			listCount = mapper.getListCount(sportsCode);
		} else {
			// 스포츠 종목별 게시판 게시글 수 조회
			listCount = mapper.getListCount(sportsCode);
		}
		
		String sportsKrName = mapper.getSportsKrName(sportsCode);
		
		Pagination pagination = new Pagination(cp, listCount);
		
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Board> boardList = mapper.selectBoardList(sportsCode, rowBounds);
		
		if(listCount == 0) {
			map.put("sportsKrName", sportsKrName);
			return map;
		}
		map.put("pagination", pagination);
		map.put("boardList", boardList);
		map.put("sportsKrName", sportsKrName);
		
		return map;
	}
	
}
