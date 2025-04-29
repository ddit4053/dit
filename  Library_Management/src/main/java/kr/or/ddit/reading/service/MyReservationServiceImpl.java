package kr.or.ddit.reading.service;

import java.util.List;
import kr.or.ddit.reading.dao.MyReservationDao;
import kr.or.ddit.reading.dao.MyReservationDaoImpl;
import kr.or.ddit.vo.ReadingReservationsVo;

public class MyReservationServiceImpl implements MyReservationService {

    private final MyReservationDao dao;

    public MyReservationServiceImpl() {
        this.dao = new MyReservationDaoImpl();
    }

    @Override
    public List<ReadingReservationsVo> getReservationsByUserNo(int userNo) {
        return dao.getReservationsByUser(userNo);  
        
        }
}
