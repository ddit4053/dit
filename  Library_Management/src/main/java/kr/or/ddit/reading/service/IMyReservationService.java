package kr.or.ddit.reading.service;

import java.time.LocalDate;
import java.util.List;

import kr.or.ddit.vo.ReadingReservationsVo;

public interface IMyReservationService {
    // 나의 예약 조회
    List<ReadingReservationsVo> getReservationsByUserNo(int userNo);

    // 예약 취소 추가
    int cancelReservation(int rReserveNo);
    
    // 예약 확인 
    boolean hasUserReservedToday(int userNo, LocalDate date);
}
