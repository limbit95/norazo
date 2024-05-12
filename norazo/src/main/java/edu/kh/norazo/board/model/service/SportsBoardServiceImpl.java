package edu.kh.norazo.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.norazo.board.model.dto.Pagination;
import edu.kh.norazo.board.model.mapper.SportsBoardMapper;
import edu.kh.norazo.member.model.dto.Member;
import edu.kh.norazo.board.model.dto.Board;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class SportsBoardServiceImpl implements SportsBoardService{

	private final SportsBoardMapper mapper;

	@Override
	public Map<String, Object> selectBoardList(String sportsCode, int cp) {
		int listCount = 0;
		
		if(sportsCode.equals("main")) {
			// 모임 게시판 모든 게시글 수 조회
			listCount = mapper.getListCount(sportsCode);
		} else {
			// 스포츠 종목별 게시판 게시글 수 조회
			listCount = mapper.getListCount(sportsCode);
		}
		
		String sportsKrName = mapper.getSportsKrName(sportsCode);
		
		Pagination pagination = new Pagination(cp, listCount, 16, 10);
		
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Board> boardList = mapper.selectBoardList(sportsCode, rowBounds);
		
		if(listCount == 0) {
			map.put("sportsKrName", sportsKrName);
			return map;
		}
		map.put("pagination", pagination);
		map.put("boardList", boardList);
		map.put("sportsKrName", sportsKrName);
		
		return map;
	}

	// 모임 게시글 모달창 조회
	@Override
	public Board modalView(Map<String, Object> map) {
		return mapper.modalView(map);
	}

	// 모임 참석 여부 확인
	@Override
	public int attendFl(Map<String, Object> map) {
		return mapper.attendFl(map);
	}
	
	// 로그인한 회원 특정 모임 참석
	@Override
	public int join(Map<String, Object> map) {
//		// 모임 참석한 회원 수 얻어오기
//		int AttendMemberCount = mapper.AttendMemberCount(map);
//		
//		// 모임에 참석 가능한 회원 제한 수 얻어오기
//		int memberCountLimit = mapper.memberCountLimit(map);
//		
//		if(memberCountLimit == AttendMemberCount) {
//			return 0;
//		}
		
		return mapper.join(map);
	}

	// 모임글 상세조회 페이지 필요한 정보 얻어오기
	@Override
	public Board selectSportsBoard(Map<String, Object> map) {
		List<Member> memberList = mapper.selectAttendMemberList(map);
		
		return mapper.selectSportsBoard(map);
	}
	// 모임글 좋아요 체크/해제
	@Override
	public int boardLike(Map<String, Object> map) {
		int result = 0;
		
		// 1. 좋아요가 체크된 상태인 경우 (likeCheck == 1)
		// -> BOARD_LIKE 테이블에 DELETE
		if((int)map.get("likeCheck") == 1) {
			result = mapper.deleteBoardLike(map);
		} else {
			// 2. 좋아요가 해제된 상태인 경우 (likeCheck == 0)
			// -> BOARD_LIKE 테이블에 INSERT
			result = mapper.insertBoardLike(map);
		}
		
		return -1;
	}

	/** 모임글 모임장 정보 조회
	 *
	 */
	@Override
	public Member boardCreateMember(int boardNo) {
		
		return mapper.boardCreateMember(boardNo);
	}

	/** 모임글 참여 취소
	 *
	 */
	@Override
	public int deleteJoinMember(int boardNo, int memberNo) {
		
		Map<String, Integer> map = new HashMap<>();
		
		map.put("boardNo", boardNo);
		map.put("memberNo", memberNo);
		
		return mapper.deleteJoinMember(map);
	}

	// 게시글 삭제
	@Override
	public int sportsBoardDelete(Map<String, Object> map) {
		return mapper.sportsBoardDelete(map);
	}

	// 멤버 리스트 최신화
	@Override
	public List<Member> selectAttendMemberList(Map<String, Object> map) {
		return mapper.selectAttendMemberList(map);
	}

}
