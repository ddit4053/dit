package kr.or.ddit.reading.service;

import java.time.LocalDate;
import java.util.List;
import kr.or.ddit.vo.ReadingReservationsVo;

public interface IReadingReservationService {

    boolean insertReservation(ReadingReservationsVo vo);

    List<ReadingReservationsVo> getReservationsByUser(int userNo);

    List<ReadingReservationsVo> getReservationsBySeat(int seatNo);

    boolean isSeatAvailable(int seatNo, String startTime, String endTime);

    List<ReadingReservationsVo> selectByUserNo(int userNo);

    boolean isWithinOperatingHours(String startTime, String endTime);

    List<ReadingReservationsVo> getAllReservations();

    // 🔥 추가 (SeatList 눈금 채우기용)
    List<ReadingReservationsVo> getReservationsByRoomAndDate(String roomName, LocalDate selectedDate);
}
