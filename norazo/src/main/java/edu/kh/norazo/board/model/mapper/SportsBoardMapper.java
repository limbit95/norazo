package edu.kh.norazo.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.norazo.board.model.dto.Board;

@Mapper
public interface SportsBoardMapper {

	/** 스포츠 종목별 게시판 게시글 수 조회
	 * @param sportsCode
	 * @return
	 */
	int getListCount(String sportsCode);

	/** 스포츠 종목별 게시판의 지정된 페이지 목록 조회 
	 * @param sportsCode
	 * @param rowBounds
	 * @return
	 */ 
	List<Board> selectBoardList(String sportsCode, RowBounds rowBounds);

	/** 스포츠 종목 한글 이름 얻어오기
	 * @param sportsCode
	 * @return
	 */
	String getSportsKrName(String sportsCode);

	/** 모임 게시글 모달창 조회
	 * @param boardNo
	 * @return
	 */
	Board modalView(int boardNo);

}
