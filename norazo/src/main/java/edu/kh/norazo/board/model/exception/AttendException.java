package edu.kh.norazo.board.model.exception;

public class AttendException extends RuntimeException {

	public AttendException() {
		super("작성한 모임에 작성한 회원 참석 중 예외 발생");
	}
	
	public AttendException(String message) {
		super(message);
	}
	
}