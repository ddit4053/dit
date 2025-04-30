package kr.or.ddit.vo;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReadingReservationsVo {
    
    private int rReserveNo;      // 예약 번호
    private int userNo;          // 사용자 번호
    private int seatNo;          // 좌석 번호
    private LocalDate reserveDate; // 예약 날짜
    private LocalTime startTime;   // 예약 시작 시간
    private LocalTime endTime;     // 예약 종료 시간
    private String rReserveStatus; // 예약 상태 (예약완료, 취소 등)
    private String roomName;       // 열람실 이름

    // -- Getter/Setter
    public int getrReserveNo() {
        return rReserveNo;
    }

    public void setrReserveNo(int rReserveNo) {
        this.rReserveNo = rReserveNo;
    }

    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    public int getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }

    public LocalDate getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(LocalDate reserveDate) {
        this.reserveDate = reserveDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getrReserveStatus() {
        return rReserveStatus;
    }

    public void setrReserveStatus(String rReserveStatus) {
        this.rReserveStatus = rReserveStatus;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
