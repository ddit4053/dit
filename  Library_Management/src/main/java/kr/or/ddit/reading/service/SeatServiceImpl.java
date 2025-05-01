package kr.or.ddit.reading.service;

import java.util.List;

import kr.or.ddit.reading.dao.SeatDao;
import kr.or.ddit.reading.dao.SeatDaoImpl;
import kr.or.ddit.vo.ReadingSeatsVo;

public class SeatServiceImpl implements SeatService {

    private SeatDao seatDao = new SeatDaoImpl();

    @Override
    public List<ReadingSeatsVo> getSeatsByRoom(String roomName) {
        return seatDao.selectSeatsByRoom(roomName);
    }
}
