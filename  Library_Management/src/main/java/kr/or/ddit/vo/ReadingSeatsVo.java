package kr.or.ddit.vo;

import lombok.Data;

@Data
public class ReadingSeatsVo {
	private int seatNo;
	private String seatNumber;
	private String isAvailable;
	private int roomNo;
}
