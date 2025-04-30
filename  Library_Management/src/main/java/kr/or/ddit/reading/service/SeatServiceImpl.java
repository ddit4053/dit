package kr.or.ddit.reading.service;

import java.util.List;
import kr.or.ddit.reading.dao.ISeatDao;
import kr.or.ddit.reading.dao.SeatDaoImpl;
import kr.or.ddit.vo.ReadingSeatsVo;

public class SeatServiceImpl implements ISeatService {

    private ISeatDao seatDao;

    public SeatServiceImpl() {
        seatDao = new SeatDaoImpl();
    }

    @Override
    public List<ReadingSeatsVo> getAllSeats() {
        return seatDao.selectAllSeats();
    }

    // 🔥 여기 수정: roomName 무시하고 그냥 전체좌석 조회
    @Override
    public List<ReadingSeatsVo> getSeatsByRoomName(String roomName) {
        return seatDao.selectAllSeats();   // 강제 전체조회
    }
}
