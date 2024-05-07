package edu.kh.norazo.board.model.dto;

import java.util.List;

import edu.kh.norazo.member.model.dto.Member;
import edu.kh.norazo.myPage.model.dto.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {
	
	private int boardNo;
	private String boardTitle;
	private String boardContent;
	private String boardWriteDate;
	private String boardUpdateDate;
	private String boardDelFl;
	private int memberNo; // 게시글 작성한 회원 번호
	private int boardCode; // 1 : 모임 게시판 / 2 : 자유 게시판 / 3 : 문의 게시판
	
// 	private List<Comment> commentList; // Comment dto 아직 안 만듬
	private int likeCheck;
	
	// 게시글 작성자 프로필 이미지
	private String profileImg;
	// 게시글 목록 썸네일 이미지
	private String thumbnail;
	
	private int attendMemberCount; // 모임에 참석한 현재 멤버 수
	
	private String sportsKrName; // 스포츠 한글 이름 (화면에 종목별 게시판 텍스트 넣기 위한 필드)
	
	// 여기서부터는 모임 게시글 작성할때만 필요한 필드
	private String sportsCode; // 스포츠 종목
	private int memberCountLimit; // 참석 인원 제한수
	private String meetingLocation; // 모임 장소
	private String meetingDate; // 모임 일시
	
	private List<Member> memberList;
}