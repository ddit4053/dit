package kr.or.ddit.reading.service;

import java.time.LocalDate;
import java.util.List;

import kr.or.ddit.reading.dao.IMyReservationDao;
import kr.or.ddit.reading.dao.MyReservationDaoImpl;
import kr.or.ddit.vo.ReadingReservationsVo;

public class MyReservationServiceImpl implements IMyReservationService {
    
    private IMyReservationDao myReservationDao;
    
    // 싱글톤 패턴 적용
    private static MyReservationServiceImpl instance = new MyReservationServiceImpl();
    
    private MyReservationServiceImpl() {
        // MyReservationDaoImpl의 getInstance() 메소드를 통해 싱글톤 객체 참조
        myReservationDao = MyReservationDaoImpl.getInstance();
    }
    
    public static MyReservationServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<ReadingReservationsVo> getReservationsByUserNo(int userNo) {
        return myReservationDao.getReservationsByUser(userNo);
    }

    @Override
    public int cancelReservation(int rReserveNo) {
        return myReservationDao.updateReservationStatus(rReserveNo);
    }
    
    @Override
    public boolean hasUserReservedToday(int userNo, LocalDate date) {
        List<ReadingReservationsVo> userReservations = myReservationDao.getReservationsByUserAndDate(userNo, date);
        return userReservations != null && !userReservations.isEmpty();
    }
}