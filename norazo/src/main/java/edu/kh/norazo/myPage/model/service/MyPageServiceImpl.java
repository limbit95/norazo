package edu.kh.norazo.myPage.model.service;

import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.myPage.model.mapper.MyPageMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor=Exception.class) // 모든 예외 발생 시 롤백
@RequiredArgsConstructor
@PropertySource("classpath:/config.properties")
public class MyPageServiceImpl implements MyPageService {

}
