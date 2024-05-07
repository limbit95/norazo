package edu.kh.norazo.main.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.norazo.main.model.mapper.MainMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class MainServiceImpl implements MainService{

	private final MainMapper mapper;

	// 스포츠 종류 목록 조회
	@Override
	public List<Map<String, Object>> selectSportsTypeList() {
		return mapper.selectSportsTypeList();
	}
	
}