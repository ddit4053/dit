package kr.or.ddit.vo;

import lombok.Data;

@Data
public class BookLoanApprovalVo {
	
	private int approvalNo;
	private String approvalStatus;
	private int approvedBy;
	private String approvedDate;
	private int loanNo;
	private int userNo;
}
