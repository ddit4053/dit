package kr.or.ddit.reading.service;

import java.time.LocalTime;
import java.util.List;

import kr.or.ddit.reading.dao.ReadingReservationDao;
import kr.or.ddit.reading.dao.ReadingReservationDaoImpl;
import kr.or.ddit.vo.ReadingReservationsVo;

public class ReadingReservationServiceImpl implements ReadingReservationService {

    private ReadingReservationDao reservationDao;

    public ReadingReservationServiceImpl() {
        reservationDao = new ReadingReservationDaoImpl();
    }

    @Override
    public boolean insertReservation(ReadingReservationsVo vo) {
        return reservationDao.insertReservation(vo) > 0;
    }

    @Override
    public List<ReadingReservationsVo> getReservationsByUser(int userNo) {
        return reservationDao.selectReservationsByUser(userNo);
    }

    @Override
    public List<ReadingReservationsVo> getReservationsBySeat(int seatNo) {
        return reservationDao.selectReservationsBySeat(seatNo);
    }

    @Override
    public boolean isSeatAvailable(int seatNo, String startTimeStr, String endTimeStr) {
        LocalTime newStart = LocalTime.parse(startTimeStr.length() == 4 ? "0" + startTimeStr : startTimeStr);
        LocalTime newEnd = LocalTime.parse(endTimeStr.length() == 4 ? "0" + endTimeStr : endTimeStr);

        List<ReadingReservationsVo> reservations = reservationDao.selectReservationsBySeat(seatNo);
        for (ReadingReservationsVo vo : reservations) {
            if (newStart.isBefore(vo.getEndTime()) && newEnd.isAfter(vo.getStartTime())) {
                return false; // 겹침
            }
        }
        return true;
    }

    @Override
    public List<ReadingReservationsVo> selectByUserNo(int userNo) {
        return reservationDao.selectByUserNo(userNo);
    }

    @Override
    public boolean isWithinOperatingHours(String startTimeStr, String endTimeStr) {
        LocalTime open = LocalTime.of(9, 0);
        LocalTime close = LocalTime.of(18, 0);
        LocalTime start = LocalTime.parse(startTimeStr.length() == 4 ? "0" + startTimeStr : startTimeStr);
        LocalTime end = LocalTime.parse(endTimeStr.length() == 4 ? "0" + endTimeStr : endTimeStr);

        return !start.isBefore(open) && !end.isAfter(close) && start.isBefore(end);
    }
}
