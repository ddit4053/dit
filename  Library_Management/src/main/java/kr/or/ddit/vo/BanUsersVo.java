package kr.or.ddit.vo;

import lombok.Data;

@Data
public class BanUsersVo {
	
	private int banNo;
	private String banDate;
	private int banDays;
	private String releaseDate;
	private int policyNo;
	private int loanNo;
	private int userNo;
	private String banNote;
}
