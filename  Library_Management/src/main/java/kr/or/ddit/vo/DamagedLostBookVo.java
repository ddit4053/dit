package kr.or.ddit.vo;

import lombok.Data;

@Data
public class DamagedLostBookVo {
	
	private int lostNo;
	private String reportDate;
	private String lostStatus;
	private String description;
	private String processed;
	private String processedDate;
	private int userNo;
	private int realBook;
}
