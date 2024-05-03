package edu.kh.norazo.main.model.service;

import java.util.List;
import java.util.Map;

public interface MainService {
	
	/** 게시판 종류 조회
	 * @return
	 */
	List<Map<String, Object>> selectBoardTypeList();

	/** 스포츠 종류 목록 조회
	 * @return
	 */
	List<Map<String, Object>> selectSportsTypeList();

}