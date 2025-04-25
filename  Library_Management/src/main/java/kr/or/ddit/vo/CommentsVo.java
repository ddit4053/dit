package kr.or.ddit.vo;

import lombok.Data;

@Data
public class CommentsVo {
	
	private int cmNo;
	private String cmContent;
	private String cmWrittenDate;
	private int cmNo2;
	private int userNo;
	private int boardNo;
}	
