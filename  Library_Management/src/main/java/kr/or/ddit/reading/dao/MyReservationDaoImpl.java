package kr.or.ddit.reading.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.or.ddit.util.DBUtil;
import kr.or.ddit.vo.ReadingReservationsVo;

public class MyReservationDaoImpl implements IMyReservationDao {
    
    // 싱글톤 패턴 적용
    private static MyReservationDaoImpl instance = new MyReservationDaoImpl();
    
    private MyReservationDaoImpl() {
        // 생성자는 private으로 설정
    }
    
    public static MyReservationDaoImpl getInstance() {
        return instance;
    }

    @Override
    public List<ReadingReservationsVo> getReservationsByUser(int userNo) {
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
    public int updateReservationStatus(int rReserveNo) {
        // 새로운 DBUtil의 재시도 로직을 사용
        boolean success = DBUtil.executeWithRetry(conn -> {
            PreparedStatement ps = null;
            int cnt = 0;
            
            try {
                // SELECT FOR UPDATE로 먼저 락 획득
                String checkSql = "SELECT r_reserve_no FROM reading_reservations WHERE r_reserve_no = ? FOR UPDATE NOWAIT";
                ps = conn.prepareStatement(checkSql);
                ps.setInt(1, rReserveNo);
                
                ResultSet rs = ps.executeQuery();
                boolean exists = rs.next();
                rs.close();
                ps.close();
                
                if (!exists) {
                    return false;
                }
                
                // 이제 업데이트 진행
                String updateSql = "UPDATE reading_reservations SET r_reserve_status = '취소완료' WHERE r_reserve_no = ?";
                ps = conn.prepareStatement(updateSql);
                ps.setInt(1, rReserveNo);
                cnt = ps.executeUpdate();
                
                return cnt > 0;
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 3); // 최대 3번 재시도
        
        return success ? 1 : 0;
    }
}