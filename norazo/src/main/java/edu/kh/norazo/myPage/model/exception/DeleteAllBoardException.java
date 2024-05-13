package edu.kh.norazo.myPage.model.exception;

public class DeleteAllBoardException extends RuntimeException {

	public DeleteAllBoardException() {
		super("탈퇴하려는 회원이 작성한 게시물 모두 삭제 중 예외 발생");
	}
	
	public DeleteAllBoardException(String message) {
		super(message);
	}
	
}