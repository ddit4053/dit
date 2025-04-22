package kr.or.ddit.vo;

import lombok.Data;

@Data
public class EduEventVo {
	
	private int evNo;
	private String evTitle;
	private String description;
	private String evDate;
	private String location;
	private int maxParticipants;
	private String registrationDeadline;
	private String evType;
}
