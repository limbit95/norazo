package edu.kh.norazo.board.model.service;

import java.util.Map;

import edu.kh.norazo.board.model.dto.Board;

public interface SportsBoardService {

	/** 스포츠 종목별 게시글 목록 조회
	 * @param sportsCode
	 * @param cp
	 * @return
	 */
	Map<String, Object> selectBoardList(String sportsCode, int cp);

	/** 모임 게시글 모달창 조회
	 * @param boardNo
	 * @return
	 */
	Board modalView(int boardNo);

}
