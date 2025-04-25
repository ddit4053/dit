package kr.or.ddit.reading.dao;

import java.util.List;
import kr.or.ddit.vo.ReadingSeatsVo;

public interface SeatDao {
    List<ReadingSeatsVo> selectSeatsByRoom(String roomName);
}
