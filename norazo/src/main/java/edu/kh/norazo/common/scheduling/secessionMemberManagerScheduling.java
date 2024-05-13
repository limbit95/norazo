//package edu.kh.norazo.common.scheduling;
//
//import java.io.File;
//import java.util.Arrays;
//import java.util.List;
//
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import edu.kh.norazo.member.model.dto.Member;
//import edu.kh.norazo.member.model.service.MemberService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class secessionMemberManagerScheduling {
//
//	private final MemberService service;
//
//	@Scheduled(fixedDelay = 5000) // 이전 작업이 시작된 후 5초 후에 수행
////	@Scheduled(fixedRate = 5000) // 이전 작업이 끝난 후 5초 후에 수행
//	// cron="초 분 시 일 월 요일 [년도]" - 요일 : 1(SUN) ~ 7(SAT)
////	@Scheduled(cron = "0,15,30,45 * * * * *") // 시계 초 단위가 0, 15, 30, 45 경우 수행
////	@Scheduled(cron = "0 0 * * * *") // 정시마다 수행
////	@Scheduled(cron = "0 0 0 * * *") // 자정마다 수행
////	@Scheduled(cron = "0 0 12 * * *") // 정오마다 수행
////	@Scheduled(cron = "0 0 0 1 * *") // 매월 1일 마다 수행
//	@Scheduled(cron = "30 * * * * *") // 시계 초 단위가 0, 30인 경우 수행 (테스트용)
//	public void secessionMemberManager() {
//		log.info("스케줄러 동작");
//		
//		List<Member> memberList = service.secessionMemberList();
//		
//		for(Member member : memberList) {
//			if(member.getMemberAddress() == null){
//				member.setMemberAddress("NULL");
//			}
//		}
//		
//		int result = service.secessionMemberManager(memberList);
//		
//		if(result > 0) {
//			log.info("탈퇴한 회원의 처리가 완료되었습니다.");
//		}
//	}
//	
////	@Scheduled(cron = "0 0 0 1 * *")
////	public void everyMonthManager() {
////		
////		List<Member> memberList = service.secessionMemberList();
////		
////		int result = service.secessionMemberManager(memberList);
////		
////		if(result > 0) {
////			log.info("탈퇴한 회원의 처리가 완료되었습니다.");
////		}
////	}
//	
//}