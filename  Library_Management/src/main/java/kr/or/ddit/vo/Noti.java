package kr.or.ddit.vo;

import lombok.Data;

@Data
public class Noti {
	private int notiNo;
	private String notiType;
	private String message;
	private String sentDate;
	private int userNo;
}
