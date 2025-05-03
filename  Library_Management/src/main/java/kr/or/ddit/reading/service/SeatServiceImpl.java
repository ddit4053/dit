package kr.or.ddit.reading.service;

import java.util.List;

import kr.or.ddit.reading.dao.ISeatDao;
import kr.or.ddit.reading.dao.SeatDaoImpl;
import kr.or.ddit.vo.ReadingSeatsVo;

public class SeatServiceImpl implements ISeatService {

    private final ISeatDao seatDao;
    
    // 싱글톤 패턴 적용
    private static SeatServiceImpl instance = new SeatServiceImpl();
    
    private SeatServiceImpl() {
        // SeatDaoImpl의 getInstance() 메소드를 통해 싱글톤 객체 참조
        this.seatDao = SeatDaoImpl.getInstance();
    }
    
    public static SeatServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<ReadingSeatsVo> getAllSeats() {
        System.out.println("===== SeatServiceImpl.getAllSeats 메소드 호출 =====");
        
        List<ReadingSeatsVo> seatList = seatDao.selectAllSeats();
        
        System.out.println("조회 결과: " + seatList.size() + "개 좌석");
        if (!seatList.isEmpty()) {
            System.out.println("첫 번째 좌석 정보: " + seatList.get(0));
        }
        
        return seatList;
    }

    @Override
    public List<ReadingSeatsVo> getSeatsByRoomName(String roomName) {
        System.out.println("===== SeatServiceImpl.getSeatsByRoomName 메소드 호출 =====");
        System.out.println("파라미터 roomName: " + roomName);
        
        List<ReadingSeatsVo> seatList = seatDao.selectSeatsByRoomName(roomName);
        
        System.out.println("조회 결과: " + seatList.size() + "개 좌석");
        if (!seatList.isEmpty()) {
            System.out.println("첫 번째 좌석 정보: " + seatList.get(0));
        }
        
        return seatList;
    }
}