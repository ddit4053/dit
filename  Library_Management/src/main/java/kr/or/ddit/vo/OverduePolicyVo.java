package kr.or.ddit.vo;

import lombok.Data;

@Data
public class OverduePolicyVo {
	private int policyNo;
	private int days;
	private String excludeWeekend;
	private String startDate;
	private String endDate;
	private String isActive;
	private String note;
}
