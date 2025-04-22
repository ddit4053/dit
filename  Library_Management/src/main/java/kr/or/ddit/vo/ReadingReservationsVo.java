package kr.or.ddit.vo;

import lombok.Data;

@Data
public class ReadingReservationsVo {
	private int rReserveNo;
	private String startTime;
	private String endTime;
	private String rReserveStatus;
	private int userNo;
	private int seatNo;
}
