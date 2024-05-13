package edu.kh.norazo.board.model.dto;

// 00 게시판 pagination
public class BoardPagination {
	
	private int currentPage;		// 현재 페이지 번호
	private int listCount;			// 전체 게시글 수

	private int prevPage;			// 이전 페이지 모음의 마지막 번호
	private int nextPage;			// 다음 페이지 모음의 시작 번호
	
	
	public BoardPagination(int currentPage, int listCount) {
		super();
		this.currentPage = currentPage;
		this.listCount = listCount;
	}


	public int getCurrentPage() {
		return currentPage;
	}


	public int getListCount() {
		return listCount;
	}


	public int getPrevPage() {
		return prevPage;
	}


	public int getNextPage() {
		return nextPage;
	}


	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}


	public void setListCount(int listCount) {
		this.listCount = listCount;
	}


	@Override
	public String toString() {
		return "BoardPagination [currentPage=" + currentPage + ", listCount=" + listCount + ", prevPage=" + prevPage
				+ ", nextPage=" + nextPage + "]";
	}
	
	private void calculate() {
		
		
	}
	
}
