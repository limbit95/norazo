package edu.kh.norazo.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.norazo.board.model.dto.Board;

@Mapper
public interface SportsBoardMapper {

	/** 스포츠 종목별 게시판 게시글 수 조회
	 * @param sportsCode
	 * @return
	 */
	int getListCount(String sportsCode);

	/** 스포츠 종목별 게시판의 지정된 페이지 목록 조회 
	 * @param sportsCode
	 * @param rowBounds
	 * @return
	 */ 
	List<Board> selectBoardList(String sportsCode, RowBounds rowBounds);

	/** 스포츠 종목 한글 이름 얻어오기
	 * @param sportsCode
	 * @return
	 */
	String getSportsKrName(String sportsCode);

	/** 모임 게시글 모달창 조회
	 * @param boardNo
	 * @return
	 */
	Board modalView(Map<String, Object> map);

	/** 모임 참석한 회원 수 얻어오기
	 * @param map
	 * @return
	 */
	int AttendMemberCount(Map<String, Object> map);

	/** 모임에 참석 가능한 회원 제한 수 얻어오기
	 * @param map
	 * @return
	 */
	int memberCountLimit(Map<String, Object> map);

	/** 로그인한 회원 특정 모임 참석 기능
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
	/** 좋아요 해제(DELETE)
	 * @param map
	 * @return
	 */
	int deleteBoardLike(Map<String, Object> map);

	/** 좋아요 체크(INSERT)
	 * @param map
	 * @return
	 */
	int insertBoardLike(Map<String, Object> map);


}
