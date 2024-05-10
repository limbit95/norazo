package edu.kh.norazo.main.model.service;

import java.util.List;
import java.util.Map;

import edu.kh.norazo.board.model.dto.Board;

public interface MainService {
	
	/** 게시판 종류 조회
	 * @return
	 */
	List<Map<String, Object>> selectBoardTypeList();

	/** 스포츠 종류 목록 조회
	 * @return
	 */
	List<Map<String, Object>> selectSportsTypeList();

	/** 메인 페이지에 올릴 모임 게시판 게시글 최신순으로 4개만 조회
	 * @param paramMap 
	 * @return
	 */
	List<Board> selectSportsBoardList(Map<String, Object> paramMap);

	/** 메인 페이지에 올릴 자유 게시판 게시글 최신순으로 5개만 조회
	 * @param paramMap 
	 * @return
	 */
	List<Board> selectFreeBoardList(Map<String, Object> paramMap);

}