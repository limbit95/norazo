package edu.kh.norazo.myPage.model.service;

import java.io.File;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.norazo.member.model.dto.Member;
import edu.kh.norazo.myPage.model.mapper.MyPageMapper;
import lombok.RequiredArgsConstructor;


@Service
@Transactional(rollbackFor=Exception.class) // 모든 예외 발생 시 롤백
@RequiredArgsConstructor
@PropertySource("classpath:/config.properties")
public class MyPageServiceImpl implements MyPageService {
	@Value("${my.profile.web-path}")
	private String profileWebPath;
	public static int seqNum = 1; // 1~99999 반복;
	private final MyPageMapper mapper;
	@Value("${my.profile.folder-path}")
	private String profileFolderPath;
	@Override
	public int profile(MultipartFile profileImg, Member loginMember) throws Exception {
			// 수정할 경로
			String updatePath = null;
			// 변경명 저장
			String rename = null;

			// 업로드한 이미지가 있을 경우
			// - 있을 경우 : 수정할 경로 조합 (클라이언트 접근 경로+리네임파일명)
			if(!profileImg.isEmpty()) {
				// updatePath 조합
				
				// 1. 파일명 변경
				
				// 20240417102705_00004.jpg
				
				// SimpleDateFormat : 시간을 원하는 형태의 문자열로 간단히 변경
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				
				// new java.util.Date() : 현재 시간을 저장한 자바 객체
				String date = sdf.format(new java.util.Date());
				
				String number = String.format("%05d", seqNum);
				
				seqNum++; // 1증가
				if(seqNum == 100000) seqNum = 1;
				
				// 확장자
				// "문자열".substring(인덱스)
				// - 문자열을 인덱스부터 끝까지 잘라낸 결과를 반환
				
				// "문자열".lastIndexOf(".")
				// - 문자열에서 마지막 "."의 인덱스를 반환
				
				String ext = profileImg.getOriginalFilename().substring(profileImg.getOriginalFilename().lastIndexOf("."));
				
				// originalFileName == 뚱이.jpg
				// .jpg
				
				rename = date + "_" + number + ext;
				
				// 2. /myPage/profile/변경된파일명
				updatePath = profileWebPath + rename;
			}
			
			// 수정된 프로필 이미지 경로 + 회원 번호를 저장할 DTO 객체
			Member mem = Member.builder()
					.memberNo(loginMember.getMemberNo())
					.profileImg(updatePath)
					.build();
			
			int result = mapper.profile(mem);
			
			if(result > 0) {
				// 프로필 이미지를 없앤 경우를 제외 -> 업로드한 이미지가 있을 경우
				if(!profileImg.isEmpty()) {
					profileImg.transferTo(new File(profileFolderPath +rename));
				}
				//세션 회원 정보에서 프로필 이미지 경로를 업데이트한 경로로 변경
				loginMember.setProfileImg(updatePath);
			}
			return result;
	}
	@Override
	public int updateInfo(Member inputMember, String[] memberAddress) {
			// 입력된 주소가 있을 경우
			// memberAddress를 A^^^B^^^C 형태로 가공
			
			// 주소 입력 X -> inputMember.getMemberAddress() -> ",,"
			if( inputMember.getMemberAddress().equals(",,") ) {
				
				// 주소에 null 대입
				inputMember.setMemberAddress(null);
				
			} else {
				// memberAddress를 A^^^B^^^C 형태로 가공
				String address = String.join("^^^", memberAddress);
				
				// 주소에 가공된 데이터를 대입
				inputMember.setMemberAddress(address);
			}
			
			// SQL 수행 후 결과 반환
			return mapper.updateInfo(inputMember);
	}

}
