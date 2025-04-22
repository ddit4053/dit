package kr.or.ddit.vo;

import lombok.Data;

@Data
public class BookRequestsVo {
	
	private int reqBookNo;
	private String reqBookTitle;
	private String reqBookAuthor;
	private String reqBookPublisher;
	private String reqBookComment;
	private String reqBookDate;
	private String reqBookStatus;
	private int userNo;
}
