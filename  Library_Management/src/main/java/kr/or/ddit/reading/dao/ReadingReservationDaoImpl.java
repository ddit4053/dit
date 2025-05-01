package kr.or.ddit.reading.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import kr.or.ddit.util.DBUtil;
import kr.or.ddit.util.MybatisUtil;
import kr.or.ddit.vo.ReadingReservationsVo;

public class ReadingReservationDaoImpl implements ReadingReservationDao {
	 @Override
	
	 public int insertReservation(ReadingReservationsVo vo) {
	     String sql = "INSERT INTO reading_reservations "
	                + "(r_reserve_no, start_time, end_time, r_reserve_status, user_no, seat_no) "
	                + "VALUES (reading_res_seq.NEXTVAL, TO_DATE(?, 'YYYY-MM-DD HH24:MI'), TO_DATE(?, 'YYYY-MM-DD HH24:MI'), ?, ?, ?)";
	     try (Connection conn = DBUtil.getConnection();
	          PreparedStatement ps = conn.prepareStatement(sql)) {

	         ps.setString(1, vo.getStartTime());  // "2025-04-25 09:00" 형식이어야 함
	         ps.setString(2, vo.getEndTime());
	         ps.setString(3, vo.getRReserveStatus());
	         ps.setInt(4, vo.getUserNo());
	         ps.setInt(5, vo.getSeatNo());

	         return ps.executeUpdate();
	     } catch (Exception e) {
	         e.printStackTrace();
	     }
	     return 0;
	 

	    }

	    @Override
	    public List<ReadingReservationsVo> selectReservationsByUser(int userNo) {
	        List<ReadingReservationsVo> list = new ArrayList<>(); 
	        String sql = "SELECT * FROM reading_reservations WHERE user_no = ?";
	        try (Connection conn = DBUtil.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            ps.setInt(1, userNo);
	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	                ReadingReservationsVo vo = new ReadingReservationsVo();
	                vo.setRReserveNo(rs.getInt("r_reserve_no"));
	                vo.setStartTime(rs.getString("start_time"));
	                vo.setEndTime(rs.getString("end_time"));
	                vo.setRReserveStatus(rs.getString("r_reserve_status"));
	                vo.setUserNo(rs.getInt("user_no"));
	                vo.setSeatNo(rs.getInt("seat_no"));
	                list.add(vo);
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return list;
	    }

	    @Override
	    public List<ReadingReservationsVo> selectReservationsBySeat(int seatNo) {
	        List<ReadingReservationsVo> list = new ArrayList<>();
	        String sql = "SELECT * FROM reading_reservations WHERE seat_no = ?";
	        try (Connection conn = DBUtil.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            ps.setInt(1, seatNo);
	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	                ReadingReservationsVo vo = new ReadingReservationsVo();
	                vo.setRReserveNo(rs.getInt("r_reserve_no"));
	                vo.setStartTime(rs.getString("start_time"));
	                vo.setEndTime(rs.getString("end_time"));
	                vo.setRReserveStatus(rs.getString("r_reserve_status"));
	                vo.setUserNo(rs.getInt("user_no"));
	                vo.setSeatNo(rs.getInt("seat_no"));
	                list.add(vo);
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return list;
	    }
	    
	    @Override
	    public List<ReadingReservationsVo> selectReservationsByRoom(String roomName) {
	        List<ReadingReservationsVo> list = new ArrayList<>();
	        String sql = "SELECT r.seat_no, r.start_time, r.end_time " +
	                     "FROM reading_reservations r " +
	                     "JOIN reading_seats s ON r.seat_no = s.seat_no " +
	                     "JOIN reading_rooms rm ON s.room_no = rm.room_no " +
	                     "WHERE rm.room_name = ?";
	        try (Connection conn = DBUtil.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            ps.setString(1, roomName);
	            ResultSet rs = ps.executeQuery();
	            while (rs.next()) {
	                ReadingReservationsVo vo = new ReadingReservationsVo();
	                vo.setSeatNo(rs.getInt("seat_no"));
	                vo.setStartTime(rs.getString("start_time"));
	                vo.setEndTime(rs.getString("end_time"));
	                list.add(vo);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return list;
	    }
	    
	    @Override
	    public List<ReadingReservationsVo> selectByUserNo(int userNo) {
	        try (SqlSession session = MybatisUtil.getInstance()) {
	            return session.selectList("readingReservation.selectByUserNo", userNo);
	        }
	    

}
}
