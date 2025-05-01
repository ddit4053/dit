package kr.or.ddit.reading.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.ReadingReservationsVo;

public interface ReadingReservationService {
	 // 1. 좌석 예약 등록
    boolean insertReservation(ReadingReservationsVo vo);

    // 2. 사용자별 예약 내역 조회
    List<ReadingReservationsVo> getReservationsByUser(int userNo);

    // 3. 좌석별 예약 시간대 조회
    List<ReadingReservationsVo> getReservationsBySeat(int seatNo);

    // 4. 해당 좌석이 주어진 시간에 예약 가능한지 확인
    boolean isSeatAvailable(int seatNo, String startTime, String endTime);
    
    //5. 내 예약내역 확인 
    List<ReadingReservationsVo> selectByUserNo(int userNo);
   

    // 5. 예약 시간이 운영시간 내(09:00~18:00)인지 확인
    boolean isWithinOperatingHours(String startTime, String endTime);
    
    Map<Integer, String> getReservationMapByRoom(String roomName);


}

