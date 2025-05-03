package kr.or.ddit.reading.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import kr.or.ddit.util.DBUtil;
import kr.or.ddit.vo.ReadingReservationsVo;
import kr.or.ddit.vo.ReadingSeatsVo;

public class SeatDaoImpl implements ISeatDao {

    // 싱글톤 패턴 적용
    private static SeatDaoImpl instance = new SeatDaoImpl();
    
    private SeatDaoImpl() {
        // 생성자는 private으로 설정
    }
    
    public static SeatDaoImpl getInstance() {
        return instance;
    }

    @Override
    public List<ReadingSeatsVo> selectAllSeats() {
        System.out.println("===== SeatDaoImpl.selectAllSeats 메소드 호출 =====");
        List<ReadingSeatsVo> list = new ArrayList<>();
        
        // 읽기 전용 쿼리임을 힌트로 추가
        String sql = "SELECT /*+ RESULT_CACHE */ s.*, r.room_name FROM reading_seats s JOIN reading_rooms r ON s.room_no = r.room_no ORDER BY s.seat_no";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            
            // 쿼리 타임아웃 설정
            ps.setQueryTimeout(5); // 5초 타임아웃
            
            rs = ps.executeQuery();

            int count = 0;
            while (rs.next()) {
                ReadingSeatsVo vo = new ReadingSeatsVo();
                vo.setSeatNo(rs.getInt("seat_no"));
                try {
                    vo.setSeatNumber(rs.getString("seat_number"));
                } catch (Exception e) {
                    System.out.println("seat_number 컬럼이 없음");
                }
                vo.setIsAvailable(rs.getString("is_available"));
                vo.setRoomNo(rs.getInt("room_no"));
                list.add(vo);
                count++;
            }
            System.out.println("조회된 좌석 수: " + count);

        } catch (Exception e) {
            System.out.println("전체 좌석 조회 중 예외 발생: " + e.getMessage());
            e.printStackTrace();
            // 복구 전략 - 샘플 데이터 생성
            createSampleSeats(list, 36);
        } finally {
            DBUtil.closeResources(conn, ps, rs);
        }

        if (list.isEmpty()) {
            System.out.println("조회 결과가 없어 샘플 데이터 생성");
            createSampleSeats(list, 36);
        }

        return list;
    }

    @Override
    public List<ReadingSeatsVo> selectSeatsByRoomName(String roomName) {
        System.out.println("===== SeatDaoImpl.selectSeatsByRoomName 메소드 호출 =====");
        List<ReadingSeatsVo> list = new ArrayList<>();
        String sql = "SELECT /*+ RESULT_CACHE */ s.* FROM reading_seats s JOIN reading_rooms r ON s.room_no = r.room_no WHERE r.room_name = ? ORDER BY s.seat_no";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, roomName);
            
            // 쿼리 타임아웃 설정
            ps.setQueryTimeout(5); // 5초 타임아웃
            
            rs = ps.executeQuery();
            
            int count = 0;
            while (rs.next()) {
                ReadingSeatsVo vo = new ReadingSeatsVo();
                vo.setSeatNo(rs.getInt("seat_no"));
                try {
                    vo.setSeatNumber(rs.getString("seat_number"));
                } catch (Exception e) {
                    System.out.println("seat_number 컬럼이 없음");
                }
                vo.setIsAvailable(rs.getString("is_available"));
                vo.setRoomNo(rs.getInt("room_no"));
                list.add(vo);
                count++;
            }
            System.out.println("조회된 좌석 수: " + count);
        } catch (Exception e) {
            System.out.println("열람실별 좌석 조회 중 예외 발생: " + e.getMessage());
            e.printStackTrace();
            createSampleSeats(list, 36);
        } finally {
            DBUtil.closeResources(conn, ps, rs);
        }

        if (list.isEmpty()) {
            System.out.println("조회 결과가 없어 샘플 데이터 생성");
            createSampleSeats(list, 36);
        }

        return list;
    }

    @Override
    public List<ReadingReservationsVo> selectReservationsBySeatAndDate(int seatNo, LocalDate reserveDate) {
        List<ReadingReservationsVo> list = new ArrayList<>();
        String sql = "SELECT /*+ INDEX(r IDX_READING_RESERVATIONS_SEAT_DATE) */ " + 
                     "* FROM reading_reservations WHERE seat_no = ? AND reserve_date = ? " + 
                     "AND r_reserve_status = '예약완료' ORDER BY start_time";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, seatNo);
            ps.setDate(2, Date.valueOf(reserveDate));
            
            // 쿼리 타임아웃 설정
            ps.setQueryTimeout(5); // 5초 타임아웃
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                ReadingReservationsVo vo = new ReadingReservationsVo();
                vo.setrReserveNo(rs.getInt("r_reserve_no"));
                vo.setUserNo(rs.getInt("user_no"));
                vo.setSeatNo(rs.getInt("seat_no"));
                vo.setReserveDate(rs.getDate("reserve_date").toLocalDate());
                vo.setStartTime(rs.getTime("start_time").toLocalTime());
                vo.setEndTime(rs.getTime("end_time").toLocalTime());
                vo.setrReserveStatus(rs.getString("r_reserve_status"));
                list.add(vo);
            }
        } catch (SQLException e) {
            System.out.println("좌석 및 날짜별 예약 조회 중 예외 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtil.closeResources(conn, ps, rs);
        }

        return list;
    }

    // 샘플 데이터 생성 메소드 추가
    private void createSampleSeats(List<ReadingSeatsVo> list, int count) {
        for (int i = 1; i <= count; i++) {
            ReadingSeatsVo vo = new ReadingSeatsVo();
            vo.setSeatNo(i);
            vo.setSeatNumber(String.valueOf(i));
            vo.setIsAvailable("Y");
            vo.setRoomNo(1);
            list.add(vo);
        }
        System.out.println("샘플 데이터 " + count + "개 생성 완료");
    }
}