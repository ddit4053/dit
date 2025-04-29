package kr.or.ddit.reading.service;

import java.util.List;

import kr.or.ddit.vo.ReadingReservationsVo;

public interface MyReservationService {
    List<ReadingReservationsVo> getReservationsByUserNo(int userNo);
    
    
}
