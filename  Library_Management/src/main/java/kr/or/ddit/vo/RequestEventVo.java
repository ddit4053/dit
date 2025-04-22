package kr.or.ddit.vo;

import lombok.Data;

@Data
public class RequestEventVo {
	private int reqEvNo;
	private String reqEvDate;
	private String activityType;
	private String reqEvStatus;
	private int userNo;
	private int evNo;
}
