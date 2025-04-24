package kr.or.ddit.reading.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.or.ddit.vo.ReadingReservationsVo;
import kr.or.ddit.vo.ReadingSeatsVo;

public class ReadingSeatDao {

    private final Connection conn;

    public ReadingSeatDao(Connection conn) {
        this.conn = conn;
    }

    public int insertReservation(ReadingReservationsVo vo) throws Exception {
        String sql = "INSERT INTO reading_reservations (seat_no, user_no, start_time, end_time, r_reserve_status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, vo.getSeatNo());
            pstmt.setInt(2, vo.getUserNo());
            pstmt.setString(3, vo.getStartTime());
            pstmt.setString(4, vo.getEndTime());
            pstmt.setString(5, vo.getRReserveStatus());
            return pstmt.executeUpdate();
        }
    }

    public List<ReadingSeatsVo> getSeatsByRoom(String roomName) throws SQLException {
        List<ReadingSeatsVo> list = new ArrayList<>();
        String sql = "SELECT * FROM seat WHERE room_name = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, roomName);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ReadingSeatsVo vo = new ReadingSeatsVo();
                    vo.setSeatNo(rs.getInt("seat_no"));
                    vo.setSeatNumber(rs.getString("seat_number"));
                    vo.setIsAvailable(rs.getString("is_available"));
                    vo.setRoomNo(rs.getInt("room_no"));
                    list.add(vo);
                }
            }
        }

        return list;
    }
}