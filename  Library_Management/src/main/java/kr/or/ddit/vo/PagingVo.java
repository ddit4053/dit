package kr.or.ddit.vo;

import lombok.Data;

@Data
public class PagingVo {
	private int currentPage;        // 현재 페이지
	private int pageSize;           // 페이지당 게시글 수
	private int totalCount;         // 전체 게시글 수
	private int totalPages;         // 전체 페이지 수
	private int startPage;          // 시작 페이지 번호
	private int endPage;            // 끝 페이지 번호
	private int startRow;           // 현재 페이지에서 표시될 첫 번째 게시글의 번호
	private int endRow;             // 현재 페이지에서 표시될 마지막 게시글의 번호
	private int pageBlockSize;      // 페이지 블록 크기 (예: 10페이지씩 표시)
	
	// 기본 생성자
	public PagingVo() {
		this.pageSize = 10;			// 기본값
		this.pageBlockSize = 10; 	// 기본값
	}
	
	// 페이징 계산 생성자
	public PagingVo(int currentPage, int pageSize, int totalCount) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.pageBlockSize = 10;
		
		calculatePaging();
	}
	
	// 페이징 계산 메서드
	public void calculatePaging() {
		
		// 전체 페이지 수 계산
		this.totalPages = (int) Math.ceil((double) totalCount / pageSize);
		
		// 잘못된 페이징 요청 정정
		// 현재 페이지가 전체 페이지 수보다 크면 마지막 페이지로 자동 조정
		if(currentPage > totalPages) currentPage = totalPages;
		if(currentPage < 1) currentPage = 1;
		
		// 게시글 시작 행, 끝 행 번호 계산
		this.startRow = (currentPage - 1) * pageSize + 1;
		this.endRow = startRow + pageSize -1;
		if(endRow > totalCount) endRow = totalCount; // 끝 페이지에서 끝 게시글 행 번호 정정
		
		// 시작 페이지, 끝 페이지 계산
		this.startPage = ((currentPage - 1) / pageBlockSize) * pageBlockSize + 1;
		this.endPage = startPage + pageBlockSize - 1;
		if(endPage > totalPages) endPage = totalPages;
	}
}