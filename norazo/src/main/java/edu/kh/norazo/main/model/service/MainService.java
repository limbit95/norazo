package edu.kh.norazo.main.model.service;

import java.util.List;
import java.util.Map;

public interface MainService {

	/** 스포츠 종류 목록 조회
	 * @return
	 */
	List<Map<String, Object>> selectSportsTypeList();

}