package edu.kh.norazo.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import edu.kh.norazo.common.interceptor.BoardTypeInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer{
	
	@Bean
	public BoardTypeInterceptor boardTypeInterceptor() {
		return new BoardTypeInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// Bean으로 등록된 BoardTypeInterceptor 를 얻어와서 매개변수 전달
		registry.addInterceptor(boardTypeInterceptor())
		.addPathPatterns("/**") // 가로챌 요청 주소를 지정
		// /** : / 이하 모든 요청 주소
		
		// 가로채지 않을 주소를 지정
		.excludePathPatterns("/css/**",
							 "/js/**",
							 "/images/**",
							 "/favicon.ico");
	}
	
}