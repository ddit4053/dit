package kr.or.ddit.reading.service;

import java.util.List;
import kr.or.ddit.vo.ReadingSeatsVo;

public interface ISeatService {
    // 열람실 구분 없이 전체 좌석 조회
    List<ReadingSeatsVo> getAllSeats();

    // 🔥 열람실 이름별 좌석 조회 추가 (SeatList.jsp에서 roomName 선택시 필요)
    List<ReadingSeatsVo> getSeatsByRoomName(String roomName);
}
