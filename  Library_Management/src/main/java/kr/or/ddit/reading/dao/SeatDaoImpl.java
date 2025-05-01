package kr.or.ddit.reading.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import kr.or.ddit.util.DBUtil;
import kr.or.ddit.vo.ReadingReservationsVo;
import kr.or.ddit.vo.ReadingSeatsVo;

public class SeatDaoImpl implements ISeatDao {

    @Override
    public List<ReadingSeatsVo> selectAllSeats() {
        List<ReadingSeatsVo> list = new ArrayList<>();
        String sql = "SELECT * FROM reading_seats ORDER BY seat_no";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                ReadingSeatsVo vo = new ReadingSeatsVo();
                vo.setSeatNo(rs.getInt("seat_no"));
                vo.setSeatNumber(rs.getString("seat_number"));
                vo.setIsAvailable(rs.getString("is_available"));
                vo.setRoomNo(rs.getInt("room_no")); 
                list.add(vo);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    @Override
    public List<ReadingSeatsVo> selectSeatsByRoomName(String roomName) {
        List<ReadingSeatsVo> list = new ArrayList<>();
        
        // SQL 수정: JOIN을 사용하여 room_name으로 좌석 조회
        String sql = "SELECT s.* FROM reading_seats s "
                   + "JOIN reading_rooms r ON s.room_no = r.room_no "
                   + "WHERE r.room_name = ? ORDER BY s.seat_no";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, roomName);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                ReadingSeatsVo vo = new ReadingSeatsVo();
                vo.setSeatNo(rs.getInt("seat_no"));
                vo.setSeatNumber(rs.getString("seat_number"));
                vo.setIsAvailable(rs.getString("is_available"));
                vo.setRoomNo(rs.getInt("room_no"));
                list.add(vo);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            // 오류 발생 시 샘플 데이터 생성 (개발 중 테스트용)
            createSampleSeats(list, 36);
        }
        
        // 조회 결과가 없으면 샘플 데이터 생성
        if (list.isEmpty()) {
            createSampleSeats(list, 36);
        }
        
        return list;
    }
    
    // 샘플 좌석 데이터 생성 (오류나 결과 없을 때 사용)
    private void createSampleSeats(List<ReadingSeatsVo> list, int count) {
        for (int i = 1; i <= count; i++) {
            ReadingSeatsVo vo = new ReadingSeatsVo();
            vo.setSeatNo(i);
            vo.setSeatNumber(String.valueOf(i));
            vo.setIsAvailable("Y");
            vo.setRoomNo(1); // 기본값
            list.add(vo);
        }
    }
    
    @Override
    public List<ReadingReservationsVo> selectReservationsBySeatAndDate(int seatNo, LocalDate reserveDate) {
        List<ReadingReservationsVo> list = new ArrayList<>();
        
        String sql = "SELECT * FROM reading_reservations "
                   + "WHERE seat_no = ? AND reserve_date = ? AND r_reserve_status = '예약완료' "
                   + "ORDER BY start_time";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, seatNo);
            ps.setDate(2, Date.valueOf(reserveDate));
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                ReadingReservationsVo vo = new ReadingReservationsVo();
                vo.setrReserveNo(rs.getInt("r_reserve_no"));
                vo.setUserNo(rs.getInt("user_no"));
                vo.setSeatNo(rs.getInt("seat_no"));
                vo.setReserveDate(rs.getDate("reserve_date").toLocalDate());
                vo.setStartTime(rs.getTime("start_time").toLocalTime());
                vo.setEndTime(rs.getTime("end_time").toLocalTime());
                vo.setrReserveStatus(rs.getString("r_reserve_status"));
                
                // room_name이 컬럼에 있으면 가져오기 (없으면 예외 무시)
                try {
                    vo.setRoomName(rs.getString("room_name"));
                } catch (Exception e) {
                    // room_name 컬럼이 없을 경우 무시
                }
                
                list.add(vo);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return list;
    }
}