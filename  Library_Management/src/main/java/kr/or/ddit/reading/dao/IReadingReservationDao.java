package kr.or.ddit.reading.dao;

import java.time.LocalDate;
import java.util.List;

import kr.or.ddit.vo.ReadingReservationsVo;

public interface IReadingReservationDao {

    int insertReservation(ReadingReservationsVo vo);

    List<ReadingReservationsVo> selectReservationsByUser(int userNo);

    List<ReadingReservationsVo> selectReservationsBySeat(int seatNo);

    List<ReadingReservationsVo> selectAllReservations();

    List<ReadingReservationsVo> selectByUserNo(int userNo);

    List<ReadingReservationsVo> selectReservationsByRoomAndDate(String roomName, LocalDate reserveDate);

    List<ReadingReservationsVo> selectReservationsBySeatAndDate(int seatNo, LocalDate reserveDate);
    
    List<ReadingReservationsVo> selectReservationsByUserAndDate(int userNo, LocalDate date);
}
