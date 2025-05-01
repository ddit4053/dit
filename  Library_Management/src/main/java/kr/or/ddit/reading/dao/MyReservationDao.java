package kr.or.ddit.reading.dao;

import java.util.List;
import kr.or.ddit.vo.ReadingReservationsVo;

public interface MyReservationDao {
    List<ReadingReservationsVo> getReservationsByUser(int userNo);
}
