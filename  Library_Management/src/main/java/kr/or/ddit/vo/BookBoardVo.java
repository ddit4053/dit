package kr.or.ddit.vo;

import lombok.Data;

@Data
public class BookBoardVo {
	
	private int boardNo;
	private String title;
	private String content;
	private String writtenDate;
	private int views;
	private int userNo;
	private int bookNo;
	private int codeNo;
	private int fileGroupNum;
}
