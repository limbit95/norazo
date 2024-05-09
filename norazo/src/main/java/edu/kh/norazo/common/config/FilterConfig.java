package edu.kh.norazo.common.config;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import edu.kh.norazo.common.filter.LoginFilter;

@Configuration
public class FilterConfig {

	
	@Bean
	public FilterRegistrationBean<LoginFilter> loginFilter(){
		FilterRegistrationBean<LoginFilter> filter = new FilterRegistrationBean<>();
		
		// 사용할 필터 객체 추가
		filter.setFilter(new LoginFilter());
		
		// /myPage/* : myPage로 시작하는 모든 요청
		String[] filteringURL = {"/myPage/*", 
								 "/sportsBoard/detail/*",
								 "/editBoard/insert/*"};
		
		// 필터가 동작할 URL을 세팅
		// Arrays.asList();
		// -> filteringURL 배열을 List로 변환
		filter.setUrlPatterns(Arrays.asList(filteringURL));
		
		// 필터 이름 지정
		filter.setName("loginFilter");
		
		// 필터 순서 지정
		filter.setOrder(1);
		
		return filter; // 반환된 객체가 필터를 생성해서 Bean으로 등록
	}
	
}