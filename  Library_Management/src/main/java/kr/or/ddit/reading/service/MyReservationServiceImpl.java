package kr.or.ddit.reading.service;

import java.util.List;

import kr.or.ddit.reading.dao.IMyReservationDao;
import kr.or.ddit.reading.dao.MyReservationDaoImpl;
import kr.or.ddit.vo.ReadingReservationsVo;

public class MyReservationServiceImpl implements IMyReservationService {

    private final IMyReservationDao dao;

    public MyReservationServiceImpl() {
        this.dao = new MyReservationDaoImpl();
    }

    @Override
    public List<ReadingReservationsVo> getReservationsByUserNo(int userNo) {
        return dao.getReservationsByUser(userNo);
    }

    @Override
    public int cancelReservation(int rReserveNo) {
        return dao.updateReservationStatus(rReserveNo);
    }
}
