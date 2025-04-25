package kr.or.ddit.reading.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import kr.or.ddit.reading.dao.ReadingReservationDao;
import kr.or.ddit.reading.dao.ReadingReservationDaoImpl;
import kr.or.ddit.vo.ReadingReservationsVo;

public class ReadingReservationServiceImpl implements ReadingReservationService {
	 private ReadingReservationDao reservationDao = new ReadingReservationDaoImpl();

	 @Override
	 public boolean insertReservation(ReadingReservationsVo vo) {
	     System.out.println("[서비스] 예약 시도:");
	     System.out.println("seatNo: " + vo.getSeatNo());
	     System.out.println("userNo: " + vo.getUserNo());
	     System.out.println("start: " + vo.getStartTime());
	     System.out.println("end: " + vo.getEndTime());

	     if (!isWithinOperatingHours(vo.getStartTime(), vo.getEndTime())) {
	         System.out.println("⛔ 운영시간 밖입니다.");
	         return false;
	     }
	     if (!isSeatAvailable(vo.getSeatNo(), vo.getStartTime(), vo.getEndTime())) {
	         System.out.println("⛔ 좌석이 해당 시간에 이미 예약되어 있습니다.");
	         return false;
	     }

	     boolean result = reservationDao.insertReservation(vo) > 0;
	     System.out.println("INSERT 결과: " + result);
	     return result;
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
	    public boolean isSeatAvailable(int seatNo, String startTime, String endTime) {
	        List<ReadingReservationsVo> existing = reservationDao.selectReservationsBySeat(seatNo);

	        for (ReadingReservationsVo res : existing) {
	            if (isTimeOverlap(startTime, endTime, res.getStartTime(), res.getEndTime())) {
	                return false;
	            }
	        }
	        return true;
	    }

	    @Override
	    public boolean isWithinOperatingHours(String startTime, String endTime) {
	        try {
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	            LocalDateTime start = LocalDateTime.parse(startTime, formatter);
	            LocalDateTime end = LocalDateTime.parse(endTime, formatter);

	            LocalTime libOpen = LocalTime.of(9, 0);
	            LocalTime libClose = LocalTime.of(18, 0);

	            return !start.toLocalTime().isBefore(libOpen) && !end.toLocalTime().isAfter(libClose);

	        } catch (Exception e) {
	            System.out.println("⛔ 운영시간 체크 실패: " + e.getMessage());
	            return false;
	        }
	    }


	    // 시간 겹침 체크 (start1~end1 vs start2~end2)
	    private boolean isTimeOverlap(String start1, String end1, String start2, String end2) {
	        LocalTime s1 = LocalTime.parse(start1);
	        LocalTime e1 = LocalTime.parse(end1);
	        LocalTime s2 = LocalTime.parse(start2);
	        LocalTime e2 = LocalTime.parse(end2);

	        // e1 <= s2 또는 s1 >= e2 → 겹치지 않음
	        // 그 반대는 겹침
	        return !e1.isBefore(s2) && !s1.isAfter(e2);
	    }
	    
	    @Override
	    public Map<Integer, String> getReservationMapByRoom(String roomName) {
	        List<ReadingReservationsVo> list = reservationDao.selectReservationsByRoom(roomName);
	        Map<Integer, List<Integer>> rawMap = new HashMap<>();

	        for (ReadingReservationsVo vo : list) {
	            int seatNo = vo.getSeatNo();
	            int start = Integer.parseInt(vo.getStartTime().substring(0, 2));
	            int end = Integer.parseInt(vo.getEndTime().substring(0, 2));
	            rawMap.putIfAbsent(seatNo, new ArrayList<>());
	            for (int i = start; i < end; i++) {
	                rawMap.get(seatNo).add(i);
	            }
	        }

	        Map<Integer, String> result = new HashMap<>();
	        for (var entry : rawMap.entrySet()) {
	            result.put(entry.getKey(), entry.getValue().stream()
	                          .map(String::valueOf).collect(Collectors.joining(",")));
	        }

	        return result;
	    }
	    
	    @Override
	    public List<ReadingReservationsVo> selectByUserNo(int userNo) {
	        return reservationDao.selectByUserNo(userNo);
	    }
	    
	    

}
