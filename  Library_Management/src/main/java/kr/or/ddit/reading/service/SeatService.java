package kr.or.ddit.reading.service;

import java.util.List;
import kr.or.ddit.vo.ReadingSeatsVo;

public interface SeatService {
    // 열람실 구분 없이 전체 좌석 조회
    List<ReadingSeatsVo> getAllSeats();
}
