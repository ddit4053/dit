package kr.or.ddit.reading.service;

import java.util.List;
import kr.or.ddit.reading.dao.SeatDao;
import kr.or.ddit.reading.dao.SeatDaoImpl;
import kr.or.ddit.vo.ReadingSeatsVo;

public class SeatServiceImpl implements SeatService {

    private SeatDao seatDao;

    public SeatServiceImpl() {
        seatDao = new SeatDaoImpl();
    }

    @Override
    public List<ReadingSeatsVo> getAllSeats() {
        return seatDao.selectAllSeats();
    }
}
