package kr.or.ddit.vo;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class ReadingReservationsVo {
    private int rReserveNo;  // 예약 번호
    private LocalDate reserveDate;   // 예약 날짜
    private LocalTime startTime;      // 시작 시간
    private LocalTime endTime;        // 종료 시간
    private String rReserveStatus;    // 예약 상태
    private int userNo;               // 사용자 번호
    private int seatNo;               // 좌석 번호
    private String roomName;          // 열람실 이름

}