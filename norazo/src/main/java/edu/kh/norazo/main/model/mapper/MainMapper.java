package edu.kh.norazo.main.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.norazo.board.model.dto.Board;

@Mapper
public interface MainMapper {
	
	/** 게시판 종류 조회
	 * @return 
	 */
	List<Map<String, Object>> selectBoardTypeList();

	/** 스포츠 종류 목록 조회
	 * @return
	 */
	List<Map<String, Object>> selectSportsTypeList();

	/** 모임 게시판 게시글 최신순 4개 조회
	 * @param paramMap 
	 * @return
	 */
	List<Board> selectSportsBoardList(Map<String, Object> paramMap);

	/** 자유 게시판 게시글 최신순 5개 조회
	 * @return
	 */
	List<Board> selectFreeBoardList(Map<String, Object> paramMap);

	
	
}