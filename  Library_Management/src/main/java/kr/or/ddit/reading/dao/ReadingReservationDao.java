package kr.or.ddit.reading.dao;

import java.util.List;

import kr.or.ddit.vo.ReadingReservationsVo;

public interface ReadingReservationDao {
	
	    int insertReservation(ReadingReservationsVo vo);

	    List<ReadingReservationsVo> selectReservationsByUser(int userNo);

	    List<ReadingReservationsVo> selectReservationsBySeat(int seatNo);

	    List<ReadingReservationsVo> selectByUserNo(int userNo);
	  
	  

	   }

	


