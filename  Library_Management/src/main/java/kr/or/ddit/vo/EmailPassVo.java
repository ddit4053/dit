package kr.or.ddit.vo;

import lombok.Data;

@Data
public class EmailPassVo {
	
	private int passNo;
	private String email;
	private String emToken;
	private String verified;
	private String emReqDate;
	private String purpose;
	
}
