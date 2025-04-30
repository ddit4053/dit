package kr.or.ddit.reading.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import kr.or.ddit.reading.dao.IReadingReservationDao;
import kr.or.ddit.reading.dao.ReadingReservationDaoImpl;
import kr.or.ddit.vo.ReadingReservationsVo;

public class ReadingReservationServiceImpl implements IReadingReservationService {

    private IReadingReservationDao reservationDao;

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
        LocalTime newStart = parseTimeSafely(startTimeStr);
        LocalTime newEnd = parseTimeSafely(endTimeStr);

        // 여기!!! 날짜를 고려해서 가져와야 해
        LocalDate today = LocalDate.now();
        List<ReadingReservationsVo> reservations = reservationDao.selectReservationsBySeatAndDate(seatNo, today);

        if (reservations == null) {
            reservations = new java.util.ArrayList<>();
        }

        for (ReadingReservationsVo vo : reservations) {
            if (newStart.isBefore(vo.getEndTime()) && newEnd.isAfter(vo.getStartTime())) {
                return false; // 시간 겹침
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

        LocalTime start = parseTimeSafely(startTimeStr);
        LocalTime end = parseTimeSafely(endTimeStr);

        return !start.isBefore(open) && !end.isAfter(close) && start.isBefore(end);
    }

    @Override
    public List<ReadingReservationsVo> getAllReservations() {
        return reservationDao.selectAllReservations();
    }

    @Override
    public List<ReadingReservationsVo> getReservationsByRoomAndDate(String roomName, LocalDate selectedDate) {
        return reservationDao.selectReservationsByRoomAndDate(roomName, selectedDate);
    }

    // 🛠️ 내부 메서드 (시간 파싱용)
    private LocalTime parseTimeSafely(String timeStr) {
        if (timeStr == null) {
            throw new IllegalArgumentException("시간이 null입니다.");
        }
        if (timeStr.length() == 4) {
            timeStr = "0" + timeStr;
        }
        return LocalTime.parse(timeStr);
    }
}
