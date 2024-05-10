package edu.kh.norazo.board.model.service;

import java.util.List;
import java.util.Map;

import edu.kh.norazo.board.model.dto.Board;

public interface BoardService {

	/** 00게시판의 지정된 페이지 목록 조회
	 * @param boardCode
	 * @param cp
	 * @return
	 */
	Map<String, Object> selectBoardList(int boardCode, int cp);


	/** 게시글 상세 조회
	 * @param map
	 * @return
	 */
	Board selectOne(Map<String, Object> map);

	/** 00 게시판 작성 
	 * @param inputBoard
	 * @return
	 */
	int insertBoard(Board inputBoard);

	/** 게시글 수정
	 * @param inputBoard
	 * @return
	 */
	int boardUpdate(Board inputBoard);


	Map<String, Object> selectmyCreateBoardList(String boardCode, int cp, int memberNo);

	Map<String, Object> selectmyBelongBoardList(String boardCode, int cp, int memberNo);

	Map<String, Object> selectmyHeartBoardList(String boardCode, int cp, int memberNo);

	

}