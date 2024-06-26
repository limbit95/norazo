package edu.kh.norazo.board.model.service;

import java.util.List;
import java.util.Map;

import edu.kh.norazo.board.model.dto.Board;
import edu.kh.norazo.member.model.dto.Member;

public interface SportsBoardService {

	/** 스포츠 종목별 게시글 목록 조회
	 * @param sportsCode
	 * @param cp
	 * @param cp 
	 * @return
	 */
	Map<String, Object> selectBoardList(String sportsCode, int cp);

	/** 모임 게시글 모달창 조회
	 * @param boardNo
	 * @return
	 */
	Board modalView(Map<String, Object> map);

	/** 로그인한 회원 특정 모임 참석
	 * @param map
	 * @return
	 */
	int join(Map<String, Object> map);

	/** 모임 참석 여부 확인
	 * @param map
	 * @return
	 */
	int attendFl(Map<String, Object> map);

	/** 모임글 상세조회 페이지 필요한 정보 얻어오기
	 * @param map
	 * @return
	 */
	Board selectSportsBoard(Map<String, Object> map);
	/** 모임글 좋아요 체크/해제
	 * @param map
	 * @return
	 */
	int boardLike(Map<String, Object> map);

	/** 모임글 모임장 정보 조회
	 * @param boardNo
	 * @return
	 */
	Member boardCreateMember(int boardNo);

	/** 모임글 참여 취소
	 * @param memberNo 
	 * @param boardNo 
	 * @return
	 */
	int deleteJoinMember(int boardNo, int memberNo);

	/** 게시글 삭제
	 * @param map
	 * @return
	 */
	int sportsBoardDelete(Map<String, Object> map);

	/** 멤버 리스트 최신화
	 * @param map
	 * @return
	 */
	List<Member> selectAttendMemberList(Map<String, Object> map);

}
