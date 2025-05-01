package kr.or.ddit.reading.dao;

import java.util.List;

import kr.or.ddit.vo.ReadingReservationsVo;

public interface ReadingReservationDao {
	
		 // 1. 예약 등록
	    int insertReservation(ReadingReservationsVo vo);

	    // 2. 사용자별 예약 조회
	    List<ReadingReservationsVo> selectReservationsByUser(int userNo);

	    // 3. 좌석별 예약 조회 (중복 체크용)
	    List<ReadingReservationsVo> selectReservationsBySeat(int seatNo);
	    
	    // 4. 좌석 전체 현황 가져오기
	    List<ReadingReservationsVo> selectReservationsByRoom(String roomName);
   
        //5.내 예약 내역 조회
	    List<ReadingReservationsVo> selectByUserNo(int userNo);
	   }

	


