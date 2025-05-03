package kr.or.ddit.reading.dao;

import java.util.List;

import kr.or.ddit.vo.ReadingReservationsVo;
import kr.or.ddit.vo.ReadingSeatsVo;

public interface ISeatDao {
    
    // 🔥 이거 추가!!
    List<ReadingSeatsVo> selectAllSeats();

    // 🔥 이미 있는 메서드들
    List<ReadingSeatsVo> selectSeatsByRoomName(String roomName);

    List<ReadingReservationsVo> selectReservationsBySeatAndDate(int seatNo, java.time.LocalDate reserveDate);
}
