package kr.or.ddit.vo;

import lombok.Data;

@Data
public class PwResetTokenVo {
	private int tokenNo;
	private String pwToken;
	private String pwReqDate;
	private int userNo;
}
