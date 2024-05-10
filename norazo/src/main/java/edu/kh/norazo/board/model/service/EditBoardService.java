package edu.kh.norazo.board.model.service;

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

	int sportsBoardUpdate(Board inputBoard, List<MultipartFile> inputThumbnail, String deleteOrder);

}