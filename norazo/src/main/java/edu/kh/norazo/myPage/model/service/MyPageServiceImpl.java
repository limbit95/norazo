package edu.kh.norazo.myPage.model.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.norazo.board.model.dto.Board;
import edu.kh.norazo.board.model.dto.Pagination;
import edu.kh.norazo.member.model.dto.Member;
import edu.kh.norazo.myPage.model.mapper.MyPageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(rollbackFor=Exception.class) // 모든 예외 발생 시 롤백
@RequiredArgsConstructor
@PropertySource("classpath:/config.properties")
public class MyPageServiceImpl implements MyPageService {
	
	@Value("${my.profile.web-path}")
	private String profileWebPath;
	
	public static int seqNum = 1; // 1~99999 반복;
	
	private final MyPageMapper mapper;
	
	private final BCryptPasswordEncoder bcrypt;
	
//	@Value("${my.profile.folder-path}")
//	private String profileFolderPath;
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
					String fullClassPath = System.getProperty("java.class.path");
					int idx = fullClassPath.indexOf("bin");
					String classPath = fullClassPath.substring(0, idx) + "src/main/resources/static/images/profile/";
					log.debug(classPath);
					
					profileImg.transferTo(new File(classPath +rename));
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

	@Override
	public int checkNickname(String memberNickname, Member inputMember) throws Exception {
		return mapper.checkNickname(memberNickname, inputMember);
	}

	@Override
	public int changePw(Map<String, Object> paramMap, int memberNo) {
		// 현재 로그인한 회원의 암호화된 비밀번호를 DB에서 조회
				String originPw = mapper.selectPw(memberNo);
				
				// 입력받은 현재 비밀번호와(평문)
				// DB에서 조회한 비밀번호 비교(암호화)
				// BCryptPasswordEncoder.matches(평문, 암호화된비밀번호)
				
				// 다를 경우
				if( !bcrypt.matches((String)paramMap.get("currentPw"), originPw) ) {
					return 0;
				}
				
				// 같을 경우
				
				// 새 비밀번호를 암호화 진행
				String encPw = bcrypt.encode((String)paramMap.get("newPw"));
				
				paramMap.put("encPw", encPw);
				paramMap.put("memberNo", memberNo);
				
				return mapper.changePw(paramMap);
	}


	public Map<String, Object> selectmyCreateBoardList(Map<String, Object> map) {
		int listCount = mapper.getMyCreateListCount(map);
		
		Pagination pagination = new Pagination((int) map.get("cp"),listCount); 
		
		int limit = pagination.getLimit();
		
		int offset = ((int) map.get("cp") - 1) * limit;
		
		RowBounds rowBounds = new RowBounds(offset, limit);

		List<Board> boardList = mapper.selectMyCreateBoardList(map, rowBounds);
		
		Map<String, Object> map2 = new HashMap<>();
		
		map.put("pagination", pagination);
		map.put("boardList", boardList);

		return map;
	}

	@Override
	public Map<String, Object> selectmyBelongBoardList(Map<String, Object> map) {
		int listCount = mapper.getMyBelongListCount(map);
		
		Pagination pagination = new Pagination((int) map.get("cp"),listCount); 
		
		int limit = pagination.getLimit();
		
		int offset = ((int) map.get("cp") - 1) * limit;
		
		RowBounds rowBounds = new RowBounds(offset, limit);

		List<Board> boardList = mapper.selectMyBelongBoardList(map, rowBounds);
		
		Map<String, Object> map2 = new HashMap<>();
		
		map.put("pagination", pagination);
		map.put("boardList", boardList);

		return map;
	}

	@Override
	public Map<String, Object> selectmyHeartBoardList(Map<String, Object> map) {
		int listCount = mapper.getMyHeartListCount(map);
		
		Pagination pagination = new Pagination((int) map.get("cp"),listCount); 
		
		int limit = pagination.getLimit();
		
		int offset = ((int) map.get("cp") - 1) * limit;
		
		RowBounds rowBounds = new RowBounds(offset, limit);

		List<Board> boardList = mapper.selectMyHeartBoardList(map, rowBounds);
		
		Map<String, Object> map2 = new HashMap<>();
		
		map.put("pagination", pagination);
		map.put("boardList", boardList);

		return map;
	}

	
	// 회원 탈퇴
		@Override
		public int secession(String memberPw, int memberNo) {
			
			// 현재 로그인한 회원의 암호화된 비밀번호를 DB에서 조회
			String originPw = mapper.selectPw(memberNo);
			
			// 다를 경우
			if( !bcrypt.matches(memberPw, originPw) ) {
				return 0;
			}

			return mapper.secession(memberNo);
		}

}
