package kr.or.ddit.reading.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import kr.or.ddit.vo.ReadingReservationsVo;

public interface IReadingReservationService {

    boolean insertReservation(ReadingReservationsVo vo);
    
    List<ReadingReservationsVo> getReservationsByUser(int userNo);
    
    List<ReadingReservationsVo> getReservationsBySeat(int seatNo);
    
    boolean isSeatAvailable(int seatNo, String startTimeStr, String endTimeStr);
    
    List<ReadingReservationsVo> selectByUserNo(int userNo);
    
    boolean isWithinOperatingHours(String startTimeStr, String endTimeStr);
    
    List<ReadingReservationsVo> getAllReservations();
    
    List<ReadingReservationsVo> getReservationsByRoomAndDate(String roomName, LocalDate selectedDate);
    
    // 추가된 메서드: 사용자가 해당 날짜에 예약한 내역이 있는지 확인
    boolean hasUserReservedToday(int userNo, LocalDate date);
    
    // 추가된 메서드: 특정 사용자의 특정 날짜 예약 내역 조회
    List<ReadingReservationsVo> selectReservationsByUserAndDate(int userNo, LocalDate date);
}