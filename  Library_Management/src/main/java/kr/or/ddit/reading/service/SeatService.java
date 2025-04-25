package kr.or.ddit.reading.service;

import java.util.List;
import kr.or.ddit.vo.ReadingSeatsVo;

public interface SeatService {
    List<ReadingSeatsVo> getSeatsByRoom(String roomName);
}

