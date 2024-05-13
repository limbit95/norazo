package edu.kh.norazo.common.scheduling;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import edu.kh.norazo.board.model.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component // 빈 등록
@RequiredArgsConstructor
public class ImageDeleteScheduling {
	
	private final BoardService service;
	
	String fullClassPath = System.getProperty("java.class.path");
	int idx = fullClassPath.indexOf("bin");
	String classPath = fullClassPath.substring(0, idx) + "src/main/resources/static/images/";
	
//	@Scheduled(fixedDelay = 5000) // 이전 작업이 시작된 후 5초 후에 수행
//	@Scheduled(fixedRate = 5000) // 이전 작업이 끝난 후 5초 후에 수행
	// cron="초 분 시 일 월 요일 [년도]" - 요일 : 1(SUN) ~ 7(SAT)
//	@Scheduled(cron = "0,15,30,45 * * * * *") // 시계 초 단위가 0, 15, 30, 45 경우 수행
//	@Scheduled(cron = "0 0 * * * *") // 정시마다 수행
//	@Scheduled(cron = "0 0 0 * * *") // 자정마다 수행
//	@Scheduled(cron = "0 0 12 * * *") // 정오마다 수행
//	@Scheduled(cron = "0 0 0 1 * *") // 매월 1일 마다 수행
	@Scheduled(cron = "0 15 * * * *") // 시계 초 단위가 0, 30인 경우 수행 (테스트용)
	public void scheduling() {
		log.info("스케줄러 동작");
		
		// DB, 서버 파일 목록 비교 후 DB에 없는
		// 서버 이미지 파일 삭제 동작
		
		// 1. 서버 파일 목록 조회하기
		File memberFolder = new File(classPath + "profile/");
		File boardFolder = new File(classPath + "board/");
		
		// 참조하는 폴더에 존재하는 파일 목록 얻어오기
		File[] memberArr = memberFolder.listFiles();
		File[] boardArr = boardFolder.listFiles();
		
		// 두 배열을 하나로 합침 (for문 한 번만 사용하기 위해서)
		// imageArr 이라는 빈 배열을 memberArr 와 memberArr 길이 만큼의 크기로 만든다
		File[] imageArr = new File[memberArr.length + boardArr.length];
		
		// 배열 내용 복사 (깊은 복사)
		// System.arraycopy(복사할 배열, 몇번인덱스부터복사할지, 새로운 배열, 새로운 배열의 몇번부터 넣을지 인덱스, 복사를 어디까지할건지)
		System.arraycopy(memberArr, 0, imageArr, 0, memberArr.length);
		System.arraycopy(boardArr, 0, imageArr, memberArr.length, boardArr.length);
		
		// 배열 -> List 변환
		List<File> serverImageList = Arrays.asList(imageArr);
		
		// 2. DB 이미지 파일 이름만 모두 조회
		List<String> dbImageList = service.selectDbImageList();
		
		// 3. 서버, DB 이미지 파일명을 비교하여
		// 서버에만 있고, DB에 없는 파일을 서버에서 삭제
		if(!serverImageList.isEmpty()) { // 서버에 이미지가 있을 경우
			for(File serverImage : serverImageList) {
				// File.getName() : 서버 파일 이름
				
				// List.indexOf(객체 전달 가능) :
				// List에 객체가 존재하면 존재하는 index 반환
				// 존재하지 않으면 -1 반환하는 메서드
				if(dbImageList.indexOf(serverImage.getName()) == -1) {
					log.info(serverImage.getName() + " 삭제");
					serverImage.delete(); // 파일 삭제
				}
			}
		}
			
			
	}
	
}