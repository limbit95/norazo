package edu.kh.norazo.main.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MainMapper {

	/** 스포츠 종류 목록 조회
	 * @return
	 */
	List<Map<String, Object>> selectSportsTypeList();

	
	
}