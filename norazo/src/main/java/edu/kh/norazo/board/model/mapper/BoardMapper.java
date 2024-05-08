package edu.kh.norazo.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.norazo.board.model.dto.Board;

@Mapper
public interface BoardMapper {

	int getFreeListCount(String boardCode);

	/** 자유 게시판의 지정된 페이지 목록 조회
	 * @param rowBounds
	 * @param boardCode
	 * @return
	 */
	List<Board> selectFreeBoardList(String boardCode, RowBounds rowBounds);

	int getFaqListCount(String boardCode);

	/** Faq 게시판의 지정된 페이지 목록 조회
	 * @param rowBounds
	 * @param boardCode
	 * @return
	 */
	List<Board> selectFaqBoardList(String boardCode, RowBounds rowBounds);





	
	
}