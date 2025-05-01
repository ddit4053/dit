package kr.or.ddit.reading.dao;

import java.util.List;
import kr.or.ddit.vo.ReadingReservationsVo;

public interface IMyReservationDao {
    List<ReadingReservationsVo> getReservationsByUser(int userNo);

    // ✨ 예약 취소 기능 추가
    int updateReservationStatus(int rReserveNo);
}
