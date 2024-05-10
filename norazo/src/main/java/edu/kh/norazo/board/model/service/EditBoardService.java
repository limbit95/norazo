package edu.kh.norazo.board.model.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.norazo.board.model.dto.Board;

public interface EditBoardService {

	/** 게시글 작성
	 * @param inputBoard
	 * @param inputThumbnail
	 * @return boardNo
	 */
	int boardInsert(Board inputBoard, List<MultipartFile> inputThumbnail) throws Exception;

	/** 게시글 수정
	 * @param inputBoard
	 * @param inputThumbnail
	 * @param deleteOrder
	 * @return
	 */
	int sportsBoardUpdate(Board inputBoard, List<MultipartFile> inputThumbnail, String deleteOrder) throws IllegalStateException, IOException;

}