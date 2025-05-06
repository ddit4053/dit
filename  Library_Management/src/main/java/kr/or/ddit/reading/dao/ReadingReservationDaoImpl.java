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

public class ReadingReservationDaoImpl implements IReadingReservationDao {

    // 싱글톤 패턴 적용
    private static ReadingReservationDaoImpl instance = new ReadingReservationDaoImpl();
    
    private ReadingReservationDaoImpl() {
        // 생성자 private 설정
    }
    
    public static ReadingReservationDaoImpl getInstance() {
        return instance;
    }

    @Override
    public int insertReservation(ReadingReservationsVo vo) {
        Connection conn = null;
        PreparedStatement checkPs = null;
        PreparedStatement insertPs = null;
        ResultSet checkRs = null;
        int cnt = 0;
        
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false); // 트랜잭션 시작
            
            // 1. 중복 예약 체크 - 낙관적 락 방식
            String checkSql = 
                "SELECT COUNT(*) FROM reading_reservations " +
                "WHERE seat_no = ? AND reserve_date = ? AND r_reserve_status = '예약완료' " +
                "AND ((start_time <= ? AND end_time > ?) OR (start_time < ? AND end_time >= ?) OR (start_time >= ? AND end_time <= ?))";
            
            checkPs = conn.prepareStatement(checkSql);
            checkPs.setInt(1, vo.getSeatNo());
            checkPs.setDate(2, Date.valueOf(vo.getReserveDate()));
            
            // 시간 겹침 체크를 위한 파라미터 설정
            java.sql.Timestamp startTs = java.sql.Timestamp.valueOf(vo.getReserveDate().atTime(vo.getStartTime()));
            java.sql.Timestamp endTs = java.sql.Timestamp.valueOf(vo.getReserveDate().atTime(vo.getEndTime()));
            
            checkPs.setTimestamp(3, endTs);
            checkPs.setTimestamp(4, startTs);
            checkPs.setTimestamp(5, startTs);
            checkPs.setTimestamp(6, endTs);
            checkPs.setTimestamp(7, startTs);
            checkPs.setTimestamp(8, endTs);
            
            checkRs = checkPs.executeQuery();
            
            if (checkRs.next() && checkRs.getInt(1) > 0) {
                // 겹치는 예약이 존재
                conn.rollback();
                return 0;
            }
            
            // 2. 예약 삽입
            String insertSql = 
                "INSERT INTO reading_reservations (r_reserve_no, reserve_date, start_time, end_time, r_reserve_status, user_no, seat_no) " +
                "VALUES (reading_reservations_seq.NEXTVAL, ?, ?, ?, ?, ?, ?)";
            
            insertPs = conn.prepareStatement(insertSql);
            insertPs.setDate(1, Date.valueOf(vo.getReserveDate()));
            insertPs.setTimestamp(2, startTs);
            insertPs.setTimestamp(3, endTs);
            insertPs.setString(4, vo.getrReserveStatus());
            insertPs.setInt(5, vo.getUserNo());
            insertPs.setInt(6, vo.getSeatNo());
            
            cnt = insertPs.executeUpdate();
            
            conn.commit(); // 트랜잭션 커밋
            
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println("예약 삽입 중 예외 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtil.closeResources(conn, checkPs, checkRs);
            DBUtil.closeResources(null, insertPs, null);
        }
        
        return cnt;
    }

    @Override
    public List<ReadingReservationsVo> selectReservationsByUser(int userNo) {
        List<ReadingReservationsVo> list = new ArrayList<>();
        String sql = "SELECT r.*, rm.room_name " +
                     "FROM reading_reservations r " +
                     "JOIN reading_seats s ON r.seat_no = s.seat_no " +
                     "JOIN reading_rooms rm ON s.room_no = rm.room_no " +
                     "WHERE r.user_no = ? " +
                     "ORDER BY r.reserve_date, r.start_time";
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userNo);
            
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
                vo.setRoomName(rs.getString("room_name"));
                list.add(vo);
            }
        } catch (SQLException e) {
            System.out.println("사용자별 예약 조회 중 예외 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtil.closeResources(conn, ps, rs);
        }
        
        return list;
    }

    @Override
    public List<ReadingReservationsVo> selectReservationsBySeat(int seatNo) {
        List<ReadingReservationsVo> list = new ArrayList<>();
        String sql = "SELECT r.*, rm.room_name " +
                     "FROM reading_reservations r " +
                     "JOIN reading_seats s ON r.seat_no = s.seat_no " +
                     "JOIN reading_rooms rm ON s.room_no = rm.room_no " +
                     "WHERE r.seat_no = ? " +
                     "ORDER BY r.reserve_date, r.start_time";
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, seatNo);
            
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
                vo.setRoomName(rs.getString("room_name"));
                list.add(vo);
            }
        } catch (SQLException e) {
            System.out.println("좌석별 예약 조회 중 예외 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtil.closeResources(conn, ps, rs);
        }
        return list;
    }

    @Override
    public List<ReadingReservationsVo> selectAllReservations() {
        List<ReadingReservationsVo> list = new ArrayList<>();
        String sql = "SELECT r.*, rm.room_name " +
                     "FROM reading_reservations r " +
                     "JOIN reading_seats s ON r.seat_no = s.seat_no " +
                     "JOIN reading_rooms rm ON s.room_no = rm.room_no " +
                     "ORDER BY r.reserve_date, r.start_time";
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            
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
                vo.setRoomName(rs.getString("room_name"));
                list.add(vo);
            }
        } catch (SQLException e) {
            System.out.println("전체 예약 조회 중 예외 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtil.closeResources(conn, ps, rs);
        }
        return list;
    }

    @Override
    public List<ReadingReservationsVo> selectByUserNo(int userNo) {
        // 이미 구현된 selectReservationsByUser 메소드를 재사용
        return selectReservationsByUser(userNo);
    }

    @Override
    public List<ReadingReservationsVo> selectReservationsByRoomAndDate(String roomName, LocalDate reserveDate) {
        List<ReadingReservationsVo> list = new ArrayList<>();
        String sql = "SELECT r.*, rm.room_name " +
                     "FROM reading_reservations r " +
                     "JOIN reading_seats s ON r.seat_no = s.seat_no " +
                     "JOIN reading_rooms rm ON s.room_no = rm.room_no " +
                     "WHERE rm.room_name = ? AND r.reserve_date = ? AND r.r_reserve_status = '예약완료' " +
                     "ORDER BY r.start_time";
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, roomName);
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
                vo.setRoomName(rs.getString("room_name"));
                list.add(vo);
            }
        } catch (SQLException e) {
            System.out.println("열람실 및 날짜별 예약 조회 중 예외 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtil.closeResources(conn, ps, rs);
        }
        return list;
    }
    
    @Override
    public List<ReadingReservationsVo> selectReservationsByUserAndDate(int userNo, LocalDate date) {
        List<ReadingReservationsVo> list = new ArrayList<>();
        String sql = "SELECT r.*, rm.room_name " +
                     "FROM reading_reservations r " +
                     "JOIN reading_seats s ON r.seat_no = s.seat_no " +
                     "JOIN reading_rooms rm ON s.room_no = rm.room_no " +
                     "WHERE r.user_no = ? AND r.reserve_date = ? AND r.r_reserve_status IN ('예약완료', '이용중') " +
                     "ORDER BY r.start_time";
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userNo);
            ps.setDate(2, Date.valueOf(date));
            
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
                vo.setRoomName(rs.getString("room_name"));
                list.add(vo);
            }
        } catch (SQLException e) {
            System.out.println("사용자 및 날짜별 예약 조회 중 예외 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtil.closeResources(conn, ps, rs);
        }
        
        return list;
    }
    
    @Override
    public List<ReadingReservationsVo> selectReservationsBySeatAndDate(int seatNo, LocalDate reserveDate) {
        List<ReadingReservationsVo> list = new ArrayList<>();
        String sql = "SELECT r.*, rm.room_name " +
                     "FROM reading_reservations r " +
                     "JOIN reading_seats s ON r.seat_no = s.seat_no " +
                     "JOIN reading_rooms rm ON s.room_no = rm.room_no " +
                     "WHERE r.seat_no = ? AND r.reserve_date = ? AND r.r_reserve_status = '예약완료' " +
                     "ORDER BY r.start_time";
        
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
                vo.setRoomName(rs.getString("room_name"));
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
    
}