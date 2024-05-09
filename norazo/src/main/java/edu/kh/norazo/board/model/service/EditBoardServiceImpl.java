package edu.kh.norazo.board.model.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.norazo.board.model.dto.Board;
import edu.kh.norazo.board.model.dto.BoardImg;
import edu.kh.norazo.board.model.exeption.BoardInsertException;
import edu.kh.norazo.board.model.mapper.EditBoardMapper;
import edu.kh.norazo.common.util.Utility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class EditBoardServiceImpl implements EditBoardService{
	
	private final EditBoardMapper mapper;

	@Value("${my.board.web-path}")
	private String webPath;
	
	// 게시글 작성
	@Override
	public int boardInsert(Board inputBoard, List<MultipartFile> inputThumbnail) throws Exception {
		int result = mapper.boardInsert(inputBoard);
		
		if(result == 0) {
			return 0;
		}
		
		int boardNo = inputBoard.getBoardNo();
		
		// 실제 업로드된 이미지의 정보를 모아둘 List 생성
		List<BoardImg> uploadList = new ArrayList<BoardImg>();
		
		// images 리스트에서 하나씩 꺼내어 선택된 파일이 있는 검사
		for(int i = 0; i < inputThumbnail.size(); i++) {
			// 실제로 선택된 파일이 존재하는 경우
			if(!inputThumbnail.get(i).isEmpty()) {
				// 원본명
				String originalName = inputThumbnail.get(i).getOriginalFilename();
				
				// 변경명
				String rename = Utility.fileRename(originalName);
				
				// 모든 값을 저장할 DTO 생성 (BoardImg)
				BoardImg img = BoardImg.builder()
							.imgPath(webPath)
							.imgOriginalName(originalName)
							.imgRename(rename)
							.imgOrder(i)
							.boardNo(boardNo)
							.uploadFile(inputThumbnail.get(i))
							.build();
				
				uploadList.add(img);
			}
		}
		
		if(uploadList.isEmpty()) {
			return boardNo;
		}
		
		result = mapper.insertUploadList(uploadList);
		
		if(result == uploadList.size()) {
			String fullClassPath = System.getProperty("java.class.path");
			int idx = fullClassPath.indexOf("bin");
			String classPath = fullClassPath.substring(0, idx) + "src/main/resources/static/images/board/";
			
			// 서버에 파일 저장
			for(BoardImg img : uploadList) {
				img.getUploadFile().transferTo(new File(classPath + img.getImgRename()));
			}
		} else {
			// 부분적으로 삽입 실패 -> 전체 서비스 실패로 판단
			// -> 이전에 삽입된 내용 모두 rollback
			
			// rollback 하는 방법 
			// == RuntimeException 강제 발생 (@Transactional 을 통해서 rollback 수행)
			
			// 사용자 정의 예외 방식 사용
			throw new BoardInsertException("이미지가 정상 삽입되지 않음");
		}
		
		return boardNo;
	}
	
}