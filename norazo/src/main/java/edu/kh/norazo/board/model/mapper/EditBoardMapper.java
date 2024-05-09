package edu.kh.norazo.board.model.mapper;

import java.util.List;

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
	
	
	
}