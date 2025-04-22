package kr.or.ddit.vo;

import lombok.Data;

@Data
public class BookLoansVo {
	
	private int loanNo;
	private String loanDate;
	private String dueDate;
	private String returnDate;
	private int userNo;
	private int realBook;
	private int policyNo;
}
