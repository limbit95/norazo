package edu.kh.norazo.board.model.service;

import java.util.List;
import java.util.Map;

public interface BoardService {

	/** 자유 게시판의 지정된 페이지 목록 조회
	 * @param boardCode
	 * @param cp
	 * @return
	 */
	Map<String, Object> selectFreeBoardList(String boardCode, int cp);

	/** Faq 게시판의 지정된 페이지 목록 조회
	 * @param boardCode
	 * @param cp
	 * @return
	 */
	Map<String, Object> selectFaqBoardList(String boardCode, int cp);

	

}