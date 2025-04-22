package kr.or.ddit.vo;

import lombok.Data;

@Data
public class BookReservationsVo {
	
	private int reserveNo;
	private String reserveDate;
	private String reserveStatus;
	private int bookNo;
	private int userNo;
}
