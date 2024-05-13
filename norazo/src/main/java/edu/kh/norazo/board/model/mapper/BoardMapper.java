package edu.kh.norazo.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.norazo.board.model.dto.Board;
import edu.kh.norazo.member.model.dto.Member;

@Mapper
public interface BoardMapper {

	int getFreeListCount(int boardCode);

	/** 자유 게시판의 지정된 페이지 목록 조회
	 * @param rowBounds
	 * @param boardCode
	 * @return
	 */
	List<Board> selectBoardList(int boardCode, RowBounds rowBounds);

	int getFaqListCount(String boardCode);


	Board selectOne(Map<String, Object> map);

	/** 게시판 글쓰기 SQL
	 * @param inputBoard
	 * @return
	 */
	int insertBoard(Board inputBoard);

	/** 00게시판 수정 
	 * @param inputBoard
	 * @return
	 */
	int boardUpdate(Board inputBoard);

	/** 게시글 삭제 
	 * @param map
	 * @return
	 */
	int boardDelete(Map<String, Object> map);

	/** DB 이미지 파일명 목록 조회
	 * @return
	 */
	List<String> selectDbImageList();
  
	Map<String, Integer> selectPrevAndNextBoard(Map<String, Object> map);

	





	
}