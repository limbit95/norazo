package edu.kh.norazo.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.norazo.board.model.dto.Board;
import edu.kh.norazo.board.model.dto.BoardImg;

@Mapper
public interface EditBoardMapper {

	/** 게시글 작성
	 * @param inputBoard
	 * @return boardNo
	 */
	int boardInsert(Board inputBoard);

	/** 게시글 이미지 삽입
	 * @param uploadList
	 * @return
	 */
	int insertUploadList(List<BoardImg> uploadList);

	/** 작성된 모임글에 작성한 회원이 가장 먼저 참석하게 하는 기능
	 * @param inputBoard
	 * @return
	 */
	int firstJoin(Board inputBoard);
 
	/** 게시글 수정
	 * @param inputBoard
	 * @return
	 */
	int sportsBoardUpdate(Board inputBoard);

	/** 게시글 이미지 삭제
	 * @param map
	 * @return
	 */
	int deleteImage(Map<String, Object> map);

	/** 게시글 이미지 수정
	 * @param img
	 * @return
	 */
	int updateImage(BoardImg img);

	/** 게시글 이미지 삽입 (1행)
	 * @param img
	 * @return
	 */
	int insertImage(BoardImg img);
	
	
	
}