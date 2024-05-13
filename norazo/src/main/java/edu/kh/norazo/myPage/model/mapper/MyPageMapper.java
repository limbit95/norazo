package edu.kh.norazo.myPage.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.norazo.board.model.dto.Board;
import edu.kh.norazo.member.model.dto.Member;

@Mapper
public interface MyPageMapper {

	int profile(Member mem);

	int updateInfo(Member inputMember);

	int checkNickname(String nickname, Member inputMember);

	String selectPw(int memberNo);

	int changePw(Map<String, Object> paramMap);

	int getMyCreateListCount(Map<String, Object> map);

	List<Board> selectMyCreateBoardList(Map<String, Object> map, RowBounds rowBounds);

	int getMyBelongListCount(Map<String, Object> map);

	List<Board> selectMyBelongBoardList(Map<String, Object> map, RowBounds rowBounds);

	int getMyHeartListCount(Map<String, Object> map);

	List<Board> selectMyHeartBoardList(Map<String, Object> map, RowBounds rowBounds);
}
