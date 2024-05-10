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

	int getMyCreateListCount(String boardCode, int memberNo);

	List<Board> selectMyCreateBoardList(String boardCode, RowBounds rowBounds, int memberNo);

	int getMyBelongListCount(String boardCode, int memberNo);

	List<Board> selectMyBelongBoardList(String boardCode, RowBounds rowBounds, int memberNo);

	int getMyHeartListCount(String boardCode, int memberNo);

	List<Board> selectMyHeartBoardList(String boardCode, RowBounds rowBounds, int memberNo);
}
