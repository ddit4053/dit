package kr.or.ddit.vo;

import lombok.Data;

@Data
public class ReviewsVo {
	private int revNo;
	private int rating;
	private String revContent;
	private String revDate;
	private int bookNo;
	private int userNo;
}
