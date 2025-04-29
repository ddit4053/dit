package kr.or.ddit.reading.service;

import java.util.List;

import kr.or.ddit.vo.ReadingReservationsVo;

public interface ReadingReservationService {

    boolean insertReservation(ReadingReservationsVo vo);

    List<ReadingReservationsVo> getReservationsByUser(int userNo);

    List<ReadingReservationsVo> getReservationsBySeat(int seatNo);

    boolean isSeatAvailable(int seatNo, String startTime, String endTime);

    List<ReadingReservationsVo> selectByUserNo(int userNo);

    boolean isWithinOperatingHours(String startTime, String endTime);
}
